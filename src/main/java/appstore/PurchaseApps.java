package appstore;

import java.util.Date;
import java.util.Map;

public class PurchaseApps extends Purchase
{
	private final Bag purchaseBag;
	private final Map<App, Integer> purchaseItems;
	private final double value;
	private final double savedValue;

	public PurchaseApps(Client aClient, Bag aBag, Date aDate) 
	{
		super(aClient, aDate);
		purchaseBag = aBag;
		value = calculateValue();
		purchaseItems = aBag.getBagItems();
		savedValue = calculateSavedValue();
	}

	// Methods
	@Override
	public String toString()
	{
		return super.toString()  + ", Purchase: " + purchaseBag;
	}
	
	@Override
	/** Value off purchase with discount**/
	protected double calculateValue()
	{
		return purchaseBag.valueInBag() * (100 - getClient().getClientDiscount() /100) ;
	}
	
	/** Value saved in Sub **/
	private double calculateSavedValue()
	{		
		return purchaseBag.valueInBag() * getClient().getClientDiscount()  / 100 ;
	}
	
	// Getters
	public Map<App, Integer> getItems()
	{
		return purchaseItems;
	}
	public double getValue() 
	{
		return value;
	}
	public double getSavedValue() 
	{
		return savedValue;
	}


}
