import Entities.*;
import Entities.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static void main(String[] args){
        ArrayList<StockOffer> tmpArray = new ArrayList<>();
        ArrayList<Double>  prices = new ArrayList<>(Arrays.asList(50.0, 75.0, 100.0, 200.0));
        int i = 200, n = prices.size();
        for(Double price : prices){
            for (int k = 0; k < i/n; ++k) {
                tmpArray.add(new StockOffer(StockOffer.Type.BUY, price));
                tmpArray.add(new StockOffer(StockOffer.Type.SELL, price));
            }
        }
        Collections.shuffle(tmpArray);

        for(StockOffer offer : tmpArray){
            StockExchange.addOffer(offer);
        }
    }
}

