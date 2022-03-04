package appstore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class App 
{
	private String name;
	private double price;
	private double discount;
	private double averageScore; // Average Score[0:5]
	private AppType type;
	private String programmerName;
	private List<Score> scores; // List of Scores given to the application
	private int timesSold;
	private List<Date> whenSold;
	
	/** Generates new application **/
	public App(String aName, double aPrice, AppType aType, String aProgrammerName)
	{
		name = aName;
		price = aPrice;
		discount = 0;
		type = aType;
		programmerName = aProgrammerName;
		scores = new ArrayList<Score>();
		timesSold = 0;
		whenSold = new ArrayList<Date>();
	}
	
	//Methods
	@Override
	public String toString() 
	{
		return name + " : " + price;	
	}
	
	/** Adds a new score to score list and updates the average score **/
	public void addScore(Score aScore)
	{
		scores.add(aScore);
		updateScore();
	}
	
	/** updates the application average score using the score list **/
	public void updateScore() 
	{
		double sum = 0;
		for (Score score : scores)
		{
			sum += score.getScoreValue();
		}
		averageScore = sum / scores.size();
	}
	
	/** register when the application was sold and updates sales counter **/
	public void registerSale(Date aBuyDate)
	{
		whenSold.add(aBuyDate);
		updateTimesSold();
	}
	
	/** updates the counter of times sold **/
	public void updateTimesSold()
	{
		timesSold += 1;
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
	
	public double getDiscount() {
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

	public String getProgrammerName()
	{
		return programmerName;
	}
	
	public List<Score> getScores() {
		return scores;
	}
	
	public int getTimesSold()
	{
		return timesSold;
	}
	
	public List<Date> getWhenSold() {
		return whenSold;
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
	
	public void setProgrammer(String aProgrammerName) 
	{
		programmerName = aProgrammerName;
	}

	public void setScores(List<Score> aScores) 
	{
		scores = aScores;
	}
	
	public void setTimesSold(int aTimesSold)
	{
		timesSold = aTimesSold;
	}

	public void setWhenSold(List<Date> aWhenSold) {
		whenSold = aWhenSold;
	}
	
	
}
