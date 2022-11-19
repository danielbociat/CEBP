package Entities;

public class StockOffer implements Runnable
{
    // region fields
    protected final StockExchange stock_exchange;
    private static int count = 0;
    private int id;
    private String instrument;
    public int number_stocks;
    public float value_stock;
    public int seller_id;

    public enum Type{
        BUY, SELL, COMPLETED
    }
    private Type type;

    // endregion



    // region ctor

    public StockOffer(StockExchange stock_exchange) {
        this.stock_exchange = stock_exchange;
    }

    // endregion

    protected void completeOffer() {
        stock_exchange.removeOffer(this);
    }

    public void run() {
        while (true) {
            System.out.println(count);
            count++;

            if (type == Type.COMPLETED) {
                completeOffer();
                return;
            }

            // look for matching


            break;
        }
    }
}