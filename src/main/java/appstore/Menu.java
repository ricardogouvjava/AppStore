package appstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu
{
	private static Scanner scanText;
	private AppStore store;

	// Constructor
	public Menu(AppStore aStore)
	{
		store =  aStore;
	}

	/* MENU LIST:
	 * 1. Main menu | menuMain()
	 *    1.1 User Login
	 *    1.2 User Creation
	 *    1.3 List AppsMenu | menuListApps()
	 *        1.3.1 By Name
	 *        1.3.2 By Times Sold
	 *        1.3.3 By Score    
	 *        1.3.3 By chosen application type | menuListAppsByType()
	 *              1.3.3.1 Games
	 *              1.3.3.2 Business
	 *              1.3.3.3 Education
	 *              1.3.3.4 Lifestyle
	 *              1.3.3.5 Entertainment
	 *              1.3.3.6 Utilities
	 *              1.3.3.7 Travel
	 *              1.3.3.8 HealthandFitness
	 *
	 * 2. Client menu | menuClient(Client aClient)
	 *    2.1 List owned applications
	 *    2.2 Buy Applications | buyAppMenu(Client aClient, Bag shoppingBag)
	 *        2.2.1 Add application to shopping bag
	 *        2.2.2 Alter number application bag
	 *        2.2.3 Remove application from bag
	 *        2.2.4 Check value of shopping bag
	 *        2.2.5 Checkout
	 *    2.3 Give Score
	 *    2.4 List application that score was given
	 *    2.5 List application that scores was not given
	 *    2.6 List scores given to application
	 *
	 * 3. Programmer menu | menuProgrammer(Programmer aProgrammer
	 *    3.1 List developed applications
	 *    3.2 Programmer average score
	 *    3.3 Earnings
	 *
	 * 4. Administrator Menu | menuAdministrator(Administrator aAdministrator)
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
	 *    4.16 Users with 'Incentive' Discount
	 *    4.17 Clients invited by Client
	 */

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
			System.err.println("System will terminate ");
			scanText.close();
			System.exit(0);
			System.err.println("Terminated");
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

	/** Several ways to list applications  **/
	private void menuListApps()
	{
		System.out.print("\n------ Menu ------"
				+ "\n (0) Return"
				+ "\n (1) List AppStore applications ordered by Name"
				+ "\n (2) List AppStore applications ordered by times Sold"
				+ "\n (3) List AppStore applications ordered by Score"
				+ "\n (4) List AppStore applications ordered by chosen application type"
				+ "\n");

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
				System.out.println(app.getName() +":"+ String.format("%,.2f", app.getScore()));
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
	
	/** Client menu options **/
	private void menuClient(Client aClient)
	{
		System.out.print("\n"
				+ "Client Options:"
				+ "\n  (0) Return"
				+ "\n  (1) List owned applications"
				+ "\n  (2) Buy Applications"
				+ "\n  (3) Subscribe to app"
				+ "\n  (4) Give Score"
				+ "\n  (5) List application that score was given"
				+ "\n  (6) List application that scores was not given"
				+ "\n  (7) List scores given to application"
				+ "\n  (8) Subscribe Premium (Normal Client Only)"
				+ "\n  (9) Cancel Premium (Premium Only)"
				+ "\n (10) Choose Weekly free app"
				+ "\n ("
				+ "\n");

		switch (askInputIntAndValidate(0, 10))
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
			buySubscritionMenu(aClient, store.createShoppingBag());		
			menuClient(aClient);
			break;
		case 4:
			// Allows user to give score
			System.out.print("\nThis option allow You to input your score of an aplication."
					+ "\nYou require to introduce:"
					+ "\n'Owned Application Name' 'Score' and 'Comment' if wanted.");

			// Asks for application name
			App appToScore = askForAppNameValidatesAndReturnsApp(aClient.getAppsNotScored());
			
			if(appToScore.equals(null)) 
			{
				menuClient(aClient);
			}
			
			// Asks for user score
			double score = askForScoreAndValidates();

			// Asks for user comment
			System.out.print("\nComment: ");
			String comment = scanText.nextLine();

			// Creates a score
			aClient.giveScore(appToScore, score, comment, store); // already verified if can score
			System.out.println("\nThe score was added to the database");
			menuClient(aClient);
			break;

		case 5:
			// Lists applications that score was given by client
			System.out.print("\nAppScored: ");
			printList(aClient.getAppsScored());
			menuClient(aClient);
			break;

		case 6:
			// Lists applications that score was given by user
			System.out.print("\nAppNotScored: ");
			printList(aClient.getAppsNotScored());
			menuClient(aClient);
			break;

		case 7:
			// List scores and comments of application
			App app = askForAppNameValidatesAndReturnsApp(store.getAppsList());
			printList(app.getScores());
			menuClient(aClient);
			break;
		
		case 8:
			// Pass to premium
			
			/** Como converter de uma classe para outra? **/
			
			String pswdC = reconfirmPassword(aClient);
			
			if (pswdC != null)
				{
					ClientPremium clientPremium = store.changeToPremium(aClient, pswdC);
					System.out.println("Subscrition sucessuful");
					menuClient(clientPremium);
					break;
				}
			else
				{
					System.out.print("Canceled action");
				}
			
			menuClient(aClient);
			break;
			
		case 9:
			// Cancel premium
			String pswdCP = reconfirmPassword(aClient);
			
			if (pswdCP != null)
				{
					Client client = store.changeToPremium(aClient, pswdCP);
					System.out.println("Cancelation sucessuful");
					menuClient(client);
					break;
				}
			else
				{
					System.out.print("Canceled action");
				}
			
			menuClient(aClient);
			break;
			
		case 10:
			if(aClient.hasChoosenFreeWeeklyApp())
			{
				System.out.print("Cannot choose an other app this week");
			}
			else
			{
				// list all applications with discount ?? weekly or also monthly
				System.out.print("Can add one free aplication this week,"
						+ "\nChoose one of the following:");
				List<App> appsToChoose = store.checkAppsWithWeeklyDiscounts();
				printList(appsToChoose);
				
				// allows to choose an application
				App appchoosen = askForAppNameValidatesAndReturnsApp(appsToChoose);
				
				if(!aClient.pickFreeAppSucess(appchoosen))
				{
					System.out.print("Application was already choosen in weekly free apps");
				}
				else 
				{
					System.out.print("Applicaton added to applications list: " + appchoosen.getName());
				}
			}
			menuClient(aClient);
			break;
		


		default:
			// Ask again for input!!
			System.out.print("\nPlease introduze a correct option");
			menuClient(aClient);
			break;
		}
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
				+ "\n (2) Alter number application licenses in the bag"
				+ "\n (3) Remove application from bag"
				+ "\n (4) Check value of shopping bag"
				+ "\n (5) Checkout"
				+ "\n ");

		// asks for user menu input and verifies its validity
		switch (askInputIntAndValidate(0,5))
		{

		case 0:
			shoppingBag = null;
			menuClient(aClient);
			break;

		case 1: // Add Application
			
			// Informs applications in store
			System.out.println("Apps in the Store: [AppName:Price]");
			printList(store.getAppsList());
			
			//If not empty informs of applications in the bag 
			if(shoppingBag.getBagItems().size() > 0)
			{
				System.out.println("\nContents of Bag: "
						+ "\n" +  shoppingBag);
			}
			System.out.println("\nChoose");
			// Asks for application name to add to bag
			App app = askForAppNameValidatesAndReturnsApp(store.getAppsList());
			
			// Ask for number of licenses to add and validates
			System.out.println("Number of licences: ");
			int numberOfLicences = askInputIntAndValidate(0, Integer.MAX_VALUE);

			// Adds application to bag
			shoppingBag.putInBag(app, numberOfLicences);
			
			// Returns to buy menu
			buyAppMenu(aClient, shoppingBag);
			break;

		case 2: // Alter number application licenses in the bag
				
			//Checks if bag is empty
			if(shoppingBag.getBagItems().size() > 0)
			{
				// Asks for application to remove and compares with store
				App appTocheck = askForAppNameValidatesAndReturnsApp(shoppingBag.getAppsInBag());
				
				// Asks for the number to change to
				System.out.println("Number of licences: ");
				int numberOfLicencesTochange = askInputIntAndValidate(0, Integer.MAX_VALUE);
				
				// Overrides the value in the bag
				shoppingBag.alterAppValueInBag(appTocheck, numberOfLicencesTochange);
			}
			else
			{
				System.out.println("App not in the bag: ");	
			}
			buyAppMenu(aClient, shoppingBag);
			break;
					
		case 3: // Remove application from bag
				
			//Checks if bag is empty
			if(shoppingBag.getBagItems().size() > 0)
			{
				// Asks for application to remove and compares with store
				App appToRemove = askForAppNameValidatesAndReturnsApp(shoppingBag.getAppsInBag());
				
				shoppingBag.removeFromBag(appToRemove);
			}
			else
			{
				System.out.println("App not in the bag: ");	
			}
			buyAppMenu(aClient, shoppingBag);
			break;
				
		case 4: // Check value of shopping bag"
			
			// Informs total value in the bag
			System.out.println("The value of goods in the bag is:" + String.format("%.2f", shoppingBag.valueInBag()));
			
			// Checks if user has discount and informs value to pay
			System.out.println("Value to pay (With Client Discounts): " + String.format("%.2f", store.valueInBag(shoppingBag, aClient)));
			
			// Returns to options menu
			buyAppMenu(aClient, shoppingBag);
			break;

		case 5: // Checkout
			
			PurchaseApps purchase = store.checkout(aClient, shoppingBag);	
			
			if(store.savedInPurchase(purchase)!=0)
			{
				System.out.println("Premium Saved:" + String.format("%.2f",store.savedInPurchase(purchase)));			
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
			
	
	/** Buy Subscription menu **/
	private void buySubscritionMenu(Client aClient , Bag shoppingBag)
	{
		// Create new shopping bag
		if (shoppingBag == null)
		{
			shoppingBag = store.createShoppingBag();
		}
		
		System.out.print("\n"
				+ "Buy Options:"
				+ "\n (0) Return / Cancel"
				+ "\n (1) Add application to Subscribe"
				+ "\n (2) Remove app from Subscriptions Bag"
				+ "\n (3) Check value of subscriptions to be added"
				+ "\n (4) Checkout"
				+ "\n (4) List application subscribed"
				+ "\n ");
		
		// asks for user menu input and verifies its validity
		switch (askInputIntAndValidate(0,3))
		{

		case 0:
			menuClient(aClient);
			break;

		case 1: // Add Application 
			
			// Informs applications in store
			System.out.println("Anual Subscription is 1/10 of BasePrice");
			System.out.println("Apps in the Store: [AppName:BasePrice]");
			printList(store.getAppsList());
			//If not empty informs of applications in the bag 
			if(shoppingBag.getBagItems().size() > 0)
			{
				System.out.println("\nContents: "
						+ "\n" +  shoppingBag);
			}

			boolean askForAppToSub = true;
			
			while(askForAppToSub)
			{	
				// Asks for application to subscribe
				App appToSub = askForAppNameValidatesAndReturnsApp(store.getAppsList());
				
				if(!aClient.getSubscribedApps().contains(appToSub))
				{
					// Adds application to bag
					shoppingBag.putInBag(appToSub, 1);
					askForAppToSub = false;
				}
				else
				{
					System.out.println("App already Subscibed");
				}
			}
			
			// Returns to buy subscription menu
			buySubscritionMenu(aClient, shoppingBag);
			break;	

		case 2: // Remove application from Subscriptions Bag
			
			//Checks if bag is empty
			if(shoppingBag.getBagItems().size() > 0)
			{
				// Asks for application to remove and compares with store
				App appToRemove = askForAppNameValidatesAndReturnsApp(shoppingBag.getAppsInBag());
				
				shoppingBag.removeFromBag(appToRemove);
			}
			else
			{
				System.out.println("App not in the bag: ");	
			}
			// Returns to buy subscription menu
			buySubscritionMenu(aClient, shoppingBag);
			break;
				
		case 4: // Informs total value in the bag
			
			System.out.println("The value of anual subcriptions for this year in the bag is:"
					+ "" + String.format("%.2f", shoppingBag.valueInBag() / 10));
			
			// Checks if user has discount and informs value to pay
			System.out.println("Value to pay (With Client Discounts): " + String.format("%.2f", store.valueInBag(shoppingBag, aClient) / 10));
			
			// Returns to buy subscription menu
			buySubscritionMenu(aClient, shoppingBag);
			break;

		case 5:
			if(store.checkoutSubscription(aClient, shoppingBag)) 
			{
				System.out.println("Subscriptions done!!");
			}
					
			menuClient(aClient);
			break;

		default:
			// Ask again for input!!
			System.out.println("Please introduze a correct option");

			// Returns to buy subscription menu
			buySubscritionMenu(aClient, shoppingBag);
			break;
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

	/** Manager menu options **/
	private void menuAdministrator(Administrator aAdministrator)
	{
		System.out.print("\n"
				+ "Administrator Options:"
				+ "\n  (0) Return"
				+ "\n  (1) Move Time Forward"
				+ "\n  (2) Total earnings"
				+ "\n  (3) Earnings by programmer"
				+ "\n  (4) Applications with weekly discount"
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
				+ "\n (15) Applications with Monthly discount / Type"
				+ "\n (16) Users with 'Incentive' Discount"
				+ "\n (17) Clients invited by client"
				+ "\n (18) List all free apps chosen by each client"
				+ "\n");

		switch (askInputIntAndValidate(0, 18))
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
			System.out.println("Apps with Weekly discount: ");
			printList(store.checkAppsWithWeeklyDiscounts());
			menuAdministrator(aAdministrator);
			break;


		case 5:
			// Prints the times an application was sold
			App app = askForAppNameValidatesAndReturnsApp(store.getAppsList());
			System.out.println("The app " + app.getName() + " was sold " + app.timesSold() + " times.");
			menuAdministrator(aAdministrator);
			break;

		case 6:
			// Prints application sales by week
			System.out.println("Wanted week: ");
			int week =  askInputIntAndValidate(0, store.getCurrentWeek().getWeekNumber());
			System.out.println("The sold applications are: ");
			printMap(store.getWeekSales(week));
			menuAdministrator(aAdministrator);
			break;

		case 7:
			// finds less sold applications in a defined week
			System.out.println("Wanted week: ");
			week =  askInputIntAndValidate(0, store.getCurrentWeek().getWeekNumber() -1);
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
			int weekNumber =  askInputIntAndValidate(0, store.getCurrentWeek().getWeekNumber());
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
		
		case 15:
			// Check applications with discounts
			System.out.println("Apps with Monthly discount: " + store.getAppTypeWithDiscount());
			printList(store.checkAppsWithMonthlyDiscounts());
			menuAdministrator(aAdministrator);
			break;

		case 16:
			System.out.println("Clients with Incentive discount: ");
			if (store.clienstWithIncentiveDiscount().size() > 0)
			{
				printList(store.clienstWithIncentiveDiscount());
			}
			else
			{
				System.out.println("None");
			}
			menuAdministrator(aAdministrator);
			break;
			
		case 17:
			// prints clients invited by client
			System.out.println("Find clients invited introduce: ");
			Client user = (Client) askForUserIdValidatesAndReturnsUser();
			
			System.out.println("Clients invited: ");
			if (store.clientsInvited(user).size() > 0)
			{
				printList(store.clientsInvited(user));
			}
			else
			{
				System.out.println("None");
			}
			menuAdministrator(aAdministrator);
			break;
			
		case 18:
			System.out.println("Free applications chosen by clients");
			System.out.println("Client Data : App List");
			printMap(store.freeAppsChosenByClients());
			break;

		default:
			// Ask again for input!!
			System.out.println("Please introduze a correct option");
			menuAdministrator(aAdministrator);
			break;
		}
	}

	/*
	* Several methods for input & validation :
	 * 1. Integer input validation | askInputIntAndValidate(int min, int max)
	 * 2. User login | serLogin()
	 * 3. User creation | userCreation()
	 * 4. User validation | askForUserIdValidatesAndReturnsUser()
	 * 4. Score Validation | askForScoreAndValidates()
	 * 5. Application Validation | askForAppNameValidatesAndReturnsApp()
	 * */
	
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
			System.out.println("\nUserId: ");
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
			System.out.print("\nPassword: ");
			userPassword = scanText.nextLine();
			if (userPassword.length() > 4 )
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
	
	
	/** Ask for password confirmation **/
	public String reconfirmPassword(User aUSer)
	{
		boolean passwordCorrect = false;
		int counter = 0;
		while(passwordCorrect == false)
		{

			System.out.println("Password: ");
			String password = scanText.nextLine();

			if(aUSer.isPasswordCorrect(password))
			{
				return password;
			}
			
			else if(password == "exit") 
			{
				break;
			}
	
			else
			{
				counter += 1;
				if(counter > 3)
				{
					System.out.print("Failed to introduce password to many times. Exiting");
					break;
				}
				System.out.print("Password introduced incorrect."
						+ "\nPlease introduce correct password or 'exit'");
			}
		}
		return null;
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
			if (store.userExists(userId))
			{
				askForId = false;
				user = store.findUser(userId);

			}
			else {
				System.out.print("UserId not valid!");
			}
		}
		return user;
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
	
	/** Ask user for an application, verifies if exists and returns application object **/
	private App askForAppNameValidatesAndReturnsApp(List<App> aAppList)
	{
		boolean askForAppName = true;
		App app = null;

		while(askForAppName)
		{
			System.out.print("\nAppName: ");
			String appName = scanText.nextLine();

			if (appExists(appName, aAppList))
			{
				askForAppName = false;
				app = findApp(appName, aAppList);
			}
			else if(appName.equals("exit"))
			{
				return null;
			}
			else {
				System.out.print("Please input a valid application name or 'exit'");
			}
		}
		return app;
	}
	
	/** Verify if application exists **/
	public boolean appExists(String aAppName, List<App> aAppList)
	{
		boolean exists = false;
		for(App app: aAppList)
		{
			if(app.getName().equals(aAppName))
			{
				exists = true;
			}
		}
		return exists;
	}
	
	/** Find and return application **/
	public App findApp(String aAppName, List<App> aAppList)
	{
		App application = null;
		for(App app : aAppList)
		{
			if(app.getName().equals(aAppName));
			application = app;
		}
		return application;
	}

	
	/* Prints */
	public void printList(List<?> aList)
	{
		if(aList.isEmpty())
		{
			System.out.println("No Items in List");
		}
		else
		{
			for(Object obj : aList)
			{
				System.out.println(obj);
			}
		}
	}

	public <K, V> void printMap(Map<K, V>  aMap)
	{
		if(aMap.isEmpty())
		{
			System.out.println("No Items in List");
		}
		else
		{
			for(Map.Entry<K, V>  entry : aMap.entrySet())
			{
				System.out.println(entry.getKey() +" : " + entry.getValue());
			}
		}
		
	}

}
