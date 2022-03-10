package appstore;

public class ClientPremium extends Client
{

	public ClientPremium(String aFirstName, String aLastName, int aAge)
	{
		super(aFirstName, aLastName, aAge);

	}
	
	
	/** Updates value spent **/
	public void updateSpendings(Bag aShoppingBag)
	{	
		setSpendings(getSpendings() + aShoppingBag.valueInBag() * AppStore.getPremimumDiscount());
	}
	
}
