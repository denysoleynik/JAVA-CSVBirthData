
/**
 * Write a description of class temperature here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import edu.duke.DirectoryResource;
import java.io.*;

import org.apache.commons.csv.*;

public class temperature {

    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord lowestsoFar = null;
        for ( CSVRecord currentRow : parser) {
            if (lowestsoFar == null) {
                lowestsoFar = currentRow;
            }
            else {
                double lowestT = Double.parseDouble(lowestsoFar.get("TemperatureF"));
                double currentT = Double.parseDouble(currentRow.get("TemperatureF"));
                if ( currentT < lowestT && currentT != -9999.00 && currentT != -9999 ) {
                    lowestsoFar = currentRow;
                }
            }          
        } 
        return lowestsoFar;
}

public void fileWithColdestTemperature() {
    DirectoryResource dr = new DirectoryResource();
    CSVRecord lowestsoFar = null;
    String nameFile = null;
    FileResource Final = null;
    for (File f : dr.selectedFiles()) {
    FileResource fr = new FileResource(f);
    CSVParser parser = fr.getCSVParser();
    CSVRecord currentRow = coldestHourInFile(parser);
    if (lowestsoFar == null) {
    lowestsoFar = currentRow;
    }
    else {
    double lowestT = Double.parseDouble(lowestsoFar.get("TemperatureF"));
    double currentT = Double.parseDouble(currentRow.get("TemperatureF"));
    if ( currentT < lowestT && currentT != -9999.00 && currentT != -9999 ) {
    lowestsoFar = currentRow;
    nameFile = f.getName();
    Final = fr;
    } 
  }
}
System.out.println("The Coldest day is " + nameFile);
System.out.println("Temp is " + lowestsoFar.get("TemperatureF"));
System.out.println("ALL temperatures: ");
CSVParser parser = Final.getCSVParser();
 for ( CSVRecord finalR : parser) {
     System.out.println(finalR.get("DateUTC") + "  :  " + finalR.get("TemperatureF"));
    }
//CSVRecord final = parserF.get("DateUTC") + " " + parserF.get("TemperatureF");
//System.out.println(final);
}

 public CSVRecord lowestHumidityInFile(CSVParser parser) {
     CSVRecord lowestsoFar = null;
     double currentT = 0;
        for ( CSVRecord currentRow : parser) {
            if (lowestsoFar == null) {
                lowestsoFar = currentRow;
            }
            if (currentRow.get("Humidity").equals("N/A")) {
            currentT = 9999;  
            }
            else {           
                double lowestT = Double.parseDouble(lowestsoFar.get("Humidity"));
                currentT = Double.parseDouble(currentRow.get("Humidity"));
                if ( currentT < lowestT && currentT != -9999.00 && currentT != -9999 ) {
                    lowestsoFar = currentRow;
                }
              }          
            } 
    return lowestsoFar;
}

    public void lowestHumidityInManyFiles() {
     DirectoryResource dr = new DirectoryResource();
     CSVRecord lowestsoFar = null;
     for (File f : dr.selectedFiles()) {
     FileResource fr = new FileResource(f);
     CSVParser parser = fr.getCSVParser();
     CSVRecord currentRow = lowestHumidityInFile(parser);
      if (lowestsoFar == null) {
       lowestsoFar = currentRow;
      }
       else {
                double lowestT = Double.parseDouble(lowestsoFar.get("Humidity"));
                double currentT = Double.parseDouble(currentRow.get("Humidity"));
                if (currentT < lowestT) {
                    lowestsoFar = currentRow;
                }
       } 
    } 
    
    System.out.println("The Lowest H is " + lowestsoFar.get("Humidity") + "  on date : " + lowestsoFar.get("DateUTC") );
}


public double averageTemperatureInFile(CSVParser parser) {
    double totalt = 0;
    double hours = 0;
    for (CSVRecord record: parser) {
        double currentT = Double.parseDouble(record.get("TemperatureF"));
        if (currentT != -9999.00 && currentT != -9999 ) {
            hours += 1;
            totalt = totalt + currentT;            
        }        
    }
    double average = totalt / hours;
    return average;
}

public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
    double totalt = 0;
    double hours = 0;
    for (CSVRecord record: parser) {
        double currentT = Double.parseDouble(record.get("TemperatureF"));
        String humidity = record.get("Humidity");
        int humidityV = Integer.parseInt(humidity);
        if (currentT != -9999.00 && currentT != -9999 && (humidityV >= value)) {
            hours += 1;
            totalt = totalt + currentT;            
        }
        else {
        }
    }
    double average = totalt / hours;
    return average;
}

public void testAverageTemperatureWithHighHumidityInFile() {
    FileResource fr = new FileResource();
    CSVParser parser = fr.getCSVParser();
    double averageT = averageTemperatureWithHighHumidityInFile(parser, 80);
    String s = Double.toString(averageT);
    if (s == "Nan" || s == "NaN") {
        System.out.println("No Temperature");
    }
    else {
    System.out.println("The average temperature for H 80 was " + averageT);
    }
    
}


public void testAverageTemperatureInFile() {
    FileResource fr = new FileResource();
    CSVParser parser = fr.getCSVParser();
    double averageT = averageTemperatureInFile(parser);
    System.out.println("The average temperature was " + averageT);
}



public void testColdestHourInFile() {
    FileResource fr = new FileResource();
    CSVParser parser = fr.getCSVParser();
    CSVRecord lowestsoFar = coldestHourInFile(parser);
    double lowestT = Double.parseDouble(lowestsoFar.get("TemperatureF"));
    System.out.println("The Lowest temperature was " + lowestT + " at " + lowestsoFar.get("DateUTC"));
}

public void testLowestHumidityInFile() {
    FileResource fr = new FileResource();
    CSVParser parser = fr.getCSVParser();
    CSVRecord lowestsoFar = lowestHumidityInFile(parser);
    double lowestT = Double.parseDouble(lowestsoFar.get("Humidity"));
    System.out.println("The Lowest Humidity was " + lowestT + " at " + lowestsoFar.get("DateUTC"));
}



}