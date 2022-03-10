package appstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu
{
	private static Scanner scanText;
	private AppStore store;

	public Menu(AppStore aStore)
	{
		store =  aStore;
	}


	/* MENU LIST:
	 * 1. Main menu
	 *    1.1 User Login
	 *    1.2 User Creation
	 *    1.3 List AppsMenu
	 *        1.3.1 By Name
	 *        1.3.2 By Times Sold
	 *        1.3.3 By Score
	 *        1.3.3 By chosen application type
	 *              1.3.3.1 Games
	 *              1.3.3.2 Business
	 *              1.3.3.3 Education
	 *              1.3.3.4 Lifestyle
	 *              1.3.3.5 Entertainment
	 *              1.3.3.6 Utilities
	 *              1.3.3.7 Travel
	 *              1.3.3.8 HealthandFitness"
	 *
	 * 2. Client menu
	 *    2.1 List owned applications
	 *    2.2 Buy Applications
	 *        2.2.1 Add application to shopping bag
	 *        2.2.2 Check value of shopping bag
	 *        2.2.3 Checkout"
	 *    2.3 Give Score
	 *    2.4 List application that score was given
	 *    2.5 List application that scores was not given
	 *    2.6 List scores given to application
	 *
	 * 3. Programmer menu
	 *    3.1 List developed applications
	 *    3.2 Programmer average score
	 *    3.3 Earnings
	 *
	 * 4. Administrator Menu
	 *    4.1 Move Time Forward
	 *    4.2 Total earnings
	 *    4.3 Earnings by programmer
	 *    4.4 applications with discount
	 *    4.5 Times an application was sold
	 *    4.6 List applications sold last week
	 *    4.7 Less sold applications in a given week
	 *    4.8 List all purchases
	 *    4.9 List purchases by week
	 *    4.10 List all Users
	 *    4.11 List all Clients
	 *    4.12 List all ClientPremium
	 *    4.13 List all Programmers
	 *    4.14 List applications off User
	 */

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

	/** Asks user for score value and validates **/
	private double askForScoreAndValidates()
	{
		boolean askForScore = true;
		double score = 0;
		while(askForScore)
		{
			System.out.print("\nScore: [0:5]");
			score = Double.valueOf(scanText.nextLine());
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

	/** Ask for an User name, verifies if exists and returns user object **/
	private User askForUserIdValidatesAndReturnsUser()
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

	
	/** Buy Applications menu **/
	private void buyAppMenu(Client aClient , Bag shoppingBag)
	{
		// Create new shopping bag
		if (shoppingBag == null)
		{
			shoppingBag = store.createShoppingBag();
		}

		System.out.print("\n"
				+ "Buy Options:"
				+ "\n (0) Return"
				+ "\n (1) Add application to shopping bag"
				+ "\n (2) Check value of shopping bag"
				+ "\n (3) Checkout"
				+ "\n ");

		// asks for user menu input and verifies its validity
		switch (askInputIntAndValidate(0,3))
		{

		case 0:
			shoppingBag = null;
			menuClient(aClient);
			break;

		case 1:
			System.out.println("Apps in the Store [AppName:Price]: " + store.getAppsList());
			System.out.println("Apps in the Bag: " +  shoppingBag);
			App app = askForAppNameValidatesAndReturnsApp();

			System.out.println("\nNumber of licences: ");
			int numberOfLicences = askInputIntAndValidate(0, Integer.MAX_VALUE);

			// Adds application to bag
			shoppingBag.putInBag(app, numberOfLicences);
			buyAppMenu(aClient, shoppingBag);
			break;

		case 2:
			System.out.println("The value of goods in the bag is:" + String.format("%.2f", shoppingBag.valueInBag()));
			System.out.println("Value to pay: " + String.format("%.2f", store.valueInBag(shoppingBag, aClient)));
			buyAppMenu(aClient, shoppingBag);
			break;

		case 3:
			Purchase purchase = store.checkout(aClient, shoppingBag);	
			if(store.savedInPurchase(purchase)!=0)
			{
				System.out.println("Premium Saved:" + 	 String.format("%.2f",store.savedInPurchase(purchase)));			
			}
			menuClient(aClient);
			break;

		default:
			// Ask again for input!!
			System.out.println("Please introduze a correct option");
			buyAppMenu(aClient, shoppingBag);
			break;
		}
	}


	/** Manager menu options **/
	private void menuAdministrator(Administrator aAdministrator)
	{
		System.out.print("\n"
				+ "Administrator Options:"
				+ "\n  (0) Return"
				+ "\n  (1) Move Time Forward"
				+ "\n  (2) Total earnings"
				+ "\n  (3) Earnings by programmer"
				+ "\n  (4) Applications with discount"
				+ "\n  (5) Times an app was sold"
				+ "\n  (6) All app sales by week"
				+ "\n  (7) Less sold apps in a given week"
				+ "\n  (8) List all purchases"
				+ "\n  (9) List purchases by week"
				+ "\n (10) List all Users"
				+ "\n (11) List all Clients"
				+ "\n (12) List all ClientPremium"
				+ "\n (13) List all Programmers"
				+ "\n (14) List applications off User"
				+ "\n");

		switch (askInputIntAndValidate(0, 14))
		{

		case 0:
			// Returns to main menu
			menuMain();
			break;

		case 1:
			// moves time forward
			System.out.println("Introduce number of days: ");
			int numberDays =  askInputIntAndValidate(0, Integer.MAX_VALUE);
			store.forwardDateXDays(numberDays);
			menuAdministrator(aAdministrator);
			break;

		case 2:
			// Prints the total earnings of the AppStore
			System.out.println("The sales total of the app store is: " + String.format("%,.2f",store.totalStoreEarnings()));
			menuAdministrator(aAdministrator);
			break;

		case 3:
			// Prints the earnings by programmer of the AppStore
			store.earningsByProgrammer();
			menuAdministrator(aAdministrator);
			break;

		case 4:
			// Check applications with discounts
			System.out.println("Apps with discount: ");
			printList(store.checkAppsWithDiscounts());
			menuAdministrator(aAdministrator);
			break;


		case 5:
			// Prints the times an application was sold
			App app = askForAppNameValidatesAndReturnsApp();
			System.out.println("The app " + app.getName() + " was sold " + app.timesSold() + " times.");
			menuAdministrator(aAdministrator);
			break;

		case 6:
			// Prints application sales by week
			System.out.println("Wanted week: ");
			int week =  askInputIntAndValidate(0, store.getCurrentWeek().weekNumber());
			System.out.println("The sold applications are: ");
			printMap(store.getWeekSales(week));
			menuAdministrator(aAdministrator);
			break;

		case 7:
			// finds less sold applications in a defined week
			System.out.println("Wanted week: ");
			week =  askInputIntAndValidate(0, store.getCurrentWeek().weekNumber());
			System.out.println("Number of Apps: ");
			int appnumber =  askInputIntAndValidate(0, store.returnWeekObject(week).getAppSales().size());

			System.out.println("The less sold applications are: ");
			printMap(store.getWeekLessSoldApps(week, appnumber));
			menuAdministrator(aAdministrator);
			break;



		case 8:
			// Lists all the purchases in the AppStore
			System.out.println("Purchases:");
			for (Purchase purchase : store.getPurchases())
			{
				System.out.println(purchase);
			}
			menuAdministrator(aAdministrator);
			break;

		case 9:
			// Lists all purchases in a certain week of the year
			System.out.println("Wanted week: ");
			int weekNumber =  askInputIntAndValidate(0, store.getCurrentWeek().weekNumber());
			List<Purchase> purchaseList = store.getWeekPurchases(weekNumber);
			for(Purchase purchase : purchaseList)
			{
				System.out.println(purchase);
			}
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

		case 14:
			// List the applications owned by and user
			printMap(askForUserIdValidatesAndReturnsUser().getApps());
			menuAdministrator(aAdministrator);
			break;


		default:
			// Ask again for input!!
			System.out.println("Please introduze a correct option");
			menuAdministrator(aAdministrator);
			break;
		}
	}

	/** User menu options **/
	private void menuClient(Client aClient)
	{
		System.out.print("\n"
				+ "User Options:"
				+ "\n  (0) Return"
				+ "\n  (1) List owned applications"
				+ "\n  (2) Buy Applications"
				+ "\n  (3) Give Score"
				+ "\n  (4) List application that score was given"
				+ "\n  (5) List application that scores was not given"
				+ "\n  (6) List scores given to application"
				+ "\n:");

		switch (askInputIntAndValidate(0,7))
		{

		case 0:
			// Returns to main menu
			menuMain();
			break;

		case 1:
			// List Application owned by user
			System.out.println("Owned applications:");
			printMap(aClient.getApps());
			menuClient(aClient);
			break;

		case 2:
			// Buy application
			buyAppMenu(aClient, store.createShoppingBag());
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

	/*
	* Several methods for input & validation :
	 * 1. Menu option input validation
	 * 2. User login
	 * 3. User creation
	 * 4. User validation
	 * 4. Score Validation
	 * 5. Application Validation
	 * */

	/** Several ways to list applications  **/
	private void menuListApps()
	{
		System.out.print("\n------ Menu ------"
				+ "\n (0) Return"
				+ "\n (1) List AppStore applications ordered by Name"
				+ "\n (2) List AppStore applications ordered by times Sold"
				+ "\n (3) List AppStore applications ordered by Score"
				+ "\n (4) List AppStore applications ordered by chosen application type"
				+ "\n :");

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

		default:
			// Ask again for input!!
			System.out.println("Please introduze a correct option");
			menuListApps();
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
				+ "\n:");

		List<App> listType =  new ArrayList<>();

		switch (askInputIntAndValidate(0,8))
		{

		case 0: // Returns to User menu
			menuListApps();
			break;

		case 1: // Returns to User menu
			listType = store.listAppsByType(AppType.GAMES);
			break;

		case 2:
			listType = store.listAppsByType(AppType.BUSINESS);
			break;

		case 3:
			listType = store.listAppsByType(AppType.EDUCATION);
			break;

		case 4:
			listType = store.listAppsByType(AppType.LIFESTYLE);
			break;

		case 5:
			listType = store.listAppsByType(AppType.ENTERTAINMENT);
			break;

		case 6:
			listType = store.listAppsByType(AppType.UTILITIES);
			break;

		case 7:
			listType = store.listAppsByType(AppType.TRAVEL);
			break;

		case 8:
			listType = store.listAppsByType(AppType.HEALTHANDFITNESS);
			break;

		default:
			// Ask again for input!!
			System.out.println("Please introduze a correct option");
			menuListAppsByType();
			break;

		}

		// Analysis if empty
		if(listType.isEmpty())
		{
			System.out.println("No applications of the chosen type");
		}

		else
		{
			System.out.println("The apps of type '" + listType.get(0).getType() + "' in the apps store are:");
			printList(listType);
		}

		menuListAppsByType();
	}

	/** Main menu options **/
	public void menuMain()
	{
		scanText = new Scanner(System.in);
		System.out.print("\n------ Menu ------"
				+ "\n (0) Exit "
				+ "\n (1) User Login {Client Client Premium Programer Admin}"
				+ "\n (2) User Creation"
				+ "\n (3) List apps options"
				+ "\n");

		//asks for input, verifies it and uses it in the switch
		switch (askInputIntAndValidate(0, 3))
		{
		case 0:
			System.err.print("System will terminate ");
			scanText.close();
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

	/** Programmer menu options **/
	private void menuProgrammer(Programmer aProgrammer)
	{
		System.out.print("\n"
				+ "User Options:"
				+ "\n (0) Return"
				+ "\n (1) List developed applications"
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
			printList(new ArrayList<>(aProgrammer.getApps().keySet()));
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

	/* Prints */
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
			userAge = Integer.valueOf(scanText.nextLine());
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
	
	/** Ask for user menu input and verifies its validity **/
	private int askInputIntAndValidate(int min, int max)
	{
		int choice = -1;
		boolean askforchoice = true;
		while(askforchoice && choice < 0)
		{
			System.out.print("input: ");
			try
			{
				int menuNumber = Integer.valueOf(scanText.nextLine());
				if(menuNumber >= min && menuNumber <= max)
				{
					askforchoice = false;
					choice = menuNumber;
				}

			}
			catch (Exception e)
			{
				System.out.print("\nWrong Value, " + e.getMessage() + ""
						+ "\nPlease introduce correct integer number!!"
						+ "\n");
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

}
