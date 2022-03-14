package appstore;

import java.util.Calendar;
import java.util.Date;

public class Subscription extends Purchase
{
	private final App app;
	private final double value;
	private Date endDate;
	private boolean active;
		
	// Constructor
	public Subscription(Client aClient, Date aDate, App aApp)
	{
		super(aClient, aDate);
		app = 	aApp;
		value = calculateValue();
		active = true;
		endDate = calculateEndDate(aDate);
		
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
		setActive(false);
		getClient().holtSubscription(this);
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
	
	public void holtSubscription(Subscription sub) 
	{
		setActive(false);
		getClient().holtSubscription(this);
	}
	
	/** Calculates Subscription end date **/
	private Date calculateEndDate(Date aDate)
	{
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(aDate);
		tempCal.add(Calendar.YEAR, 1);
		return tempCal.getTime();
		
	}
	
	// Getters
	public App getApp() 
	{
		return app;
	}
	public double getValue() 
	{
		return value;
	}
	public Date getEndDate() 
{
		return endDate;
	}
	public boolean isActive() {
		return active;
	}

	// Setters
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
