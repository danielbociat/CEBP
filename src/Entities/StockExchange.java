package Entities;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.ArrayList;

public class StockExchange {
    private ArrayList<StockOffer> offers;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private final Lock offerLock = new ReentrantLock();


    // region ctor
    public StockExchange(int i) {
        this.offers = new ArrayList<StockOffer>();

        for (int k = 0; k < i; ++k)
            offers.add(new StockOffer(this));
    }
    // endregion


    public void removeOffer(StockOffer stock_offer){
        offerLock.lock();

        try{
            offers.remove(stock_offer);
            /// TBA log
        }finally {
            offerLock.unlock();
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
