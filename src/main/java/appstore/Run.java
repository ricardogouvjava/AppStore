package appstore;

public class Run
{	
	static AppStore storeRequalificar;
	static Menu storeMenu;
	
	public static void main(String[] args)
	{
		storeRequalificar = new AppStore("Requalificar App Store");
		storeMenu = new Menu(storeRequalificar);
		storeMenu.menuMain();
	
	}	
}
