package appstore;

import java.util.Calendar;
import java.util.Date;

public class Subscription extends Purchase
{
	private App app;
	private Date endDate;
	
	// Constructor
	public Subscription(Client aClient, Date aDate, App aApp)
	{
		super(aClient, aDate);
		this.app = 	aApp;
		super.setValue(calculateValue());
		this.endDate = calculateEndDate(aDate);
		
	}

	
	// Methods
	@Override
	public String toString()
		{
			return super.toString() + ":" + app;
		}
	
	@Override
	protected double calculateValue()
	{
		// value by year base Price / 10
		return app.getPrice() / 10;
	}

	
	/** Check if subscription passed finished **/
	public boolean isSubscriptionValid(Calendar aCalendar)
	{
		if(getEndDate().after(aCalendar.getTime())) 
		{
			return true;
		}
		return false;
	}

	/** Extends subscription **/
	public boolean extendSubscription() 
	{
		try
		{	setEndDate(calculateEndDate(Calendar.getInstance().getTime()));
			return true;

		}
		catch (Exception e)
		{
		e.getStackTrace();	
		return false;
		}
	}
	
	/** **/
	private Date calculateEndDate(Date aDate)
	{
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(aDate);
		tempCal.add(Calendar.YEAR, 1);
		return tempCal.getTime();
		
	}
	
	/** holt subscription **/
	public boolean holtSubscription(AppStore store) 
	{
		try
		{	
			// Remove application from lists SubscribedApps and watingReSubscriptionApps and store
			super.getClient().holtSubscription(this);
			store.getSubscriptions().remove(store.getSubscriptions().indexOf(this));
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
			super.getClient().cancelSubscrition(this);
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
	public Date getEndDate() {
		return endDate;
	}
	
	// Setters
	public void setApp(App app) 
	{
		this.app = app;
	}


	


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
