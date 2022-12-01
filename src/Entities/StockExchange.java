package Entities;
import java.util.concurrent.*;


public class StockExchange {
    public static ConcurrentLinkedQueue<StockOffer> offers = new ConcurrentLinkedQueue<>();

    public static void matchOffer(StockOffer stock_offer){
        for(StockOffer targetOffer : offers){
            if(stock_offer.type == StockOffer.Type.COMPLETED)
                break;

            if (targetOffer.getType() != stock_offer.getType() && stock_offer.checkMatch(targetOffer)) {
                if (targetOffer.matchLock.tryLock()) {
                     try {
                        stock_offer.setToCompleted();
                        targetOffer.setToCompleted();

                        System.out.println(stock_offer + " matches with " + targetOffer);
                    }finally {
                         targetOffer.matchLock.unlock();
                     }
                }
            }
        }
    }

    public static void removeOffer(StockOffer stock_offer){
        for(StockOffer offer : offers){
            if(offer == stock_offer) {
                offers.remove(stock_offer);
            }
        }
    }

    public static void addOffer(StockOffer offer){
        offers.add(offer);
        Thread t = new Thread(() -> matchOffer(offer));
        t.start();
    }
}
