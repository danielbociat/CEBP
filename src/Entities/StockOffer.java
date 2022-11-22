package Entities;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StockOffer implements Runnable
{
    // region fields
    protected final StockExchange stock_exchange;
    private static int count = 0;
    public int id;
    private String instrument;
    private int number_stocks;
    private double value_stock;
    private int seller_id;

    public final Lock matchLock = new ReentrantLock();
    public final Lock setCompleteLock = new ReentrantLock();

    public enum Type{
        BUY, SELL, COMPLETED
    }
    public Type type;

    // endregion



    // region ctor

    public StockOffer(StockExchange stock_exchange, Type t, double value) {
        this.id = this.count++;
        this.stock_exchange = stock_exchange;
        this.type = t;
        this.value_stock = value;
    }

    // endregion

    protected void completeOffer() {
        stock_exchange.removeOffer(this);
    }
    protected void tryMatch() {
        stock_exchange.matchOffer(this);
    }

    public void setToCompleted(){
        this.type = Type.COMPLETED;
    }

    public Type getType(){
        return this.type;
    }

    public double getValue(){
        return this.value_stock;
    }

    public boolean checkMatch(StockOffer so){
        return Math.abs(so.getValue() - this.getValue()) <= 0.05;
    }

    public String toString(){
        return "Offer " + this.id + " with price " + this.value_stock;
    }

    public void run() {
        while (true) {
            if (type == Type.COMPLETED) {
                this.completeOffer();
                return;
            }

            // look for matching
            if(type == Type.BUY)
                this.tryMatch();

        }
    }
}