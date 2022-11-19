package broker;

import java.util.List;
import java.util.*;
import StockUnit from .Entities.*

public class Broker implementes IBroker{



	@Override
	public String addOffer(StockOffer offer, StockUnit stockID)
	{
		newOffers.add(offer);

		return null;
	}

	@Override
	public String deleteOffer(StockOffer offer, StockUnit stockID)
	{
		Offer.delete(offer);

		return null;
	}

	@Overrdie
	public String modifyOffeR(StockOffer offerID, StockOffer newOffer)
	{
		//tbd
		return null;
	}

	@Override
	public String subscribe(StockUnit stock)
	{
		//make a map / list with the stocks
		return null;
	}

	@Override
	public int addTransction(TransactionRequest transaction)
	{
		//make a map / list with the transactions
		return 0;
	}

	@Override
	public List<Strings> getStockList()
	{
		return new ArrayList();
	}

	@Override
	public List<StockOffer> getBuyOffers(StockUnit stockID)
	{
		return null //create new stock and offer to be bought. 
	}

	@Override
	public List<StockOffer> getSellOffers(StockUnit stockID)
	{
		return null //create new stock and offer to be sold. 
	}

	@Override
	public List<TransactionRequest> getStockHistory(StockUnit stockID)
	{
		//Create new stack and return the list for all stockID.
		return null;
	}

	@Override
	public double getStockPrice(StockUnit stockID)
	{
		return 0; // get the prices for a stock id.
	}



}