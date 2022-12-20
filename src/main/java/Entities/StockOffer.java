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
    private int id;

    static int total_id = 0;
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
        this.id = total_id++;
    }

    // endregion

    public void setToCompleted(){
        this.type = Type.COMPLETED;
        StockExchange.removeOffer(this);
    }

    public Type getType(){
        return this.type;
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

    public int compareTo(StockOffer so){
        int string_comp = so.instrument.compareTo(instrument), time_comp = so.timestamp.compareTo(timestamp), id_comp = so.id - id;
        if(string_comp != 0)
            return string_comp;

        if(time_comp != 0)
            return time_comp;

        return id_comp;
    }

    public StockOfferMessage getStockOfferMessage() {
        return new StockOfferMessage(client.getId(), instrument, quantity, value_stock, timestamp, id);
    }
}