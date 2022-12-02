package Entities;

import java.util.ArrayList;

public class Client {
    private int id;
    private static int count = 0;
    private ArrayList<StockOffer> offers;

    public Client() {
        this.id = count++;
    }

    public int getId(){
        return this.id;
    }

    public ArrayList<StockOffer> getOffers(){
        return this.offers;
    }
    public void setOffers(ArrayList<StockOffer> offers){
        this.offers = offers;
    }

    public boolean equals(Client c){
        return this.id == c.getId();
    }

    public void placeOffers(){
        for(StockOffer offer : offers){
            StockExchange.addOffer(offer);
        }
    }

    public String toString(){
        return String.valueOf(id);
    }
}
