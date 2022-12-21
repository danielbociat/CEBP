package Entities;

import java.util.ArrayList;

public class Client {

    // region fields

    private int id;
    private static int count = 0;
    private ArrayList<StockOffer> offers;

    // endregion

    // region ctor

    public Client() {
        this.id = count++;
    }

    // endregion

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
            String queue = "offers.sell";
            if(offer.getType() == StockOffer.Type.BUY)
                queue = "offers.buy";

            RabbitMQSender.SendMessageToOfferQueue(queue, offer);

            StockExchange.addOffer(offer);
        }
    }

    public String toString(){
        return String.valueOf(id);
    }
}
