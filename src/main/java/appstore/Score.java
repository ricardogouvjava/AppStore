package appstore;

public class Score
{
	private String username;
	private String appname;
	private double score;
	private String comment;
	
	public Score(String aUserName, String aAppName, double aScore, String aComment)
	{
		username = aUserName;
		appname = aAppName;
		score = aScore;
		comment = aComment;
	}
	
	//Methods

	
	// Getters
	public String getUserName() 
	{
		return username;
	}

	public String getApp() 
	{
		return appname;
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
		username = aUserName;
	}
	
	public void setApp(String aAppName) 
	{
		appname = aAppName;
	}
	
	public void setScoreValue(double aScore) 
	{
		score = aScore;
	}

	public void setComment(String aComment) {
		comment = aComment;
	}
}
