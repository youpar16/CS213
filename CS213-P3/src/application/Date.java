package application;
import java.util.Calendar;

/** 
 * This class defines the properties of a Date object.
 * @author Andrew Park
 * @author Soorim Kim
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month; 
    private int day;
    private String inputDate;
    private static final int EARLIEST_YEAR = 1900;
    private static final int LATEST_YEAR = 2021;
    private static final int JAN = 1;
    private static final int FEB = 2;
    private static final int MAR = 3;
    private static final int MAY = 5;
    private static final int JUL = 7;
    private static final int AUG = 8;
    private static final int OCT = 10;
    private static final int DEC = 12;
    private static final int MIN_DAYS_IN_MONTH = 1;
    private static final int MAX_DAYS_IN_MONTH = 31;
    private static final int DAYS_IN_MONTH = 30;
    private static final int DAYS_IN_LEAP_MONTH = 29;
    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUATERCENTENNIAL = 400;

    /**
     * Constructor that takes a string "mm/dd/yyyy" and creates a Date object
     * @param date that you want to use to create a Date object with
     */
    public Date(String date) {
        String inputedDate[] = date.split("/");
        this.month = Integer.parseInt(inputedDate[0]);
        this.day = Integer.parseInt(inputedDate[1]);
        this.year = Integer.parseInt(inputedDate[2]);
        this.inputDate = date; 
    }

    /**
     * Default constructor for the date class that creates a Date object using today's date
     */
    public Date() { 
     //create an object with today’s date (see Calendar class)
        Calendar now = Calendar.getInstance();
        this.year = now.get(Calendar.YEAR);
        this.month = ((now.get(Calendar.MONTH)) + 1);
        this.day = now.get(Calendar.DAY_OF_MONTH);
    } 

    /**
     * Checks if the given date is valid by checking that its after 1990, the date corresponds to the month, checks for leap year,etc
     * @return true if date is valid and false if date is not valid
     */
    public boolean isValid() { 
        boolean validBounds = this.isDateWithinBounds();
        boolean validDate = this.isBeforeTodaysDate();
        boolean validCorrespondence = this.checkDaysMonthCorrespondence();

        if(validBounds && validDate && validCorrespondence){    
            return true;    
        }else{
            return false;
        }
    }

    /**
     * Helper method to check if the input date is within bounds of between 1990 and 2021, if the month is between 1 and 12, and if the days are between 1 and 31
     * @return true if the date is within bounds and false otherwise
     */
    private boolean isDateWithinBounds(){
        if(this.year < EARLIEST_YEAR || this.year > LATEST_YEAR){
            return false;
        }
        if(this.month > DEC || this.month < JAN){
            return false;
        }
        if(this.day > MAX_DAYS_IN_MONTH || this.day < MIN_DAYS_IN_MONTH){
            return false;
        }
        return true;
    }

    /**
     * Helper method to check if the date is before the current date
     * @return true if date is before and false otherwise
     */
    private boolean isBeforeTodaysDate(){
        Date todaysDate = new Date();
        if(this.year == todaysDate.year){
            if(this.month > todaysDate.month){
                return false;
            }else if (this.month == todaysDate.month){
                if(this.day > todaysDate.day){
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Helper method to see if the day corresponds to the number of days in that month
     * @return true if the date is valid and false otherwise
     */
    private boolean checkDaysMonthCorrespondence(){
        if(this.day == MAX_DAYS_IN_MONTH){
            if(this.month != JAN && this.month != MAR && this.month != MAY && this.month != JUL && this.month != AUG && this.month != OCT && this.month != DEC){
                return false;
            }
        }
        if (this.day == DAYS_IN_MONTH){
            if(this.month == FEB){
                return false;
            }
        }
        if (this.day == DAYS_IN_LEAP_MONTH && this.month == FEB){
            return this.isLeapYear();
        }    
        return true;
    }

    /**
     * Helper method that checks if the year is a leap year
     * @return true if it is a leap year and false otherwise
     */
    private boolean isLeapYear(){
        // Check if the year is a valid leap year
        if(this.year % QUADRENNIAL == 0){
            if(this.year % CENTENNIAL == 0){
                if(this.year % QUATERCENTENNIAL == 0){
                    return true;
                }
            }else{
                return true;
            }
        }else{
            return false;
        }
        return false;
    }

    /**
     * Helper method to retrieve the year variable
     * @return integer variable year
     */
    public int getYear(){
        return this.year;
    }

    /**
     * Helper method to retrieve the month variable
     * @return integer variable month
     */
    public int getMonth(){
        return this.month;
    }

    /**
     * Helper method to retrieve the day variable
     * @return integer variable day
     */
    public int getDay(){
        return this.day;
    }

    /**
     * Helper method to retrieve the string version of the Date
     * @return String date
     */
    public String getDateString(){
        return this.inputDate;
    }

    /**
     * Helper method to compare two dates
     * @param date that you are comparing to
     * @return 1 if this.date is greater than date, 0 if this.date is equal to date, and -1 if this.date is less than date
     */
    @Override
    public int compareTo(Date date) { 
        if(this.year > date.year){
            return 1;
        }else if(this.year == date.year){
            if(this.month > date.month){
                return 1;
            }else if(this.month == date.month){
                if(this.day > date.day){
                    return 1;
                }else if(this.day == date.day){
                    return 0;
                }
            }
        }
        return -1;
    }
}