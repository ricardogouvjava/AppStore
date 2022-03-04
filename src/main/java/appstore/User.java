package appstore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

abstract class User
{
	private String name;
	private int age;
	private final UUID userNumber;
	private double averageScore; 
	private List<Double> scores;
	
	
	//Constructor
	public User(String aName, int aAge) 
	{
		userNumber = UUID.randomUUID();
		name = aName;
		age = aAge;
		scores = new ArrayList<>();
		averageScore = 0;
	}

	//Methods
	@Override
    public String toString() 
	{
		return "Id :" + userNumber + ", Name: " + name + ", Age: " + age;	
	}
	
	public void addScore(double aScore)
	{
		scores.add(aScore);
		updateScore();
	}
	
	public void updateScore() 
	{
		double sum = 0;
		for (double score : scores)
		{
			sum += score;
		}
		averageScore = sum / scores.size();
	}
	
	// Getters
	public String getName()
	{
		return name;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public UUID getUserNumber()
	{
		return userNumber;
	}
		
	public List<Double> getScores() {
		return scores;
	}

	public double getAverageScore()
	{
		return averageScore;
	}
	
	// Setters
	public void setName(String aName)
	{
		name = aName;
	}
	
	public void setAge(int aAge)
	{
		age = aAge;
	}

	public void setAverageScore(double aAverageScore)
	{
		averageScore = aAverageScore;
	}

	public void setScores(List<Double> aScores) {
		scores = aScores;
	}
	
	

}
