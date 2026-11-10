
public class Stock {

    String symbol;
    String name;
    double price;

    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    public void displayStock() {
        System.out.printf("%-10s %-20s Rs. %.2f%n",
                symbol, name, price);
    }
}
