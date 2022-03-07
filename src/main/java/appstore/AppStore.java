package appstore;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AppStore 
{
	private String name;									// Store name
	private LocalDate date;							// When AppStore starts existing
	private static int currentWeeK;
	private static Calendar calendar;
	private List<App> apps;							// Applications in the store
	private List<Purchase> purchases; 				// Purchases made in the store
	private List<User> users;						// List of all users in the store
	private List<Score> scores; 						// List of all scores given to the applications
	private static Generator generator;
	private int discountValueWeek = 15;
	
	public AppStore(String aName)
	{	
		name = aName;
		date = LocalDate.now();
		calendar = Calendar.getInstance();
		currentWeeK = calendar.get(Calendar.WEEK_OF_YEAR);
		apps = new ArrayList<App>();
		purchases = new ArrayList<Purchase>();
		users = new ArrayList<User>();
		scores = new ArrayList<Score>();
		generator = new Generator(this);
	}

	// Methods

	/** Ads new user to AppStore **/
	public void addUser(String aType, String aFirstName, String aLastName, int aAge)
	{
		if (aType.equals("Client"))
		{
			Client client = new Client(aFirstName, aLastName, aAge);
			System.out.println("Client added: " + client);
			users.add(client);
		}
		
		else if (aType.equals("ClientPremium"))
		{
			ClientPremium clientPremium = new ClientPremium(aFirstName, aLastName, aAge);
			System.out.println("ClientPremium added: " + clientPremium);
			users.add(clientPremium);	
		}
		
		else if (aType.equals("Programmer"))
		{
			Programmer programmer = new Programmer(aFirstName, aLastName, aAge);
			System.out.println("Programmer: " + programmer);
			
			users.add(programmer);	
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

	/** Ads an application item to a give shopping bag **/
	public void addtobag(String aName, Bag aBag, int aNumber) 
	{
		for (App app : apps)
		{
			if(app.getName().equals(aName))
			{
				aBag.putInBag(app, aNumber);
			}
		}
	}

	/** Checkouts the client with items in the shopping bag **/
	public void checkout(String aClient, Bag shoppingBag)
	{	
		Purchase purchase = new Purchase(aClient, shoppingBag, calendar.getTime());
		
		try 
		{
			chekIfClient(aClient).buy(purchase);
			for (App app : shoppingBag.getAppsInBag()) 
			{
				
				app.registerSale(calendar.getTime());
			}
			System.out.println("Purchase made: " + purchase );
			purchases.add(purchase);
		}
		catch (NullPointerException e) 
		{
			System.out.println(e.getMessage());
		}
		
	}
	
	/** Prints the times an application was sold **/
	public int timesAppSold(String aAppName)
	{
		int timesSold = 0;
		for (App app: apps)
		{
			if (app.getName().equals(aAppName))
			{
				timesSold = app.getTimesSold();
			}
		}
		return timesSold;
	}

	/** Prints the list of applications of one chosen Type **/ 
	public void listAppsByType(AppType aType)
	{
		List<App> listType =  new ArrayList<App>();

		for (App app : apps)
		{
			if (app.getType().equals(aType))
			{
				listType.add(app);
			}
		}
		System.out.println("The apps of type '" + aType + "' in the apps store are: " + listType);
	}

	/** Prints the list of applications in AppStore ordered by {Name Sold Score} **/ 
	public void listAppsBy(String aCase)
	{
		switch (aCase)
		{

		case "Name":
			System.out.println("\nApps Listed by Name:");
			apps.stream()
			.map(s -> s.getName())
			.sorted()
			.forEach(System.out::println);
			break;

		case "Sold":
			System.out.println("\nApps listed by times sold:");
			Comparator<App> compareBySold = Comparator
					.comparing(App::getTimesSold)
					.thenComparing(App::getName)
					.reversed();

			apps.stream()
			.sorted(compareBySold)
			.forEach(s -> System.out.println(s.getName() +":" + s.getTimesSold()));
			break;

		case "Score":
			System.out.println("\nApps listed by score:");
			Comparator<App> compareByScore = Comparator
					.comparing(App::getAverageScore)
					.thenComparing(App::getName)
					.reversed();

			apps.stream()
			.sorted(compareByScore)
			.forEach(s -> System.out.println(s.getName() +":" + s.getAverageScore()));
			break;
		}
	}

	/** Prints list of all the scores of a given application **/
	public void listAppScores(String aAppName)
	{
		for (App app : apps)
		{
			if (!app.getScores().isEmpty() && app.getName().equals(aAppName))
			{
				System.out.println("The existing scores for the aplication '" + app.getName() + "' are: ");
				for(Score score: app.getScores())
				{
					System.out.println("Score: " + score.getScoreValue() + " Comment: " + score.getComment());
				}
			}
		}
	}

	/** Prints list of all applications of a user **/
	public void listUserApp(String aUser)
	{
		for (User user : users)
		{
			if (user.getName().equals(aUser) && user instanceof Client)
			{
				System.out.println("The apps the client " + user.getName() + ","
						+ " has bought are: " + ( (Client)user).getAppsbought());
			}
			else if (user.getName().equals(aUser) && user instanceof Programmer)
			{
				System.out.print("\nThe apps the programmer " + user.getName() + ","
						+ "has made are: " + ( (Programmer)user).getDeveloppedApps());
			}
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
	public void earningsByProgrammer(AppStore aStore)
	{	
		System.out.println("The programmers earnings are:");
		for(Programmer programmer : getProgrammersList())
		{
			System.out.println("Programmer: '" + programmer.getName() + "' earned: " + programmer.getEarnings(aStore));
		}
	}

	/** Adds score to score list **/
	public void addScore(Score aScore)
	{
		scores.add(aScore);
	}

	/** Prints all Purchases made in the AppStore **/
	public void listPurchases()
	{
		System.out.println("Purchases:");
		for (Purchase purchase : purchases)
		{
			System.out.println(purchase);
		}
	}
	
	/** Returns all Purchases made in a certain week **/
	public List<Purchase> getWeekPurchases(int aWeek)
	{
		List<Purchase> tempList = new ArrayList<Purchase>();
		for(Purchase purchase : purchases) 
		{
			if(purchase.getWeekPurchase() == aWeek)
			{
				tempList.add(purchase);
			}
		}
		return tempList;
	}
	
	/** Returns count of all application sold made in a certain week **/
	public HashMap<String, Integer> getAppsSoldInWeek(int aWeek)
	{
		HashMap<String, Integer> weekAppSales = new HashMap<String, Integer>();
		List<Purchase> weekPurchases = getWeekPurchases(aWeek);
			
		for(Purchase purchase : weekPurchases)
		{
			//Map of bag items
			Map<App, Integer> bag = purchase.getPurchaseBag().getBagData();
			
			for(Map.Entry<App, Integer> entry : bag.entrySet())
			{
				if(!weekAppSales.containsKey(entry.getKey().getName()))
				{
					weekAppSales.putIfAbsent(entry.getKey().getName(), entry.getValue());
				}
				
				else 
				{
					weekAppSales.put(entry.getKey().getName(), weekAppSales.get(entry.getKey().getName()) + entry.getValue());
				}
			}		
		}
		return weekAppSales;
	
	}
	
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
	
	/** Finds 5 least sold applications in a determined week **/
	public HashMap<String, Integer> checkLessSoldApps(int aWeek, int appNumber)
	{
		HashMap<String, Integer> weekAppSold = getAppsSoldInWeek(aWeek);
		HashMap<String, Integer> weekAppSales = new HashMap<String, Integer>();
		weekAppSales.putAll(weekAppSold);
		
		for(App app : apps) 
		{
			if(!weekAppSales.containsKey(app.getName()))
			{
				weekAppSales.put(app.getName(), 0);
			}
		}
		
		LinkedHashMap<String, Integer> sortedWeekAppSales = new LinkedHashMap<>();
	    
		weekAppSales.entrySet().stream()
								.sorted(Map.Entry.comparingByValue())
								.limit(appNumber)
								.forEachOrdered(x -> sortedWeekAppSales.put(x.getKey(), x.getValue()));
	     
		return sortedWeekAppSales;

	}
	
	/** Moves local date X amount of days forward **/
	public String forwardLocalDateXDays(int aDays) 
	{
		String inform = "";
		if(aDays > 0)
		{
			
			for(int i = 0; i <= aDays; i++)
			{
				int tempWeek = calendar.get(Calendar.WEEK_OF_YEAR);
				
				if(tempWeek != currentWeeK)
				{
					System.out.println("Week:" + calendar.get(Calendar.WEEK_OF_YEAR));
					updateAppDiscounts(tempWeek , discountValueWeek);
					currentWeeK = tempWeek;
				}

				calendar.add(Calendar.DATE, 1);
				System.out.println("New Day");
				
				generator.generateDaysData();

			}
		}
		else {
			inform = "Invalid date";
		}
		return inform;
	}
	
	/** update application discounts based on performance of previous week **/
	public void updateAppDiscounts(int aWeek, int discountValue)
	{
		Map<String, Integer> lessSold = new HashMap<String, Integer>(5);
		lessSold = checkLessSoldApps(aWeek , 5);
		giveDiscount(lessSold.keySet(), discountValue);
	}
	
	/** Check applications with discounts **/
	public List<String> checkAppsWithDiscounts()
	{
		List<String> appsWithDiscount = new ArrayList<String>();
		
		for(App app : apps) 
		{
			if(app.getDiscount() != 0) 
			{
				appsWithDiscount.add(app.getName());
			}
		}
		
		return appsWithDiscount;
	}
	
	/** Reset discounts **/
	public void resetAllAppDiscounts() 
	{
		for(App app : apps) 
		{
			app.setDiscount(0);
		}
	}
	
	/** Give discount 15% **/
	public void giveDiscount(Set<String> aApps, int discountValue)
	{
		for(App appToChange : apps) 
		{
			for(String appNameToCompare : aApps) 
			{
				if(appToChange.getName().equals(appNameToCompare))
				{
					appToChange.setDiscount(discountValue);
				}
				
			}
		}
	}
	
	
	/** Methods that verify **/
		
	/** Verify if user exists and is Client, returns object client **/
	public Client chekIfClient(String aName)
	{	
		Client returnclient = null;
		for (User user: users)
		{
			if (user.getName().equals(aName) && user instanceof Client)
			{
				returnclient = (Client) user;
			}
		}
		return returnclient;
	}
	
	/** Verify if user exists and is ClientPremium, returns object client **/
	public ClientPremium chekIfClientPremium(String aName)
	{	
		ClientPremium returnclient = null;
		for (User user: users)
		{
			if (user.getName().equals(aName) && user instanceof ClientPremium)
			{
				returnclient = (ClientPremium) user;
			}
		}
		return returnclient;
	}
	
	/** Verify if user exists and is Programmer, returns object **/
	public Programmer chekIfProgrammer(String aName)
	{	
		Programmer returnProgrammer = null;
		for (User user: users)
		{
			if (user.getName().equals(aName) && user instanceof Programmer)
			{
				returnProgrammer = (Programmer) user;
			}
		}
		return returnProgrammer;
	}
	
	
	/** Methods that return Lists **/
	
	/** Return Client list **/
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
	
	/** Return ClientPremium list **/
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
	
	/** Return ClientPremium list **/
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
	
	/** Methods that return variable **/
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

	public void setDate(LocalDate aDate) {
		date = aDate;
	}

	public void setCurrentWeeK(int aCurrentWeeK) {
		currentWeeK = aCurrentWeeK;
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

	public LocalDate getDate() {
		return date;
	}

	public  int getCurrentWeeK() {
		return currentWeeK;
	}

}

