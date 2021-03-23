package application;

import java.text.DecimalFormat;
/**
 * Class that defines the common data and operations for all employee type; each employee has a profile that uniquely identifies the employee.
 * @author Andrew Park
 * @author Soorim Kim
 */
public class Employee{
    private Profile profile;
    private Character employmentType; // P, F, M
    private double payment; //payment of the employee
    private static final double NO_PAYMENT = 0;
    private DecimalFormat df = new DecimalFormat("#,###.00");
    
    /**
     * Default constructor for the Employee class
     */
    public Employee(){}

    /**
     * A constructor for the Employee class that creates a Employee object with the given parameters
     * @param name of the employee
     * @param departmentCode of the employee
     * @param date in which the employee was hired
     */
    public Employee(String name, String departmentCode, Date date){
        this.profile = new Profile(name, departmentCode, date);
    }
    
    /**
     * Method used to calculate payment of all the employees in emplist[]. Will be overrided by subclasses
     */
    public void calculatePayment(){}
    
    /**
     * The equals method checks for the actual type of an instance of Employee
     * @return true, if two employees are equal and false, otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Employee){
            Employee input = (Employee) obj; 
            if((this.profile.equals(input.profile)) && this.employmentType == input.employmentType){
                    return true;
            }
        }
        return false;
    }

    /**
     * The toString method returns the string that gives us information about an employee
     * @return string outputString
     */
    @Override
    public String toString() {
        String outputString = "";
        if(this.payment != NO_PAYMENT){
            outputString+= this.profile.toString() + "::" + "Payment $" + df.format(this.payment) + "::";
        }else{
            outputString+= this.profile.toString() + "::" + "Payment $" + String.format("%.2f", this.payment) + "::";
        }
        return outputString;
    }

    /**
     * Helper method to retrieve the Object profile
     * @return Profile object profile
     */
    public Profile getProfile(){
        return this.profile;
    }

    /**
     * Helper method to retrieve the variable payment
     * @return (double) payment
     */
    public double getPayment(){
        return this.payment;
    }

    /**
     * Helper method to retrieve the variable employmentType
     * @return character employmentType
     */
    public Character getEmploymentType(){
        return this.employmentType;
    }

    /**
     * Helper method to change the payment variable
     * @param payment that you want to change it to
     */
    public void changePayment(double payment){
        this.payment = payment;
    }
}