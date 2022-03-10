package appstore;

import java.util.HashMap;
import java.util.Map;

public class Bag
{
	private Map<App, Integer> bagItems;

	public Bag ()
	{
		bagItems = new HashMap<App, Integer>();
	}

	// Method 
	@Override
	public String toString()
	{
		String toPrint = "[<App : Price : Licences : SubTotal> Total] >> [";

		for(Map.Entry<App, Integer> set : bagItems.entrySet())
		{	
			toPrint += " <" + set.getKey().getName() + " : " + String.format("%.2f",set.getKey().getPrice())
			+ " : " + set.getValue() + " : " + String.format("%.2f", set.getKey().getPrice() * set.getValue()) + "> ";
		}
		toPrint += "> " + String.format("%.2f", valueInBag()) + "]";
		return toPrint;
	}

	/** Add application to bag **/
	public void putInBag(App aApp, int aQuantity)
	{
		bagItems.merge(aApp, aQuantity, (v1, v2) -> v1 + v2);
	}
	
	/** Calculates the value in the shopping bag **/
	public double valueInBag()
	{
		double sum = 0;
		for(Map.Entry<App, Integer> entry : bagItems.entrySet()) 
		{
			sum += entry.getKey().getPrice() * entry.getValue();
		}
		return sum;
	}

	// Getters
	public Map<App, Integer> getBagItems() 
	{
		return bagItems;
	}

	// Setters
	public void setBag(Map<App, Integer> aBagItems)
	{
		bagItems = aBagItems;
	}

}
