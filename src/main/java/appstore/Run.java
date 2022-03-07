package appstore;

import java.time.LocalDate;
import java.time.Month;

public class Run
{	
	static AppStore storeRequalificar;
	static Menu storeMenu;
	
	
	
	public static void main(String[] args)
	{
		storeRequalificar = new AppStore("Requalificar App Store");
		storeRequalificar.setDate(LocalDate.of(2022, Month.FEBRUARY, 01));		
		storeMenu = new Menu(storeRequalificar);
		storeMenu.menuMain();
	
	}	
}
