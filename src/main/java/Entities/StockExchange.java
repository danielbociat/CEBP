package Entities;
import java.util.concurrent.*;


public class StockExchange {
    public static ConcurrentLinkedQueue<StockOffer> offers = new ConcurrentLinkedQueue<>();

    public static void printTransaction(StockOffer left, StockOffer right){
        if(left.getType() == StockOffer.Type.BUY){
            System.out.println("Client " + left.getOwner() + " bought " + left.getInstrument() + " from " + right.getOwner() + " for " + right.getValue());
        }

        if(left.getType() == StockOffer.Type.SELL){
            System.out.println("Client " + left.getOwner() + " sold " + left.getInstrument() + " to " + right.getOwner() + " for " + right.getValue());
        }

    }

    public static void matchOffer(StockOffer stock_offer){
        for(StockOffer targetOffer : offers){
            if(stock_offer.type == StockOffer.Type.COMPLETED)
                break;

            if (targetOffer.getType() != stock_offer.getType() && stock_offer.checkMatch(targetOffer)) {
                if (targetOffer.matchLock.tryLock()) {
                     try {
                        printTransaction(stock_offer, targetOffer);
                        stock_offer.setToCompleted();
                        targetOffer.setToCompleted();

                        RabbitMQSender.SendMatchMessage(stock_offer, targetOffer);
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
