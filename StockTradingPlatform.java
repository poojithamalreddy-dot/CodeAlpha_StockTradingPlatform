
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

public class StockTradingPlatform {

    static ArrayList<Stock> market = new ArrayList<>();
    static ArrayList<Transaction> transactions =
            new ArrayList<>();

    static User user;

    static BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        // Add sample stocks
        market.add(new Stock("TCS", "Tata Consultancy", 500));
        market.add(new Stock("INFY", "Infosys", 400));
        market.add(new Stock("WIPRO", "Wipro", 300));
        market.add(new Stock("HDFC", "HDFC Bank", 600));
        market.add(new Stock("RELIANCE", "Reliance", 700));

        System.out.println("===== STOCK TRADING PLATFORM =====");

        System.out.print("Enter your name: ");
        String name = br.readLine().trim();

        if (name.isEmpty()) {
            name = "User";
        }

        user = new User(name, 10000);

        System.out.println("Welcome, " + user.name + "!");
        System.out.println("Starting Balance: Rs. 10000");

        int choice = 0;

        do {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stocks");
            System.out.println("3. Sell Stocks");
            System.out.println("4. View Portfolio");
            System.out.println("5. Check Balance");
            System.out.println("6. View Profit/Loss");
            System.out.println("7. View Transactions");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(br.readLine());

                switch (choice) {

                    case 1:
                        viewMarket();
                        break;

                    case 2:
                        buyStock();
                        break;

                    case 3:
                        sellStock();
                        break;

                    case 4:
                        user.displayPortfolio();
                        break;

                    case 5:
                        checkBalance();
                        break;

                    case 6:
                        calculateProfitLoss();
                        break;

                    case 7:
                        viewTransactions();
                        break;

                    case 8:
                        System.out.println("Thank you for using "
                                + "Stock Trading Platform!");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                choice = 0;
            }

        } while (choice != 8);
    }

    // Display available stocks
    static void viewMarket() {

        System.out.println("\n===== STOCK MARKET =====");

        System.out.printf("%-10s %-20s %s%n",
                "Symbol", "Company", "Price");

        for (Stock stock : market) {
            stock.displayStock();
        }
    }

    // Find stock using its symbol
    static Stock findStock(String symbol) {

        for (Stock stock : market) {

            if (stock.symbol.equalsIgnoreCase(symbol)) {
                return stock;
            }
        }

        return null;
    }

    // Buy stocks
    static void buyStock() throws IOException {

        viewMarket();

        System.out.print("\nEnter stock symbol: ");
        String symbol = br.readLine().trim();

        Stock stock = findStock(symbol);

        if (stock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity: ");

        try {
            int quantity = Integer.parseInt(br.readLine());

            if (quantity <= 0) {
                System.out.println("Quantity must be positive.");
                return;
            }

            double total = stock.price * quantity;

            if (total > user.balance) {
                System.out.println("Insufficient balance!");
                return;
            }

            user.balance -= total;

            int oldQuantity = user.portfolio.getOrDefault(
                    stock.symbol, 0);

            user.portfolio.put(
                    stock.symbol, oldQuantity + quantity);

            transactions.add(new Transaction(
                    "BUY", stock.symbol, quantity, stock.price));

            System.out.println("Stock purchased successfully!");
            System.out.printf("Total Amount: Rs. %.2f%n", total);

        } catch (NumberFormatException e) {
            System.out.println("Enter a valid quantity.");
        }
    }

    // Sell stocks
    static void sellStock() throws IOException {

        user.displayPortfolio();

        System.out.print("\nEnter stock symbol to sell: ");
        String symbol = br.readLine().trim().toUpperCase();

        Stock stock = findStock(symbol);

        if (stock == null) {
            System.out.println("Stock not found!");
            return;
        }

        int owned = user.portfolio.getOrDefault(symbol, 0);

        if (owned == 0) {
            System.out.println("You do not own this stock.");
            return;
        }

        System.out.print("Enter quantity to sell: ");

        try {
            int quantity = Integer.parseInt(br.readLine());

            if (quantity <= 0 || quantity > owned) {
                System.out.println("Invalid quantity!");
                return;
            }

            double total = stock.price * quantity;

            user.balance += total;

            int remaining = owned - quantity;

            if (remaining == 0) {
                user.portfolio.remove(symbol);
            } else {
                user.portfolio.put(symbol, remaining);
            }

            transactions.add(new Transaction(
                    "SELL", symbol, quantity, stock.price));

            System.out.println("Stock sold successfully!");
            System.out.printf("Amount Received: Rs. %.2f%n", total);

        } catch (NumberFormatException e) {
            System.out.println("Enter a valid quantity.");
        }
    }

    // Display available balance
    static void checkBalance() {

        System.out.println("\n===== ACCOUNT BALANCE =====");

        System.out.println("User: " + user.name);

        System.out.printf("Available Balance: Rs. %.2f%n",
                user.balance);
    }

    // Calculate portfolio value and profit/loss
    static void calculateProfitLoss() {

        double portfolioValue = 0;

        for (Stock stock : market) {

            int quantity = user.portfolio.getOrDefault(
                    stock.symbol, 0);

            portfolioValue += quantity * stock.price;
        }

        double totalValue = user.balance + portfolioValue;

        double profitLoss = totalValue - user.initialBalance;

        System.out.println("\n===== PROFIT / LOSS REPORT =====");

        System.out.printf("Available Balance: Rs. %.2f%n",
                user.balance);

        System.out.printf("Portfolio Value: Rs. %.2f%n",
                portfolioValue);

        System.out.printf("Total Account Value: Rs. %.2f%n",
                totalValue);

        System.out.printf("Profit / Loss: Rs. %.2f%n",
                profitLoss);

        if (profitLoss > 0) {
            System.out.println("Status: PROFIT");
        } else if (profitLoss < 0) {
            System.out.println("Status: LOSS");
        } else {
            System.out.println("Status: NO PROFIT, NO LOSS");
        }
    }

    // Display transaction history
    static void viewTransactions() {

        System.out.println("\n===== TRANSACTION HISTORY =====");

        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        for (Transaction t : transactions) {
            t.displayTransaction();
        }
    }
}
