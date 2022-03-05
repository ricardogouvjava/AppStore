package appstore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
			menuManager();
			break;
	
		case 2:
			String clientName = askForClientNameAndValidate();
			Client client = (Client) returnUserObject(clientName, "Client");
			menuUser(client);
			break;
	
		case 3:
			System.out.print("\nUser Creation"
					+ "\nUserType: {Client, ClientPremium, Programmer} ");
			
			String userType = null, userName = null;
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
	
			store.addUser(userType, userName, userAge);
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
	
		switch (askInputIntAndValidate(0, 21))
		{
	
		case 0:
			// Returns to main menu
			menuMain();
			break;
	
		case 1:
			// Lists all the users in the AppStore
			printList(store.getUsersList());
			menuManager();
			break;
	
		case 20:
			  // Lists all the purchases in the AppStore 
			  store.listPurchases();
			  menuManager(); 
			  break;
		 	
		case 21:
			// Lists all purchases in a certain week of the year
			System.out.println("Wanted week: ");
			int weekNumber =  askInputIntAndValidate(0, Calendar.WEEK_OF_YEAR);
			List<Purchase> purchaseList = store.listPurchasesByWeek(weekNumber);
			for(Purchase purchase : purchaseList)
			{
				System.out.println(purchase);
			}
			menuManager();
			break;
			
		case 3:
			// Prints the total earnings of the AppStore
			System.out.println("The sales total of the app store is: " + store.totalStoreEarnings());
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
			menuManager();
			break;
			
		case 8:
			// finds less sold applications in a defined time	
			System.out.println("Wanted week. ");
			int week =  askInputIntAndValidate(0, Calendar.WEEK_OF_YEAR);
			System.out.println("Number of Apps. ");
			int appnumber =  askInputIntAndValidate(0, store.getAppsList().size());
						
			System.out.println("The less sold applications are: " +
					store.checkLessSoldApps(week, appnumber).keySet());
			menuManager();
			break;
			
		case 9:
			// moves time forward
			System.out.println("Introduce number of days: ");
			int numberDays =  askInputIntAndValidate(0, Integer.MAX_VALUE);
			store.forwardLocalDateXDays(numberDays);
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
				+ "\n (1) List apps By Type"
				+ "\n (2) List apps By Name"
				+ "\n (3) List apps By Sold"
				+ "\n (4) List apps By Score"
				+ "\n (5) Give score"
				+ "\n (6) List Scores and Comments of App"
				+ "\n (7) Buy Apps"
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
			// Allows user to give score
			System.out.print("\nThis option allow You to input your score of an aplication."
					+ "\nYou require to introduce:"
					+ "\n'AppName' 'Score' and 'Comment' if wanted.");
	
			// Asks for application name
			String appName = askForAppNameAnValidates();
	
			// Asks for user score
			double score = askForScoreAndValidate();
	
			// Asks for user comment
			System.out.print("\nComment: ");
			String comment = scanText.nextLine();
	
			// Creates a score
			aClient.giveScore(appName, score, comment, store);
			System.out.println("\nThe score was added to the database");
			menuUser(aClient);
			break;
	
		case 6:
			// List scores and comments of application
			System.out.print("\nAppName: ");
			appName = scanText.nextLine();
			store.listAppScores(appName);
			menuUser(aClient);
			break;
	
		case 7:
			// Buy application
			buyAppMenu(aClient);
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
				if(menuNumber > min && menuNumber < max)
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
			System.out.print("\nClientName: ");
			clientName = scanText.nextLine();
			
			if (userExists(clientName, "Client"))
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
	
	/** Ask, verifies if exists and returns a given user object **/
	private User askForUserAndValidate()
	{
		boolean askForUserName = true;
		User user = null;
		String userName = null, userType = null;
		
		while(askForUserName)
		{
			System.out.println("UserName: ");
			userName = scanText.nextLine();
			
			if (userName.equals("exit"))
			{
				askForUserName = false;
				menuMain();
			}
			
			System.out.println("UserType {Client, ClientPremium, Programmer}: ");
			userType = scanText.nextLine();
		
			if (userExists(userName, userType))
			{
				askForUserName = false;
				user = returnUserObject(userName, userType);
			}

			else 
			{
				System.out.print("User not existente please insert correct user "
						+ "or 'exit' in user name to exit");
			}
		}
			
		return user;
	}

	/** Check if User/Client/ClientPremium/Programmer Exist **/
	private boolean userExists(String aUserName, String aClassName)
	{
		boolean exists = false;
		for(User user: store.getUsersList()) 
		{
			if(user.getName().equals(aUserName) && user.getClass().getSimpleName().equals(aClassName) ) 
			{
				exists = true;
			}
		}
		return exists;
	}
	
	/** Return User/Client/ClientPremium/Programmer Object **/
	public User returnUserObject(String aUserName, String aClassName)
	{
		User returnUser = null;
		for(User user: store.getUsersList()) 
		{
			if(user.getName().equals(aUserName) && user instanceof Client) 
			{
				returnUser = (Client) user;
			}
			
			else if(user.getName().equals(aUserName) && user instanceof ClientPremium) 
			{
				returnUser = (ClientPremium) user;
			}
			
			else if(user.getName().equals(aUserName) && user instanceof Programmer) 
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
}
