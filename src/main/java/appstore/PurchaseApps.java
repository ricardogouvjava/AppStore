package appstore;

import java.util.Date;
import java.util.Map;

public class PurchaseApps extends Purchase
{
	private Bag purchaseBag;
	private Map<App, Integer> purchaseItems;
	private double savedValue;

	public PurchaseApps(Client aClient, Bag aBag, Date aDate) 
	{
		super(aClient, aDate);
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
	double calculateValue()
	{
		return purchaseBag.valueInBag() *(100 - getClient().getClientDiscount() /100) ;
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
	public double getSavedValue() 
	{
		return savedValue;
	}

	// Setters
	public void setSavedValue(double savedValue) 
	{
		this.savedValue = savedValue;
	}

}
