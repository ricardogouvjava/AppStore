package appstore;

import java.util.ArrayList;
import java.util.List;

public class Client extends User 
{
	private List<String> appsBought;
	private double spendings;
	private double averageScore; 
	private List<Score> scores;

	// Constructor
	public Client(String aFirstName, String aLastName, int aAge)
	{
		super(aFirstName, aLastName, aAge);
		scores = new ArrayList<>();
		averageScore = 0;
		appsBought = new ArrayList<String>();
		spendings = 0;
	}

	// Methods
	/** Adds application to user library **/
	public void buy(Purchase aPurchase)
	{
		for (App app : aPurchase.getPurchaseBag().getAppsInBag())
		{
			appsBought.add(app.getName());
		}
		
		updateSpendings(aPurchase.getPurchaseBag().valueInBag());
	}
	
	public List<String> getAppsScored()
	{
		List<String> templist = new ArrayList<String>();
		for(Score score : scores)
		{
			templist.add(score.getAppName());
		}
		return templist;
	}
	
	
	
	/** Allows user to give a score to an application **/
	public void giveScore(String aAppName, double aScore, String aComment, AppStore aStore)
	{
		/* Verify if user bought the application so he can score it */
		for (App app: aStore.getAppsList())
		{
			if (app.getName().equals(aAppName) && appsBought.contains(aAppName))
			{
				Score score = new Score(this.getName(), aAppName, aScore, aComment);
				app.addScore(score);
				aStore.addScore(score);
				scores.add(score);
				updateScore();
				
				// Update programmer score
				String programmerName = app.getProgrammerName();
				for (Programmer programmer : aStore.getProgrammersList())
				{
					if (programmer.getName().equals(programmerName))
					{
						programmer.addReview(score);
					}
				}
				System.out.println("Score given: " + score);
			}
			
			else if (app.getName().equals(aAppName))
			{
				System.out.println("\nThis user is not allowed to score this application: " + app.getName());
			}
		}	
	}
	
	public void updateScore() 
	{
		double sum = 0;
		for (Score score : getScores())
		{
			sum += score.getScoreValue();
		}
		averageScore = sum / scores.size();
	}
	
	public List<String> getAppsNotScored()
	{
		List<String> appsNotScored = appsBought;
		appsNotScored.removeAll(this.getAppsScored());
		
		return appsNotScored;
	}
	
	public void updateSpendings(Double aValue)
	{	
		spendings += aValue;
	}
	
	// Getters 
	public List<String> getAppsbought()
	{
		return appsBought;
	}
	
	public double getSpendings() 
	{
		return spendings;
	}
	
	public List<Score> getScores() 
	{
		return scores;
	}

	public double getAverageScore()
	{
		return averageScore;
	}
	
	
	// Setters 
	public void setAppsbought(List<String> aAppsbought)
	{
		appsBought = aAppsbought;
	}

	
}

