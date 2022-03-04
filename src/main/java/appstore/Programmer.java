package appstore;

import java.util.ArrayList;
import java.util.List;

public class Programmer extends User {
	private List<App> developpedApps;
	
	//Constructor
	public Programmer(String aName, int aAge)
	{
		super(aName, aAge);
		developpedApps = new ArrayList<App>();
	}
	
	//Methods
	public App developApp(String aAppName, double aAppPrice, AppType aAppType)
	{
		App app = new App(aAppName, aAppPrice, aAppType, getName());
		developpedApps.add(app);
		return app;
	}
	
	@Override
	public void addScore(double aScore)
	{
		super.addScore(aScore);
		updateScore();
	}
	
	@Override
	public void updateScore() 
	{
		double sum = 0;
		for(App app : developpedApps) 
		{
			sum += app.getAverageScore();
		}
		super.setAverageScore(sum / developpedApps.size());
	}
	
	public double getEarnings()
	{	
		double earn = 0;
		for(App app : developpedApps)
		{
			earn += app.getPrice(); 
		}
		return earn;
	}
	
	// Getters
	public List<App> getDeveloppedApps() 
	{
		return developpedApps;
	}

	// Setters
	public void setDeveloppedApps(List<App> aDeveloppedApps) {
		developpedApps = aDeveloppedApps;
	}
	
	
}
