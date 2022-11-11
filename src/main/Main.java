package src.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import src.main.model.Trade;
import src.main.model.Trade.Stock;
import src.main.model.Trade.Type;
import src.main.model.account.Account;
import src.main.model.account.Personal;
import src.main.model.account.TFSA;
import src.main.utils.Color;

public class Main {

    static Account account; 
    static final double INITIAL_DEPOSIT = 4000;
    static Scanner scanner = new Scanner(System.in);
  
    public static void main(String[] args) {  
         
        explainApp();
        String s = accountChoice();

        if(s.equals("a")){
             account = new Personal(INITIAL_DEPOSIT);
        }else {
            account = new TFSA(INITIAL_DEPOSIT);
        }
        initialBalance();
        for (int j = 1; j <= 10; j++) {
            try{ 
        
        displayPrices(j);
        String str = buyOrSell();
        
        if(str.equalsIgnoreCase("Do Nothing")){
            continue;
        }

        String stock = chooseStock();
        int i = numShares(str);
        Type mt = Type.MARKET_SELL;
        if(str.equalsIgnoreCase("buy")){
            mt = Type.MARKET_BUY;
        }

        Trade trade = new Trade(Stock.valueOf(stock), mt, Double.parseDouble(getPrice(Stock.valueOf(stock), j)), i);
        boolean res =  account.makeTrade(trade);
        if(res == true){
        tradeStatus("successful");
        }else {
            tradeStatus("failed");
        }
    }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    }

    public static void explainApp() {
        System.out.print(Color.BLUE + "\n - PERSONAL: ");
        System.out.println(Color.YELLOW + "Every sale made in a personal account is charged a 5% fee.");
        System.out.print(Color.BLUE + "\n - TFSA: ");
        System.out.println(Color.YELLOW + "Every trade (buy/sell) made from a TFSA is charged a 1% fee.\n");
        System.out.println(Color.BLUE + " - Neither account has a limit on the amount of trades that can be made." + Color.RESET);
    }
    
    public static void initialBalance() {
        System.out.print("\n\n  You created a " + Color.YELLOW + account.getClass().getSimpleName() + Color.RESET + " account. ");
        System.out.println(" Your account balance is " + Color.GREEN + "$" + INITIAL_DEPOSIT + Color.RESET);
        System.out.print("\n  Enter anything to start trading: ");
        scanner.nextLine();
    }


    public static String accountChoice() {
        System.out.print("\n  Respectively, type 'a' or 'b' to create a Personal account or TFSA: ");
        String choice = scanner.nextLine();
        while (!choice.equals("a") && !choice.equals("b")) {
            System.out.print("  Respectively, type 'a' or 'b' to create a Personal account or TFSA: ");
            choice = scanner.nextLine();
        }
        return choice;
    }
    
    
    public static String buyOrSell() {
        System.out.print("\n\n  Would you like to 'buy' or 'sell' or 'Do nothing': ");
        String choice = scanner.nextLine();
        while (!choice.equalsIgnoreCase("buy") && !choice.equalsIgnoreCase("sell") && !choice.equalsIgnoreCase("Do nothing"))  {
            System.out.print("  Would you like to 'buy' or 'sell' or 'Do nothing': ");
            choice = scanner.nextLine();
        }
        return choice;
    }

    public static String doNothing(){
        return "Do Nothing :)";
    }

    public static String chooseStock() {
        System.out.print("  Choose a stock: ");
        String stock = scanner.nextLine(); 
        while (!stock.equals("AAPL") && !stock.equals("FB") && !stock.equals("GOOG") && !stock.equals("TSLA") ) {
            System.out.print("  Choose a stock: ");
            stock = scanner.nextLine();
        }
        return stock;
    }

    public static int numShares(String choice) {
        System.out.print("  Enter the number of shares you'd like to " + choice + ": ");
        int shares = scanner.nextInt(); 
        scanner.nextLine(); //throwaway nextLine
        while (shares <= 0) {
            System.out.print("  Enter the number of shares you'd like to " + choice + ": ");
            shares = scanner.nextInt();
            scanner.nextLine(); //throwaway nextLine

        }
        return shares;
    }
    
    // TODO
    public static void displayPrices(int day) {
    
            
        System.out.println("\n\n\t  DAY " + day + " PRICES\n");
     
        System.out.println("  " + Color.BLUE + Stock.AAPL + "\t\t" + Color.GREEN + getPrice(Stock.AAPL, day));
        System.out.println("  " + Color.BLUE + Stock.FB + "\t\t" + Color.GREEN + getPrice(Stock.FB, day));
        System.out.println("  " + Color.BLUE + Stock.GOOG + "\t\t" + Color.GREEN + getPrice(Stock.GOOG, day));
        System.out.println("  " + Color.BLUE + Stock.TSLA + "\t\t" + Color.GREEN + getPrice(Stock.TSLA, day) + Color.RESET);
            
        
        
    }
    
    public static void tradeStatus(String result) {
        System.out.println("\n  The trade was " + (result.equals("successful") ? Color.GREEN : Color.RED) + result + Color.RESET + ". Here is your portfolio:");
        System.out.println(account);
        System.out.print("\n  Press anything to continue");
        scanner.nextLine();
    }
    
    
    // TODO
    public static String getPrice(Stock stock, int day) {
        Path path = getPath(stock.name());
        try {
        FileInputStream fis = new FileInputStream(path.toFile());
        Scanner scan = new Scanner(fis);
        scan.nextLine();
        while(scan.hasNextLine()){
            String values[] = scan.nextLine().split(",");
            if(Integer.parseInt(values[0]) == day){
                return values[1];
            }
        }
            
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return "Null";
    }


    public static Path getPath(String stock) {
       return Paths.get("C:/Users/HP/Downloads/Java-Bootcamp-Resources-main/Module 2 - Object Oriented Programming/Capstone/starter/stock-trader/src/main/data/"+ stock + ".csv");
    }
    //


}
