package Entities;

import java.sql.Timestamp;

public class StockOfferMessage {
    public int clientId;
    public String instrument;
    public int quantity;
    public double value_stock;
    public Timestamp timestamp;
    public int id;
    public StockOffer.Type type;

    public StockOfferMessage(int clientId, String instrument, int quantity, double value_stock, Timestamp timestamp, int id, StockOffer.Type type)
    {
        this.clientId = clientId;
        this.instrument = instrument;;
        this.quantity = quantity;
        this.value_stock = value_stock;
        this.timestamp = timestamp;
        this.id = id;
        this.type = type;
    }

    public String toString(){
        return String.format("[%s] OFFER PLACED --- Id: %d, Client: Client %d, Instrument: %s, Type: %s, Quantity: %d, ValuePerStock: %f",
                timestamp, id, clientId, instrument, type, quantity, value_stock);
    }
}
