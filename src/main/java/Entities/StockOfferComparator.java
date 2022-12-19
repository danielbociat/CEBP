package Entities;

import java.util.Comparator;

public class StockOfferComparator implements Comparator<StockOffer> {
    public int compare(StockOffer str1, StockOffer str2)
    {
        return str2.compareTo(str1);
    }
}
