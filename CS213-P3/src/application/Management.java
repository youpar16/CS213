package application;


import java.text.DecimalFormat;

/**
 * Class that extends the Employee class and includes specific data and operations to a part-time employee.
 * @author Andrew Park
 * @author Soorim Kim
 */
public class Management extends Fulltime{
    private int managementCode; //1,2,3
    private static final double MANAGER_COMPENSATION = 5000;
    private static final double DEPARTMENT_HEAD_COMPENSATION = 9500;
    private static final double DIRECTOR_COMPENSATION = 12000;
    private static final double PAY_PERIODS = 26;
    private static final int MANAGER_CODE = 1;
    private static final int DEPARTMENT_HEAD_CODE = 2;
    private DecimalFormat df = new DecimalFormat("#,###.00");

    /**
     * A constructor for the management class that creates a Management object with the given parameters
     * @param name of the employee
     * @param departmentCode of the employee
     * @param date in which the employee was hired
     * @param annualSalary of the employee
     * @param managementCode tells us what type of manager this employee is
     */
    public Management(String name, String departmentCode, Date date, double  annualSalary, int managementCode){
        super (name, departmentCode, date, annualSalary);
        this.managementCode = managementCode;
    }

    /**
     * Equals method that checks if two Management objects are the same
     * @param obj that you want to compare
     * @return true if the objects are equal and false, otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Management){
            Management input = (Management) obj; 
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
        if(managementCode == MANAGER_CODE){
            return super.toString() + "::Manager Compensation $" + df.format(MANAGER_COMPENSATION/PAY_PERIODS);
        }else if(managementCode == DEPARTMENT_HEAD_CODE){
            return super.toString() + "::Department Head Compensation $" + df.format(DEPARTMENT_HEAD_COMPENSATION/PAY_PERIODS);
        }else{
            return super.toString() + "::Director Compensation $" + df.format(DIRECTOR_COMPENSATION/PAY_PERIODS);
        }
    }

    /**
     * The calculatePayment method calculates the payment that an employee would recieve and respectively calls the changePayment in the Employee class to set the payment value to the correct amount
     */
    @Override
    public void calculatePayment() { 
        super.calculatePayment();
        double payment;
        if(this.managementCode == MANAGER_CODE){
            payment = MANAGER_COMPENSATION / PAY_PERIODS;
        }else if(this.managementCode == DEPARTMENT_HEAD_CODE){
            payment = DEPARTMENT_HEAD_COMPENSATION / PAY_PERIODS;
        }else{
            payment = DIRECTOR_COMPENSATION / PAY_PERIODS;
        }
        df.format(payment);
        this.changePayment(payment + this.getPayment());
    }
}
