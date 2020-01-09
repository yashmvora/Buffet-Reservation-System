import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day implements Cloneable {
    
    private int year;
    private int month;
    private int day;
    private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
    private List<String> tablesList = Arrays.asList("T01","T02","T03","T04","T05","T06","T07","T08","T09","T10","F01","F02","F03","F04","F05","F06","H01","H02","H03");
    private ArrayList<String> tablesAvailable;
    private ArrayList<String> tablesAssigned;
    
    @Override
    public Day clone()
    {
        Day copy = null;
        try {
            copy = (Day) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }
    
    //Constructor
    public Day(int y, int m, int d) {
        this.year=y;
        this.month=m;
        this.day=d;
    }
    
    public Day(String sDay)
    {
    	tablesAvailable = new ArrayList<>();
        tablesAvailable.addAll(tablesList);
        tablesAssigned = new ArrayList<>();
        this.set(sDay);
    }
    
    public void removeTableFromList(String table)
    {
    	tablesAvailable.remove(table);
    	tablesAssigned.add(table);
    }
    
    public void printTablesAvailable()
    {
    	for (String table: tablesAvailable)
    		System.out.printf("%s ", table);
    }
    
    @Override
    public int hashCode()
    {
    	return (year*1000)+(month*100)+day;
    }
    
    @Override
    public boolean equals(Object dayObj)
    {
    	if (dayObj == null)
    		return false;
    	if (this.getClass() != dayObj.getClass())
    		return false;
    	
    	Day dayObject = (Day) dayObj;
    	if (this.day != dayObject.day || this.month != dayObject.month || this.year != dayObject.year)
    		return false;
    	
    	return true;
    		
    }
    
    public void set(String sDay)
    {
        String[] sDayParts = sDay.split("-");
        this.day = Integer.parseInt(sDayParts[0]);
        this.year = Integer.parseInt(sDayParts[2]);
        this.month = MonthNames.indexOf(sDayParts[1])/3+1;
    }
    
    // check if a given year is a leap year
    static public boolean isLeapYear(int y)
    {
        if (y%400==0)
            return true;
        else if (y%100==0)
            return false;
        else if (y%4==0)
            return true;
        else
            return false;
    }
    
    // check if y,m,d valid
    static public boolean valid(int y, int m, int d)
    {
        if (m<1 || m>12 || d<1) return false;
        switch(m){
            case 1: case 3: case 5: case 7:
            case 8: case 10: case 12:
                     return d<=31; 
            case 4: case 6: case 9: case 11:
                     return d<=30; 
            case 2:
                     if (isLeapYear(y))
                         return d<=29; 
                     else
                         return d<=28; 
        }
        return false;
    }
    
    // Return a string for the day like dd MMM yyyy
    public String toString() {
         
        return day+"-"+MonthNames.substring((month-1)*3, (month)*3)+"-"+year;
    }
}