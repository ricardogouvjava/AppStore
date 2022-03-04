package appstore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Run {
	
	private static String userPollNames;
	private static AppStore storeRequalificar;
	private static String menuNumber, menuText, userName, clientName, userType, appName, comment;
	private static int userAge;
	private static double score;
	private static Scanner scanInt, scanText;
	private static Bag shoppingBag = null;

	public static void main(String[] args)
	{
		storeRequalificar = createStore("Requalificar App Store");
		storeRequalificar.setDate(LocalDate.of(2022, Month.FEBRUARY, 01));
		loadUsers(storeRequalificar);
		loadApps(storeRequalificar);
		
		
		loadPreviusPurchaces(storeRequalificar);
		loadPreviusScores(storeRequalificar);

		scanInt = new Scanner(System.in);
		scanText = new Scanner(System.in);	
		
		menuMain(storeRequalificar);
	
		scanInt.close();
		scanText.close();
		
	}
	
	/* Several variable creation 
	 * 1. AppStore
	 * 2. Users
	 * 3. Purchases
	 * 4. Scores
	 * */

	/** Creates an AppStore **/
	public static AppStore createStore(String aName) 
	{
		return new AppStore(aName);
	}

	/** Creates 10 users - 5 clients & 5 programmers **/
	public static void loadUsers(AppStore store) 
	{
		/* Generate Objects */
		/* Clients */
		store.addUser("Client", "Paulo", 45);
		store.addUser("Client", "Sandra", 34);
		store.addUser("Client", "Alice", 18);
		store.addUser("Client", "Ines", 65);
		store.addUser("Client", "Doink", 85);

		/* Client Premium */
		store.addUser("ClientPremium", "Ricardo", 45);
		store.addUser("ClientPremium", "Joao", 34);
		store.addUser("ClientPremium", "Juan", 18);
		store.addUser("ClientPremium", "Emanuel", 65);
		store.addUser("ClientPremium", "Filipe", 85);

		/* Programmers */
		store.addUser("Programmer", "Ricardo", 38);
		store.addUser("Programmer", "Maria", 25);
		store.addUser("Programmer", "Miguel", 32);
		store.addUser("Programmer", "Joana", 27);
		store.addUser("Programmer", "Sienna", 18);
	}

	/** Creates 8 Applications trough programmer designation **/
	public static void loadApps(AppStore store) 
	{
		/* Generate new Applications */
		store.designateProgrammer("Tetris", 2.0, AppType.GAMES, "Maria");
		store.designateProgrammer("Skyrim", 50.0, AppType.GAMES, "Sienna");
		store.designateProgrammer("DocReader", 25.0, AppType.UTILITIES, "Joana");
		store.designateProgrammer("Healthy", 15.0, AppType.HEALTHANDFITNESS, "Sienna");
		store.designateProgrammer("GoFun", 10.0, AppType.ENTERTAINMENT, "Ricardo");
		store.designateProgrammer("ReadYouMust", 20.0, AppType.EDUCATION, "Ricardo");
		store.designateProgrammer("GoWorld", 13.50, AppType.TRAVEL, "Miguel");
		store.designateProgrammer("Brains and Fun", 8.0, AppType.EDUCATION, "Ricardo");
	}

	/** Creates 3 purchases examples **/
	public static void loadPreviusPurchaces(AppStore aStore)
	{
		// Create new shopping bags and adds applications to bag!

		// Shopping list 1 
		Bag sb1 = aStore.createShoppingBag(); 
		aStore.addtobag("Tetris", sb1, 1);
		aStore.addtobag("GoFun", sb1, 1);

		// Shopping list 2 
		Bag sb2 = aStore.createShoppingBag();
		aStore.addtobag("DocReader", sb2, 1);
		aStore.addtobag("Healthy", sb2, 1);
		aStore.addtobag("Tetris", sb2, 1);
		aStore.addtobag("GoFun", sb2, 1);

		// Shopping list 3 
		Bag sb3 = aStore.createShoppingBag();
		aStore.addtobag("GoWorld", sb3, 1);
		aStore.addtobag("Brains and Fun", sb3, 1);
		aStore.addtobag("Skyrim", sb3, 1);
		aStore.addtobag("DocReader", sb3, 1);
		aStore.addtobag("Healthy", sb3, 1);
		aStore.addtobag("ReadYouMust", sb3, 1);
		aStore.addtobag("GoFun", sb3, 1);

	}

	/** Creates 2 Scores examples **/
	public static void loadPreviusScores(AppStore aStore)
	{
		// Give Score
		((Client) aStore.chekIfClient("Paulo"))
		.giveScore("Tetris", 4.8, "Super Dope", aStore);

		((Client) aStore.chekIfClient("Alice"))
		.giveScore("Tetris", 4.5, "I like this old game!", aStore);;
	}

	/* Several menu options area:
	 * 1. Main menu
	 * 2. Manager menu
	 * 3. User menu
	 * 4. Buy Application menu
	 */

	/** Main menu options **/
	private static void menuMain(AppStore aStore)
	{
		System.out.print("\n------ Menu ------"
				+ "\n (0) Exit Manager"
				+ "\n (1) Manager Options"
				+ "\n (2) User Options"
				+ "\n (3) Create User"
				+ "\n"
				+ "\n input:");

		//asks for input, verifies it and uses it in the switch
		switch (askInputInt()) 
		{
		case 0:
			System.err.print("System will terminate ");
			System.exit(0);
			System.err.print("Terminated");

		case 1:
			menuManager(aStore);
			break;

		case 2:
			pseudoLogin(aStore);
			break;

		case 3:
			System.out.print("\nCreation User"
					+ "\nUserType: {Client, Programmer} ");

			boolean askForType = true;
			while(askForType)
			{
				System.out.print("\nUserType: ");
				userType = scanText.nextLine();
				if (userType.equals("Client") || userType.equals("Programmer"))
				{
					askForType = false;
				}
				else {
					System.out.print("Please input a valid user Type");
				}
			}

			boolean askForName = true; 
			while(askForName)
			{
				System.out.print("\nUserName: ");
				userName = scanText.nextLine();
				if (userName.length() > 3)
				{
					askForName = false;
				}
				else {
					System.out.print("Please input a valid user name");
				}
			}

			boolean askForAge = true; 
			while(askForAge)
			{
				System.out.print("\nUser Age: ");
				try 
				{
					userAge = Integer.parseInt(scanText.nextLine());
					askForAge = false;

				}
				catch (NumberFormatException e)
				{
					System.out.print("Not a valid integer."
							+ "Please input a valid number.");
				}
			}

			aStore.addUser(userType, userName, userAge);
			System.out.print("\nUser added.");
			menuMain(aStore);
			break;

		default:
			System.out.print("Please introduze a correct option");
			menuMain(aStore);
		}
	}

	/** Manager menu options **/
	private static void menuManager(AppStore aStore) 
	{
		System.out.print("\n"
				+ "Manager Options:"
				+ "\n (0) Return"
				+ "\n (1) List all Users"
				+ "\n (20) List all purchases"
				+ "\n (21) List purchases by week"
				+ "\n (3) Total earnings"
				+ "\n (4) Earnings by programmer"
				+ "\n (5) List applications owned by User"
				+ "\n (6) Times an app was sold"
				+ "\n (7) Less sold apps by time Interval"
				+ "\n (8) Less sold apps by week"
				+ "\n (9) TimeFoward"
				+ "\n"
				+ "\n input:");	

		menuNumber = scanInt.nextLine();

		switch (Integer.valueOf(menuNumber))
		{

		case 0:
			// Returns to main menu
			menuMain(aStore);
			break;

		case 1:
			// Lists all the users in the AppStore
			aStore.getUsers();
			menuManager(aStore);
			break;

		case 20:
			  // Lists all the purchases in the AppStore 
			  aStore.listPurchases();
			  menuManager(aStore); 
			  break;
		 	
		case 21:
			// Lists all purchases in a certain week
			System.out.println("Wanted week: ");
			int weekNumber =  askInputInt();
			List<Purchase> purchaseList = aStore.listPurchasesByWeek(weekNumber);
			for(Purchase purchase : purchaseList)
			{
				System.out.println(purchase);
			}
			menuManager(aStore);
			break;
			
		case 3:
			// Prints the total earnings of the AppStore
			System.out.println("The sales total of the app store is: " + aStore.totalStoreEarnings());
			menuManager(aStore);
			break;		

		case 4:
			// Prints the earnings by programmer of the AppStore
			aStore.earningsByProgrammer();
			menuManager(aStore);
			break;	

		case 5:
			// List the applications owned by and user
			System.out.println("UserName: ");
			userName = scanText.nextLine();
			aStore.listUserApp(userName);
			menuManager(aStore);
			break;

		case 6:
			// Prints the times an application was sold
			System.out.println("AppName: ");
			appName = scanText.nextLine();
			System.out.println("The app " + appName + " was sold " + aStore.timesAppSold(appName) + " times.");
			menuManager(aStore);
			break;
		
		case 7:
			// finds 5 less sold applications in a defined time
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
			
			System.out.println("Define the start date and end date for analysis."
					+ "\nStart date (00-00-0000): ");
			Date startDate  = null;
			try
			{
				startDate = formatter.parse(scanText.nextLine());
			} catch (ParseException e1)
			{
				e1.printStackTrace();
				System.out.println("Wrong data value: " + startDate);
			}
			
			System.out.println("End date (00-00-0000): ");
			Date endDate = null;
			try 
			{
				endDate = formatter.parse(scanText.nextLine());
			} 
			catch (ParseException e)
			{
				e.printStackTrace();
				System.out.println("Wrong data value: " + endDate);

			}
			
			//System.out.println("The Least sold applications are: " +
				//	aStore.checkLessSoldApps(startDate, endDate));
			menuManager(aStore);
			break;
			
		case 8:
			// finds less sold applications in a defined time	
			System.out.println("Wanted week. ");
			int week =  askInputInt();
			System.out.println("Number of Apps. ");
			int appnumber =  askInputInt();
						
			System.out.println("The less sold applications are: " +
					aStore.checkLessSoldApps(week, appnumber).keySet());
			menuManager(aStore);
			break;
			
		case 9:
			// moves time forward
			System.out.println("Introduce number of days: ");
			int numberDays =  askInputInt();
			aStore.forwardLocalDateXDays(numberDays);
			menuManager(aStore);
			break;


		default:
			// Ask again for input!!
			System.out.println("Please introduze a correct option");
			menuManager(aStore);
			break;
		}
	}

	/** User menu options **/
	private static void menuUser(Client aClient, AppStore aStore) 
	{		
		System.out.print("\n"
				+ "User Options:"
				+ "\n (0) Return"
				+ "\n (1) List apps By Type"
				+ "\n (2) List apps By Name"
				+ "\n (3) List apps By Sold"
				+ "\n (4) List apps By Score"
				+ "\n (5) Give score"
				+ "\n (6) List Scores and Comments of App"
				+ "\n (7) Buy Apps"
				+ "\n"
				+ "\n input:");

		switch (askInputInt()) 
		{

		case 0:
			// Returns to main menu
			menuMain(aStore);
			break;

		case 1:
			// List Application by type
			menuUserListByType(aClient, aStore);
			menuUser(aClient, aStore);
			break;

		case 2:
			// List Application by name 
			aStore.listAppsBy("Name");
			menuUser(aClient, aStore);
			break;

		case 3:
			// List Application by quantity sold 
			aStore.listAppsBy("Sold");
			menuUser(aClient, aStore);
			break;

		case 4:
			// List Application by Score
			aStore.listAppsBy("Score");
			menuUser(aClient, aStore);
			break;

		case 5:
			// Allow user to give score
			System.out.print("\nThis option allow You to input a Score of an App."
					+ "\nYou require to introduce:"
					+ "\n'AppName' 'Score' and 'Comment' if wanted.");

			// Asks for application name
			appName = checkAppName(aStore);

			// Asks for user score
			score = checkScore(aStore);

			// Asks for user comment
			System.out.print("\nComment: ");
			comment = scanText.nextLine();

			// Creates a score
			aClient.giveScore(appName, score, comment, aStore);
			System.out.println("\nThe score was added to the database");
			menuUser(aClient, aStore);
			break;

		case 6:
			// List scores and comments of application
			System.out.print("\nAppName: ");
			appName = scanText.nextLine();
			aStore.listAppScores(appName);
			menuUser(aClient, aStore);
			break;

		case 7:
			// Buy application
			shoppingBag = null;
			buyAppMenu(aClient, aStore);
			menuUser(aClient, aStore);
			break;

		default:
			// Ask again for input!!
			System.out.print("\nPlease introduze a correct option");
			menuUser(aClient, aStore);
			break;
		}
	}

	/** Buy Application menu **/
	public static void buyAppMenu(Client aClient, AppStore aStore)
	{
		// Create new shopping bag 
		if (shoppingBag == null)
		{
			shoppingBag = aStore.createShoppingBag();
		}

		System.out.print("\n"
				+ "Buy Options:"
				+ "\n (0) Return"
				+ "\n (1) Add App to shopping bag"
				+ "\n (2) Check value of shopping bag"
				+ "\n (3) checkout"
				+ "\n input:");

		// asks for user menu input and verifies its validity
		switch (askInputInt()) 
		{

		case 0:
			shoppingBag = null;
			menuUser(aClient, aStore);
			break;

		case 1:
			System.out.print("\nApps in the Store [AppName:Price]: " + aStore.getApps());
			System.out.print("\nApps in the Bag: " +  shoppingBag);
			appName = checkAppName(aStore);
			
			System.out.println("\nNumber of licences: ");
			int appNumber = askInputInt();

			// Adds application to bag
			aStore.addtobag(appName, shoppingBag, appNumber);
			buyAppMenu(aClient, aStore);
			break;

		case 2:
			System.out.println("The value of goods in the bag is:" + shoppingBag.valueInBag());
			buyAppMenu(aClient, aStore);
			break;

		case 3:
			aStore.checkout(aClient.getName(), shoppingBag);
			System.out.println("Sucessful buy of: " + shoppingBag );
			menuUser(aClient, aStore);
			break;		
		}
	}

	/* 
	 * Several sub menu functions
	 * 1. List applications by type
	 * 2. List all user
	 * 3. List all Purchases made in the store
	 * 4. Value of shopping bag 
	 * */

	/** Lists the applications by Type **/
	public static void menuUserListByType(Client aClient, AppStore aStore)
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

		menuText = scanText.nextLine();

		if(menuText.equals("0") || menuText == "Return")
		{
			// Returns to User menu
			menuUser(aClient, aStore);
		}

		else if(menuText.equals("1") || menuText.equals(AppType.GAMES.name()))
		{
			aStore.listAppsByType(AppType.GAMES);
		}

		else if (menuText.equals("2") || menuText.equals(AppType.BUSINESS.name()))
		{
			aStore.listAppsByType(AppType.BUSINESS);
		}

		else if (menuText.equals("3") || menuText.equals(AppType.EDUCATION.name()))
		{
			aStore.listAppsByType(AppType.EDUCATION);
		}

		else if (menuText.equals("4") || menuText.equals(AppType.LIFESTYLE.name())) 
		{
			aStore.listAppsByType(AppType.LIFESTYLE);
		}

		else if (menuText.equals("5") || menuText.equals(AppType.ENTERTAINMENT.name()))
		{
			aStore.listAppsByType(AppType.ENTERTAINMENT);
		}

		else if (menuText.equals("6") || menuText.equals(AppType.UTILITIES.name()))
		{
			aStore.listAppsByType(AppType.UTILITIES);
		}

		else if (menuText.equals("7") || menuText.equals(AppType.TRAVEL.name()))
		{
			aStore.listAppsByType(AppType.TRAVEL);
		}

		else if (menuText.equals("8") || menuText.equals(AppType.HEALTHANDFITNESS.name())) 
		{
			aStore.listAppsByType(AppType.HEALTHANDFITNESS);
		}

		else {
			System.out.print("Please introduze a correct option");
		}

		// Refresh the menu options
		menuUserListByType(aClient, aStore);
	}

	/*
	 * Several methods for input & validation
	 * 1. Menu option input validation
	 * 2. Existence of Client name
	 * 3. Existence of Application name
	 * 4. Application Score input
	 * */

	/** Ask for user menu input and verifies its validity **/
	public static int askInputInt() 
	{	
		int choice = -1;
		boolean askforchoice = true;
		while(askforchoice && choice < 0)
		{
			try {
				menuNumber = scanInt.nextLine();
				choice = Integer.valueOf(menuNumber);
				askforchoice = false;
			}
			catch (Exception e)
			{
				System.out.print("\nIorrect, " + e.getMessage() + ""
						+ "\nPlease introduce correct integer number"
						+ "\n: ");
			}
		}
		return choice;
	}

	/** Ask and verifies if exists a given user name **/
	public static String checkClientName(AppStore aStore)
	{
		boolean askForClientName = true;
		while(askForClientName)
		{
			System.out.print("\nUserName: ");
			clientName = scanText.nextLine();
			if (aStore.getClientsList().stream().filter(n -> n.getName().equals(userName)).count() > 0)
			{
				askForClientName = false;
			}

			else if (clientName.equals("exit"))
			{
				askForClientName = false;
				menuMain(aStore);
			}

			else {
				System.out.print("User not existente please insert correct user "
						+ "or 'exit' to create user");
			}
		}
		return clientName;
	}

	/** Ask, verifies if exists and returns a given user object **/
	public static void pseudoLogin(AppStore aStore)
	{
		boolean askForClientName = true;
		Client clientReturn = null;
		while(askForClientName)
		{
			System.out.print("\nUserName: ");
			clientName = scanText.nextLine();

			if (clientReturn == null && clientName.equals("exit"))
			{
				askForClientName = false;
				menuMain(aStore);
			}	

			else {
				for (Client client : aStore.getClientsList())
				{
					if (client.getName().equals(clientName))
					{
						clientReturn = client;
						askForClientName = false;
					}
				}
			}

			if (clientReturn == null)
			{
				System.out.print("User not existente please insert correct user "
						+ "or 'exit' to create user");
			}
		}

		menuUser(clientReturn, aStore);
	}

	/** Ask and verifies if exists a given application name **/
	public static String checkAppName(AppStore aStore)
	{
		boolean askForAppName = true;
		while(askForAppName)
		{
			System.out.print("\nAppName: ");
			appName = scanText.nextLine();
			if (aStore.getApps().stream().filter(n -> n.getName().equals(appName)).count() > 0)
			{
				askForAppName = false;
			}
			else {
				System.out.print("Please input a valid application name");
			}
		}
		return appName;
	}

	/** Asks and verifies validity of an user Score input **/
	public static double checkScore(AppStore aStore)
	{
		boolean askForScore = true;
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

}
