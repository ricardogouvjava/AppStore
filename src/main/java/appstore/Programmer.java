package appstore;

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
	public App developApp(String aAppName, double aAppPrice, AppType aAppType)
	{
		App app = new App(aAppName, aAppPrice, aAppType, this);
		addApp(app, 0);
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
