package Entities;

import java.sql.Time;
import java.sql.Timestamp;

public class StockOfferMessage {
    public int clientId;
    public String instrument;
    public int quantity;
    public double value_stock;
    public Timestamp timestamp;
    public int id;

    public StockOfferMessage(int clientId, String instrument, int quantity, double value_stock, Timestamp timestamp, int id)
    {
        this.clientId = clientId;
        this.instrument = instrument;;
        this.quantity = quantity;
        this.value_stock = value_stock;
        this.timestamp = timestamp;
        this.id = id;
    }

    public String toString(){
        return String.format("[%s] OFFER PLACED --- Client: Client %d, Instrument: %s, Quantity: %d, ValuePerStock: %f",
                timestamp, clientId, instrument, quantity, value_stock);
    }
}
