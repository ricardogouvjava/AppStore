package appstore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AppStore 
{
	private String name;								// Store name
	private static Calendar calendar;					// running calendar
	private List<App> apps;								// Applications in the store
	private List<Purchase> purchases; 					// Purchases made in the store
	private Week week; 									
	private List<User> users;							// List of all users in the store
	private List<Score> scores; 						// List of all scores given to the applications
	private static Generator generator;
	private int discountValueWeek = 15;
	
	
	public AppStore(String aName)
	{	
		name = aName;
		calendar = Calendar.getInstance();
		apps = new ArrayList<App>();
		purchases = new ArrayList<Purchase>();
		week = new Week(calendar);
		users = new ArrayList<User>();
		scores = new ArrayList<Score>();
		generator = new Generator(this);
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
		for(int i = 0; i <= aDays; i++)
		{
			
			// when in new week updates discounts
			if(calendar.get(Calendar.WEEK_OF_YEAR) != week.weekNumber())
			{
				week.setWeek(calendar);
				
				System.err.println("Week:" + calendar.get(Calendar.WEEK_OF_YEAR));
				
				updateAppsSoldPreviousWeekCounter();
				updateAppDiscounts(tempWeek , discountValueWeek);
				week.resetWeek(calendar);
				
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
			System.out.println("Client added: " + client);
			users.add(client);
		}
		
		else if (aType.equals("ClientPremium"))
		{
			ClientPremium clientPremium = new ClientPremium(aId, aPassword, aAge);
			System.out.println("ClientPremium added: " + clientPremium);
			users.add(clientPremium);	
		}
		
		else if (aType.equals("Programmer"))
		{
			Programmer programmer = new Programmer(aId, aPassword, aAge);
			System.out.println("Programmer: " + programmer);
			users.add(programmer);	
		}
		
		else if (aType.equals("Administrator"))
		{
			Administrator administrator = new Administrator(aId, aPassword, aAge);
			System.out.println("Administrator created");
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
			
			System.out.println("Purchase made: " + purchase);
			purchases.add(purchase);
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
	
	
	/* Application Sold Methods & analysis */
	
	
	
	/** update application discounts based on performance of previous week **/
	private void updateAppDiscounts(int aWeek, int discountValue)
	{
		Map<App, Integer> lessSold = new HashMap<App, Integer>(5);
		lessSold = checkLessSoldApps(aWeek , 5);
		giveDiscount(lessSold.keySet(), discountValue);
	}
		
	
	/** Finds 5 least sold applications in a determined week **/
	public HashMap<App, Integer> checkLessSoldApps(int aWeek, int appNumber)
	{
		HashMap<App, Integer> weekAppSold = getAppsSoldInWeek(aWeek);
		HashMap<App, Integer> weekAppSales = new HashMap<App, Integer>();
		weekAppSales.putAll(weekAppSold);
		
		for(App app : apps) 
		{
			if(!weekAppSales.containsKey(app))
			{
				weekAppSales.put(app, 0);
			}
		}
		
		LinkedHashMap<App, Integer> sortedWeekAppSales = new LinkedHashMap<>();
	    
		weekAppSales.entrySet().stream()
								.sorted(Map.Entry.comparingByValue())
								.limit(appNumber)
								.forEachOrdered(x -> sortedWeekAppSales.put(x.getKey(), x.getValue()));
	     
		return sortedWeekAppSales;
		
		/**
		 * 	System.out.println("\nApps listed by times sold:");
			Comparator<App> compareBySold = Comparator
					.comparing(App::timesSold)
					.thenComparing(App::getName)
					.reversed();

			apps.stream()
			.sorted(compareBySold)
			.forEach(s -> System.out.println(s.getName() +":" + s.timesSold()));
			break;
		 */

	}
	
	/** Returns list of what each application sold last Week  **/
	public Map<App, Integer> listAppsSoldLastWeek()
	{	
		//SortedMap<App, Integer> soldLastWeek = new TreeMap<App, Integer>( new LessSoldAppComparator());
		
		Map<App, Integer> soldLastWeek = new HashMap<App, Integer>();
		
		
		for(App app : apps)
		{
			soldLastWeek.put(app, app.getTimesSoldLastWeek());
		}
		return soldLastWeek;
		
	}
	
	/** 5 Less sold applications **/
	public List<String> lessSoldApps() 
	{
		Map<App, Integer> soldLastWeek = listAppsSoldLastWeek();
		List<String> lessSoldAppsLastWeek = new ArrayList<String>(5);
		soldLastWeek.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.limit(5)
				.forEachOrdered(x -> lessSoldAppsLastWeek.add(x.getKey().getName()));
		return lessSoldAppsLastWeek;
	}
	
	/** Returns count of all application sold made in a certain week **/
	public HashMap<App, Integer> getAppsSoldInWeek(int aWeek)
	{
		HashMap<App, Integer> weekAppSales = new HashMap<App, Integer>();
		List<Purchase> weekPurchases = getWeekPurchases(aWeek);
			
		for(Purchase purchase : weekPurchases)
		{
			//Map of bag items
			Map<App, Integer> bag = purchase.getPurchaseBag().getBagItems();
			
			for(Map.Entry<App, Integer> entry : bag.entrySet())
			{
				if(!weekAppSales.containsKey(entry.getKey()) && entry.getValue() > 0)
				{
					weekAppSales.putIfAbsent(entry.getKey(), entry.getValue());
				}
				
				else 
				{
					weekAppSales.put(entry.getKey(), weekAppSales.get(entry.getKey()) + entry.getValue());
				}
			}		
		}
		return weekAppSales;
	
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

	public void setPurchases(List<Purchase> aPurchases) 
	{
		purchases = aPurchases;
	}

	public void setScores(List<Score> aScores) 
	{
		scores = aScores;
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

	public Week getWeek()
	{
		return week;
	}

}

