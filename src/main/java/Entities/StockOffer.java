package Entities;

import java.sql.Timestamp;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StockOffer
{
    // region fields
    private Client client;
    private String instrument;
    private  int quantity;
    private final double value_stock;
    public final Lock matchLock = new ReentrantLock();
    private Timestamp timestamp;

    public enum Type{
        BUY, SELL, COMPLETED
    }
    public Type type;

    // endregion

    // region ctor

    public StockOffer(Type t, double value, String instrument, Client client, int quantity) {
        this.type = t;
        this.value_stock = value;
        this.instrument = instrument;
        this.client = client;
        this.quantity = quantity;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    // endregion

    public void setToCompleted(){
        this.type = Type.COMPLETED;
        StockExchange.removeOffer(this);
    }

    public Type getType(){
        return this.type;
    }
    public Client getClient(){
        return this.client;
    }
    public double getValue(){
        return this.value_stock;
    }

    public String getInstrument(){
        return this.instrument;
    }

    public boolean checkMatch(StockOffer so){
        return !client.equals(so.client) && instrument.equals(so.instrument) && so.type != Type.COMPLETED && Math.abs(so.value_stock - value_stock) <= 0.05;
    }

    public int getQuantityOfMatching(StockOffer targetOffer){
        int quant = Math.min(this.quantity, targetOffer.quantity);

        return quant;

    }

    public void updateQuantity(int quantity){
        this.quantity -= quantity;

        if(this.quantity == 0){
            this.setToCompleted();
        }
    }

    public String toString(){
        return String.format("[%s] OFFER PLACED --- Client: Client %d, Type: %s, Instrument: %s, Quantity: %d, ValuePerStock: %f",
                timestamp, client.getId(), type, instrument, quantity, value_stock);
    }
}