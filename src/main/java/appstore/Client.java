package appstore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Client extends User
{
	private int accountDiscount;
	private List<Purchase> purchases;
	private double spendings;
	private boolean hasIncentiveDiscount; //when true applies incentive discount for having brought a new user
	private List<Client> invitedClients;

	// Constructor
	public Client(String aId, String aPassword, int aAge)
	{
		super(aId, aPassword, aAge);
		purchases = new ArrayList<>();
		spendings = 0;
		accountDiscount = 0;
		hasIncentiveDiscount = false;
		invitedClients = new ArrayList<Client>();
	}

	// Methods
	/** Adds application to user library **/
	public Purchase buy(Bag aShoppingBag, Calendar aCalendar)
	{
		Purchase purchase = new Purchase(this, aShoppingBag, aCalendar.getTime());
		
		// adds purchase to list
		this.purchases.add(purchase);

		// Update applications bought list
		super.addApps(aShoppingBag); 

		// updates spending made by client
		updateSpending(purchase.getPurchaseValue());
		
		// removes incentive discount
		setHasIncentiveDiscount(false);
		
		return purchase;
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

	/** Updates value spent **/
	private void updateSpending(double aValue)
	{
		setSpendings(getSpendings() + aValue);
	}

	/** Allow User to refer a client to Application store  and gain a bonus discount**/
	public void inviteClient(AppStore aAppstore)
	{		
		// Creates a random user for functionality
		// alternatively go to user menu creation
		invitedClients.add(AppStore.generator.generateReturnClient());
		
		// incentive bonus discount to true
		setHasIncentiveDiscount(true);
	}
	
	
	// Getters
	public double getSpendings()
	{
		return spendings;
	}

	public boolean hasIncentiveDiscount()
	{
		return hasIncentiveDiscount;
	}

	public int getAccountDiscount() 
	{
		return accountDiscount;
	}

	public List<Purchase> getPurchases()
	{
		return purchases;
	}
	
	public List<Client> getInvitedClients() {
		return invitedClients;
	}


	// Setters
	

	public void setSpendings(double spendings) {
		this.spendings = spendings;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	public void setAccountDiscount(int discount)
	{
		this.accountDiscount = discount;
		}

	public void setHasIncentiveDiscount(boolean aHasIncentiveDiscount) 
	{
		this.hasIncentiveDiscount = aHasIncentiveDiscount;
	}

	public void setInvitedClients(List<Client> invitedClients) {
		this.invitedClients = invitedClients;
	}
}

