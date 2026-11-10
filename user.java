
import java.util.HashMap;
import java.util.Map;

public class User {

    String name;
    double balance;
    double initialBalance;

    HashMap<String, Integer> portfolio = new HashMap<>();

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.initialBalance = balance;
    }

    public void displayPortfolio() {

        System.out.println("\n===== MY PORTFOLIO =====");

        if (portfolio.isEmpty()) {
            System.out.println("No stocks purchased yet.");
            return;
        }

        for (Map.Entry<String, Integer> entry :
                portfolio.entrySet()) {

            System.out.println("Stock: " + entry.getKey()
                    + " | Quantity: " + entry.getValue());
        }
    }
}
