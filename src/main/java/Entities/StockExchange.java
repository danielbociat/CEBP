package Entities;
import java.util.concurrent.*;

public class StockExchange {
    public static ConcurrentSkipListSet<StockOffer> offers = new ConcurrentSkipListSet<>(new StockOfferComparator());

    public static void matchOffer(StockOffer stock_offer){
        for(StockOffer targetOffer : offers){
            if(stock_offer.matchLock.tryLock()){

                if(stock_offer.type == StockOffer.Type.COMPLETED) {
                    stock_offer.matchLock.unlock();
                    break;
                }

                try {
                    if (targetOffer.getType() != stock_offer.getType() && stock_offer.checkMatch(targetOffer)) {
                        if (targetOffer.matchLock.tryLock()) {
                            try {
                                int quantity = stock_offer.getQuantityOfMatching(targetOffer);

                                if (stock_offer.type == StockOffer.Type.SELL) {
                                    RabbitMQSender.SendMessageToMatchQueue(stock_offer, targetOffer);
                                } else {
                                    RabbitMQSender.SendMessageToMatchQueue(targetOffer , stock_offer);
                                }

                                stock_offer.updateQuantity(quantity);
                                targetOffer.updateQuantity(quantity);
                            }finally {
                                targetOffer.matchLock.unlock();
                            }
                        }
                    }
                }finally {
                    stock_offer.matchLock.unlock();
                }
            }
        }
    }

    public static void MatchOffers(){
        for(StockOffer stock_offer : offers){
            matchOffer(stock_offer);
        }
    }

    public static void removeOffer(StockOffer stock_offer){
        offers.remove(stock_offer);
    }

    public static void addOffer(StockOffer offer){
        offers.add(offer);
    }
}
