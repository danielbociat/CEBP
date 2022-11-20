package Entities;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.ArrayList;

public class StockExchange {
    private ArrayList<StockOffer> offers;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private final Lock offersLock = new ReentrantLock();


    // region ctor
    public StockExchange(int i) {
        this.offers = new ArrayList<StockOffer>();

        for (int k = 0; k < i/2; ++k)
            offers.add(new StockOffer(this, StockOffer.Type.BUY, 50.0));

        for (int k = 0; k < i/2; ++k)
            offers.add(new StockOffer(this, StockOffer.Type.SELL, 50.0));
    }
    // endregion

    public void matchOffer(StockOffer stock_offer){

        offersLock.lock();

        try{
            for (StockOffer stockOffer : offers) {
                stockOffer.offerLock.lock();
                boolean flag = false;
                try {
                    if (stockOffer.getType() == StockOffer.Type.SELL && stock_offer.checkMatch(stockOffer)) {
                        stock_offer.setToCompleted();
                        stockOffer.setToCompleted();

                        flag = true;
                    }
                } finally {
                    stockOffer.offerLock.unlock();

                }

                if(flag)
                    break;
            }
        }finally {
            offersLock.unlock();
        }



    }

    public void removeOffer(StockOffer stock_offer){
        offersLock.lock();

        try{
            offers.remove(stock_offer);
            /// TBA log
        }finally {
            offersLock.unlock();
        }

        if (offers.isEmpty()) {
            executor.shutdown();
            /// TBA log
            return;
        }
    }

    public void start(){
        for(StockOffer offer : offers){
            executor.execute(offer);
        }
    }

}
