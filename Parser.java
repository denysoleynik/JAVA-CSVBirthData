
/**
 * Write a description of class Parser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class Parser {
    
    public void countryInfo(CSVParser parser, String countryname) {
    for (CSVRecord record: parser) {
        String country = record.get("Country");
        if (country.contains(countryname)) {
        String countryinfo = record.get("Country") + ":" + record.get("Exports") + ":" + record.get("Value (dollars)");
        System.out.println(countryinfo);
    }
    else { 
        System.out.println("NOT FOUND");
    }
    
}
}

public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
    for (CSVRecord record: parser) {
        String country = record.get("Country");
        String export = record.get("Exports");
        if (export.contains(exportItem1) && export.contains(exportItem2)) {
        System.out.println(country);
        }      
    }
}

public void bigExporters(CSVParser parser, String amount) {
    int lenght = amount.length();
    for (CSVRecord record: parser) {
    String country = record.get("Country");
    String value = record.get("Value (dollars)");
    int valueLength = value.length();
    if (valueLength > lenght) {
    System.out.println(country + " " + value);
    }
    }      
}

public void numberExportcountries(CSVParser parser, String exportItem) {
    int number = 0;
    for (CSVRecord record: parser) {
        //String country = record.get("Country");
        String export = record.get("Exports");
        if (export.contains(exportItem)) {
        //System.out.println(country);
        number += 1;
        }      
    }
    System.out.println(number);
}


    public void tester() {
    FileResource fr = new FileResource();
    CSVParser parser = fr.getCSVParser();
    bigExporters(parser, "$999,999,999,999");
    //countryInfo(parser, "Nauru");
    //listExportersTwoProducts(parser, "cotton", "flowers");
    //method;
    
    //parser = fr.getCSVParser();
    //numberExportcountries(parser, "cocoa");

}
}
