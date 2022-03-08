package appstore;

import java.util.ArrayList;
import java.util.List;

public class Programmer extends User 
{
	private List<String> developpedApps;
	private List<Score> reviews;
	double averageScoreReview;

	
	//Constructor
	public Programmer(String aFirstName, String aLastName, int aAge)
	{
		super(aFirstName, aLastName, aAge);
		developpedApps = new ArrayList<String>();
		reviews = new ArrayList<Score>();
		averageScoreReview = 0;
	}
	
	//Methods
	public App developApp(String aAppName, double aAppPrice, AppType aAppType)
	{
		App app = new App(aAppName, aAppPrice, aAppType, getName());
		developpedApps.add(aAppName);
		return app;
	}
	
	public void addReview(Score aScore)
	{
		reviews.add(aScore);
		updateScoreReview();
	}
	
	public void updateScoreReview() 
	{
		double sum = 0;
		for (Score review : reviews)
		{
			sum += review.getScoreValue();
		}
		averageScoreReview = sum / reviews.size();
	}
	
	public List<String> getAppsreviewed()
	{
		List<String> templist = new ArrayList<String>();
		for(Score score : reviews)
		{
			templist.add(score.getAppName());
		}
		return templist;
	}
	
	public List<String> getAppsNotScored()
	{
		List<String> appsNotScored = developpedApps;
		appsNotScored.removeAll(getAppsreviewed());
		
		return appsNotScored;
	}
		
	public double getEarnings(AppStore aStore)
	{	
		double earnings = 0;
		for(String appName : developpedApps)
		{
			App app = aStore.findApp(appName);
			earnings += app.getPrice() * app.timesSold(); 
		}
		return earnings;
	}
	
	// Getters
	public List<String> getDeveloppedApps() 
	{
		return developpedApps;
	}

	
	public List<Score> getReviews() {
		return reviews;
	}

	public double getAverageScoreReview() {
		return averageScoreReview;
	}

	// Setters
	public void setDeveloppedApps(List<String> aDeveloppedApps)
	{
		developpedApps = aDeveloppedApps;
	}

	public void setAppsreviewed(List<Score> appsreviews) {
		this.reviews = appsreviews;
	}
	
}
