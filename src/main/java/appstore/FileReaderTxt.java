package appstore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class FileReaderTxt 
{
	
	
	static void processString(String aString) 
	{
		String[] arrOfStr;
		int count = 0;
		for (int i = 0; i < aString.length(); i++)
		{
		    if (aString.charAt(i) == ';') {
		        count++;
		    }
		}
		
		arrOfStr = aString.split(";", count);
		
		for (String string : arrOfStr) 
		{
			String[] subString = string.split(";", count);

			for (String subsubString : subString) 
			{
			System.out.println(subsubString);
			}
		}
			
			
		
		/*
			 * for (String substring : arrOfStr) { System.out.println(substring); String[]
			 * tempNameString = substring.split(",", 1); for (String subsubstring :
			 * substring) {
			 * 
			 * } System.out.println(tempNameString); JSONObject jo = new JSONObject(); //
			 * jo.put("firstName", tempNameString[0]); // jo.put("lastName",
			 * tempNameString[1]); //System.out.println(tempNameString.toString());
			 */ 
	            
	}
	
    public static String readFile(String aString) 
    {
    	StringBuilder data = null;
        var fileName = "src/main/resources/" + aString;
        
        try
        {
        	FileReader filereader = new FileReader(fileName, StandardCharsets.UTF_8);
            try (BufferedReader br = new BufferedReader(filereader))
            {
				var sb = new StringBuilder();

				String line;
				while ((line = br.readLine()) != null)
				{
				    sb.append(line);						
					sb.append(";");
					//sb.append(System.lineSeparator());
				}
				data = sb;
			}
        }
        
        catch (IOException e) 
        {
        	 System.out.println("Error in loading data");
        }
        
        return data.toString();
    }
}
