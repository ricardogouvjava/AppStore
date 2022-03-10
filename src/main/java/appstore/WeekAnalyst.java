package appstore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WeekAnalyst
{
	private int week;
	private List<Purchase> weekPurchases;
	private Map<App, Integer> weekAppSales;

	public WeekAnalyst(Calendar aCalendar)
	{
		week = aCalendar.get(Calendar.WEEK_OF_YEAR);
		weekPurchases = new ArrayList<>();
		weekAppSales = new HashMap<>();
	}

	// Methods
	public void addPurchase(Purchase aPurchase)
	{
		weekPurchases.add(aPurchase);
		updateWeekAppSales(aPurchase);
	}

	public int appSoldValue(App aApp)
	{
		int sold = 0;
		for(Map.Entry<App, Integer> entry : weekAppSales.entrySet())
		{
			if(entry.getKey().equals(aApp))
			{
				sold = entry.getValue();
			}
		}
		return sold;
	}

	public Map<App, Integer> getAppSales()
	{
		return weekAppSales;
	}

	public List<Purchase> getWeekPurchases()
	{
		return weekPurchases;
	}

	private void updateWeekAppSales(Purchase aPurchase)
	{
		// Checks if key exists and sums value
		for(Map.Entry<App, Integer> entry : aPurchase.getPurchaseBag().getBagItems().entrySet())
		{
			weekAppSales.merge(entry.getKey(), entry.getValue(), (vOld, vNew) -> vOld + vNew);
		}
	}

	public HashMap<App, Integer> weekLessSoldApps(List<App> aAppsInStore, int aNumber)
	{
		// adds applications absent from list
		for(App app : aAppsInStore)
		{
			weekAppSales.putIfAbsent(app, 0);
		}

		// Sort map by value and limits number
		LinkedHashMap<App, Integer> sortedWeekAppSales = new LinkedHashMap<>();
	    weekAppSales.entrySet().stream()
								.sorted(Map.Entry.comparingByValue())
								.limit(aNumber)
								.forEachOrdered(x -> sortedWeekAppSales.put(x.getKey(), x.getValue()));

	   // finds the highest sold number after filter
	   int maxSoldFromLess = sortedWeekAppSales.entrySet().stream()
			   .max((Entry<App, Integer> e1, Entry<App, Integer> e2) -> e1.getValue().compareTo(e2.getValue())).get().getValue();

	   // finds applications with similar value and adds to list
	   for(Map.Entry<App, Integer> entry : weekAppSales.entrySet())
	    {
	    	if(entry.getValue().equals(maxSoldFromLess))
	    	{
	    		sortedWeekAppSales.putIfAbsent(entry.getKey(), entry.getValue());
	    	}
	    }

		return sortedWeekAppSales;
	}

	// Getter
	public int weekNumber()
	{
		return week;
	}

	// Setters


}
