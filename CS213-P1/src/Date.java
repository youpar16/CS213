package src;
import java.util.Calendar;

/** 
 * This class defines the properties of a Date object.
 * @author Andrew Park
 * @author Soorim Kim
 */
public class Date {
    private int year;
    private int month; 
    private int day;
    public String inputDate;
    public static final int EARLIEST_YEAR = 1990;
    public static final int LATEST_YEAR = 2021;
    public static final int JAN = 1;
    public static final int FEB = 2;
    public static final int MAR = 3;
    public static final int MAY = 5;
    public static final int JUL = 7;
    public static final int AUG = 8;
    public static final int OCT = 10;
    public static final int DEC = 12;
    public static final int MIN_DAYS_IN_MONTH = 1;
    public static final int MAX_DAYS_IN_MONTH = 31;
    public static final int DAYS_IN_MONTH = 30;
    public static final int DAYS_IN_LEAP_MONTH = 29;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

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
        this.month = ((now.get(Calendar.MONTH))+1);
        this.day = now.get(Calendar.DAY_OF_MONTH);
    } 

    /**
     * Checks if the given date is valid by checking that its after 1990, the date corresponds to the month, checks for leap year,etc
     * @return true if date is valid, false if date is not valid
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
     * @return true if the date is within bounds, false if it is not
     */
    public boolean isDateWithinBounds(){
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
     * @return true if date is before, false if it is not
     */
    public boolean isBeforeTodaysDate(){
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
     * @return true if the date is valid, false if invalid
     */
    public boolean checkDaysMonthCorrespondence(){
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
     * @return true if it is a leap year, false if it is not
     */
    public boolean isLeapYear(){
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
     *  Testbed main to exercise the isValid() method in this class
     * @param arg command line arguments
     */
    public static void main(String arg[]){
        //Testing the isValid() method
        //test case #1, valid input date
        System.out.println("Running test case #1");
        Date date = new Date("4/25/2000");
        boolean result = date.isValid();
        if (result){
            System.out.println("Test case #1, testing if date is valid, PASSED");
        }else{
            System.out.println("Test case #1, testing if date is valid, FAILED");
        }
        //Testing the isDateWithinBounds() method
        //test case #2, input date is within bounds
        System.out.println("\nRunning test case #2");
        date.year = 2003;
        date.month = 3;
        date.day = 25;
        result = date.isDateWithinBounds();
        if (result){
            System.out.println("Test case #2, testing if date is within bounds, PASSED");
        }else{
            System.out.println("Test case #2, testing if date is within bounds, FAILED");
        }
        //test case #3, input year is greater than latest possible year
        System.out.println("\nRunning test case #3");
        date.year = 2025;
        date.month = 8;
        date.day = 23;
        result = date.isDateWithinBounds();
        if (result){
            System.out.println("Test case #3, testing if date is within bounds, PASSED");
        }else{
            System.out.println("Test case #3, testing if date is within bounds, FAILED");
        }
        //test case #4, input year is less than earliest possible year 
        System.out.println("\nRunning test case #4");
        date.year = 1889;
        date.month = 3;
        date.day = 30;
        result = date.isDateWithinBounds();
        if (result){
            System.out.println("Test case #4, testing if date is within bounds, PASSED");
        }else{
            System.out.println("Test case #4, testing if date is within bounds, FAILED");
        }
        //test case #5, input month is greater than the latest possible month
        System.out.println("\nRunning test case #5");
        date.year = 2000;
        date.month = 31;
        date.day = 2;
        result = date.isDateWithinBounds();
        if (result){
            System.out.println("Test case #5, testing if date is within bounds, PASSED");
        }else{
            System.out.println("Test case #5, testing if date is within bounds, FAILED");
        }
        //test case #6, input month is less than the earliest possible month
        System.out.println("\nRunning test case #6");
        date.year = 2020;
        date.month = -1;
        date.day = 24;
        result = date.isDateWithinBounds();
        if (result){
            System.out.println("Test case #6, testing if date is within bounds, PASSED");
        }else{
            System.out.println("Test case #6, testing if date is within bounds, FAILED");
        }
        //test case #7, input day is greater than the maximum number of days
        System.out.println("\nRunning test case #7");
        date.year = 2005;
        date.month = 3;
        date.day = 35;
        result = date.isDateWithinBounds();
        if (result){
            System.out.println("Test case #7, testing if date is within bounds, PASSED");
        }else{
            System.out.println("Test case #7, testing if date is within bounds, FAILED");
        }
        //test case #8, input day is less than the minimum number of days 
        System.out.println("\nRunning test case #8");
        date.year = 1999;
        date.month = 4;
        date.day = 0;
        result = date.isDateWithinBounds();
        if (result){
            System.out.println("Test case #8, testing if date is within bounds, PASSED");
        }else{
            System.out.println("Test case #8, testing if date is within bounds, FAILED");
        }
        //Testing the isBeforeTodaysDate() method
        //test case #9, input date is before today's date
        System.out.println("\nRunning test case #9");
        date.year = 2010;
        date.month = 5;
        date.day = 8;
        result = date.isBeforeTodaysDate();
        if (result){
            System.out.println("Test case #9, testing if date is before today's date, PASSED");
        }else{
            System.out.println("Test case #9, testing if date is before toady's date, FAILED");
        }
        //test case #10, input month is after the current month
        System.out.println("\nRunning test case #10");
        date.year = 2021;
        date.month = 3;
        date.day = 1;
        result = date.isBeforeTodaysDate();
        if (result){
            System.out.println("Test case #10, testing if date is before today's date, PASSED");
        }else{
            System.out.println("Test case #10, testing if date is before toady's date, FAILED");
        }
        //test case #11, input date is after the current date
        System.out.println("\nRunning test case #11");
        date.year = 2021;
        date.month = 2;
        date.day = 30;
        result = date.isBeforeTodaysDate();
        if (result){
            System.out.println("Test case #11, testing if date is before today's date, PASSED");
        }else{
            System.out.println("Test case #11, testing if date is before toady's date, FAILED");
        }
        //Testing the checkDaysMonthCorrespondence() method
        //test case #12, input date and month correspond
        System.out.println("\nRunning test case #12");
        date.year = 2009;
        date.month = 1;
        date.day = 31;
        result = date.checkDaysMonthCorrespondence();
        if (result){
            System.out.println("Test case #12, testing if the day and month correspond, PASSED");
        }else{
            System.out.println("Test case #12, testing if the day and month correspond, FAILED");
        }
        //test case #13, input has over 29 days in February
        System.out.println("\nRunning test case #13");
        date.year = 2020;
        date.month = 2;
        date.day = 30;
        result = date.checkDaysMonthCorrespondence();
        if (result){
            System.out.println("Test case #13, testing if the day and month correspond, PASSED");
        }else{
            System.out.println("Test case #13, testing if the day and month correspond, FAILED");
        }
        //test case #14, input has ove 30 days in April
        System.out.println("\nRunning test case #14");
        date.year = 2009;
        date.month = 4;
        date.day = 31;
        result = date.checkDaysMonthCorrespondence();
        if (result){
            System.out.println("Test case #14, testing if the day and month correspond, PASSED");
        }else{
            System.out.println("Test case #14, testing if the day and month correspond, FAILED");
        }
        //test case #15, input has ove 30 days in June
        System.out.println("\nRunning test case #15");
        date.year = 2008;
        date.month = 6;
        date.day = 31;
        result = date.checkDaysMonthCorrespondence();
        if (result){
            System.out.println("Test case #15, testing if the day and month correspond, PASSED");
        }else{
            System.out.println("Test case #15, testing if the day and month correspond, FAILED");
        }
        //test case #16, input has ove 30 days in September
        System.out.println("\nRunning test case #16");
        date.year = 2007;
        date.month = 9;
        date.day = 31;
        result = date.checkDaysMonthCorrespondence();
        if (result){
            System.out.println("Test case #16, testing if the day and month correspond, PASSED");
        }else{
            System.out.println("Test case #16, testing if the day and month correspond, FAILED");
        }
        //test case #17, input has ove 30 days in November
        System.out.println("\nRunning test case #17");
        date.year = 2006;
        date.month = 11;
        date.day = 31;
        result = date.checkDaysMonthCorrespondence();
        if (result){
            System.out.println("Test case #17, testing if the day and month correspond, PASSED");
        }else{
            System.out.println("Test case #17, testing if the day and month correspond, FAILED");
        }
        //Testing the isLeapYear() method
        //test case #18, input date is a leap year
        System.out.println("\nRunning test case #18");
        date.year = 2020;
        date.month = 2;
        date.day = 29;
        result = date.isLeapYear();
        if (result){
            System.out.println("Test case #18, testing if date is a leap year, PASSED");
        }else{
            System.out.println("Test case #18, testing if date is a leap year, FAILED");
        }
         //test case #19, input day and month imply a leap year, but date is not a leap year
         System.out.println("\nRunning test case #19");
         date.year = 2021;
         date.month = 2;
         date.day = 29;
         result = date.isLeapYear();
         if (result){
             System.out.println("Test case #19, testing if date is a leap year, PASSED");
         }else{
             System.out.println("Test case #19, testing if date is a leap year, FAILED");
         }
    }
}
