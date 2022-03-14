package appstore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App
{
	private String name;
	private double basePrice;
	private AppType type;
	private Date buildDate;
	private Programmer programmer;
	private double weeklyDiscount;
	private double monthlyAppTypeDiscount;
	private double score; // Average Score[0:5]
	private List<Score> scores; // List of Scores given to the application
	private Map<Date, Integer> sales; //all application sales
	
	/** Generates new application **/
	public App(String aName, double aPrice, AppType aType, Date aBuildDate,Programmer aProgrammer)
	{
		name = aName;
		basePrice = aPrice;
		buildDate = aBuildDate;
		type = aType;
		programmer = aProgrammer;

		weeklyDiscount = 0;
		monthlyAppTypeDiscount = 0;
		scores = new ArrayList<>();
		sales = new HashMap<>();
	}

	/*Methods
	 * to String
	 * getPrice()
	 * registerSale
	 * timesSold
	 * addScore
	 * updateScore
	 * userScoredApp
	 */
	
	/** Prints minimal application information **/
	@Override
	public String toString()
	{
		return name + " : " + String.format("%.2f", getPrice());
	}

	/**Calculates price considering discount exits **/
	public double getPrice()
	{
		return basePrice * (100 - weeklyDiscount - monthlyAppTypeDiscount) / 100;
	}
	
	/** Calculate Application current discounts **/
	public double getDiscounts()
	{
		return weeklyDiscount + monthlyAppTypeDiscount;
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

	/** Adds a new score to score list and updates the average score **/
	public void addScore(Score aScore)
	{
		scores.add(aScore);
		updateScore();
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
	public double getBasePrice()
	{
		return basePrice;
	}
	public AppType getType()
	{
		return type;
	}	
	public Date getBuildDate() {
		return buildDate;
	}
	public Programmer getProgrammer()
	{
		return programmer;
	}	
	public double getScore()
	{
		return score;
	}
	public List<Score> getScores()
	{
		return scores;
	}
	public double getWeeklyDiscount()
	{
		return weeklyDiscount;
	}
	public double getMonthlyAppTypeDiscount() {
		return monthlyAppTypeDiscount;
	}

	// Setters
	public void setName(String aName)
	{
		name = aName;
	}
	public void setBasePrice(double aPrice)
	{
		basePrice = aPrice;
	}
	public void setType(AppType aType)
	{
		type = aType;
	}	
	public void setBuildDate(Date buildDate) 
	{
		this.buildDate = buildDate;
	}
	public void setProgrammer(Programmer aProgrammer)
	{
		programmer = aProgrammer;
	}
	public void setScore(int aScore)
	{
		score = aScore;
	}
	public void setScores(List<Score> aScores)
	{
		scores = aScores;
	}
	public void setWeeklyDiscount(double aWeeklyDiscount) 
	{
		this.weeklyDiscount = aWeeklyDiscount;
	}
	public void setMonthlyAppTypeDiscount(double aMonthlyAppTypeDiscount) 
	{
		this.monthlyAppTypeDiscount = aMonthlyAppTypeDiscount;
	}
	
}
