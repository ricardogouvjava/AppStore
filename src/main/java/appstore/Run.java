package appstore;

public class Run
{
	static AppStore storeRequalificar;
	static Menu storeMenu;

	public static void main(String[] args)
	{
		storeRequalificar = new AppStore("Requalificar App Store");
		storeMenu = new Menu(storeRequalificar);
		storeRequalificar.addUser("Administrator", "admin", "admin", 0);
		storeRequalificar.addUser("Client", "c", "0", 0);
		storeRequalificar.addUser("ClientPremium", "cp", "0", 0);
		storeRequalificar.addUser("Programmer", "p", "0", 0);
		System.err.println("Month start:"+ storeRequalificar.getCurrentMonth());
		System.err.println("WeeK start:"+ storeRequalificar.getCurrentWeek().getWeekNumber());
		storeRequalificar.forwardDateXDays(10);
		storeMenu.menuMain();
	}
}
