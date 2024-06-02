package Services;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class  CommonServices{
    // Function that takes data from the csv file and reads it
    public static ArrayList<String[]> getData(String filePath) 
	{
		ArrayList<String[]> records = new ArrayList<>();
		File file = new File(filePath);

		if (file.exists() && !file.isDirectory()) { // If the file exists and is not a folder
			try (Scanner fileReader = new Scanner(file)) { // Use try-with-resources to automatically close the Scanner
				while (fileReader.hasNextLine()) { // keep reading while there are lines
					String line = fileReader.nextLine();
					String[] listData = line.split(";", -1);// if the fields are empty, they will still be added
					records.add(listData);
				}
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
				e.printStackTrace();
			}
		} else {
			System.out.println("The file does not exist or is a directory");
		}

		return records;
	}

    // Function that converts strings to Date type
    public static Date convertStringToDate(String dateString, String format) 
	{ 
		// dd/MM/yyyy HH:mm:ss
		 SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false); // avoids the possibility of invalid dates
        try 
        {
            return dateFormat.parse(dateString);
        } 
        catch (ParseException e) 
        {
            
            return null;
        }
	 }

	 public static boolean convertStringToBoolean(String value){
		if(value.toLowerCase().equals("true"))
			return true;
		else
			return false;
	}
}
