package appstore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppStore
{
	static Calendar calendar;					// running calendar
	static Generator generator;
	static int discountValueWeek = 15;
	static int userIcentiveDiscount = 5;
	static int premimumDiscount = 40;
	static int discountValueMonth = 5;
	private AppType appTypeWithDiscount;
	private String name;								// Store name
	private List<App> apps;								// Applications in the store
	private WeekAnalyst currentWeek;
	private int currentMonth;
	private List<User> users;							// List of all users in the store
	private List<Purchase> purchases; 					// Purchases made in the store
	private List<Score> scores; 						// List of all scores given to the applications
	private List<WeekAnalyst> weeks;
	private List<Subscription> subscriptions;
	private List<List<WeekAnalyst>> years;
	
	// Constructor
	public AppStore(String aName)
	{
		name = aName;
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		apps = new ArrayList<App>();
		currentWeek = new WeekAnalyst(calendar);
		currentMonth = calendar.get(Calendar.MONTH);
		weeks = new ArrayList<WeekAnalyst>();
		users = new ArrayList<User>();
		purchases = new ArrayList<Purchase>();
		scores = new ArrayList<Score>();
		generator = new Generator(this);
		appTypeWithDiscount = Generator.randomAppType();
		subscriptions = new ArrayList<Subscription>();
		years = new ArrayList<List<WeekAnalyst>>();
	}

	/* - Methods -
	 * Administrator Methods:
	 * 	forwardDateXDays(int aDays)
	 * 
	 * AppStore Method:
	 * addUser(String aType, String aId, String aPassword, int aAge)
	 * addScore(Score aScore)
	 * designateProgrammer(String aAppName, double aPrice, AppType aAppType, Programmer programmer)
	 * createShoppingBag()
	 * checkout(Client aClient, Bag shoppingBag)
	 * 
	 *
	 * 
	 *
	 *
	 */
	
	/* Administrator methods */
	/** Moves local date X amount of days forward **/
	public void forwardDateXDays(int aDays)
	{
		for(int i = 1; i <= aDays; i++)
		{
			
			if(calendar.get(Calendar.YEAR) != currentWeek.getYear())
			{
				years.add(weeks);
				weeks.clear();
			}
			
			// when in new Month updates discounts
			if(calendar.get(Calendar.MONTH) != currentMonth)
			{
				
				appTypeWithDiscount = Generator.randomAppType();

				updateAppMonthlyDiscounts(discountValueMonth);
				
				currentMonth = calendar.get(Calendar.MONTH);
			
				System.err.println("Month:" + calendar.get(Calendar.MONTH));
			}

			// when in new week updates discounts
			if(calendar.get(Calendar.WEEK_OF_YEAR) != currentWeek.getWeekNumber())
			{
				weeks.add(currentWeek);

				currentWeek = new WeekAnalyst(calendar);

				System.err.println("Week:" + calendar.get(Calendar.WEEK_OF_YEAR));

				updateWeeklyDiscounts();

			}

			calendar.add(Calendar.DATE, 1); // Moves a day forward

			System.err.println("New Day");

			updateSubs(); 	// check subscriptions
			 
			generator.generateDaysData(); // Generates random data for a day
		}
	}
	
	/* AppStore Methods */
	/** Ads new user to AppStore **/
	public void addUser(String aType, String aId, String aPassword, int aAge)
	{
		if (aType.equals("Client"))
		{
			Client client = new Client(aId, aPassword, aAge);
			System.out.println("Client added >> " + client);
			users.add(client);
		}

		else if (aType.equals("ClientPremium"))
		{
			ClientPremium clientPremium = new ClientPremium(aId, aPassword, aAge, premimumDiscount);
			System.out.println("ClientPremium added >> " + clientPremium);
			users.add(clientPremium);
		}

		else if (aType.equals("Programmer"))
		{
			Programmer programmer = new Programmer(aId, aPassword, aAge);
			System.out.println("Programmer added >>: " + programmer);
			users.add(programmer);
		}

		else if (aType.equals("Administrator"))
		{
			Administrator administrator = new Administrator(aId, aPassword, aAge);
			System.out.println("Administrator added >>");
			users.add(administrator);
		}

	}

	/** Adds score to score list **/
	public void addScore(Score aScore)
	{
		scores.add(aScore);
		System.out.println("\nScore added:" + aScore);
	}
	
	/** Create new shopping bag **/
	public Bag createShoppingBag()
	{
		return new Bag();
	}

	/** Checkouts the client with items in the shopping bag **/
	public PurchaseApps checkout(Client aClient, Bag shoppingBag)
	{
		PurchaseApps purchase = null;
		purchase = aClient.buy(shoppingBag, calendar);

		// Register sale in applications
		for (Map.Entry<App, Integer> entry : shoppingBag.getBagItems().entrySet())
		{

			entry.getKey().registerSale(calendar.getTime(), entry.getValue());
		}

		System.out.println("\nPurchase made: " + purchase);
		purchases.add(purchase);
		currentWeek.addPurchase(purchase);
	

		
		return purchase;

	}
		
	/** Checkouts the client with items in the shopping bag **/
	public boolean checkoutSubscription(Client aClient, Bag shoppingBag)
	{
		Subscription subscription = null;
		try
		{
			// Register subscriptions of applications
			for (Map.Entry<App, Integer> entry : shoppingBag.getBagItems().entrySet())
			{
				subscription = aClient.subscribe(entry.getKey(), calendar);
				subscriptions.add(subscription);
				System.out.println("New sub: " + subscription);
			}	
		}

		catch (NullPointerException e)
		{
			System.out.println(e.getMessage());
		}
		
		return true;
	}	
	
	/** Check applications with Weekly discounts **/
	public List<App> checkAppsWithWeeklyDiscounts()
	{
		List<App> appsWithDiscount = new ArrayList<>();

		for(App app : apps)
		{
			if(app.getWeeklyDiscount() != 0)
			{
				appsWithDiscount.add(app);
			}
		}
		return appsWithDiscount;
	}

	/** Check applications with Monthly discounts **/
	public List<App> checkAppsWithMonthlyDiscounts()
	{
		List<App> appsWithDiscount = new ArrayList<>();

		for(App app : apps)
		{
			if(app.getMonthlyAppTypeDiscount() != 0)
			{
				appsWithDiscount.add(app);
			}
		}
		return appsWithDiscount;
	}
		
	/** Value in bag **/
	public double valueInBag(Bag tempBag, Client aClient)
	{
		double purchasevalue = 0;
		if(aClient instanceof ClientPremium)
		{
			purchasevalue = tempBag.valueInBag() * premimumDiscount / 100 ;
		}
		else
		{
			purchasevalue = tempBag.valueInBag();
		}
		
		return purchasevalue;
	}
	
	/** Value saved in purchase **/
	public double savedInPurchase(PurchaseApps aPurchase)
	{
		return aPurchase.getSavedValue();
	}
		
	/** Designates programmer to develop an application **/
	public App designateProgrammer(String aAppName, double aPrice, AppType aAppType, Programmer programmer)
	{
		App app = programmer.developApp(aAppName, aPrice, aAppType, calendar.getTime());
		apps.add(app);
		return app;
	}

	/** Prints earnings by programmer **/
	public void earningsByProgrammer()
	{
		System.out.println("The programmers earnings are:");
		for(Programmer programmer : getProgrammersList())
		{
			System.out.println("Programmer: '" + programmer.getId() +
					"' earned: " + String.format("%,.2f",programmer.getEarnings(this)));
		}
	}

	/* Find Methods */
	/** Verify if User exists **/
	public boolean userExists(String aId)
	{
		boolean exists = false;
		for(User user: users)
		{
			if(user.getId().equals(aId))
			{
				exists = true;
			}
		}
		return exists;
	}
	
	/** Returns user object **/
	public User findUser(String aUserId)
	{
		User returnUser = null;
		for(User user: users)
		{
			if(user.getId().equals(aUserId))
			{
				returnUser = user;
			}
		}
		return returnUser;
	}

		
	/** Returns the list of applications of one chosen Type **/
	public List<App> listAppsByType(AppType aType)
	{
		List<App> listType =  new ArrayList<>();

		for (App app : apps)
		{
			if (app.getType().equals(aType))
			{
				listType.add(app);
			}
		}
		return listType;
	}

	/** Returns all Purchases made in a time period **/
	public List<Purchase> listPurchasesTimePeriod(Date aStart, Date aEnds)
	{
		List<Purchase> tempList = new ArrayList<>();
		for (Purchase purchase : purchases)
		{
			if(purchase.getBuyDate().after(aStart) && purchase.getBuyDate().before(aEnds))
			{
				tempList.add(purchase);
			}
		}
		return tempList;
	}

	/** Returns the list of applications in AppStore ordered by {Name Sold Score} **/
	public List<App> orderAppsBy(String aCase)
	{
		List<App> returnList = new ArrayList<>();

		switch (aCase)
		{

		case "Name":
			Comparator<App> compareByName = Comparator
			.comparing(App::getName);
			returnList = apps.stream().sorted(compareByName).collect(Collectors.toList());
			break;

		case "Sold":
			Comparator<App> compareBySold = Comparator
											.comparing(App::timesSold)
											.thenComparing(App::getName)
											.reversed();

			returnList = apps.stream().sorted(compareBySold).collect(Collectors.toList());
			break;

		case "Score":
			Comparator<App> compareByScore = Comparator
												.comparing(App::getScore)
												.thenComparing(App::getName)
												.reversed();

			returnList = apps.stream().sorted(compareByScore).collect(Collectors.toList());
			break;
		}
		return returnList;
	}

	/** Returns global earnings **/
	public double totalStoreEarnings()
	{
		double sum = 0;
		for(Purchase purchase : purchases)
		{
			sum += purchase.calculateValue();
		}
		return sum;
	}
	
	/** update application discounts based Application type Month **/
	private void updateAppMonthlyDiscounts(int discountValue)
	{
		resetAllMonthlyAppDiscounts();
		for(App app : apps)
		{
			if(app.getType() == appTypeWithDiscount) 
			{
				app.setMonthlyAppTypeDiscount(discountValue);
			}
		}
	}
	
	/** Reset application discounts of applications **/
	public void resetAllMonthlyAppDiscounts()
	{
		for(App app : apps)
		{
			app.setMonthlyAppTypeDiscount(0);
		}
	}
	
	/* Week Methods */
	/** Returns week object **/
	public WeekAnalyst returnWeekObject(int aWeekNumber)
	{
		WeekAnalyst returnWeek = null;
		for(WeekAnalyst week: weeks)
		{
			if(week.getWeekNumber() == aWeekNumber)
			{
				returnWeek = week;
			}
		}
		return returnWeek;
	}
	
	/** returns list purchases done in X week **/
	public List<Purchase> getWeekPurchases(int aWeekNumber)
	{
		return returnWeekObject(aWeekNumber).getWeekPurchases();

	}

	/** returns list of less sold application X week **/
	public HashMap<App, Integer> getWeekLessSoldApps(int aWeekNumber, int aNumberOfApps)
	{
		if(currentWeek.getWeekNumber() == 1)
		{
			for(List<WeekAnalyst> weeksYear : years) 
			{
				for(WeekAnalyst week : weeksYear) 
				{
					if(week.getWeekNumber() == 52 && week.getYear() == calendar.get(Calendar.YEAR) -1)
					{
						return week.lessSoldApps(this, aNumberOfApps);
					}
				}
			}
		}
		else 
		{
			return returnWeekObject(aWeekNumber).lessSoldApps(this, aNumberOfApps);	
		}
		return null;
	}

	/** finds last week less sold applications and applies discounts **/
	public void updateWeeklyDiscounts() 
	{
		// find previous week number
		int previousWeek = currentWeek.getWeekNumber() - 1;
		
		// get less sold applications previous week
		HashMap<App, Integer> appsToDiscount = getWeekLessSoldApps(previousWeek, 5);
		
		// apply discounts
		currentWeek.updateAppWeeklyDiscounts(this, appsToDiscount, discountValueMonth);
	}
	
	
	/** Clients with Incentive Discount */
	public List<Client> clienstWithIncentiveDiscount()
	{
		List<Client> clientsWithIcentive = new ArrayList<Client>();
		for(User user: users)
		{
			if(user instanceof Client)
			{
				if(((Client) user).hasIncentiveDiscount())
				{
					clientsWithIcentive.add((Client)user);	
				}
			}
		}
		return clientsWithIcentive;
	}
	
	/** Clients Invited by Client **/
	public List<Client> clientsInvited(Client aClient)
	{
		return aClient.getInvitedClients();
	}
		
	/* Change subscription */
	/** Upgrades the normal client to Premium 
	 * Transfers the data to a new Client object**/
	public ClientPremium changeToPremium(Client aClient, String userPassword)
	{
		
		String id = aClient.getId();
		int age = aClient.getAge();
		double averageScore = aClient.getAverageScore();
		double spendings = aClient.getSpendings();
				
		List<Purchase> purchases = aClient.getPurchases();
		List<Client> invitedclients = aClient.getInvitedClients();
		List<Score> scoresGiven = aClient.getScores();
		Map<App, Integer> appsbought = aClient.getApps();
		
		getUsersList().remove(aClient);
		
		addUser("ClientPremium", id, userPassword, age);
		
		ClientPremium clientPremium = (ClientPremium) findUser(id);
		clientPremium.setAverageScore(averageScore);
		clientPremium.setSpendings(spendings);
		
		clientPremium.setPurchases(purchases);
		clientPremium.setInvitedClients(invitedclients);
		clientPremium.setApps(appsbought);
		clientPremium.setScores(scoresGiven);
		
		return clientPremium;
	}
	
	/** Downgrades the premium client to normal client 
	 * Transfers the data to a new Client object**/
	public Client changeToClient(ClientPremium aClientPremium, String userPassword)
	{
		String id = aClientPremium.getId();
		int age = aClientPremium.getAge();
		double averageScore = aClientPremium.getAverageScore();
		double spendings = aClientPremium.getSpendings();
			
		List<Purchase> purchases = aClientPremium.getPurchases();
		List<Client> invitedclients = aClientPremium.getInvitedClients();
		List<Score> scoresGiven = aClientPremium.getScores();
		Map<App, Integer> appsbought = aClientPremium.getApps();
		
		getUsersList().remove(aClientPremium);
		
		addUser("Client", id, userPassword, age);
		
		Client client = (ClientPremium) findUser(id);
		client.setAverageScore(averageScore);
		client.setInvitedClients(invitedclients);
		client.setApps(appsbought);
		client.setScores(scoresGiven);
		client.setSpendings(spendings);
		client.setPurchases(purchases);
		
		return client;
	}
	
	
	/** Returns free applications chosen by users  **/
	public Map<Client, List<App>> freeAppsChosenByClients()
	{
		Map<Client, List<App>> freeAppsList = new HashMap<Client, List<App>>();
		for(Client client : getClientsList())
		{
			if(!client.getFreeApps().isEmpty())
			{
				freeAppsList.put(client, client.getFreeApps());
			}
		}
		return freeAppsList;
	}
	
	
	/** Check subscriptions date **/
	private List<Subscription> checkSubsOutDate()
	{
		List<Subscription> subsOutOfDate = new ArrayList<Subscription>();
		
		// find out of date subs
		for(Subscription sub : subscriptions)
		{
			if(!sub.isSubscriptionValid(calendar))
			{
				subsOutOfDate.add(sub);
			}
		}
		
		return subsOutOfDate;
	}
	
	/** Remove out of date subscriptions from subscriptions list and from user **/
	private boolean processOutOfDateSubscriptions(List<Subscription> aSubsOutOfDate)
	{
		try 
		{
			for(Subscription sub : aSubsOutOfDate)
			{
				sub.holtSubscription(this);
			}
			return true;
		}
		catch(Exception e)
		{
			e.getStackTrace();
			return false;
		}
	
	}
	
	/** Remove out of date subscriptions from subscriptions list and from user **/
	private boolean updateSubs()
	{
		return processOutOfDateSubscriptions(checkSubsOutDate());
	}
	
	
	// Setters
 	public void setName(String aName)
	{
		name = aName;
	}
	public void setScores(List<Score> aScores)
	{
		scores = aScores;
	}
	public void setUsers(List<User> aUsers)
	{
		users = aUsers;
	}
	public void setAppTypeWithDiscount(AppType appTypeWithDiscount) {
		this.appTypeWithDiscount = appTypeWithDiscount;
	}
	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}

	
	//Getters
	public String getName()
	{
		return name;
	}	
	public List<App> getAppsList()
	{
		return apps;
	}		
	public List<Purchase> getPurchases()
	{
		return purchases;
	}
	public List<Score> getScores()
	{
		return scores;
	}
	public List<User> getUsersList()
	{
		return users;
	}
	public int getDiscountValueWeek() {
		return discountValueWeek;
	}
	public int getPremimumDiscount() {
		return premimumDiscount;
	}
	public int getDiscountValueMonth() {
		return discountValueMonth;
	}
	public WeekAnalyst getCurrentWeek()
	{
		return currentWeek;
	}	
	public int getCurrentMonth() {
		return currentMonth;
	}
	public List<WeekAnalyst> getWeeks()
	{
		return weeks;
	}
	public AppType getAppTypeWithDiscount() {
		return appTypeWithDiscount;
	}
	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	/** Returns ClientPremium list **/
	public List<Programmer> getProgrammersList()
	{
		List<Programmer> returnClientPremium = new ArrayList<>();
		for (User user: users)
		{
			if (user instanceof Programmer)
			{
				returnClientPremium.add((Programmer) user);
			}
		}
		return returnClientPremium;
	}	
	/** returns list application sales done in X week **/
	public Map<App, Integer> getWeekSales(int aWeekNumber)
	{
		return returnWeekObject(aWeekNumber).getAppSales();

	}
	/** Returns Client list **/
	public List<Client> getClientsList()
	{
		List<Client>  returnclients = new ArrayList<>();
		for (User user: users)
		{
			if (user instanceof Client)
			{
				returnclients.add((Client) user);
			}
		}
		return returnclients;
	}
	/** Returns ClientPremium list **/
	public List<ClientPremium> getClientsPremiumList()
	{
		List<ClientPremium>  returnClientPremium = new ArrayList<>();
		for (User user: users)
		{
			if (user instanceof ClientPremium)
			{
				returnClientPremium.add((ClientPremium) user);
			}
		}
		return returnClientPremium;
	}
	
}
