package Entities;

import java.sql.Timestamp;

public class Transaction
{
    public int id;
    private int buyerId;
    private int sellerId;
    private String instrument;
    private int quantity;
    private double valuePerStock;
    private double totalValue;
    private Timestamp timestamp;

    static int counter = 0;

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
        return String.format("[%s] TRANSACTION --- Seller: Client %d, Buyer: Client %d, Instrument: %s, Quantity: %d, ValuePerStock: %f, Total: %f",
                timestamp, sellerId, buyerId, instrument, quantity, valuePerStock, totalValue);
    }

}