package appstore;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Menu
{
	private AppStore store; 
	private static Scanner scanInt, scanText;
	private int menuNumber;
	
	public Menu(AppStore aStore) 
	{
		store =  aStore;
	}
	
	private void startScanners() 
	{
		scanInt = new Scanner(System.in);
		scanText = new Scanner(System.in);	
	}
	
	private void stopScanners() 
	{
		scanInt.close();
		scanText.close();
		
	}
	
	/* Several menu options area:
	 * 1. Main menu
	 * 2. Manager menu
	 * 3. User menu
	 * 4. Buy Application menu
	 */

	/** Main menu options **/
	public void menuMain()
	{
		startScanners();
		
		System.out.print("\n------ Menu ------"
				+ "\n (0) Exit Manager"
				+ "\n (1) Manager Options"
				+ "\n (2) User Options"
				+ "\n (3) Create User"
				+ "\n"
				+ "\n input:");
	
		//asks for input, verifies it and uses it in the switch
		switch (askInputIntAndValidate(0, 3)) 
		{
		case 0:
			stopScanners();
			System.err.print("System will terminate ");
			System.exit(0);
			System.err.print("Terminated");
			break;
	
		case 1:
			//Access Manager options
			menuManager();
			break;
	
		case 2:
			//Access client options
			String clientName = askForClientNameAndValidate();
			Client client = (Client) returnUserObject(clientName, "Client");
			menuUser(client);
			break;
	
		case 3:
			System.out.print("\nUser Creation"
					+ "\nUserType: {Client, ClientPremium, Programmer} ");
			
			String userType = null, userFirstName = null, userLastName = null;
			int userAge = 0;
			
			boolean askForType = true;
			while(askForType)
			{
				System.out.print("\nUserType: ");
				userType = scanText.nextLine();
				if (userType.equals("Client") || userType.equals("ClientPremium") || userType.equals("Programmer"))
				{
					askForType = false;
				}
				else {
					System.out.print("Please input a valid user Type");
				}
			}
	
			boolean askForFirstName = true; 
			
			while(askForFirstName)
			{
				System.out.print("\nUserFirstName: ");
				userFirstName = scanText.nextLine();
				if (userFirstName.length() > 3)
				{
					askForFirstName = false;
				}
				else {
					System.out.print("Please input a valid user first name");
				}
			}
	
			boolean askForLastName = true; 
			
			while(askForLastName)
			{
				System.out.print("\nUserLastName: ");
				userLastName = scanText.nextLine();
				if (userLastName.length() > 3)
				{
					askForLastName = false;
				}
				else {
					System.out.print("Please input a valid user last name");
				}
			}
			
			boolean askForAge = true; 
			
			while(askForAge)
			{
				System.out.print("\nUser Age: ");
				userAge = scanInt.nextInt();
				if(userAge < 120 && userAge > 18)
				{
					askForAge = false;
				}
				else
				{
					System.out.print("Not a valid integer for user creation."
							+ "Please input a valid number.");
				}
			}
	
			store.addUser(userType, userFirstName, userLastName, userAge);
			System.out.print("\nUser added.");
			menuMain();
			break;
	
		default:
			System.out.print("Please introduze a correct option");
			menuMain();
		}
	}
	
	/** Manager menu options **/
	private void menuManager() 
	{
		System.out.print("\n"
				+ "Manager Options:"
				+ "\n  (0) Return"
				+ "\n  (1) List all purchases"
				+ "\n  (2) List purchases by week"
				+ "\n  (3) Total earnings"
				+ "\n  (4) Earnings by programmer"
				+ "\n  (5) List applications owned by User"
				+ "\n  (6) Times an app was sold"
				+ "\n  (7) Apps with discount"
				+ "\n  (8) List applications sold last week"
				+ "\n  (9) TimeFoward"
				+ "\n (10) List all Users"
				+ "\n (11) List all Clients"
				+ "\n (12) List all ClientPremium"
				+ "\n (13) List all Programmers"
				+ "\n"
				+ "\n input:");	
	
		switch (askInputIntAndValidate(0, 13))
		{
	
		case 0:
			// Returns to main menu
			menuMain();
			break;
	
		case 1:
			  // Lists all the purchases in the AppStore 
				System.out.println("Purchases:");
				for (Purchase purchase : store.getPurchases())
				{
					System.out.println(purchase);
				}
			  menuManager(); 
			  break;
		 	
		case 2:
			// Lists all purchases in a certain week of the year
			System.out.println("Wanted week: ");
			int weekNumber =  askInputIntAndValidate(0, store.getCurrentWeeK());
			List<Purchase> purchaseList = store.getWeekPurchases(weekNumber);
			for(Purchase purchase : purchaseList)
			{
				System.out.println(purchase);
			}
			menuManager();
			break;
			
		case 3:
			// Prints the total earnings of the AppStore
			System.out.println("The sales total of the app store is: " + String.format("%2f",store.totalStoreEarnings()));
			menuManager();
			break;		
	
		case 4:
			// Prints the earnings by programmer of the AppStore
			store.earningsByProgrammer();
			menuManager();
			break;	
	
		case 5:
			// List the applications owned by and user
			System.out.println("UserName: ");
			String userName = scanText.nextLine();
			store.listUserApp(userName);
			menuManager();
			break;
	
		case 6:
			// Prints the times an application was sold
			System.out.println("AppName: ");
			String appName = scanText.nextLine();
			System.out.println("The app " + appName + " was sold " + store.timesAppSold(appName) + " times.");
			menuManager();
			break;
		
		case 7:
			// Check applications with discounts
			System.out.println("Apps with discount: " + store.checkAppsWithDiscounts());
			//printList(store.checkAppsWithDiscounts());
			menuManager();
			break;
			
		case 8:
			// Lists applications sold last week
			System.out.println("The applications sold last week are: ");
			for(Entry<App, Integer> entry : store.listAppsSoldLastWeek().entrySet()) 
			{
				System.out.println(entry.getKey() + " >> sold:" + entry.getValue());
			}
			menuManager();
			break;
			
		case 18:
			// finds less sold applications in a defined week	
			System.out.println("Wanted week: ");
			int week =  askInputIntAndValidate(0, store.getCurrentWeeK());
			System.out.println("Number of Apps: ");
			int appnumber =  askInputIntAndValidate(0, store.getAppsSoldInWeek(week).size());
						
			System.out.println("The less sold applications are: ");
			printMap(store.checkLessSoldApps(week, appnumber));
			menuManager();
			break;
			
		case 9:
			// moves time forward
			System.out.println("Introduce number of days: ");
			int numberDays =  askInputIntAndValidate(0, Integer.MAX_VALUE);
			store.forwardDateXDays(numberDays);
			menuManager();
			break;
		
		case 10:
			// Lists all the users in the AppStore
			System.out.println("Users: ");
			printList(store.getUsersList());
			menuManager();
			break;

		case 11:
			// Lists all the Clients in the AppStore
			System.out.println("Clients: ");
			printList(store.getClientsList());
			menuManager();
			break;

		case 12:
			// Lists all the Premium Clients in the AppStore
			System.out.println("ClientPrmium: ");
			printList(store.getClientsPremiumList());
			menuManager();
			break;

		case 13:
			// Lists all the Programmers in the AppStore
			System.out.println("Programmers: ");
			printList(store.getProgrammersList());
			menuManager();
			break;
	
		default:
			// Ask again for input!!
			System.out.println("Please introduze a correct option");
			menuManager();
			break;
		}
	}
	
	/** User menu options **/
	private void menuUser(Client aClient) 
	{	
		System.out.print("\n"
				+ "User Options:"
				+ "\n (0) Return"
				+ "\n (1) List AppStore applications by Type"
				+ "\n (2) List AppStore applications by Name"
				+ "\n (3) List AppStore applications by times Sold"
				+ "\n (4) List AppStore applications by Score"
				+ "\n (5) List onwed applications"
				+ "\n (6) List application that score was given"
				+ "\n (7) List application that score was not given"
				+ "\n (8) List Scores and Comments of an App"
				+ "\n (9) Buy Apps"
				+ "\n (10) Give score"
				+ "\n"
				+ "\n input:");
	
		switch (askInputIntAndValidate(0,7)) 
		{
	
		case 0:
			// Returns to main menu
			menuMain();
			break;
	
		case 1:
			// List Application by type
			menuUserListByType(aClient);
			menuUser(aClient);
			break;
	
		case 2:
			// List Application by name 
			store.listAppsBy("Name");
			menuUser(aClient);
			break;
	
		case 3:
			// List Application by quantity sold 
			store.listAppsBy("Sold");
			menuUser(aClient);
			break;
	
		case 4:
			// List Application by Score
			store.listAppsBy("Score");
			menuUser(aClient);
			break;
	
		case 5:
			// List Application owned by user
			System.out.println("Owned applications:");
			printList(aClient.getAppsbought());
			menuUser(aClient);
			break;
	
		case 6:
			// Lists applications that score was given by user
			System.out.print("\nAppScored: ");
			printList(aClient.getAppsScored());
			menuUser(aClient);
			break;

		case 7:
			// Lists applications that score was given by user
			System.out.print("\nAppNotScored: ");
			printList(aClient.getAppsNotScored());
			menuUser(aClient);
			break;
		
		case 8:
			// List scores and comments of application
			System.out.print("\nAppName: ");
			String appName = scanText.nextLine();
			store.listAppScores(appName);
			menuUser(aClient);
			break;
			
		case 9:
			// Buy application
			buyAppMenu(aClient);
			menuUser(aClient);
			break;
		
		case 10:
			// Allows user to give score
			System.out.print("\nThis option allow You to input your score of an aplication."
					+ "\nYou require to introduce:"
					+ "\n'AppName' 'Score' and 'Comment' if wanted.");
	
			// Asks for application name
			String appToScore = askForAppNameAnValidates();
	
			// Asks for user score
			double score = askForScoreAndValidate();
	
			// Asks for user comment
			System.out.print("\nComment: ");
			String comment = scanText.nextLine();
	
			// Creates a score
			aClient.giveScore(appToScore, score, comment, store);
			System.out.println("\nThe score was added to the database");
			menuUser(aClient);
			break;
	
		default:
			// Ask again for input!!
			System.out.print("\nPlease introduze a correct option");
			menuUser(aClient);
			break;
		}
	}
	
	/** Buy Application menu **/
	private void buyAppMenu(Client aClient)
	{
		Bag shoppingBag = null;
		// Create new shopping bag 
		if (shoppingBag == null)
		{
			shoppingBag = store.createShoppingBag();
		}
	
		System.out.print("\n"
				+ "Buy Options:"
				+ "\n (0) Return"
				+ "\n (1) Add App to shopping bag"
				+ "\n (2) Check value of shopping bag"
				+ "\n (3) checkout"
				+ "\n input:");
	
		// asks for user menu input and verifies its validity
		switch (askInputIntAndValidate(0,3)) 
		{
	
		case 0:
			shoppingBag = null;
			menuUser(aClient);
			break;
	
		case 1:
			System.out.print("\nApps in the Store [AppName:Price]: " + store.getAppsList());
			System.out.print("\nApps in the Bag: " +  shoppingBag);
			String appName = askForAppNameAnValidates();
			
			System.out.println("\nNumber of licences: ");
			int numberOfLicences = askInputIntAndValidate(0, Integer.MAX_VALUE);
	
			// Adds application to bag
			store.addtobag(appName, shoppingBag, numberOfLicences);
			buyAppMenu(aClient);
			break;
	
		case 2:
			System.out.println("The value of goods in the bag is:" + shoppingBag.valueInBag());
			buyAppMenu(aClient);
			break;
	
		case 3:
			store.checkout(aClient.getName(), shoppingBag);
			System.out.println("Sucessful buy of: " + shoppingBag );
			menuUser(aClient);
			break;		
		}
	}

	/** Lists the applications by Type **/
	private void menuUserListByType(Client aClient)
	{
		// List Application by type 
		System.out.print("\n"
				+ "Choose one of the type Options:"
				+ "\n (0) Return"
				+ "\n (1) Games"
				+ "\n (2) Business"
				+ "\n (3) Education"
				+ "\n (4) Lifestyle"
				+ "\n (5) Entertainment"
				+ "\n (6) Utilities"
				+ "\n (7) Travel"
				+ "\n (8) HealthandFitness"
				+ "\n"
				+ "\n:");

		String menuText = scanText.nextLine();

		if(menuText.equals("0") || menuText == "Return")
		{
			// Returns to User menu
			menuUser(aClient);
		}

		else if(menuText.equals("1") || menuText.equals(AppType.GAMES.name()))
		{
			store.listAppsByType(AppType.GAMES);
		}

		else if (menuText.equals("2") || menuText.equals(AppType.BUSINESS.name()))
		{
			store.listAppsByType(AppType.BUSINESS);
		}

		else if (menuText.equals("3") || menuText.equals(AppType.EDUCATION.name()))
		{
			store.listAppsByType(AppType.EDUCATION);
		}

		else if (menuText.equals("4") || menuText.equals(AppType.LIFESTYLE.name())) 
		{
			store.listAppsByType(AppType.LIFESTYLE);
		}

		else if (menuText.equals("5") || menuText.equals(AppType.ENTERTAINMENT.name()))
		{
			store.listAppsByType(AppType.ENTERTAINMENT);
		}

		else if (menuText.equals("6") || menuText.equals(AppType.UTILITIES.name()))
		{
			store.listAppsByType(AppType.UTILITIES);
		}

		else if (menuText.equals("7") || menuText.equals(AppType.TRAVEL.name()))
		{
			store.listAppsByType(AppType.TRAVEL);
		}

		else if (menuText.equals("8") || menuText.equals(AppType.HEALTHANDFITNESS.name())) 
		{
			store.listAppsByType(AppType.HEALTHANDFITNESS);
		}

		else {
			System.out.print("Please introduze a correct option");
		}

		// Refresh the menu options
		menuUserListByType(aClient);
	}

	/*
	 * Several methods for input & validation
	 * 1. Menu option input validation
	 * 2. Existence of Client name
	 * 3. Existence of Application name
	 * 4. Application Score input
	 * */
	/** **/
	private void userLogin() 
	{
		Client client = null;
		
		boolean askForUserName = true;
		while(askForUserName)
		{
			System.out.println("UserId: ");
			String aUserId = scanText.nextLine();
			
			if (userExists(aUserId, "Client") || userExists(aUserId, "ClientPremium"))
			{
				client = (Client) returnUserObject(aUserId);
				askForUserName = false;
			}
			
			

			else if (clientName.equals("exit"))
			{
				askForUserName = false;
				menuMain();
			}

			else {
				System.out.print("User Id not existente please insert correct user "
						+ "or 'exit' to create user");
			}
		}
		
		boolean askForPassoword = true;
		int counter = 0;
		while(askForPassoword)
		{

			System.out.println("password: ");
			String password = scanText.nextLine();
			
			
			if (userExists(username, "Client") || userExists(username, "ClientPremium"))
			{
				askForUserName = false;
			}

			else if (clientName.equals("exit"))
			{
				askForUserName = false;
				menuMain();
			}

			else {
				System.out.print("User Id not existente please insert correct user "
						+ "or 'exit' to create user");
			}
		}
	}
	
	
	/** Ask for user menu input and verifies its validity **/
	private int askInputIntAndValidate(int min, int max) 
	{	
		int choice = -1;
		boolean askforchoice = true;
		while(askforchoice && choice < 0)
		{
			try
			{
				menuNumber = scanInt.nextInt();
				if(menuNumber >= min && menuNumber <= max)
				{
					askforchoice = false;
					choice = menuNumber;
				}
				
			}
			catch (Exception e)
			{
				System.out.print("\nWrong Value, " + e.getMessage() + ""
						+ "\nPlease introduce correct integer number"
						+ "\n: ");
			}
		}
		return choice;
	}

	/** Asks and validates an user Score **/
	private double askForScoreAndValidate()
	{
		boolean askForScore = true;
		double score = 0;
		while(askForScore)
		{
			System.out.print("\nScore: [0:5]");
			score = Double.valueOf(scanInt.nextLine());
			if (score >= 0 && score <= 5 )
			{
				askForScore = false;
			}
			else {
				System.out.print("Please input a valid score [0:5]");
			}
		}
		return score;
	}

	/** Ask and verifies if exists a given user name **/
	private String askForClientNameAndValidate()
	{
		boolean askForClientName = true;
		String clientName = "";
		
		while(askForClientName)
		{
			System.out.println("ClienFirsttName: ");
			String firstName = scanText.nextLine();
			System.out.println("ClientLastName: ");
			String lastName = scanText.nextLine();
			clientName = firstName + " " + lastName;
			
			if (userExists(clientName, "Client") || userExists(clientName, "ClientPremium"))
			{
				askForClientName = false;
			}

			else if (clientName.equals("exit"))
			{
				askForClientName = false;
				menuMain();
			}

			else {
				System.out.print("User not existente please insert correct user "
						+ "or 'exit' to create user");
			}
		}
		return clientName;
	}
	
	/** Check if User/Client/ClientPremium/Programmer Exist **/
	private boolean userExists(String aUserId, String aClassName)
	{
		boolean exists = false;
		for(User user: store.getUsersList()) 
		{
			if(user.getUserId().equals(aUserId) && user.getClass().getSimpleName().equals(aClassName)) 
			{
				exists = true;
			}
		}
		return exists;
	}
	
	/** Return User/Client/ClientPremium/Programmer Object **/
	public User returnUserObject(String aUserId)
	{
		User returnUser = null;
		for(User user: store.getUsersList()) 
		{
			if(user.getUserId().equals(aUserId) && user instanceof Client) 
			{
				returnUser = (Client) user;
			}
			
			else if(user.getUserId().equals(aUserId) && user instanceof ClientPremium) 
			{
				returnUser = (ClientPremium) user;
			}
			
			else if(user.getUserId().equals(aUserId) && user instanceof Programmer) 
			{
				returnUser = (Programmer) user;
			}
		}
		return returnUser;
	}
	
	/** Ask for an application and verifies if exists **/
	private String askForAppNameAnValidates()
	{
		boolean askForAppName = true;
		String appName = null;
		
		while(askForAppName)
		{
			System.out.print("\nAppName: ");
			appName = scanText.nextLine();
			
			if (appExists(appName))
			{
				askForAppName = false;
			}
			else {
				System.out.print("Please input a valid application name");
			}
		}
		return appName;
	}

	/** Check if User/Client/ClientPremium/Programmer Exist **/
	private boolean appExists(String aAppName)
	{
		boolean exists = false;
		for(App app: store.getAppsList()) 
		{
			if(app.getName().equals(aAppName)) 
			{
				exists = true;
			}
		}
		return exists;
	}

	/** Print List **/
	public void printList(List<?> aList)
	{
		for(Object obj : aList) 
		{
			System.out.println(obj);
		}
	}

	public <K, V> void printMap(Map<K, V>  aMap)
	{
		for(Map.Entry<K, V>  entry : aMap.entrySet()) 
		{
			System.out.println(entry.getKey() +" : " + entry.getValue());
		}
	}

}
