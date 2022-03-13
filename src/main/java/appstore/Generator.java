package appstore;

import java.util.List;
import java.util.Random;

public class Generator
{
	private static Random rand;
	private AppStore store;
	private String[] userPollType = {"Client", "ClientPremium"};


	public Generator(AppStore aStore)
	{
		rand = new Random();
		store = aStore;

	}

	// Methods

	public void generateDaysData()
	{
		/** User Generation **/
		if(store.getUsersList().size() < 1000)
		{
			int generateClients = rand.nextInt(3) + 1;
			for(int i = 0 ; i <=  generateClients ; i++)
			{
				generateUser();

			}
		}
		
		/** User invite **/ 
		if(store.getUsersList().size() < 1000)
		{
			int generateClients = rand.nextInt(3) + 1;
			for(int i = 0 ; i <=  generateClients ; i++)
			{
				pickRandomClient().inviteClient(store);
			}
		}
				
		/** Programmer Generation **/ 
		if(store.getProgrammersList().size() < 100)
		{
			int generateProgrammers = rand.nextInt(2) + 1;
			for(int i = 0 ; i <=  generateProgrammers ; i++)
			{
				generateProgrammer();
			}
		}

		/** Application Generation **/
		if (store.getAppsList().size() < 500)
		{
			int generateApps = rand.nextInt(store.getProgrammersList().size() / 5 + 1) + 1;
			for(int i = 0 ; i <=  generateApps ; i++)
			{
				generateProgrammerDesignation();
			}
		}

		/** Purchase Generation **/
		int generatePurchases = (int) (rand.nextInt(store.getUsersList().size()) * 0.08 + 1);
		for(int i = 0 ; i <=  generatePurchases ; i++)
		{
			generatePurchase();
		}

		/** Score Generation **/
		if(store.getScores().size() < store.getPurchases().size() * 0.5)
		{
			int generateScores = (int) ((store.getPurchases().size() * 0.2) + 1);
			for(int i = 0 ; i <=  generateScores ; i++)
			{
				generateRandomScore();
			}
		}
		
		if(store.getAppsList().size() > 3)
		{
			/** Pick free weekly application generation **/
			Client clientWantsFreeApp = pickRandomClient();
			App appWanted =  pickRandomApp();
			clientWantsFreeApp.addChoosenFreeApp(appWanted);
		}
		
	}

	/** User Generator **/
	public void generateUser()
	{
		String randomType = userPollType[rand.nextInt(userPollType.length)];
		store.addUser(randomType, generateRandomUserId(), generateRandomPassword(), generateRandomAge());
	}
	
	/** Generates client and returns object 
	 * @return **/
	public Client generateReturnClient()
	{
		Client client = null;
		while(client == null)
		{
		
			String randomType = userPollType[rand.nextInt(userPollType.length)];
			
			if(randomType.equals("Client"))
			{
				client = new Client(generateRandomUserId(), generateRandomPassword(), generateRandomAge());
			}
			else if((randomType.equals("ClientPremium")))
				{
				client = new ClientPremium(generateRandomUserId(), generateRandomPassword(), generateRandomAge(), store.getPremimumDiscount());
				}
		}	
		System.out.print(client);
		return client;		
	}
	
	/** Programmer Generator**/
	public void generateProgrammer()
	{
		store.addUser("Programmer", generateRandomUserId(), generateRandomPassword(), generateRandomAge());
	}
	
	/** UserId Generator**/
	private String generateRandomUserId()
	{
		return randomStringGenerator() + randomStringGenerator();
	}

	/** Random String Generator **/
	private String randomStringGenerator()
	{
		String[] Beginning = { "Kr", "Ca", "Ra", "Mrok", "Cru",
				"Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
				"Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
				"Mar", "Luk" };
		String[] Middle = { "air", "ir", "mi", "sor", "mee", "clo",
				"red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
				"marac", "zoir", "slamar", "salmar", "urak" };
		String[] End = { "d", "ed", "ark", "arc", "es", "er", "der",
				"tron", "med", "ure", "zur", "cred", "mur" };

		return Beginning[rand.nextInt(Beginning.length)] + Middle[rand.nextInt(Middle.length)] +
				   End[rand.nextInt(End.length)];
	}

	/** Random Age Generator **/
	private int generateRandomAge()
	{
		return rand.nextInt(72) + 18;
	}

	/** Random Password Generator **/
	private String generateRandomPassword()
	{
		return randomStringGenerator() + randomStringGenerator();
	}

	/** Application Generator **/
	public void generateProgrammerDesignation()
	{
		List<Programmer> tempList = store.getProgrammersList();

		Programmer programmer = tempList.get(rand.nextInt(tempList.size()));

		try
		{
			App app = store.designateProgrammer(generateRandomUserId(), randomPriceGenerator(), randomAppType(), programmer);
			System.out.println("App added >> " + app);
		}
		
		catch(Exception e)
		{
			System.out.println("Fail to generate app");
		}
	}

	/** Purchases Generator **/
	public void generatePurchase()
	{
		Client randomClient = pickRandomClient();
		Bag bag = generateRandomBag(3);

		store.checkout(randomClient, bag);
	}

	/** Purchase Bag Generator **/
	private Bag generateRandomBag(int maxPurchaseItems)
	{
		int numberApps = rand.nextInt(maxPurchaseItems) + 1;
		Bag tempBag = new Bag();


		for(int i = 0; i <= numberApps; i++)
		{
			App randomApp =  pickRandomApp();
			tempBag.putInBag(randomApp, 1);
		}
		return tempBag;
	}

	/** Random application chooser **/
	private App pickRandomApp()
	{
		return store.getAppsList().get(rand.nextInt(store.getAppsList().size()));

	}

	/** Random Client chooser **/
	private Client pickRandomClient()
	{
		Client client= null;
		try
		{
			int randomClientIndex = rand.nextInt(store.getClientsList().size());
			client = store.getClientsList().get(randomClientIndex);
		}
		catch(Exception e)
		{
			System.out.print("No client found" + e.getMessage());
		}
		return client;
	}

	/** Score Generator **/
	private void generateRandomScore()
	{
		Score score = null;
		Client client = null;
		App app = null;
		boolean findUserToScore = true;
			
		while(findUserToScore)
		{
			client = pickRandomClient();
			app = pickRandomNotScoredApp(client);
			if(app == null)
			{
				findUserToScore = true;
			}
			else
			{
				double scoreValue = rand.nextDouble() * 5;
				String comment = "";

				for(int i = 0; i <= (rand.nextInt(10)); i++)
				{
					comment += randomStringGenerator();
				}

				findUserToScore = false;
				score = client.giveScore(app, scoreValue, comment, store);
			}
		}
	
		System.out.print("Score aded: " + score);
	}

	/** Random RandomNotScoredApp chooser **/
	private App pickRandomNotScoredApp(Client client)
	{
		App app = null;
		List<App> notScoredApp = client.getAppsNotScored();
		int size = notScoredApp.size();
		if(size > 0)
		{
			int randomIndex = rand.nextInt(size);
			app = notScoredApp.get(randomIndex);
		}
		return app;
	}

	/** Random AppType chooser **/
	public static AppType randomAppType()
	{
		return AppType.values()[rand.nextInt(AppType.values().length)];
	}

	/** Random Price Generator **/
	private double randomPriceGenerator()
	{
		return rand.nextDouble() * rand.nextInt(100) + 1;
	}

	//Getter & Setters
	public AppStore getStore() {
		return store;
	}
	public void setStore(AppStore store) {
		this.store = store;
	}

}
