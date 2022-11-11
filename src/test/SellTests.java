package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import src.main.model.Trade;
import src.main.model.Trade.Stock;
import src.main.model.Trade.Type;
import src.main.model.account.Account;
import src.main.model.account.Personal;
import src.main.model.account.TFSA;

public class SellTests {
    
    Account[] accounts;

    @Before
    public void setup(){
        accounts = new Account[] {
            new Personal(1000),
            new TFSA(1000)
        };
    }

    @Test
    public void personalShares(){
        accounts[0].makeTrade(new Trade(Stock.GOOG, Type.MARKET_SELL, 50, 5));
        assertEquals(accounts[0].getShares(Stock.GOOG), 2);
    }

    @Test
    public void TFSAShares(){
        accounts[1].makeTrade(new Trade(Stock.TSLA, Type.MARKET_SELL, 50, 4));
        assertEquals(accounts[1].getShares(Stock.TSLA), 1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void insufficientShares(){
        accounts[0].makeTrade(new Trade (Stock.FB, Type.MARKET_SELL, 100, 5));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void afterSalePersonal(){
        accounts[0].makeTrade(new Trade(Stock.AAPL, Type.MARKET_SELL, 50, 2));
        assertEquals(1052.5, accounts[0].getFunds());
    }

    @Test
    public void afterSaleTFSA(){
        accounts[1].makeTrade(new Trade(Stock.GOOG, Type.MARKET_SELL, 60, 3));
        assertEquals(1059.4, accounts[1].getFunds());
    }

}
