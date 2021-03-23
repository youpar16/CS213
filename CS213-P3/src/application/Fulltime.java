package application;


import java.text.DecimalFormat;
/**
 * Class that extends the Employee class and includes specific data and operations to a full-time employee.
 * @author Andrew Park
 * @author Soorim Kim
 */
public class Fulltime extends Employee{
    private double annualSalary;
    private DecimalFormat df = new DecimalFormat("#,###.00");
    private static final int PAY_PERIODS = 26;

    /**
     * A constructor for the Fulltime class that creates a Fulltime object with the given parameters
     * @param name of the employee
     * @param departmentCode of the employee
     * @param date in which the employee was hired
     * @param annualSalary of the employee
     */
    public Fulltime(String name, String departmentCode, Date date, double annualSalary){
        super (name, departmentCode, date);
        this.annualSalary = annualSalary;
    }

    /**
     * Equals method that checks if two Fulltime objects are the same
     * @param obj that you want to compare
     * @return true if the objects are equal and false, otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Fulltime){
            Fulltime input = (Fulltime) obj; 
            if(super.equals(input)){
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
        return super.toString() + "FULL TIME::Annual Salary $" + df.format(annualSalary);
    }
    
    /**
     * The calculatePayment method calculates the payment that an employee would recieve and respectively calls the changePayment in the Employee class to set the payment value to the correct amount
     */
    @Override
    public void calculatePayment() { 
        super.calculatePayment();
        double payment = this.annualSalary / PAY_PERIODS;
        df.format(payment);
        this.changePayment(payment);
    }  
}