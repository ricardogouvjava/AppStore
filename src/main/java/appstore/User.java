package appstore;

import java.util.UUID;

abstract class User
{
	private String firstName;
	private String lastName;
	private int age;
	private final UUID userNumber;

	
	
	//Constructor
	public User(String aFirstName, String aLastName, int aAge) 
	{
		userNumber = UUID.randomUUID();
		firstName = aFirstName;
		lastName = aLastName;
		age = aAge;

	}

	//Methods
	@Override
    public String toString() 
	{
		return "Id :" + userNumber + ", Name: " + firstName +" " + lastName + ", Age: " + age;	
	}
	
	// Getters
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getName()
	{
		return firstName + " " + lastName;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public UUID getUserNumber()
	{
		return userNumber;
	}
		
	
	// Setters
	public void setName(String aFirstName, String aLastName)
	{
		firstName = aFirstName;
		lastName = aLastName;
	}
	
	public void setAge(int aAge)
	{
		age = aAge;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
