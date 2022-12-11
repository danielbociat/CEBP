package Entities;

import java.sql.Timestamp;

public class Transaction
{
    public int id;
    private Client buyer;
    private Client seller;
    private String instrument;
    private int quantity;
    private double valuePerStock;
    private double totalValue;
    private Timestamp timestamp;

    static int counter = 0;

    public Transaction(StockOffer of1, StockOffer of2, int quantity){
        this.id = this.counter++;

        if(of1.getType() == StockOffer.Type.BUY){
            this.buyer = of1.getClient();
            this.seller = of2.getClient();
        }
        else{
            this.buyer = of2.getClient();
            this.seller = of1.getClient();
        }

        this.instrument = of1.getInstrument();
        this.quantity = quantity;
        this.valuePerStock = of1.getValue();
        this.totalValue = quantity * valuePerStock;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String toString(){
        return String.format("[%s] TRANSACTION --- Seller: Client %d, Buyer: Client %d, Instrument: %s, Quantity: %d, ValuePerStock: %f, Total: %f",
                timestamp, seller.getId(), buyer.getId(), instrument, quantity, valuePerStock, totalValue);
    }
}