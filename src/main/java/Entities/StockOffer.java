package Entities;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StockOffer
{
    // region fields
    private static int count = 0;
    private int id;
    private Client owner;
    private String instrument;
    //private int number_stocks;
    private final double value_stock;
    public final Lock matchLock = new ReentrantLock();

    public enum Type{
        BUY, SELL, COMPLETED
    }
    public Type type;

    // endregion

    // region ctor

    public StockOffer(Type t, double value, String instrument, Client owner) {
        this.id = count++;
        this.type = t;
        this.value_stock = value;
        this.instrument = instrument;
        this.owner = owner;
    }

    // endregion

    public void setToCompleted(){
        this.type = Type.COMPLETED;
        StockExchange.removeOffer(this);
    }

    public Type getType(){
        return this.type;
    }
    public Client getOwner(){
        return this.owner;
    }
    public double getValue(){
        return this.value_stock;
    }

    public String getInstrument(){
        return this.instrument;
    }

    public boolean checkMatch(StockOffer so){
        return !owner.equals(so.owner) && instrument.equals(so.instrument) && so.type != Type.COMPLETED && Math.abs(so.value_stock - value_stock) <= 0.05;
    }

    public String toString(){
        return "Client: " + owner + ", Type:" + type + ", Instrument: " + instrument;
    }
}