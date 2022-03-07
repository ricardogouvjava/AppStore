package appstore;

import java.util.Arrays;
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
	
	private List<String> generateRandomUserName()
	{
		List<String> splitedFullName = Arrays.asList(new String[]{randomNameGenerator(), randomNameGenerator()});
		return splitedFullName;
	}
	
	public void generateUser() 
	{
		List<String> randomName = generateRandomUserName();
		
		int randomAge = rand.nextInt(82) + 18;
		
		String randomType = userPollType[rand.nextInt(userPollType.length)];
		
				
		store.addUser(randomType, randomName.get(0), randomName.get(1), randomAge);
	}
	
	
	public void generateProgrammer() 
	{
		List<String> randomName = generateRandomUserName();
		
		int randomAge = rand.nextInt(82) + 18;
		
		store.addUser("Programmer", randomName.get(0), randomName.get(1), randomAge);
	}
	
	public void generateProgrammerDesignation()
	{
		List<Programmer> tempList = store.getProgrammersList();
		
		Programmer programmer = tempList.get(rand.nextInt(tempList.size()));
		
		store.designateProgrammer(randomNameGenerator(), randomPriceGenerator(), randomAppType(), programmer);
	}
	
	/** Generates purchases and checkout**/
	public void generatePurchase()
	{
		Client randomClient = pickRandomClient();
		Bag bag = generateRandomBag(3);
		
		store.checkout(randomClient.getName(), bag);
	}
	
	private void generateRandomScore()
	{	
		Client client = null;
		String appName = null;
		boolean findUserToScore = true;
		while(findUserToScore)
		{
			client = pickRandomClient();
			appName = pickRandomNotScoredApp(client);
			if(appName.equals("Not Found"))
			{
				findUserToScore = true;
			}
			else
			{
				double scoreValue = rand.nextDouble() * 5;
				String comment = "";
				
				for(int i = 0; i <= (rand.nextInt(10)); i++)
				{
					comment += randomNameGenerator();
				}
			
				findUserToScore = false;
				client.giveScore(appName, scoreValue, comment, store);
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
	
	private String pickRandomNotScoredApp(Client client)
	{
		String appName = null;
		List<String> notScoredAppNames = client.getAppsNotScored();
		int size = notScoredAppNames.size();
		if(size > 0)
		{		
			int randomIndex = rand.nextInt(size);
			appName = notScoredAppNames.get(randomIndex);
		}
		else 
		{
			appName = "Not Found";
		}
		return appName;
	}
	
	private String randomNameGenerator()
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
