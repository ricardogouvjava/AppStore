package appstore;

import java.util.List;
import java.util.Random;

public class Generator
{
	private Random rand;
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
		if(store.getUsersList().size() < 1000)
		{
			int generateClients = rand.nextInt(3) + 1;
			for(int i = 0 ; i <=  generateClients ; i++) 
			{
				generateUser();
				
			}
		}
		
		if(store.getProgrammersList().size() < 100)
		{
			int generateProgrammers = rand.nextInt(2) + 1;
			for(int i = 0 ; i <=  generateProgrammers ; i++) 
			{
				generateProgrammer();
			}
		}
		if (store.getAppsList().size() < 100)
		{
			int generateApps = rand.nextInt(store.getProgrammersList().size() % 5 + 1);
			for(int i = 0 ; i <=  generateApps ; i++) 
			{
				generateProgrammerDesignation();
			}	
		}
		
		
		
		int generatePurchases = (int) (rand.nextInt(store.getUsersList().size()) * 0.08 + 1);
		for(int i = 0 ; i <=  generatePurchases ; i++) 
		{
			generatePurchase();
		}
		
		if(store.getScores().size() < store.getPurchases().size() * 0.25)
		{
			int generateScores = (int) (store.getPurchases().size() * 0.2);
			for(int i = 0 ; i <=  generateScores ; i++) 
			{
				generateRandomScore();
			}	
		}
		
	}
		
	/**
	private List<String> chooseUserNameFromFile(String aFileNames)
	{
		List<String> users = FileReaderTxt.convertTxtDataNamesToMap(aFileNames);
		
		namePollSize = users.size();
		
		int randomIndex = rand.nextInt(namePollSize);
		String fullname = users.get(randomIndex);
		
		List<String> splitedFullName = Arrays.asList(fullname.split(" "));
		
		users.remove(randomIndex);
		
		return splitedFullName;
	}
	**/
	
	private String generateRandomUserId()
	{
		return randomStringGenerator() + randomStringGenerator();
	}
	
	private String generateRandomPassword()
	{
		return randomStringGenerator() + randomStringGenerator();
	}
	
	private int generateRandomAge()
	{
		return rand.nextInt(72) + 18;
	}
	
	
	public void generateUser() 
	{
		String randomType = userPollType[rand.nextInt(userPollType.length)];
		store.addUser(randomType, generateRandomUserId(), generateRandomPassword(), generateRandomAge());
	}
	
	public void generateProgrammer() 
	{	
		store.addUser("Programmer", generateRandomUserId(), generateRandomPassword(), generateRandomAge());
	}
	
	public void generateProgrammerDesignation()
	{
		List<Programmer> tempList = store.getProgrammersList();
		
		Programmer programmer = tempList.get(rand.nextInt(tempList.size()));
		
		store.designateProgrammer(generateRandomUserId(), randomPriceGenerator(), randomAppType(), programmer);
	}
	
	/** Generates purchases and checkout**/
	public void generatePurchase()
	{
		Client randomClient = pickRandomClient();
		Bag bag = generateRandomBag(3);
		
		store.checkout(randomClient, bag);
	}
	
	private void generateRandomScore()
	{	
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
				client.giveScore(app, scoreValue, comment, store);
			}
		}
	}
	
	private App pickRandomApp()
	{
		return store.getAppsList().get(rand.nextInt(store.getAppsList().size()));

	}
	
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
	
	private double randomPriceGenerator()
	{
		return rand.nextDouble() * rand.nextInt(100) + 1;
	}
	
	private AppType randomAppType()
	{
		return AppType.values()[rand.nextInt(AppType.values().length)];
	}
	
	
	
	//Getter & Setters
	public AppStore getStore() {
		return store;
	}

	public void setStore(AppStore store) {
		this.store = store;
	}

	
}
