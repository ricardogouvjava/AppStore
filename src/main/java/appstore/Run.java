package appstore;

import java.time.LocalDate;
import java.time.Month;

public class Run
{	
	private static AppStore storeRequalificar;
	private static Menu storeMenu;
	
	
	public static void main(String[] args)
	{
		storeRequalificar = new AppStore("Requalificar App Store");
		storeRequalificar.setDate(LocalDate.of(2022, Month.FEBRUARY, 01));
		
		loadTestData(storeRequalificar);
		
		storeMenu = new Menu(storeRequalificar);
		storeMenu.menuMain();
	
	}
		
	/** Several objects for test **/
	public static void loadTestData(AppStore store) 
	{
		
		/** Creates 10 users - 5 clients & 5 programmers **/
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
	
		/** Creates 8 Applications trough programmer designation **/
		/* Generate new Applications */
		store.designateProgrammer("Tetris", 2.0, AppType.GAMES, "Maria");
		store.designateProgrammer("Skyrim", 50.0, AppType.GAMES, "Sienna");
		store.designateProgrammer("DocReader", 25.0, AppType.UTILITIES, "Joana");
		store.designateProgrammer("Healthy", 15.0, AppType.HEALTHANDFITNESS, "Sienna");
		store.designateProgrammer("GoFun", 10.0, AppType.ENTERTAINMENT, "Ricardo");
		store.designateProgrammer("ReadYouMust", 20.0, AppType.EDUCATION, "Ricardo");
		store.designateProgrammer("GoWorld", 13.50, AppType.TRAVEL, "Miguel");
		store.designateProgrammer("Brains and Fun", 8.0, AppType.EDUCATION, "Ricardo");
		/** Creates 3 purchases examples **/
	
		// Create new shopping bags and adds applications to bag!

		// Shopping list 1 
		Bag sb1 = store.createShoppingBag(); 
		store.addtobag("Tetris", sb1, 1);
		store.addtobag("GoFun", sb1, 1);
		store.checkout("Alice", sb1);

		// Shopping list 2 
		Bag sb2 = store.createShoppingBag();
		store.addtobag("DocReader", sb2, 1);
		store.addtobag("Healthy", sb2, 1);
		store.addtobag("Tetris", sb2, 1);
		store.addtobag("GoFun", sb2, 1);
		store.checkout("Sandra", sb2);
		
		// Shopping list 3 
		Bag sb3 = store.createShoppingBag();
		store.addtobag("GoWorld", sb3, 1);
		store.addtobag("Brains and Fun", sb3, 1);
		store.addtobag("Skyrim", sb3, 1);
		store.addtobag("DocReader", sb3, 1);
		store.addtobag("Healthy", sb3, 1);
		store.addtobag("ReadYouMust", sb3, 1);
		store.addtobag("GoFun", sb3, 1);
		
		store.checkout("Ricardo", sb3);

		/** Creates 2 Scores examples **/
		// Give Score
		((Client) store.chekIfClient("Sandra")).giveScore("Tetris", 4.8, "Super Dope", store);
		((Client) store.chekIfClient("Alice")).giveScore("Tetris", 4.5, "I like this old game!", store);;
	}

	
}
