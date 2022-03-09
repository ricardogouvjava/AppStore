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
	 * 2. Client menu
	 * 3. Programmer menu
	 * 4. Administrator Menu
	 */

	/** Main menu options **/
	public void menuMain()
	{
		startScanners();
		
		System.out.print("\n------ Menu ------"
				+ "\n (0) Exit "
				+ "\n (1) User Login {Client Client Premium Programer Admin}"
				+ "\n (2) User Creation"
				+ "\n (3) List apps options"
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
			// User login
			User user = userLogin();
			
			if(user instanceof Client || user instanceof ClientPremium) 
			{
				// Access User options
				menuClient((Client) user);
			}
			
			else if(user instanceof Programmer) 
			{
				// Access Programmer options
				menuProgrammer((Programmer) user);
			}
			
			else if(user instanceof Administrator) 
			{
				// Access Programmer options
				menuAdministrator((Administrator) user);
			}
			
			break;
	
		case 2:
			// Allows User creation
			userCreation();
			menuMain();
			break;
			
		case 3:
			// Allows listing of applications
			menuListApps();
			menuMain();
			break;
		
		default:
			System.out.print("Please introduze a correct option");
			menuMain();
		}
	}
	
	/** User menu options **/
	private void menuClient(Client aClient) 
	{	
		System.out.print("\n"
				+ "User Options:"
				+ "\n  (0) Return"
				+ "\n  (1) List onwed applications"
				+ "\n  (2) Buy Apps"
				+ "\n  (3) Give Score"
				+ "\n  (4) List application that score was given"
				+ "\n  (5) List application that scores were not given"
				+ "\n  (6) List scores given to app"
				+ "\n"
				+ "\n input:");
	
		switch (askInputIntAndValidate(0,7)) 
		{
	
		case 0:
			// Returns to main menu
			menuMain();
			break;
		
		case 1:
			// List Application owned by user
			System.out.println("Owned applications:");
			printList(aClient.getApps());
			menuClient(aClient);
			break;
		
		case 2:
			// Buy application
			buyAppMenu(aClient);
			menuClient(aClient);
			break;
			
			
		case 3:
			// Allows user to give score
			System.out.print("\nThis option allow You to input your score of an aplication."
					+ "\nYou require to introduce:"
					+ "\n'Owned Application Name' 'Score' and 'Comment' if wanted.");
	
			// Asks for application name
			App appToScore = askForAppNameValidatesAndReturnsApp();
	
			// Asks for user score
			double score = askForScoreAndValidates();
	
			// Asks for user comment
			System.out.print("\nComment: ");
			String comment = scanText.nextLine();
	
			// Creates a score
			aClient.giveScore(appToScore, score, comment, store);
			System.out.println("\nThe score was added to the database");
			menuClient(aClient);
			break;
		
		case 4:
			// Lists applications that score was given by client
			System.out.print("\nAppScored: ");
			printList(aClient.getAppsScored());
			menuClient(aClient);
			break;
			
		case 5:
			// Lists applications that score was given by user
			System.out.print("\nAppNotScored: ");
			printList(aClient.getAppsNotScored());
			menuClient(aClient);
			break;
		
		case 6:
			// List scores and comments of application
			App app = askForAppNameValidatesAndReturnsApp();
			printList(app.getScores());
			menuClient(aClient);
			break;
					

	
		default:
			// Ask again for input!!
			System.out.print("\nPlease introduze a correct option");
			menuClient(aClient);
			break;
		}
	}
	
	/** Programmer menu options **/
	private void menuProgrammer(Programmer aProgrammer)
	{
		System.out.print("\n"
				+ "User Options:"
				+ "\n (0) Return"
				+ "\n (1) List developped applications"
				+ "\n (2) Programmer average score"
				+ "\n (3) Earnings"
				+ "\n input:");
		
		switch (askInputIntAndValidate(0,7)) 
		{
	
		case 0:
			// Returns to main menu
			menuMain();
			break;
		
		case 1:
			// List Application owned by user
			System.out.println("Developped applications:");
			printList(aProgrammer.getApps());
			menuProgrammer(aProgrammer);
			break;
		
		case 2:
			// Programmer average score
			System.out.println("Developped applications:" + aProgrammer.getAverageScoreReview());
			menuProgrammer(aProgrammer);
			break;
	
		case 3:
			// Programmer average score
			System.out.println("Earnings:" + aProgrammer.getEarnings(store));
			menuProgrammer(aProgrammer);
			break;
		
		}
	
	}
	
	/** Manager menu options **/
	private void menuAdministrator(Administrator aAdministrator) 
	{
		System.out.print("\n"
				+ "Administrator Options:"
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
			menuAdministrator(aAdministrator); 
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
			menuAdministrator(aAdministrator);
			break;
			
		case 3:
			// Prints the total earnings of the AppStore
			System.out.println("The sales total of the app store is: " + String.format("%2f",store.totalStoreEarnings()));
			menuAdministrator(aAdministrator);
			break;		
	
		case 4:
			// Prints the earnings by programmer of the AppStore
			store.earningsByProgrammer();
			menuAdministrator(aAdministrator);
			break;	
	
		case 5:
			// List the applications owned by and user
			printList(askForUseridValidatesAndReturnsClient().getApps());
			menuAdministrator(aAdministrator);
			break;
	
		case 6:
			// Prints the times an application was sold
			System.out.println("AppName: ");
			String appName = scanText.nextLine();
			System.out.println("The app " + appName + " was sold " + store.timesAppSold(appName) + " times.");
			menuAdministrator(aAdministrator);
			break;
		
		case 7:
			// Check applications with discounts
			System.out.println("Apps with discount: " + store.checkAppsWithDiscounts());
			//printList(store.checkAppsWithDiscounts());
			menuAdministrator(aAdministrator);
			break;
			
		case 8:
			// Lists applications sold last week
			System.out.println("The applications sold last week are: ");
			for(Entry<App, Integer> entry : store.listAppsSoldLastWeek().entrySet()) 
			{
				System.out.println(entry.getKey() + " >> sold:" + entry.getValue());
			}
			menuAdministrator(aAdministrator);
			break;
			
		case 18:
			// finds less sold applications in a defined week	
			System.out.println("Wanted week: ");
			int week =  askInputIntAndValidate(0, store.getCurrentWeeK());
			System.out.println("Number of Apps: ");
			int appnumber =  askInputIntAndValidate(0, store.getAppsSoldInWeek(week).size());
						
			System.out.println("The less sold applications are: ");
			printMap(store.checkLessSoldApps(week, appnumber));
			menuAdministrator(aAdministrator);
			break;
			
		case 9:
			// moves time forward
			System.out.println("Introduce number of days: ");
			int numberDays =  askInputIntAndValidate(0, Integer.MAX_VALUE);
			store.forwardDateXDays(numberDays);
			menuAdministrator(aAdministrator);
			break;
		
		case 10:
			// Lists all the users in the AppStore
			System.out.println("Users: ");
			printList(store.getUsersList());
			menuAdministrator(aAdministrator);
			break;

		case 11:
			// Lists all the Clients in the AppStore
			System.out.println("Clients: ");
			printList(store.getClientsList());
			menuAdministrator(aAdministrator);
			break;

		case 12:
			// Lists all the Premium Clients in the AppStore
			System.out.println("ClientPrmium: ");
			printList(store.getClientsPremiumList());
			menuAdministrator(aAdministrator);
			break;

		case 13:
			// Lists all the Programmers in the AppStore
			System.out.println("Programmers: ");
			printList(store.getProgrammersList());
			menuAdministrator(aAdministrator);
			break;
	
		default:
			// Ask again for input!!
			System.out.println("Please introduze a correct option");
			menuAdministrator(aAdministrator);
			break;
		}
	}
	
	
	/** Buy Applications menu **/
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
			menuClient(aClient);
			break;
	
		case 1:
			System.out.print("\nApps in the Store [AppName:Price]: " + store.getAppsList());
			System.out.print("\nApps in the Bag: " +  shoppingBag);
			App app = askForAppNameValidatesAndReturnsApp();
			
			System.out.println("\nNumber of licences: ");
			int numberOfLicences = askInputIntAndValidate(0, Integer.MAX_VALUE);
	
			// Adds application to bag
			shoppingBag.putInBag(app, numberOfLicences);
			buyAppMenu(aClient);
			break;
	
		case 2:
			System.out.println("The value of goods in the bag is:" + shoppingBag.valueInBag());
			buyAppMenu(aClient);
			break;
	
		case 3:
			store.checkout(aClient, shoppingBag);
			System.out.println("Sucessful buy of: " + shoppingBag );
			menuClient(aClient);
			break;		
		}
	}

	/** Lists the applications by Type **/
	private void menuListAppsByType()
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
			menuListApps();
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
		menuListAppsByType();
	}

	
	/** Several  **/
	private void menuListApps() 
	{
		System.out.print("\n------ Menu ------"
				+ "\n (0) Return"
				+ "\n (1) List AppStore applications by Name"
				+ "\n (2) List AppStore applications by times Sold"
				+ "\n (3) List AppStore applications by Score"
				+ "\n "
				+ "\n input:");
	
		//asks for input, verifies it and uses it in the switch
		switch (askInputIntAndValidate(0,7)) 
		{
		case 0:
			// Returns to main menu
			menuMain();
			break;
	
		case 1:
			// List Application by name 
			System.out.println("Apps Listed by Name:");
			printList(store.orderAppsBy("Name"));
			menuListApps();
			break;
	
		case 2:
			// List Application by times sold 
			System.out.println("Apps listed by times sold:");
			for(App app : store.orderAppsBy("Sold"))
			{
				System.out.println(app.getName() +":"+ app.timesSold());
			}
			menuListApps();
			break;
	
		case 3:
			// List Application by Score
			System.out.println("\nApps listed by score:");
			for(App app : store.orderAppsBy("Score"))
			{
				System.out.println(app.getName() +":"+ String.format("%,.2f", app.getAverageScore()));
			}
			menuListApps();
			break;
		
		case 4:
			// List Application by type
			menuListAppsByType();
			menuListApps();
			break;

		}
	}
	
	
	/*
	 * Several methods for input & validation :
	 * 1. Menu option input validation
	 * 2. User login
	 * 3. User creation
	 * 4. Score Validation
	 * 5. Application Name Validation
	 * */
		
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

	/** Ask user for data and validates for login **/
	private User userLogin() 
	{
		User user = null;
		
		boolean askForUserName = true;
		while(askForUserName)
		{
			System.out.println("UserId: ");
			String aUserId = scanText.nextLine();
			
			if (store.userExists(aUserId))
			{
				user = store.findUser(aUserId);
				askForUserName = false;
			}

			else if(aUserId.equals("exit"))
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

			System.out.println("Password: ");
			String password = scanText.nextLine();
			
			if(user.isPasswordCorrect(password))
			{
				askForPassoword = false;
			}
			else
			{
				counter += 1;
				if(counter > 3)
				{
					System.out.print("Failed to introduce password to many times. Exiting");
					askForPassoword = false;
					user = null;
					menuMain();
				}
				
				System.out.print("Password introduced incorrect."
						+ "\nPlease introduce correct password or 'exit' to main menu");
				
				askForPassoword = true;
			}
		}
		
		return user;
	}
	
	/** Any Type of user creation **/
	public boolean userCreation()
	{
		System.out.print("\nUser Creation"
				+ "\nUserType: {Client, ClientPremium, Programmer} ");
		
		String userType = null, userId = null, userPassword = null;
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

		boolean askForId = true; 
		while(askForId)
		{
			System.out.print("\nUserId: ");
			userId = scanText.nextLine();
			if (userId.length() > 4 && !store.userExists(userId))
			{
				askForId = false;
			}
			else {
				System.out.print("UserId in use or not valid!");
			}
		}
		
		boolean askForPassword = true; 
		while(askForPassword)
		{
			System.out.print("\nUserId: ");
			userPassword = scanText.nextLine();
			if (userId.length() > 4 && !store.userExists(userId))
			{
				askForPassword = false;
			}
			else {
				System.out.print("Password not valid!");
			}
		}
			
		boolean askForAge = true; 
		while(askForAge)
		{
			System.out.print("\nUser Age: ");
			userAge = scanInt.nextInt();
			if(userAge < 100 && userAge > 18)
			{
				askForAge = false;
			}
			else
			{
				System.out.print("Not a valid integer for user creation."
						+ "Please input a valid number.");
			}
		}

		store.addUser(userType, userId, userPassword, userAge);
		
		return true;
	}
	
	/** Asks user for score value and validates **/
	private double askForScoreAndValidates()
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

	/** Ask for an application, verifies if exists and returns application object **/
	private App askForAppNameValidatesAndReturnsApp()
	{
		boolean askForAppName = true;
		App app = null;
		
		while(askForAppName)
		{
			System.out.print("\nAppName: ");
			String appName = scanText.nextLine();
			
			if (store.appExists(appName))
			{
				askForAppName = false;
				app = store.findApp(appName);
			}
			else {
				System.out.print("Please input a valid application name");
			}
		}
		return app;
	}

	/** Ask for an User name, verifies if exists and returns user object **/
	private User askForUseridValidatesAndReturnsClient()
	{
		boolean askForId = true;
		User user = null;
		while(askForId)
		{
			System.out.print("\nUserId: ");
			String userId = scanText.nextLine();
			if (userId.length() > 4 && !store.userExists(userId))
			{
				askForId = false;
				user = store.findUser(userId);
				
			}
			else {
				System.out.print("UserId in use or not valid!");
			}
		}
		return user;
	}

	
	/* Prints */
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
