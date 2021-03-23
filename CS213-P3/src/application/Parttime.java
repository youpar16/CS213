package application;

import java.text.DecimalFormat;
/**
 * Class that extends the Fulltime class and includes specific data and operations to a full-time employee with a management role.
 * @author Andrew Park
 * @author Soorim Kim
 */
public class Parttime extends Employee{
    private double hourlyRate;
    private int hoursWorked; // Count of how many hours worked 
    private static final int OVERTIME_HOURS = 80;
    private static final double OVERTIME_RATE = 1.5;
    private DecimalFormat df = new DecimalFormat("#,###.00");
    
    /**
     * A constructor for the Parttime class that creates a Parttime object with the given parameters
     * @param name of the employee
     * @param departmentCode of the employee
     * @param date in which the employee was hired
     * @param hourlyRate of the employee
     */
    public Parttime(String name, String departmentCode, Date date, double hourlyRate){
        super (name, departmentCode, date);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = 0;
    }

    /**
     * Equals method that checks if two Parttime objects are the same
     * @param obj that you want to compare
     * @return true if the objects are equal and false, otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Parttime){
            Parttime input = (Parttime) obj; 
            if(super.equals(input)){ //&& this.hourlyRate == input.hourlyRate && this.hoursWorked== input.hoursWorked){
                    return true;
            }
        }
        return false;
    }

    /**
     * The toString method returns the string that gives us information about an employee
     * @return string
     */
    @Override
    public String toString() {
        return super.toString() + "PART TIME::Hourly Rate $" + df.format(hourlyRate) + "::Hours worked this period: " + hoursWorked;
    }

    /**
     * The calculatePayment method calculates the payment that an employee would recieve and respectively calls the changePayment in the Employee class to set the payment value to the correct amount
     */
    @Override
    public void calculatePayment() { 
        double payment;
        if(this.hoursWorked > OVERTIME_HOURS){
            payment = (this.hourlyRate * OVERTIME_HOURS) + (this.hourlyRate * OVERTIME_RATE * (this.hoursWorked - OVERTIME_HOURS));
        }else{
            payment = this.hourlyRate * this.hoursWorked;
        }
        df.format(payment);
        this.changePayment(payment);
    }

    /**
     * Method that takes in an inputHours and changes the hoursWorked value to the input
     * @param inputHours to change hoursWorked to
     */
    public void changeHoursWorked(int inputHours){
        this.hoursWorked = inputHours;
    }

    /**
     * Helper method to retrieve the hourlyRate variable 
     * @return (double) hourlyRate
     */
    public double getWage(){
        return this.hourlyRate;
    }
}