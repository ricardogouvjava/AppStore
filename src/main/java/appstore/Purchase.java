package appstore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Purchase 
{
	private Client client;
	private Bag purchaseBag;
	private Date buyDate;
	private double price;

	
	public Purchase(Client aClient, Bag aBag, Date aDate)
	{	
		client = aClient;
		purchaseBag = aBag;
		buyDate = aDate;
		price = aBag.valueInBag();
		
	}
		
	//Methods
	@Override
	public String toString()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return "Date: "+ formatter.format(buyDate.getTime()) + ", Client: " + client.getId() + 
				"\nBag:"+ purchaseBag;
	}
	
	public int getWeekPurchase() 
	{
		Calendar cal = Calendar.getInstance();
	    cal.setTime(buyDate);
	    return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
		
	// Getters
	public Client getClientName() 
	{
		return client;
	}
	
	public Bag getPurchaseBag() 
	{
		return purchaseBag;
	}
	
	public Date getBuyDate() 
	{
		return buyDate;
	}

	public double getPrice()
	{
		return price;
	}
	
	// Setters
	public void setClient(Client aClientName) 
	{
		client = aClientName;
	}
	
	public void setPurchaseBag(Bag aBag)
	{
		purchaseBag = aBag;
	}

	public void setBuyDate(Date aDate)
	{
		buyDate = aDate;
	}
	
	public void setPrice(double aPrice)
	{
		price = aPrice;
	}
}
