import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DatabaseCommunicator {
    private static final String URL = "jdbc:postgresql:postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "mylittlepony";
    private static final String GET_PLAINS = "SELECT\n" +
            "    plains_to_pilots.plain_id,\n" +
            "    count(plains_to_pilots.pilot_id) pilots,\n" +
            "       plains.model,\n" +
            "       plains.seats\n" +
            "FROM plains_to_pilots\n" +
            "LEFT JOIN plains on plains.plain_id = plains_to_pilots.plain_id\n" +
            "WHERE plains_to_pilots.plain_id IN (SELECT plains_to_pilots.plain_id\n" +
            "    FROM plains_to_pilots\n" +
            "    GROUP BY plains_to_pilots.plain_id HAVING mod(count(*),2) != 1)\n" +
            "GROUP BY plains_to_pilots.plain_id, plains.model, plains.seats\n" +
            "ORDER BY plains_to_pilots.plain_id;";

    public Connection getConnection() {
        Connection connection = null;
        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);
        try {
            connection = DriverManager.getConnection(URL, properties);
        } catch (SQLException exception) {
            System.out.println("\t[...couldn't get a connection...]");
        }
        return connection;
    }

    public Map<String, Integer> getPlainsAvailable(Connection connection) {
        Map<String, Integer> plains = new HashMap<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(GET_PLAINS);
            while (result.next()) {
                String model = result.getString(3);
                int seats = result.getInt(4);
                plains.put(model, seats);
            }
        } catch (SQLException exception) {
            System.out.println("[...something went wrong...]");
            exception.printStackTrace();
        }
        return plains;
    }
}
