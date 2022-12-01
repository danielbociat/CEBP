package Entities;

import java.util.ArrayList;

public class Client implements Runnable {
    public int id;
    public static int count = 0;
    public ArrayList<StockOffer> offers;

    public Client(int i){
        this.id = count++;
    }

    public void run(){
        return;
    }
}
