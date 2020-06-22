
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;


/**
 * Write a description of class Birth here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

import java.io.*;

public class Birth {
    
    public void totalBirths (FileResource fr) {
        int totalBirth = 0;
        int totalBoyBirths = 0;
        int totalGirlsBirths = 0;
        int totalNames = 0;
        int totalBoyNames =0;
        int totalGirlNames =0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            totalBirth += Integer.parseInt(rec.get(2));
            totalNames += 1;
            if (rec.get(1).equals("M")) {
                totalBoyBirths += Integer.parseInt(rec.get(2));
                totalBoyNames += 1;
            }
            else {
                totalGirlsBirths += Integer.parseInt(rec.get(2));
                totalGirlNames += 1;
            }
        }
        System.out.println("Total Births: " + totalBirth);
        System.out.println("Total BoyBirths: " + totalBoyBirths);
        System.out.println("Total GirlsBirths: " + totalGirlsBirths);
        System.out.println("Total Names: " + totalNames);
        System.out.println("Total Boys Names: " + totalBoyNames);
        System.out.println("Total Girls Names: " + totalGirlNames);

    }
    //String name, String gender
    public int getRank (int year, String name, String gender) {
        long maxline = 0;
        long currline = 0;
        String yearS = Integer.toString(year);
        FileResource fr = new FileResource("yob" + yearS + ".csv");
        //FileResource fr = new FileResource("yob2012short.csv");
        CSVParser parser = fr.getCSVParser(false);
        //while (maxline == 0) {
        for (CSVRecord rec : parser) {
            if (gender.equals(rec.get(1))) {
                maxline = (parser.getCurrentLineNumber());
                break;
                }
           }
        for (CSVRecord rec : parser) {
            if (gender.equals(rec.get(1)) && name.equals(rec.get(0))) {
                currline = (parser.getCurrentLineNumber());
                break;
                }
        }
        if (currline == 0) {
            long rank = -1;
            int rankI = (int) rank;
            System.out.println("No Names for " + name + " and for  " + gender);
            return rankI;

        }
        else {
        long rank = currline - maxline + 1;
        System.out.println(maxline + "  " + currline + " " + ": Rank the name is " + name + " " + rank);
        int rankI = (int) rank;
        return rankI;
        }
        
    }
    
public String getName(int year, int rank, String gender) {
    int number = 0;
    String neededName = null;
    CSVRecord lookingname = null;
    int find = 0;
    String yearS = Integer.toString(year);
    FileResource fr = new FileResource("yob" + yearS + ".csv");
    //FileResource fr = new FileResource("yob2012short.csv");
    CSVParser parser = fr.getCSVParser(false);
    for (CSVRecord rec : parser) {
        if (gender.equals(rec.get(1))) {
            number += 1;
            if (rank == number) {
                neededName = rec.get(0);
                //System.out.println("Names for rank 3 is" + neededName + " and for  " + gender);
                find = 1;
                break;
            }                      
        }
   }
   if (find == 1) {
       System.out.println("Names for rank 3 is  " + neededName + " and for  " + gender);
       return neededName;
    }
   else {
       System.out.println("No name");
       return "No Name";

    }  
}

public String whatIsNameInYear(String name, int year, int newYear, String gender) {
  String startedYear = Integer.toString(year);
  FileResource frS = new FileResource("yob" + startedYear + ".csv");
  String endYear = Integer.toString(year);
  FileResource frE = new FileResource("yob" + endYear + ".csv");
  int rank = getRank (year, name, gender);
  String endname = getName(newYear, rank, gender);
  String whatIsNameInYear = name + " is " + rank + " in year " + year + " . The name of the same rank in " + newYear + " is " + endname; 
  System.out.println(whatIsNameInYear);
  return whatIsNameInYear;
}

public int yearOfHighestRank(String name, String gender) {
    DirectoryResource dr = new DirectoryResource();
    int rankH = 0;
    int yearH = 0;
    int maxyear = 0;
    for ( File f : dr.selectedFiles()) {
        String FileName = f.getName();
        String year = FileName.substring(3, 7);
        int yearI = Integer.parseInt(year);
        //System.out.println(yearI);
        FileResource fr = new FileResource(f);
        CSVParser parser = fr.getCSVParser(false);
        int rank = 0;
        for (CSVRecord rec : parser) {
        rank = getRank (yearI, name, gender);        
        //System.out.println("Rank is " + rank);
        break;
       }
       if (rankH == 0 && rank != -1 ) {
        rankH = rank;
        maxyear = yearI;
        }
       if (rank != -1 && rank < rankH) {
        rankH = rank;
        maxyear = yearI;
        }
      }   
    if (rankH == 0) {
        maxyear = -1;
    }
    System.out.println("For name " + name + " max rank is " + rankH + " on year " + maxyear);
    return maxyear;
}    

public double getAverageRank(String name, String gender) {
    DirectoryResource dr = new DirectoryResource();
    int totalranks = 0;
    int totalappears = 0;   
    for (File f : dr.selectedFiles()) {
        String FileName = f.getName();
        String year = FileName.substring(3, 7);
        int yearI = Integer.parseInt(year);
        //System.out.println(yearI);
        FileResource fr = new FileResource(f);
        CSVParser parser = fr.getCSVParser(false);
        for (CSVRecord rec : parser) {
        int rank = getRank (yearI, name, gender); 
        if (rank != -1) {
            totalranks += rank;
            totalappears += 1;
        //System.out.println("Rank is " + rank);       
       }  
       break;
      }           
   }
   double averrank = (double) totalranks / totalappears;
   if (totalappears == 0) {
       averrank = -1;
   }   
   System.out.println("For name " + name + " avrank is " + averrank);
   return averrank;
}

public void getTotalBirthsRankedHigher(int year, String name, String gender) {
  String startedYear = Integer.toString(year);
  FileResource fr = new FileResource("yob" + startedYear + ".csv");
  int totalBirth = 0;
  int lookingrank = 0;
  int curriteration = 0;
  for (CSVRecord rec : fr.getCSVParser(false)) {
      lookingrank = getRank(year, name, gender);
      break;
    }
  if (lookingrank == -1) {
        //return lookingrank;
  }
  for (CSVRecord rec : fr.getCSVParser(false)) {
      if (rec.get(1).equals(gender)) {
          curriteration += 1;
          if (curriteration < lookingrank) {
              int currentbirth = Integer.parseInt(rec.get(2));
              totalBirth = totalBirth + currentbirth;
            }                       
        }                          
  }
  System.out.println("For name " + name + " in year " + startedYear + " the birth for rank higher than " + lookingrank + " is " + totalBirth);
  //return totalBirth;   
}




                
public void testBirths () {
        FileResource fr = new FileResource("yob1905.csv");
        totalBirths(fr);
    }


public void testgetrank () {
        int year = 1960;
        getRank(1971, "Frank", "M");
    }
    
public void testgetName () {
        int year = 2012;
        getName(1982, 450, "M");
    }
    
public void testwhatIsNameInYear() {
String name = "Owen";
int year = 1974;
int newYear = 2014;
String gender = "M";
whatIsNameInYear(name, year, newYear, gender);
}

public void testyearOfHighestRank () {
yearOfHighestRank("Mich", "M");
}

public void testgetAverageRank () {
getAverageRank("Robert", "M");
}

public void TESTgetTotalBirthsRankedHigher() {
String name = "Drew";
int year = 1990;
String gender = "M";
getTotalBirthsRankedHigher(year, name, gender);

}
}

    

