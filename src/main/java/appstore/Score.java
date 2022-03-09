package appstore;

public class Score
{
	private Client client;
	private App app;
	private double score;
	private String comment;
	
	public Score(Client aClient, App aApp, double aScore, String aComment)
	{
		client = aClient;
		app = aApp;
		score = aScore;
		comment = aComment;
	}
	
	//Methods
	public String toString() 
	{
		return "Client: " + client.getId() + ", App: " + app.getName() + ", Score:  " + String.format("%.2f",score) +" Comment: '"+ comment+"'";
	}
	
	// Getters
	public User getClient() 
	{
		return client;
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
	public void setUser(Client aClient) 
	{
		client = aClient;
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
