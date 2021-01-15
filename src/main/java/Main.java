public class Main {
    public static void main(String[] args) {
        System.out.println("\t[...trying to calculate available seats...]\n");
        Service.calculateSeats();
        System.out.println("\tTotally we have " + Service.getAllSeats() + " available seats." +
                " Here are the available plains and seats:\n\t" + Service.printPlains());
    }
}
