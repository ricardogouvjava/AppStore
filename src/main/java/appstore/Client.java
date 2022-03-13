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
	private boolean hasChoosenFreeWeeklyApp;
	private List<Client> invitedClients;
	private List<App> freeApps;
	private List<Subscription> subscriptions;
	private List<App> subscribedApps;
	private List<App> watingReSubscriptionApps;

	// Constructor
	public Client(String aId, String aPassword, int aAge)
	{
		super(aId, aPassword, aAge);
		purchases = new ArrayList<>();
		spendings = 0;
		accountDiscount = 0;
		hasIncentiveDiscount = false;
		hasChoosenFreeWeeklyApp = false;
		invitedClients = new ArrayList<Client>();
		freeApps = new ArrayList<App>();
		subscriptions = new ArrayList<Subscription>();
		subscribedApps = new ArrayList<App>();
		watingReSubscriptionApps = new ArrayList<App>();
	}

	// Methods
	/** Adds application to user library **/
	public PurchaseApps buy(Bag aShoppingBag, Calendar aCalendar)
	{
		PurchaseApps purchase = new PurchaseApps(this, aShoppingBag, aCalendar.getTime());
		
		// adds purchase to list
		this.purchases.add(purchase);

		// Update applications bought list
		super.addApps(aShoppingBag); 

		// updates spending made by client
		updateSpending(purchase.getValue());
		
		// removes incentive discount
		setHasIncentiveDiscount(false);
		
		return purchase;
	}

	/** Adds application to user library **/
	public Subscription subscribe(App aApp, Calendar aCalendar)
	{
		Subscription subscription = new Subscription(this, aCalendar.getTime(), aApp);
		
		// adds subscription to list
		this.subscriptions.add(subscription);

		// updates spending made by client
		updateSpending(subscription.getValue());
		
		// removes incentive discount
		setHasIncentiveDiscount(false);
		
		return subscription;
	}
	
	/** Hold Subscription **/
	public boolean holdSubscription(Subscription aSub)
	{
		if(subscribedApps.remove(aSub.getApp()))
		{
			watingReSubscriptionApps.add(aSub.getApp());
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/** Hold Subscription **/
	public boolean reSubscribe(App aApp)
	{
		if(watingReSubscriptionApps.contains(aApp))
		{
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	
	/** Calculates current client discount **/
	public double getClientDiscount() 
	{
		// Client Class Discount
		double discount = getAccountDiscount();
		
		// Incentive Discount
		if (hasIncentiveDiscount())
		{
			discount += AppStore.userIcentiveDiscount;
		}
		return discount;
	}
	
	/** Allows Client to give a score to an application **/
	public Score giveScore(App aApp, double aScoreValue, String aComment, AppStore aStore)
	{
		Score score = new Score(this, aApp, aScoreValue, aComment);
		aApp.addScore(score);					// Add Score to Application list
		aStore.addScore(score); 				// Add Score to Store list
		addScore(score); 						// Add Score to client list and updates average
		aApp.getProgrammer().addScore(score);	// Add Score to programmer list and updates average
		
		return score;
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
	
	/** Add application to application list **/
	public void addChoosenFreeApp(App aChoosenApp)
	{
		super.addApp(aChoosenApp, 1);
		freeApps.add(aChoosenApp);
		setHasChoosenFreeWeeklyApp(true);
	}
	
	/** verifies if application was chosen as free application **/
	public boolean pickFreeAppSucess(App appchoosen)
	{
		if(getFreeApps().contains(appchoosen))
		{
			return false;
		}
		else 
		{
			addChoosenFreeApp(appchoosen);
			return true;
		}
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
	public boolean hasChoosenFreeWeeklyApp() 
	{
		return hasChoosenFreeWeeklyApp;
	}
	public List<App> getFreeApps() {
		return freeApps;
	}
	public boolean getHasIncentiveDiscount()
	{
		return hasIncentiveDiscount;
	}
	public boolean getHasChoosenFreeWeeklyApp() 
	{
		return hasChoosenFreeWeeklyApp;
	}
	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}
	public List<App> getWatingReSubscriptionApps() {
		return watingReSubscriptionApps;
	}
	public List<App> getSubscribedApps()
	{
		return subscribedApps;
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
	public void setInvitedClients(List<Client> invitedClients) 
{
		this.invitedClients = invitedClients;
	}
	public void setFreeApps(List<App> freeApps) {
		this.freeApps = freeApps;
	}
	public void setHasChoosenFreeWeeklyApp(boolean hasChoosenFreeWeeklyApp) {
		this.hasChoosenFreeWeeklyApp = hasChoosenFreeWeeklyApp;
	}
	public void setWatingReSubscriptionApps(List<App> watingReSubscriptionApps) {
		this.watingReSubscriptionApps = watingReSubscriptionApps;
	}
	public void setSubscriptions(List<Subscription> subscribedApps) {
		this.subscriptions = subscribedApps;
	}
	public void setSubscribedApps(List<App> subscriptionApps) {
		this.subscribedApps = subscriptionApps;
	}
}

