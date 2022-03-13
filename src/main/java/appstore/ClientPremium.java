package appstore;

public class ClientPremium extends Client
{
	public ClientPremium(String aFirstName, String aLastName, int aAge, int aDiscount)
	{
		super(aFirstName, aLastName, aAge);
		this.setAccountDiscount(aDiscount);
	}


	/** Updates value spent **/
	public void updateSpendings(PurchaseApps aPurchase)
	{
		setSpendings(getSpendings() + aPurchase.getValue());
	}

}
