package Entities;

import java.sql.Timestamp;

public class Transaction
{
    private int id;
    private int buyerId;
    private int sellerId;
    private String instrument;
    private int quantity;
    private double valuePerStock;
    private double totalValue;
    private Timestamp timestamp;

    private static int counter = 0;

    public Transaction(StockOfferMessage sellOffer, StockOfferMessage buyOffer, int quantity){
        this.id = this.counter++;
        this.buyerId = buyOffer.clientId;
        this.sellerId = sellOffer.clientId;
        this.instrument = sellOffer.instrument;
        this.quantity = quantity;
        this.valuePerStock = sellOffer.value_stock;
        this.totalValue = quantity * valuePerStock;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String toString(){
        return String.format("[%s] TRANSACTION %d --- Buyer: Client %d, Seller: Client %d, Instrument: %s, Quantity: %d, ValuePerStock: %f, Total: %f",
                timestamp, id, buyerId, sellerId, instrument, quantity, valuePerStock, totalValue);
    }

}