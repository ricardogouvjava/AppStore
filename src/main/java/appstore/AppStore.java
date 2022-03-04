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
import java.util.Random;
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
	private List<Client> clients; 					// List of clients normals in the store
	private List<ClientPremium> clientsPremium;	 	// List of clients in the store
	private List<Programmer> programmers; 			// List of programmers in the store
	private List<Score> scores; 						// List of all scores given to the applications

	public AppStore(String aName)
	{	
		name = aName;
		date = LocalDate.now();
		calendar = Calendar.getInstance();
		currentWeeK = calendar.get(Calendar.WEEK_OF_YEAR);
		apps = new ArrayList<App>();
		purchases = new ArrayList<Purchase>();
		users = new ArrayList<User>();
		clients = new ArrayList<Client>();
		clientsPremium = new ArrayList<ClientPremium>(); 
		programmers = new ArrayList<Programmer>();
		scores = new ArrayList<Score>();
	}

	// Methods

	/** Ads new user to AppStore **/
	public void addUser(String aType, String aName, int aAge)
	{
		if (aType.equals("Client"))
		{
			Client client = new Client(aName, aAge);
			clients.add(client);
			users.add(client);
		}
		else if (aType.equals("Programmer"))
		{
			Programmer programmer = new Programmer(aName, aAge);
			programmers.add(programmer);
			users.add(programmer);	
		}
		else if (aType.equals("ClientPremium"))
		{
			ClientPremium clientPremium = new ClientPremium(aName, aAge);
			clientsPremium.add(clientPremium);
			users.add(clientPremium);	
		}
	}

	/** Designates programmer to develop an application **/
	public void designateProgrammer(String aAppName, double aPrice, AppType aAppType, String aProgrammerName)
	{
		for(Programmer programer: programmers)
		{
			if(programer.getName().equals(aProgrammerName))
			{
				apps.add(programer.developApp(aAppName, aPrice, aAppType));
			}
		}
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
		Purchase tempPurchace = new Purchase(aClient, shoppingBag, calendar.getTime());
		
		for (App app : shoppingBag.getAppsInBag()) 
		{
			findClient(aClient).buy(app.getName());
			app.registerSale(calendar.getTime());
		}
		purchases.add(tempPurchace);
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
	public void earningsByProgrammer()
	{	
		System.out.println("The programmers earnings are:");
		for(Programmer programmer : programmers)
		{
			System.out.println("Programmer: '" + programmer.getName() + "' earned: " + programmer.getEarnings());
		}
	}

	/** Adds score to score list **/
	public void addScore(Score aScore)
	{
		scores.add(aScore);
	}

	/** Finds user in list and return object user **/
	public User findUser(String aName)
	{	
		User returnuser = null;
		for (User user: users)
		{
			if (user.getName().equals(aName))
			{
				returnuser = user;
			}
		}
		return returnuser;
	}
	
	/** Finds client in list and return object client **/
	public Client findClient(String aName)
	{	
		Client returnclient = null;
		for (Client client: clients)
		{
			if (client.getName().equals(aName))
			{
				returnclient = client;
			}
		}
		return returnclient;
	}

	/** Prints all Users in the AppStore **/
	public void  listUsers()
	{
		System.out.println("Users:");
		for (User user: users)
		{
			System.out.println(user);
		}
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
	public List<Purchase> listPurchasesByWeek(int aWeek)
	{
		List<Purchase> tempList = new ArrayList<Purchase>();
		for(Purchase purchase : purchases) 
		{
			Calendar cal = Calendar.getInstance();
		    cal.setTime(purchase.getBuyDate());
			
		    if (cal.get(Calendar.WEEK_OF_YEAR) == aWeek) 
			{
				tempList.add(purchase);
			}
		}
		return tempList;
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
	
	/** Finds 5 least sold applications in a determined time **/
	
	/*
	public HashMap<String, Integer> checkLessSoldApps(Date aStart, Date aEnds){

		Map<String, Integer> appsSoldInPeriod = new HashMap<String, Integer>();
		
		// finds all applications sold in time period
		for(App app : apps)
		{
			int count = 0;
			
			{
				if(date.after(aStart) && date.before(aEnds))
				{
					count += 1;
					appsSoldInPeriod.put(app.getName(), count);
				}
			}
		}
		
		// function to sort hashmap by values
		HashMap<String, Integer> tempmaplist = appsSoldInPeriod.entrySet()
				.stream()
				.sorted((i1, i2) -> i1.getValue().compareTo(i2.getValue()))
				.limit(5)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
		
		return tempmaplist;

	}
	*/

	/** Finds 5 least sold applications in a determined week **/
	public HashMap<String, Integer> checkLessSoldApps(int aWeek, int appNumber)
	{
		Map<String, Integer> appsSoldInWeek = new HashMap<String, Integer>();
		
		
		// function to sort hashmap by values
		HashMap<String, Integer> tempmaplist = appsSoldInWeek.entrySet()
				.stream()
				.sorted((i1, i2) -> i1.getValue().compareTo(i2.getValue()))
				.limit(appNumber)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
		
		return tempmaplist;

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
					updateAppDiscounts(tempWeek);
					currentWeeK = tempWeek;
				}
				else
				{
					calendar.add(Calendar.DATE, 1);
					System.out.println("New Day");
					Random rand = new Random();
					int randBuys = rand.nextInt(3);
					
					for(int j = 0 ; j <= randBuys ; j++)
					{
						generatePurchase();
					}

				}
			}
		}
		else {
			inform = "Invalid date";
		}
		return inform;
	}
	
	/** update application discounts based on performance of previous week
	 * @param i **/
	public void updateAppDiscounts(int aWeek)
	{
		Map<String, Integer> lessSold = checkLessSoldApps(aWeek , 5);
		giveDiscount(lessSold.keySet());
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
	public void giveDiscount(Set<String> aApps)
	{

		for(App appToChange : apps) 
		{
			for(String appNameToCompare : aApps) 
			{
				if(appToChange.getName().equals(appNameToCompare))
				{
					appToChange.setDiscount(15);
				}
				
			}
		}
	}
	
	/** Generates purchases and checkout**/
	public void generatePurchase()
	{
		Random rand = new Random();
		
		int numberApps = rand.nextInt(3);
		Bag tempBag = new Bag();
		Client randomClient = clients.get(rand.nextInt(clients.size()));
		
		for(int i = 0; i <= numberApps; i++)
		{	
			App randomApp =  apps.get(rand.nextInt(apps.size()));
			tempBag.putInBag(randomApp, 1);
		}
		checkout(randomClient.getName(), tempBag);
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

	public void setClients(List<Client> aClients)
	{
		clients = aClients;
	}

	public void setProgrammers(List<Programmer> aProgrammers)
	{
		programmers = aProgrammers;
	}

	public void setClientsPremium(List<ClientPremium> aClientsPremium)
	{
		clientsPremium = aClientsPremium;
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

	public List<App> getApps() 
	{
		return apps;
	}

	public List<User> getUsers() 
	{
		return users;
	}

	public List<Client> getClients()
	{
		return clients;
	}

	public List<Programmer> getProgrammers() 
	{
		return programmers;
	}

	public List<ClientPremium> getClientsPremium() 
	{
		return clientsPremium;
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

