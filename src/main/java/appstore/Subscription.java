package appstore;

import java.util.Calendar;
import java.util.Date;

public class Subscription extends Purchase
{
	private App app;
	
	// Constructor
	public Subscription(Client aClient, Date aDate, App aApp)
	{
		super(aClient, aDate);
		this.app = 	aApp;
	}

	
	// Methods
	@Override
	public String toString()
		{
			return super.toString() + ":" + app;
		}
	
	@Override
	double calculateValue()
	{
		// value by year base Price / 10
		return getApp().getPrice() / 10;
	}

	
	/** Check if subscription passed finished **/
	public boolean isSubscriptionValid(Calendar aCalendar)
	{
		boolean	isSubscriptionValid = false;
		Calendar checkCalendar = Calendar.getInstance();
		checkCalendar.add(Calendar.YEAR, -1);
		Date checkDate = checkCalendar.getTime();
		
		if(checkDate.after(this.getBuyDate())) 
		{
			return true;
			
		}
		return isSubscriptionValid;
	}

	/** Extends subscription **/
	public boolean extendSubscription(Date aDate) 
	{
		try
		{	super.setBuyDate(aDate);
			return true;

		}
		catch (Exception e)
		{
		e.getStackTrace();	
		return false;
		}
	}
	
	/** Cancel subscription **/
	public boolean cancelSubscription(AppStore store) 
	{
		try
		{	
			// Remove application from lists SubscribedApps and watingReSubscriptionApps and store
			super.getClient().getWatingReSubscriptionApps().remove(super.getClient().getWatingReSubscriptionApps().indexOf(this.app));
			super.getClient().getSubscribedApps().remove(super.getClient().getSubscribedApps().indexOf(this.app));
			store.getSubscriptions().remove(store.getSubscriptions().indexOf(this));
			return true;

		}
		catch (Exception e)
		{
		e.getStackTrace();	
		return false;
		}
	}
	
	// Getters
	public App getApp() 
	{
		return app;
	}

	// Setters
	public void setApp(App app) 
	{
		this.app = app;
	}
	
}
