package src.main.model.account;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import src.main.model.Trade;
import src.main.model.Trade.Stock;

public abstract class Account {

    protected Map<Stock, Integer> portfolio;
    private double funds;

   

    public Account( double funds){
        this.funds = funds;
        this.portfolio = new HashMap<Stock, Integer>();
        this.portfolio.put(Stock.AAPL,  0);
        this.portfolio.put(Stock.FB, 0);
        this.portfolio.put(Stock.GOOG, 0);
        this.portfolio.put(Stock.TSLA, 0);
    }

    public Account(Account source){
        this.portfolio = copyMap(source.portfolio);
        this.funds = source.funds;
    }

   private Map<Stock, Integer> copyMap(Map<Stock, Integer> map){
    return map.entrySet().stream() 
    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
   }

    public void setPortfolio(HashMap<Stock, Integer> portfolio) {
        this.portfolio = portfolio;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public abstract boolean makeTrade(Trade trade);

    public Integer getShares(Stock stock){
        return this.portfolio.get(stock);
    }

   public String displayPortofolio(){
    String  out = "Stock\tValues\n";
    
    for (Map.Entry<Stock, Integer> entry : this.portfolio.entrySet()) {
        
        out = out + entry.getKey() + " \t " + entry.getValue() + "\n";
    }
    return out;
   }

    protected double round(double amount) {
        DecimalFormat formatter = new DecimalFormat("#.##");
        return Double.parseDouble(formatter.format(amount));
    }

}
