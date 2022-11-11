package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Before;
import org.junit.Test;

import src.main.model.Trade;
import src.main.model.Trade.Stock;
import src.main.model.Trade.Type;
import src.main.model.account.Account;
import src.main.model.account.Personal;
import src.main.model.account.TFSA;

public class BuyTests {
    
    Account[] accounts;
     
    @Before
    public void setup(){
        accounts = new Account[]{
            new Personal(1000),
            new TFSA(1000)
        };
    }

    @Test
    public void personalShares() {
        accounts[0].makeTrade(new Trade(Stock.AAPL, Type.MARKET_BUY, 15.649286, 5));
        assertEquals(accounts[0].getShares(Stock.AAPL), 5);
    }

    @Test
    public void ifsaShares(){
        accounts[1].makeTrade(new Trade(Stock.FB, Type.MARKET_BUY, 10.3745, 3));
        assertEquals(accounts[1].getShares(Stock.FB), 3);
    }

    @Test
    public void lowBalanceTest(){
        boolean mtb = accounts[0].makeTrade(new Trade(Stock.FB, Type.MARKET_BUY, 2000, 3));
        assertFalse(mtb);
    }

    @Test
    public void noSuchStock(){
       boolean mt = accounts[0].makeTrade(new Trade(Stock.TOPSECRET, Type.MARKET_BUY, 20, 10));
       assertFalse(mt);
    }

    @Test
    public void noSuchStockTFSA(){
        accounts[1].makeTrade(new Trade(Stock.TOPSECRET, Type.MARKET_BUY, 20, 10));
    }

    @Test
    public void afterPurchasePersonal(){
        accounts[0].makeTrade(new Trade(Stock.AAPL, Type.MARKET_BUY, 50, 3));
        assertEquals(950, accounts[0].getFunds());
    }

    @Test
    public void afterPurchaseTFSA(){
        accounts[1].makeTrade(new Trade(Stock.FB, Type.MARKET_BUY, 100, 5));
        assertEquals(899, accounts[1].getFunds());
    }

}
