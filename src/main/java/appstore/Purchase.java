package appstore;

import java.text.SimpleDateFormat;
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
		price = getPurchaseValue();

	}
	//Methods
		@Override
		public String toString()
		{
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			return "Date: "+ formatter.format(buyDate.getTime()) + ", Client: " + client.getId() +
					"\nBag:"+ purchaseBag;
		}

	public double getPurchaseValue() 
	{
		return purchaseBag.valueInBag() *(100 - client.getDiscount() /100) ;
	}
	
	public double savedInPurchase()
	{		
		return purchaseBag.valueInBag() * client.getDiscount()  / 100 ;
	}
	

	// Getters
	public Client getClientName()
	{
		return client;
	}
	public double getPrice()
	{
		return price;
	}
	public Date getBuyDate()
	{
		return buyDate;
	}
	public Bag getPurchaseBag()
	{
		return purchaseBag;
	}

	// Setters
	public void setClient(Client aClient)
	{
		client = aClient;
	}
	public void setPrice(double aPrice)
	{
		price = aPrice;
	}
	public void setPurchaseBag(Bag aBag)
	{
		purchaseBag = aBag;
	}
	public void setBuyDate(Date aDate)
	{
		buyDate = aDate;
	}
	
}
