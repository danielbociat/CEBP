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

    public Transaction(StockOfferMessage of1, StockOfferMessage of2, int quantity){
        this.id = this.counter++;
        if(of1.type == StockOffer.Type.BUY){
            this.buyerId = of1.clientId;
            this.sellerId = of2.clientId;
        }
        else{
            this.buyerId = of2.clientId;
            this.sellerId = of1.clientId;
        }
        this.instrument = of1.instrument;
        this.quantity = quantity;
        this.valuePerStock = of1.value_stock;
        this.totalValue = quantity * valuePerStock;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String toString(){
        return String.format("[%s] TRANSACTION %d --- Buyer: Client %d, Seller: Client %d, Instrument: %s, Quantity: %d, ValuePerStock: %f, Total: %f",
                timestamp, id, buyerId, sellerId, instrument, quantity, valuePerStock, totalValue);
    }

}