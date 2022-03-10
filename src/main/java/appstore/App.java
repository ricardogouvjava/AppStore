package appstore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App
{
	private String name;
	private double price;
	private double weekDiscount;
	private double score; // Average Score[0:5]
	private AppType type;
	private Programmer programmer;
	private List<Score> scores; // List of Scores given to the application
	private Map<Date, Integer> sales; //all application sales
	
	/** Generates new application **/
	public App(String aName, double aPrice, AppType aType, Programmer aProgrammer)
	{
		name = aName;
		price = aPrice;
		weekDiscount = 0;
		type = aType;
		programmer = aProgrammer;
		scores = new ArrayList<>();
		sales = new HashMap<>();
	}

	/** Adds a new score to score list and updates the average score **/
	public void addScore(Score aScore)
	{
		scores.add(aScore);
		updateScore();
	}

	/*Methods
	 * to String
	 * getPrice()
	 * registerSale
	 * timesSold
	 * updateScore
	 * userScoredApp
	 */
	
	/** Prints minimal application information **/
	@Override
	public String toString()
	{
		return "App: " + name;
	}

	/**Calculates price considering discount exits **/
	public double getPrice()
	{
		return price * (100 - weekDiscount) / 100;
	}
	
	/** register when the application was sold and updates sales counter **/
	public void registerSale(Date aBuyDate, int aLicences)
	{
		sales.put(aBuyDate , aLicences);
	}

	/** calculates times sold **/
	public int timesSold()
	{
		int timesSold = 0;
		for(Integer value: sales.values())
		{
			timesSold += value;
		}
		return timesSold;
	}

	/** updates the application average score using the score list **/
	private void updateScore()
	{
		double sum = 0;
		for (Score score : scores)
		{
			sum += score.getScoreValue();
		}
		score = sum / scores.size();
	}

	/** Verify if user scored application**/
	public boolean userScoredApp(Client aClient)
	{
		boolean userScored = false;
		if(aClient.getAppsScored().contains(this))
		{
			userScored = true;
		}

		return userScored;
	}

	//Getters
	public String getName()
	{
		return name;
	}
	public Programmer getProgrammer()
	{
		return programmer;
	}	
	public AppType getType()
	{
		return type;
	}	
	public double getScore()
	{
		return score;
	}
	public List<Score> getScores()
	{
		return scores;
	}
	public double getDiscount()
	{
		return weekDiscount;
	}
	
	// Setters
	public void setName(String aName)
	{
		name = aName;
	}
	public void setPrice(double aPrice)
	{
		price = aPrice;
	}
	public void setProgrammer(Programmer aProgrammer)
	{
		programmer = aProgrammer;
	}
	public void setType(AppType aType)
	{
		type = aType;
	}	
	public void setScore(int aScore)
	{
		score = aScore;
	}
	public void setScores(List<Score> aScores)
	{
		scores = aScores;
	}
	public void setDiscount(double aDiscount) {
		weekDiscount = aDiscount;
	}	
	
}
