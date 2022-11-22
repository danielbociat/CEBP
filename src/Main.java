import Entities.*;

public class Main {
    public static void main(String[] args){
        StockExchange stock_exchange = new StockExchange(200);
        stock_exchange.start();
    }
}

