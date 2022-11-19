import Entities.*;

public class Main {
    public static void main(String[] args){
        StockExchange stock_exchange = new StockExchange(100);
        stock_exchange.start();
    }
}

