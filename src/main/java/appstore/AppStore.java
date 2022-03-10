package appstore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AppStore 
{
	private String name;								// Store name
	private static Calendar calendar;					// running calendar
	private List<App> apps;								// Applications in the store
	private Week currentWeek; 
	private List<Week> weeks;
	private List<User> users;							// List of all users in the store
	private List<Purchase> purchases; 					// Purchases made in the store
	private List<Score> scores; 						// List of all scores given to the applications
	private static Generator generator;
	private static int discountValueWeek = 15;
	private static int premimumDiscount = 40;
	
	
	public AppStore(String aName)
	{	
		name = aName;
		calendar = Calendar.getInstance();
		apps = new ArrayList<App>();
		currentWeek = new Week(calendar);
		weeks = new ArrayList<Week>();
		users = new ArrayList<User>();
		purchases = new ArrayList<Purchase>();
		scores = new ArrayList<Score>();
		generator = new Generator(this);
		discountValueWeek = 15;
		premimumDiscount = 40;
	}

	/* Methods :
	 * Functional methods
	 * addUser(String aType, String aId, String aPassword, int aAge)
	 * designateProgrammer(String aAppName, double aPrice, AppType aAppType, Programmer programmer)
	 * createShoppingBag()
	 * checkout(Client aClient, Bag shoppingBag)
	 * 
	 * 
	 * 
	 */
	
	/** Moves local date X amount of days forward **/
	public void forwardDateXDays(int aDays) 
	{		
		for(int i = 1; i <= aDays; i++)
		{
			
			// when in new week updates discounts
			if(calendar.get(Calendar.WEEK_OF_YEAR) != currentWeek.weekNumber())
			{
				weeks.add(currentWeek);
				
				currentWeek = new Week(calendar);
				
				System.err.println("Week:" + calendar.get(Calendar.WEEK_OF_YEAR));
				
				updateAppDiscounts(discountValueWeek);
				
			}

			calendar.add(Calendar.DATE, 1); // Moves a day forward
			
			System.err.println("New Day");
			
			generator.generateDaysData(); // Generates random data for a day
		}
	}
	
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
			ClientPremium clientPremium = new ClientPremium(aId, aPassword, aAge);
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

	/** Designates programmer to develop an application **/
	public void designateProgrammer(String aAppName, double aPrice, AppType aAppType, Programmer programmer)
	{
		apps.add(programmer.developApp(aAppName, aPrice, aAppType));
	}

	/** Create new shopping bag **/
	public Bag createShoppingBag()
	{
		return new Bag();
	}

	/** Checkouts the client with items in the shopping bag **/
	public void checkout(Client aClient, Bag shoppingBag)
	{	
		try 
		{
			Purchase purchase = aClient.buy(shoppingBag, calendar);
			
			// Register sale in applications
			for (Map.Entry<App, Integer> entry : shoppingBag.getBagItems().entrySet()) 
			{
				
				entry.getKey().registerSale(calendar.getTime(), entry.getValue());
			}
			
			System.out.println("\nPurchase made: "
							+ "\n" + purchase);
			purchases.add(purchase);
			currentWeek.addPurchase(purchase);
		}
		
		catch (NullPointerException e) 
		{
			System.out.println(e.getMessage());
		}
		
	}
	
	/** Returns global earnings **/
	public double totalStoreEarnings()
	{
		double sum = 0;
		for(Purchase purchase : purchases)
		{
			sum += purchase.getPrice();
		}
		return sum;
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

	/** Adds score to score list **/
	public void addScore(Score aScore)
	{
		scores.add(aScore);
		System.out.println("\nScore added:" + aScore);
	}

		
	/* Week Methods */
	/** Returns week object **/
	public Week returnWeekObject(int aWeekNumber)
	{
		Week returnWeek = null;
		for(Week week: weeks)
		{
			if(week.weekNumber() == aWeekNumber) 
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
	
	/** returns list application sales done in X week **/
	public Map<App, Integer> getWeekSales(int aWeekNumber)
	{
		return returnWeekObject(aWeekNumber).getAppSales();
		
	}
	
	/** returns list of less sold application X week **/
	public HashMap<App, Integer> getWeekLessSoldApps(int aWeekNumber, int aNumberOfApps)
	{
		return returnWeekObject(aWeekNumber).weekLessSoldApps(apps, aNumberOfApps);
	}
	
		
	/** update application discounts based on performance of previous week **/
	private void updateAppDiscounts(int discountValue)
	{
		resetAllAppDiscounts();
		giveDiscount(this.getWeekLessSoldApps(currentWeek.weekNumber() -1 , 5).keySet(), discountValue);
	}
		
	
	/* Purchase Methods */
			
	/** Returns all Purchases made in a time period **/
	public List<Purchase> listPurchasesTimePeriod(Date aStart, Date aEnds)
	{
		List<Purchase> tempList = new ArrayList<Purchase>();
		for (Purchase purchase : purchases)
		{
			if(purchase.getBuyDate().after(aStart) && purchase.getBuyDate().before(aEnds))
			{
				tempList.add(purchase);
			}
		}
		return tempList;
	}
	
	
	/* Application methods */
	
	/** Verify if application exists **/
	public boolean appExists(String aAppName)
	{
		boolean exists = false;
		for(App app: apps) 
		{
			if(app.getName().equals(aAppName)) 
			{
				exists = true;
			}
		}
		return exists;
	}
	
	/** Find and return application **/
	public App findApp(String aAppName)
	{
		App application = null;
		for(App app : apps)
		{
			if(app.getName().equals(aAppName));
			application = app;
		}
		return application;
	}
	
	/** Give discount to applications**/
	public void giveDiscount(Set<App> aApps, int discountValue)
	{
		for(App app : aApps) 
		{
			app.setDiscount(discountValue);
		}
	}
	
	/** Reset application discounts of applications **/
	public void resetAllAppDiscounts() 
	{
		for(App app : apps) 
		{
			app.setDiscount(0);
		}
	}
	
	/** Check applications with discounts **/
	public List<App> checkAppsWithDiscounts()
	{
		List<App> appsWithDiscount = new ArrayList<App>();
		
		for(App app : apps) 
		{
			if(app.getDiscount() != 0) 
			{
				appsWithDiscount.add(app);
			}
		}
		return appsWithDiscount;
	}
	
	/** Returns the list of applications in AppStore ordered by {Name Sold Score} **/ 
	public List<App> orderAppsBy(String aCase)
	{
		List<App> returnList = new ArrayList<App>();
		
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
												.comparing(App::getAverageScore)
												.thenComparing(App::getName)
												.reversed();

			returnList = apps.stream().sorted(compareByScore).collect(Collectors.toList());
			break;
		}
		return returnList;
	}

	/** Returns the list of applications of one chosen Type **/ 
	public List<App> listAppsByType(AppType aType)
	{
		List<App> listType =  new ArrayList<App>();

		for (App app : apps)
		{
			if (app.getType().equals(aType))
			{
				listType.add(app);
			}
		}
		return listType;
	}
	
		
	/* User methods */
	
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
	
	/** Returns Client list **/
	public List<Client> getClientsList()
	{	
		List<Client>  returnclients = new ArrayList<Client>();
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
		List<ClientPremium>  returnClientPremium = new ArrayList<ClientPremium>();
		for (User user: users)
		{
			if (user instanceof ClientPremium)
			{
				returnClientPremium.add((ClientPremium) user);
			}
		}
		return returnClientPremium;
	}
	
	/** Returns ClientPremium list **/
	public List<Programmer> getProgrammersList()
	{	
		List<Programmer> returnClientPremium = new ArrayList<Programmer>();
		for (User user: users)
		{
			if (user instanceof Programmer)
			{
				returnClientPremium.add((Programmer) user);
			}
		}
		return returnClientPremium;
	}
	
	// Setters
	public void setName(String aName) 
	{
		name = aName;
	}

	public void setApps(List<App> aApps) 
	{
		apps = aApps;
	}

	public void setUsers(List<User> aUsers) 
	{
		users = aUsers;
	}

	public void setScores(List<Score> aScores) 
	{
		scores = aScores;
	}

	
	
	public static void setDiscountValueWeek(int discountValueWeek) {
		AppStore.discountValueWeek = discountValueWeek;
	}

	public static void setPremimumDiscount(int premimumDiscount) {
		AppStore.premimumDiscount = premimumDiscount;
	}

	// Getters
	public String getName() 
	{
		return name;
	}

	public List<App> getAppsList() 
	{
		return apps;
	}

	public List<User> getUsersList() 
	{
		return users;
	}
	
	public List<Purchase> getPurchases()
	{
		return purchases;
	}

	public List<Score> getScores()
	{
		return scores;
	}

	
	public Week getCurrentWeek() 
	{
		return currentWeek;
	}

	
	public List<Week> getWeeks() 
	{
		return weeks;
	}

	
	public static int getDiscountValueWeek() {
		return discountValueWeek;
	}

	public static int getPremimumDiscount() {
		return premimumDiscount;
	}

	
}

