package appstore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Client extends User
{
	private List<Purchase> purchases;
	private double spendings;

	// Constructor
	public Client(String aId, String aPassword, int aAge)
	{
		super(aId, aPassword, aAge);
		purchases = new ArrayList<>();
		setSpendings(0);
	}

	// Methods
	/** Adds application to user library **/
	public Purchase buy(Bag aShoppingBag, Calendar aCalendar)
	{
		Purchase purchase = new Purchase(this, aShoppingBag, aCalendar.getTime());

		this.purchases.add(purchase);

		super.addApps(aShoppingBag); // Update applications list

		updateSpendings(aShoppingBag);

		return purchase;
	}

	public List<Purchase> getPurchases()
	{
		return purchases;
	}

	// Getters
	public double getSpendings()
	{
		return spendings;
	}

	/** Allows Client to give a score to an application **/
	public void giveScore(App aApp, double aScoreValue, String aComment, AppStore aStore)
	{
		/* Verify if user bought the application so he can score it */
		if(super.getApps().containsKey(aApp) && !super.getAppsScored().contains(aApp))
		{
			Score score = new Score(this, aApp, aScoreValue, aComment);

			aApp.addScore(score);					// Add Score to Application list
			aStore.addScore(score); 				// Add Score to Store list
			addScore(score); 						// Add Score to client list and updates average
			aApp.getProgrammer().addScore(score);	// Add Score to programmer list and updates average
		}
		else
		{
			System.out.println("\nThis user is not allowed to score this application: " + aApp.getName());
		}
	}

	// Setters
	public void setSpendings(double spendings) {
		this.spendings = spendings;
	}



	/** Updates value spent **/
	private void updateSpendings(Bag aShoppingBag)
	{
		setSpendings(getSpendings() + aShoppingBag.valueInBag());
	}
}

