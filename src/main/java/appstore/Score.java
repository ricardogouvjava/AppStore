package appstore;

public class Score
{
	private String userName;
	private String appName;
	private double score;
	private String comment;
	
	public Score(String aUserName, String aAppName, double aScore, String aComment)
	{
		userName = aUserName;
		appName = aAppName;
		score = aScore;
		comment = aComment;
	}
	
	//Methods
	public String toString() 
	{
		return userName + " " + appName + " " + score +" "+ comment;
	}
	
	// Getters
	public String getUserName() 
	{
		return userName;
	}

	public String getAppName() 
	{
		return appName;
	}

	public double getScoreValue() 
	{
		return score;
	}

	public String getComment() {
		return comment;
	}

	// Setters
	public void setUser(String aUserName) 
	{
		userName = aUserName;
	}
	
	public void setApp(String aAppName) 
	{
		appName = aAppName;
	}
	
	public void setScoreValue(double aScore) 
	{
		score = aScore;
	}

	public void setComment(String aComment) {
		comment = aComment;
	}
}
