import Entities.*;
import Entities.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args){
        ArrayList<StockOffer> tmpArray;

        ArrayList<Double>  prices = new ArrayList<>(Arrays.asList(50.0, 75.0, 100.0, 200.0, 100.0, 500.0, 5.0, 105.0));
        ArrayList<String> instruments = new ArrayList<>(Arrays.asList("META", "TESLA", "AAPL", "AMZN", "PLTR", "SEX", "NEO", "NITO"));
        int no_stocks = instruments.size();

        ArrayList<Client> clients = new ArrayList<>();
        int c = 10, no_offers = 120, n = prices.size();

        for(int client = 0; client < c; ++client){
            tmpArray = new ArrayList<>();

            Client newClient = new Client();
            for(int instr = 0;  instr < no_stocks; ++instr){
                for (int k = 0; k < no_offers/n; ++k) {
                    tmpArray.add(new StockOffer(StockOffer.Type.BUY, prices.get(instr), instruments.get(instr), newClient));
                    tmpArray.add(new StockOffer(StockOffer.Type.SELL, prices.get(instr), instruments.get(instr), newClient));
                }
            }
            Collections.shuffle(tmpArray);
            newClient.setOffers(tmpArray);

            clients.add(newClient);
        }


        for(Client cl : clients){
            Thread t = new Thread(() -> cl.placeOffers());
            t.start();
        }
        try{
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n\n");
        for(StockOffer off : StockExchange.offers)
            System.out.println(off);
    }
}

