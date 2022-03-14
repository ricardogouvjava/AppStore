package appstore;

import java.text.SimpleDateFormat;
import java.util.Date;

abstract class Purchase
{
	private final Client client;
	private final Date buyDate;
		
	// Constructor
	public Purchase(Client aClient, Date aDate)
	{
		client = aClient;
		buyDate = aDate;
	}
	
	//Methods
	@Override
	public String toString()
		{
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			return "Date: "+ formatter.format(buyDate.getTime()) + ", Client: " + client.getId();
		}

	/** Value off purchase with discount**/
	protected abstract double calculateValue();
	
	
	// Getters
	public Client getClient()
	{
		return client;
	}
	public Date getBuyDate()
	{
		return buyDate;
	}
	
		
}
