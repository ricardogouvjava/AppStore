package appstore;

import java.util.ArrayList;
import java.util.List;

public class Client extends User 
{
	private List<String> appsBought;

	// Constructor
	public Client(String aName, int aAge)
	{
		super(aName, aAge);
		appsBought = new ArrayList<String>();
	}

	// Methods
	
	public void buy(String aAppName)
	{
		appsBought.add(aAppName);
	}
	
	/** Allows user to give a score to an application **/
	public void giveScore(String aAppName, double aScore, String aComment, AppStore aStore)
	{
		/* Verify if user bought the application so he can score it */
		for (App app: aStore.getApps())
		{
			if (app.getName().equals(aAppName) && this.getAppsbought().contains(aAppName))
			{
				Score score = new Score(this.getName(), aAppName, aScore, aComment);
				app.addScore(score);
				aStore.addScore(score);
				this.addScore(aScore);
				
				// Update programmer score
				String programmerName = app.getProgrammerName();
				for (Programmer programmer : aStore.getProgrammersList())
				{
					if (programmer.getName().equals(programmerName))
					{
						programmer.addScore(aScore);
					}
				}
			}
			
			else if (app.getName().equals(aAppName))
			{
				System.out.println("\nThis user is not allowed to score this application");
			}
		}	
	}
	
	
	// Getters 
	public List<String> getAppsbought()
	{
		return appsBought;
	}
	
	// Setters 
	public void setAppsbought(List<String> aAppsbought)
	{
		appsBought = aAppsbought;
	}
}
