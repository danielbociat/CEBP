import Entities.*;
import Entities.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args){
        ArrayList<StockOffer> tmpArray;
        ArrayList<Double> prices = new ArrayList<>(Arrays.asList(50.0, 75.0, 100.0, 200.0, 100.0, 500.0, 5.0, 105.0));
        ArrayList<String> instruments = new ArrayList<>(Arrays.asList("META", "TESLA", "AAPL", "AMZN", "PLTR", "RIOT", "NEO", "NITO"));
        int no_stocks = instruments.size();
        ArrayList<Client> clients = new ArrayList<>();
        int no_clients = 10, no_offers_per_client = 120, n = prices.size();
        int max = 15, min = 1;

        for(int client = 0; client < no_clients; ++client){
            tmpArray = new ArrayList<>();

            Client newClient = new Client();
            for(int instr = 0;  instr < no_stocks; ++instr){
                for (int k = 0; k < no_offers_per_client/n; ++k) {
                    StockOffer buyOffer = new StockOffer(StockOffer.Type.BUY, prices.get(instr), instruments.get(instr), newClient, new Random().nextInt(max - min + 1) + min);
                    StockOffer sellOffer = new StockOffer(StockOffer.Type.SELL, prices.get(instr), instruments.get(instr), newClient, new Random().nextInt(max - min + 1) + min);
                    tmpArray.add(buyOffer);
                    tmpArray.add(sellOffer);
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

        for(int i = 0; i < 5; ++i)
        {
            Thread t = new Thread(() -> {
                while(true){
                    StockExchange.MatchOffers();
                }
            });
            t.start();
        }

        /**
        try{
            TimeUnit.SECONDS.sleep(45);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(StockOffer.getTotalId());

        System.out.println("\n\n\n");
        for(StockOffer off : StockExchange.offers)
            System.out.println(off);
        **/
    }
}

