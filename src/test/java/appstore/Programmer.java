package appstore;

import java.util.Date;

public class Programmer extends User
{
	double averageScoreReview;


	//Constructor
	public Programmer(String aId, String aPassword, int aAge)
	{
		super(aId, aPassword, aAge);
		averageScoreReview = 0;
	}

	//Methods
	/** Allows programmer to add application to add store**/
	public App developApp(String aAppName, double aAppPrice, AppType aAppType, Date aCurentDate)
	{
		App app = new App(aAppName, aAppPrice, aAppType, aCurentDate, this);

		addApp(app, 0); // adds application to programmer list of applications
		
		return app;
	}
	
	
	// Getters
	public double getAverageScoreReview()
	{
		return averageScoreReview;
	}

	public double getEarnings(AppStore aStore)
	{
		double earnings = 0;
		for(App app : getApps().keySet())
		{
			earnings += app.getPrice() * app.timesSold();
		}
		return earnings;
	}

	// Setters
}
