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
	private static Calendar calendar;					// running calendar
	private static Generator generator;
	private int discountValueWeek = 15;
	static int premimumDiscount = 40;

	private String name;								// Store name
	private List<App> apps;								// Applications in the store
	private Week currentWeek;

	private List<Week> weeks;

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

	private List<User> users;							// List of all users in the store

	private List<Purchase> purchases; 					// Purchases made in the store

	private List<Score> scores; 						// List of all scores given to the applications

	public AppStore(String aName)
	{
		name = aName;
		calendar = Calendar.getInstance();
		apps = new ArrayList<>();
		currentWeek = new Week(calendar);
		weeks = new ArrayList<>();
		users = new ArrayList<>();
		purchases = new ArrayList<>();
		scores = new ArrayList<>();
		generator = new Generator(this);
		discountValueWeek = 15;
	}

	/** Adds score to score list **/
	public void addScore(Score aScore)
	{
		scores.add(aScore);
		System.out.println("\nScore added:" + aScore);
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

	/** Check applications with discounts **/
	public List<App> checkAppsWithDiscounts()
	{
		List<App> appsWithDiscount = new ArrayList<>();

		for(App app : apps)
		{
			if(app.getDiscount() != 0)
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
			purchasevalue = tempBag.valueInBag() * AppStore.premimumDiscount / 100 ;
		}
		else
		{
			purchasevalue = tempBag.valueInBag();
		}
		
		return purchasevalue;
	}
	
	/** Value in bag **/
	public double savedInPurchase(Purchase aPurchase)
	{
		return aPurchase.savedInPurchase();
	}
	
	/** Checkouts the client with items in the shopping bag **/
	public Purchase checkout(Client aClient, Bag shoppingBag)
	{
		Purchase purchase = null;
		try
		{
			purchase = aClient.buy(shoppingBag, calendar);

			// Register sale in applications
			for (Map.Entry<App, Integer> entry : shoppingBag.getBagItems().entrySet())
			{

				entry.getKey().registerSale(calendar.getTime(), entry.getValue());
			}

			System.out.println("\nPurchase made: " + purchase);
			purchases.add(purchase);
			currentWeek.addPurchase(purchase);
		}

		catch (NullPointerException e)
		{
			System.out.println(e.getMessage());
		}
		
		return purchase;

	}

	/** Create new shopping bag **/
	public Bag createShoppingBag()
	{
		return new Bag();
	}

	/** Designates programmer to develop an application **/
	public void designateProgrammer(String aAppName, double aPrice, AppType aAppType, Programmer programmer)
	{
		apps.add(programmer.developApp(aAppName, aPrice, aAppType));
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


	/* Purchase Methods */

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


	/* Application methods */

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

	public List<App> getAppsList()
	{
		return apps;
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


	/** returns list of less sold application X week **/
	public HashMap<App, Integer> getWeekLessSoldApps(int aWeekNumber, int aNumberOfApps)
	{
		return returnWeekObject(aWeekNumber).weekLessSoldApps(apps, aNumberOfApps);
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

	/** Give discount to applications**/
	public void giveDiscount(Set<App> aApps, int discountValue)
	{
		for(App app : aApps)
		{
			app.setDiscount(discountValue);
		}
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
												.comparing(App::getAverageScore)
												.thenComparing(App::getName)
												.reversed();

			returnList = apps.stream().sorted(compareByScore).collect(Collectors.toList());
			break;
		}
		return returnList;
	}

	/** Reset application discounts of applications **/
	public void resetAllAppDiscounts()
	{
		for(App app : apps)
		{
			app.setDiscount(0);
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


	/** update application discounts based on performance of previous week **/
	private void updateAppDiscounts(int discountValue)
	{
		resetAllAppDiscounts();
		giveDiscount(this.getWeekLessSoldApps(currentWeek.weekNumber() -1 , 5).keySet(), discountValue);
	}

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
	public void setPremimumDiscount(int aPremimumDiscount)
	{
		premimumDiscount = aPremimumDiscount;
	}
	public void setDiscountValueWeek(int aDdiscountValueWeek)
	{
		discountValueWeek = aDdiscountValueWeek;
	}
	//Getters
	public String getName()
	{
		return name;
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
	public Week getCurrentWeek()
	{
		return currentWeek;
	}	
	public List<Week> getWeeks()
	{
		return weeks;
	}

}

