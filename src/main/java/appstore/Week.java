package appstore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Week 
{
	private int week;
	private List<Purchase> weekPurchases;
	private Map<App, Integer> appSales;
	
	public Week(Calendar aCalendar)
	{
		week = aCalendar.get(Calendar.WEEK_OF_YEAR);
		
		weekPurchases = new ArrayList<Purchase>();
		appSales = new HashMap<App, Integer>();
	}

	// Methods
	public void addPurchase(Purchase aPurchase)
	{
		weekPurchases.add(aPurchase);
	}
	
	public void updateAppWeekSales(Purchase aPurchase)
	{
		// Checks if key exists and sums value
		for(Map.Entry<App, Integer> entry : aPurchase.getPurchaseBag().getBagItems().entrySet())
		{
			appSales.merge(entry.getKey(), entry.getValue(), (vOld, vNew) -> vOld + vNew);
		}
	}
	
	public int appSoldValue(App aApp) 
	{
		int sold = 0;
		for(Map.Entry<App, Integer> entry : appSales.entrySet())
		{
			if(entry.getKey().equals(aApp))
			{
				sold = entry.getValue();
			}
		}
		return sold;
	}
	
	public void resetWeek(Calendar aCalendar) 
	{
		weekPurchases.clear();
		setWeek(aCalendar);
	}
	
	
	// Getter
	public int weekNumber()
	{
		return week;
	}
	
	public List<Purchase> getWeekPurchases() 
	{
		return weekPurchases;
	}
	

	public Map<App, Integer> getAppSales() {
		return appSales;
	}

	// Setters
	public void setWeek(Calendar aCalendar)
	{
		week = aCalendar.get(Calendar.WEEK_OF_YEAR);
	}
		
	
}
