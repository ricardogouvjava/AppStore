package appstore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class FileReaderTxt
{



	public static List<String> convertTxtDataNamesToMap(String aFile)
	{
		String dataString = readFile(aFile);
		List<String> splitedDataStrings = processStringToList(dataString);
		return splitedDataStrings;
	}

	/** Converts string into an array of strings **/
    static List<String> processStringToList(String aString)
	{
		String[] names;
		int count = 0;
		for (int i = 0; i <= aString.length() - 1; i++)
		{
		    if (aString.charAt(i) == ';')
		    {
		        count++;
		    }
		}

		names = aString.split(";", count);

		return Arrays.asList(names);
	}

	/** Reads the file and converts data into a single string **/
    private static String readFile(String aString)
    {
    	StringBuilder data = null;
        String fileName = "src/main/resources/" + aString;

        try
        {
        	FileReader filereader = new FileReader(fileName, StandardCharsets.UTF_8);
            try (BufferedReader br = new BufferedReader(filereader))
            {
            	StringBuilder sb = new StringBuilder();

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
