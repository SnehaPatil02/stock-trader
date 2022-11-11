package src.main.model.account;

import src.main.model.Trade;
import src.main.model.Trade.Stock;
import src.main.utils.Color;

public class TFSA extends Account {

    public TFSA (double funds){
        super(funds);
        this.portfolio.put(Stock.TOPSECRET, 0);
    }

    public TFSA (TFSA source){
        super(source);
    }

    public String toString() {
        return "\n  Stock\t\t"  + Color.RESET + "Shares" +
        "\n\n" + this.displayPortofolio() + Color.RESET +
        "\n  Funds Left\t" + Color.GREEN + "$" + round(this.getFunds()) + Color.RESET;
    }

    @Override
    public boolean makeTrade(Trade trade) {
        if(Trade.Type.MARKET_BUY == trade.getType()){
                if(this.getFunds() < trade.getprice() || !(this.portfolio.containsKey(trade.getStock()))){
                    return false;
                }
           this.setFunds(this.getFunds() - (trade.getprice() + (trade.getprice() * 0.01)));
            this.portfolio.put(trade.getStock(), this.portfolio.get(trade.getStock()) + trade.getShares());
        } else if(Trade.Type.MARKET_SELL == trade.getType()){
            this.setFunds(this.getFunds() + (trade.getprice() - (trade.getprice() * 0.01)));
            int noOfShares = this.portfolio.get(trade.getStock()) - trade.getShares();
            this.portfolio.put(trade.getStock(), noOfShares);
        } else {
            System.out.println("Do Nothing :)");
        }
        return true;
    }

   
        
        
    
    
}
