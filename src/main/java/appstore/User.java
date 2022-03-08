package appstore;

abstract class User
{
	private String userId;
	private String name;
	private String password;
	private int age;
	
	
	//Constructor
	public User(String aUserId, String aName, String aPassword, int aAge) 
	{
		userId = aUserId;
		name = aName;
		password = aPassword;
		age = aAge;
	}

	//Methods
	@Override
    public String toString() 
	{
		return "Id :" + userId + ", Name: " + name +", Age: " + age;	
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
	
	public String getUserId()
	{
		return userId;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	// Setters
	public void setName(String aName)
	{
		name = aName;	}
	
	public void setAge(int aAge)
	{
		age = aAge;
	}



	public void setUserId(String aUserId)
	{
		userId = aUserId;
	}
	
	public void setPassword(String aPassword)
	{
		password = aPassword;
	}

	

}
