package appstore;

import java.text.SimpleDateFormat;
import java.util.Date;

abstract class Purchase
{
	private Client client;
	private Date buyDate;
	private double value;
	
	// Constructor
	public Purchase(Client aClient, Date aDate)
	{
		client = aClient;
		buyDate = aDate;
		value = calculateValue();
	}
	
	//Methods
	@Override
	public String toString()
		{
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			return "Date: "+ formatter.format(buyDate.getTime()) + ", Client: " + client.getId();
		}

	/** Value off purchase with discount**/
	abstract double calculateValue();
	
	
	// Getters
	public Client getClient()
	{
		return client;
	}
	public Date getBuyDate()
	{
		return buyDate;
	}
	public double getValue() 
	{
		return value;
	}
	
	// Setters
	public void setClient(Client aClient)
	{
		client = aClient;
	}
	public void setValue(double aValue)
	{
		value = aValue;
	}
	public void setBuyDate(Date aDate)
	{
		buyDate = aDate;
	}
	
}
