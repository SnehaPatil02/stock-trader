package src.main.model.account;

import javax.swing.event.TreeSelectionEvent;

import src.main.model.Trade;
import src.main.utils.Color;

public class Personal extends Account{
    
    public static final double SELL_FEE = 0.05;

    public Personal(double funds){
        super(funds);
    }

    public Personal(Personal source){
        super(source);
    }

    public String toString() {
        return this.displayPortofolio() + Color.RESET +
        "\n  Funds Left\t" + Color.GREEN + "$" + round(this.getFunds()) + Color.RESET;
    }

    @Override
    public boolean makeTrade(Trade trade) {
        if(Trade.Type.MARKET_BUY == trade.getType()){
            if(this.getFunds() < trade.getprice() || !(this.portfolio.containsKey(trade.getStock()))){
                return false;
            }
        this.setFunds(this.getFunds() - trade.getprice());
        int numstock = this.portfolio.get(trade.getStock());
        this.portfolio.put(trade.getStock(), numstock + trade.getShares());
        } else if(Trade.Type.MARKET_BUY == trade.getType()){
            this.setFunds(this.getFunds() + (trade.getprice() - (trade.getprice() * 0.05)));
            int noOfStock = this.portfolio.get(trade.getStock());
            if(noOfStock < trade.getShares()){
                return false;
            }
            this.portfolio.put(trade.getStock(), noOfStock - trade.getShares());
        } else {
            System.out.println("Do Nothing :)");
        }
        return true;
    }
}
