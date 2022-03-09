package appstore;

public class Score
{
	private User user;
	private App app;
	private double score;
	private String comment;
	
	public Score(User aUser, App aApp, double aScore, String aComment)
	{
		user = aUser;
		app = aApp;
		score = aScore;
		comment = aComment;
	}
	
	//Methods
	public String toString() 
	{
		return user.getId() + " " + app.getName() + " " + String.format("%.2f",score) +" '"+ comment+"'";
	}
	
	// Getters
	public User getUser() 
	{
		return user;
	}

	public App getApp() 
	{
		return app;
	}

	public double getScoreValue() 
	{
		return score;
	}

	public String getComment() 
	{
		return comment;
	}

	// Setters
	public void setUser(User aUser) 
	{
		user = aUser;
	}
	
	public void setApp(App aApp) 
	{
		app = aApp;
	}
	
	public void setScoreValue(double aScore) 
	{
		score = aScore;
	}

	public void setComment(String aComment) 
	{
		comment = aComment;
	}
}
