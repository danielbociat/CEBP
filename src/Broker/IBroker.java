package broker;

import java.util.List;
import java.util.*;
import StockUnit from ./Entities.*


public interface IBroker{
	public String addOffer(StockOffer offerID, StockUnit stockID);
	public String deleteOffer(StockOffer offerID, StockUnit stockID);
	public String modifyOffer(StockOffer offerID, StockOffer newOffer);

	public String subscribe(); // tbd
	public int addTransaction(TransactionRequest transaction);

	public List<String> getStockList();
	public List<StockOffer> getBuyOffers(StockUnit stockID);
	public List<StockOffer> getSellOffers(StockUnit stockID);
	public List<TransactionRequest> getStockHistory(StockUnit stockID);
	public double getStockPrice(StockUnit stockID);

}