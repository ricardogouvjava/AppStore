package appstore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Purchase 
{
	private String clientName;
	private Bag purchaseBag;
	private Date buyDate;
	private double price;

	
	public Purchase(String aClient, Bag aBag, Date aDate)
	{	
		clientName = aClient;
		purchaseBag = aBag;
		buyDate = aDate;
		price = aBag.valueInBag();
		
	}
		
	//Methods
		@Override
		public String toString()
		{
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			return "Date: "+ formatter.format(buyDate.getTime()) + ", Client: " + clientName + 
					", value: " + price + ", Apps: " + purchaseBag.getAppsInBag();
		}
		
	
	// Getters
	public String getClientName() 
	{
		return clientName;
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
	public void setClient(String aClientName) 
	{
		clientName = aClientName;
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
