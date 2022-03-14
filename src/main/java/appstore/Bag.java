package appstore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Bag
{
	private Map<App, Integer> bagItems;
	
	public Bag ()
	{
		bagItems = new HashMap<>();
	}

	// Method
	/** Prints returns string of all bag data **/
	@Override
	public String toString()
	{
		String toPrint = "[<App : Licences = SubTotal> -> Total]\n[";

		for(Map.Entry<App, Integer> set : bagItems.entrySet())
		{
			toPrint += "<" + set.getKey().getName() + " : " + set.getValue() + 
					" = " + String.format("%.2f", set.getKey().getPrice() * set.getValue()) + ">";
		}
		toPrint += "> -> " + String.format("%.2f", valueInBag()) + "]";
		return toPrint;
	}

	/** Add application to bag **/
	public void putInBag(App aApp, int aQuantity)
	{
		bagItems.merge(aApp, aQuantity, (v1, v2) -> v1 + v2);
	}
	
	/** Add application to bag **/
	public void removeFromBag(App aApp)
	{
		bagItems.remove(aApp);
	}
	
	/** Alter value of licenses **/
	public void alterAppValueInBag(App aApp, int aLicenses)
	{
		bagItems.put(aApp, aLicenses);
	}

	
	/** Returns list of bag Applications **/
	public List<App> getAppsInBag()
	{
		return bagItems.keySet().stream().collect(Collectors.toList());
	}
	
	/** Calculates the value in the shopping bag with application current discount prices**/
	public double valueInBag()
	{
		double sum = 0;
		for(Map.Entry<App, Integer> entry : bagItems.entrySet())
		{
			sum += entry.getKey().getPrice() * entry.getValue();
		}
		return sum;
	}
		
	/** Calculates the value in the shopping bag with application base prices **/
	public double calculateValueInBagWithBasePrices()
	{
		double sum = 0;
		for(Map.Entry<App, Integer> entry : bagItems.entrySet())
		{
			sum += entry.getKey().getBasePrice() * entry.getValue();
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
