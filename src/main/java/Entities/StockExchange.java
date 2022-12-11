package Entities;
import java.util.concurrent.*;


public class StockExchange {
    public static ConcurrentLinkedQueue<StockOffer> offers = new ConcurrentLinkedQueue<>();

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
                                Transaction transaction = new Transaction(stock_offer, targetOffer, quantity);
                                // System.out.println(transaction);
                                RabbitMQSender.SendMessageToQueue("offers.match",transaction);

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
