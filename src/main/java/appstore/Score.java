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

	public App getApp()
	{
		return app;
	}

	// Getters
	public User getClient()
	{
		return client;
	}

	public String getComment()
	{
		return comment;
	}

	public double getScoreValue()
	{
		return score;
	}

	public void setApp(App aApp)
	{
		app = aApp;
	}

	public void setComment(String aComment)
	{
		comment = aComment;
	}

	public void setScoreValue(double aScore)
	{
		score = aScore;
	}

	// Setters
	public void setUser(Client aClient)
	{
		client = aClient;
	}

	//Methods
	@Override
	public String toString()
	{
		return "Client: " + client.getId() + ", App: " + app.getName() + ", Score:  " + String.format("%.2f",score) +" Comment: '"+ comment+"'";
	}
}
