package Entities;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StockOffer
{
    // region fields
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

    public StockOffer(Type t, double value) {
        this.id = this.count++;
        this.type = t;
        this.value_stock = value;
    }

    // endregion

    public void setToCompleted(){
        this.type = Type.COMPLETED;
        StockExchange.removeOffer(this);
    }

    public Type getType(){
        return this.type;
    }

    public double getValue(){
        return this.value_stock;
    }

    public boolean checkMatch(StockOffer so){
        return so.type != Type.COMPLETED && Math.abs(so.getValue() - this.getValue()) <= 0.05;
    }

    public String toString(){
        return "Offer " + this.id + " with price " + this.value_stock;
    }
}