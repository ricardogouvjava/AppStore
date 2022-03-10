package appstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;

abstract class User
{
	private String id;
	private String encyptedPassword;
	private PasswordEncryptor encryptor = new BasicPasswordEncryptor();
	private int age;
	private double averageScore;
	private List<Score> scores;
	private Map<App, Integer> apps;

	//Constructor
	public User(String aUserId, String aPassword, int aAge)
	{
		id = aUserId;
		encyptedPassword = encryptPassword(aPassword);
		age = aAge;
		averageScore = 0;
		scores = new ArrayList<>();
		apps = new HashMap<>();

	}

	/** Add application to list **/
	public void addApp(App aApp, int aQuantity)
	{
		apps.merge(aApp, aQuantity, (v1 , v2) -> v1 + v2 );
	}

	public void addApps(Bag aBag)
	{
		aBag.getBagItems().forEach((key, value) -> addApp(key , value));
	}

	/** Add Score to list **/
	public void addScore(Score aScore)
	{
		scores.add(aScore);
		updateScore();
	}

	/** Encrypt password  **/
	private String encryptPassword(String aPassword)
	{
	    return encryptor.encryptPassword(aPassword);
	}

	public int getAge()
	{
		return age;
	}

	public Map<App, Integer> getApps()
	{
		return apps;
	}

	/** return list of applications not scored **/
	public List<App> getAppsNotScored()
	{
		List<App> appsNotScored = new ArrayList<>(apps.keySet()); //Makes Copy of all applications in userList Set into List
		appsNotScored.removeAll(getAppsScored()); //Remove applications scored

		return appsNotScored;
	}

	/** return applications scored **/
	public List<App> getAppsScored()
	{
		List<App> scoredApps = new ArrayList<>();
		for(Score score : scores)
		{
			scoredApps.add(score.getApp());
		}
		return scoredApps;
	}

	public double getAverageScore()
	{
		return averageScore;
	}

	// Getters
	public String getId()
	{
		return id;
	}

	public List<Score> getScores()
	{
		return scores;
	}

	/** Compare password **/
	public boolean isPasswordCorrect(String userInput)
	{
		boolean passwordCorrect = false;

		if (encryptor.checkPassword(userInput, encyptedPassword))
        {
        	passwordCorrect = true;
        }

		else
		{
        	passwordCorrect = false;
        }

        return passwordCorrect;
	}

	// Setters
	public void setAge(int aAge)
	{
		age = aAge;
	}

	public void setEncyptedPassword(String aPassword)
	{
		encyptedPassword = encryptPassword(encyptedPassword);
	}


	public void setPassword(String aPassword)
	{
		encyptedPassword = encryptPassword(aPassword);
	}

	public void setUserId(String aUserId)
	{
		id = aUserId;
	}

	//Methods
	@Override
    public String toString()
	{
		return "Id :" + id + ", Age: " + age;
	}

	/** Update average Score **/
	private void updateScore()
	{
		double sum = 0;
		for (Score score : getScores())
		{
			sum += score.getScoreValue();
		}
		averageScore = sum / getScores().size();
	}

}
