package Entities;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.ArrayList;

public class StockExchange {
    private ConcurrentLinkedQueue<StockOffer> offers;
    private ExecutorService executor = Executors.newCachedThreadPool();


    // region ctor
    public StockExchange(int i) {
        ArrayList tmpArray = new ArrayList<StockOffer>();
        ArrayList<Double>  prices = new ArrayList<>(Arrays.asList(50.0, 75.0, 100.0, 200.0));


        int n = prices.size();
        for(Double price : prices){
            for (int k = 0; k < i/n; ++k) {
                tmpArray.add(new StockOffer(this, StockOffer.Type.BUY, price));
                tmpArray.add(new StockOffer(this, StockOffer.Type.SELL, price));
            }
        }

        Collections.shuffle(tmpArray);
        this.offers = new ConcurrentLinkedQueue<StockOffer>(tmpArray);
    }
    // endregion

    public void matchOffer(StockOffer stock_offer){

        for(StockOffer stockOffer : offers){

            if(stock_offer.type == StockOffer.Type.COMPLETED)
                break;

            try{
                stockOffer.matchLock.lock();
                if (stockOffer.getType() == StockOffer.Type.SELL && stock_offer.checkMatch(stockOffer)) {
                    try {
                        stockOffer.setCompleteLock.lock();

                        stock_offer.setToCompleted();
                        stockOffer.setToCompleted();

                        System.out.println( stock_offer + " matches with " + stockOffer);

                    }finally {
                        stockOffer.setCompleteLock.unlock();
                    }
                }
            }finally {
                stockOffer.matchLock.unlock();
            }


        }



    }

    public void removeOffer(StockOffer stock_offer){
        for(StockOffer offer : offers){
            if(offer == stock_offer) {
                offers.remove(stock_offer);
            }
        }

        /// TBA log
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
