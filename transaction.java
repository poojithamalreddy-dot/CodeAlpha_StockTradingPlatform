
public class Transaction {

    String type;
    String symbol;
    int quantity;
    double price;

    public Transaction(String type, String symbol,
                       int quantity, double price) {

        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
    }

    public void displayTransaction() {

        System.out.println(type + " | "
                + symbol + " | Quantity: "
                + quantity + " | Price: Rs. "
                + price);
    }
}
