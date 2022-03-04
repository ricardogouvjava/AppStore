package appstore;

import java.io.IOException;

public class TesteSpace {

	public static void main(String[] args) throws IOException 
	{
		//System.out.println();
		FileReaderTxt.processString(FileReaderTxt.readFile("names.txt"));
	}
		
}
