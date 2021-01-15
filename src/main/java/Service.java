import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Service {
    private static Map<String, Integer> plains;

    private Service() {}

    public static void calculateSeats() {
        try {
            setPlainsAvailable();
        } catch (SQLException exception) {
            System.out.println("[...oops, an error occurred...]");
        }
    }

    public static int getAllSeats() {
        int allSeats = 0;
        for (int seat : plains.values()) {
            allSeats += seat;
        }
        return allSeats;
    }

    public static void setPlainsAvailable() throws SQLException {
        plains = new HashMap<>();
        DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator();
        try (Connection connection = databaseCommunicator.getConnection()){
            plains = databaseCommunicator.getPlainsAvailable(connection);
        }
    }

    public static String printPlains() {
        String plainsToPrint = "";
        for (Map.Entry<String, Integer> plain : plains.entrySet()) {
            plainsToPrint += plain.getKey() + ":\t" + plain.getValue() + "\n\t";
        }
        return plainsToPrint;
    }
}
