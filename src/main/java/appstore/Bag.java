package appstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bag
{
	private Map<App, Integer> bag;

	public Bag ()
	{
		bag = new HashMap<App, Integer>();
	}

	// Method 
	@Override
	public String toString() {
		String toPrint = "\nBag List:"
				+ "\nApp : Price : Licences : SubTotal";

		for(Map.Entry<App, Integer> set : bag.entrySet())
		{	
			toPrint += "\n" + set.getKey() + " : " + set.getValue() + " :"
					+ " " + set.getKey().getPrice()*set.getValue();
		}
		return toPrint;
	}

	/** Add application to bag **/
	public void putInBag(App aApp, int aQuantity)
	{
		if(!isAppInBag(aApp))
		{
			bag.put(aApp, aQuantity);
		}
		
		else 
		{
			for(Map.Entry<App, Integer> set : bag.entrySet())
			{
				if(set.getKey().getName().equals(aApp.getName()))
				{
					set.setValue(set.getValue() + aQuantity);
				}
			}
		}
	}

	/** Check if application already in bag **/
	public boolean isAppInBag(App aApp)
	{
		boolean appInBag = false;
		
		for(App app : bag.keySet())
		{
			if(app.getName().equals(aApp.getName()))
			{
				appInBag = true;
			}
		}
		
		return appInBag;
	}
	
	
	/** Returns list of applications in the bag **/ 
	public List<App> getAppsInBag()
	{
		List<App> templist = new ArrayList<App>();
		for(App app : bag.keySet())
		{
			templist.add(app);
		}
		return templist;
	}
	
	/** Calculates the value in the shopping bag **/
	public Double valueInBag()
	{
		double sum = 0;
		for(Map.Entry<App, Integer> set : bag.entrySet()) 
		{
			sum += set.getKey().getPrice() * set.getValue();
		}
		return sum;
	}

	// Getters
	public Map<App, Integer> getBagData() 
	{
		return bag;
	}

	// Setters
	public void setBag(Map<App, Integer> aBag)
	{
		bag = aBag;
	}

}
