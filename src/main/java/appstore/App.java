package appstore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App 
{
	private String name;
	private double price;
	private double discount;
	private double averageScore; // Average Score[0:5]
	private AppType type;
	private Programmer programmer;
	private List<Score> scores; // List of Scores given to the application
	private Map<Date, Integer> sales;
	private int timesSoldLastWeek;
	
	/** Generates new application **/
	public App(String aName, double aPrice, AppType aType, Programmer aProgrammer)
	{
		name = aName;
		price = aPrice;
		discount = 0;
		type = aType;
		programmer = aProgrammer;
		scores = new ArrayList<Score>();
		sales = new HashMap<Date, Integer>();
		timesSoldLastWeek = 0;
	}
	
	//Methods
	@Override
	public String toString() 
	{
		return "App: " + name;	
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
		averageScore = sum / scores.size();
	}
	
	/** register when the application was sold and updates sales counter **/
	public void registerSale(Date aBuyDate, int aLicences)
	{
		sales.put(aBuyDate , aLicences);
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
	
	/** Returns sold date list set **/
	public Set<Date> whenSold() 
	{
		return sales.keySet();
	}
	
	/** Returns previous week sales **/
	public Map<Date, Integer> previousWeekSalesList(int currentweek)
	{
		Calendar cal = Calendar.getInstance();
		Map<Date, Integer> salesPreviousWeek = new HashMap<Date, Integer>();
		for(Map.Entry<Date, Integer> entry : sales.entrySet())
		{
			 cal.setTime(entry.getKey());
			 int soldWeek = cal.get(Calendar.WEEK_OF_YEAR);
			 if(soldWeek == currentweek -1) 
			 {
				 salesPreviousWeek.put(entry.getKey(), entry.getValue());
			 }
		}
		
	    return salesPreviousWeek;
	}
	
	/** **/
	private int timesSoldLastWeek(int currentweek)
	{
		Calendar cal = Calendar.getInstance();
		int sold = 0;
		for(Map.Entry<Date, Integer> entry : sales.entrySet())
		{
			 cal.setTime(entry.getKey());
			 int soldWeek = cal.get(Calendar.WEEK_OF_YEAR);
			 if(soldWeek == (currentweek -1)) 
			 {
				 sold += entry.getValue();
			 }
		}
		return sold;
	}
	
	/** Update times sold last Week**/
	public void updateTimesSoldLastWeek(int currentweek)
	{
		timesSoldLastWeek = timesSoldLastWeek(currentweek);
	}
	
	
	//Getters
	public String getName()
	{
		return name;
	}
	
	public double getPrice()
	{
		return price * (100 - discount) / 100;
	}
	
	public double getDiscount() 
	{
		return discount;
	}

	public double getAverageScore()
	{
		return averageScore;
	}

	public AppType getType()
	{
		return type;
	}

	public Programmer getProgrammer()
	{
		return programmer;
	}
	
	public List<Score> getScores() 
	{
		return scores;
	}
	

	public int getTimesSoldLastWeek() {
		return timesSoldLastWeek;
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
	
	public void setDiscount(double aDiscount) {
		discount = aDiscount;
	}

	public void setScore(int aScore)
	{
		averageScore = aScore;
	}

	public void setType(AppType aType) 
	{
		type = aType;
	}
	
	public void setProgrammer(Programmer aProgrammer) 
	{
		programmer = aProgrammer;
	}

	public void setScores(List<Score> aScores) 
	{
		scores = aScores;
	}

	public void setTimesSoldLastWeek(int timesSoldLastWeek) {
		this.timesSoldLastWeek = timesSoldLastWeek;
	}
}
