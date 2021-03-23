
import java.util.Scanner;

/**
 * User-Interface class used to interacting with the users at the frontend, dealing with the inputs and outputs of the project
 * @author Andrew Park
 * @author Soorim Kim
 */
public class PayrollProcessing {
    private static final int NOT_FOUND = -1;
    private static final int MAX_HOURS = 100;
    private static final int MIN_HOURS = 0;
    private static final int NO_EMPLOYEES = 0;
    private static final String DNE = "-1";
    private static final String MANAGER = "1";
    private static final String DEPARTMENT_HEAD = "2";
    private static final String DIRECTOR = "3";
    /**
     * Method that runs the entire code and handles the user input and output
     */
    public void run(){
        System.out.println("Payroll Processing starts.");
        Company company = new Company();
        while(true){
            Scanner scanner = new Scanner(System.in); //resource leak?
            String userInput;
            String[] parsedUserInput;
            userInput = scanner.nextLine();
            parsedUserInput = userInput.split(" ");
            if(parsedUserInput[0].equals("Q")){
                scanner.close();
                System.out.println("Payroll Processing completed.");
                System.exit(0);
            }else if(parsedUserInput[0].equals("AP") || parsedUserInput[0].equals("AF")){
                this.addHelper(company, parsedUserInput[0].charAt(1), parsedUserInput[1], parsedUserInput[2], parsedUserInput[3], parsedUserInput[4], DNE);
            }else if (parsedUserInput[0].equals("AM")){
                if(parsedUserInput[5].equals(MANAGER) || parsedUserInput[5].equals(DEPARTMENT_HEAD) || parsedUserInput[5].equals(DIRECTOR)){  
                    this.addHelper(company, parsedUserInput[0].charAt(1), parsedUserInput[1], parsedUserInput[2], parsedUserInput[3], parsedUserInput[4], parsedUserInput[5]);
                }else{
                    System.out.println("Invalid management code.");
                }
            }else if(parsedUserInput[0].equals("R")){
                if(company.getNumEmployee() == NO_EMPLOYEES){
                    System.out.println("Employee database is empty.");
                }else{
                    this.removeHelper(company, parsedUserInput[1], parsedUserInput[2], parsedUserInput[3]);
                }
            }else if(parsedUserInput[0].equals("C")){
                if(company.getNumEmployee() == NO_EMPLOYEES){
                    System.out.println("Employee database is empty.");
                }else{
                    this.processPaymentHelper(company);
                }
            }else if(parsedUserInput[0].equals("S")){
                if(company.getNumEmployee() == NO_EMPLOYEES){
                    System.out.println("Employee database is empty.");
                }else{
                    this.setHoursHelper(company, parsedUserInput[1], parsedUserInput[2], parsedUserInput[3], parsedUserInput[4]);
                }
            }else if(parsedUserInput[0].equals("PA")){
                if(company.getNumEmployee() == NO_EMPLOYEES){
                    System.out.println("Employee database is empty.");
                }else{
                    company.print();
                }
            }else if(parsedUserInput[0].equals("PH")){
                if(company.getNumEmployee() == NO_EMPLOYEES){
                    System.out.println("Employee database is empty.");
                }else{
                    company.printByDate();
                }
            }else if(parsedUserInput[0].equals("PD")){
                if(company.getNumEmployee() == NO_EMPLOYEES){
                    System.out.println("Employee database is empty.");
                }else{
                    company.printByDepartment();
                }
            }else{
                System.out.println("Command '" + parsedUserInput[0] + "' not supported!");
            } 
        }   
    }
    
    /**
     * A helper method for the add command. Initializes an employee and adds them to the Company if they pass the conditions of the checkValidemployee helper method.
     * @param company in which we want to add the employee to
     * @param employeeType character that tells us if the employee is part time, full time, or management
     * @param name of the employee
     * @param department that the employee belongs in
     * @param stringDate the date in which the employee was hired (as a string not a Date object)
     * @param wage how much the employee gets paid
     * @param managementCode tells us what type of management role the employee has (if applicable)
     */
    private void addHelper(Company company, Character employeeType, String name, String department, String stringDate, String wage, String managementCode){
        Employee employee = new Employee();
        Date inputDate = new Date(stringDate);
        double inputWage = Double.parseDouble(wage);
        int inputCode = Integer.parseInt(managementCode);
        if(checkValidEmployee(employeeType, name, department, stringDate, wage, managementCode)){
            if(employeeType == 'P'){    // Part Time
                employee = new Parttime(name, department, inputDate, inputWage);
            }else if(employeeType == 'F'){  // Full Time
                employee = new Fulltime(name, department, inputDate, inputWage);
            }else{  // Management
                 employee = new Management(name, department, inputDate, inputWage, inputCode);
            }
            if(company.add(employee)){
                System.out.println("Employee added.");
            }else{
                System.out.println("Employee is already in the list.");
            }
        }
    }

    /**
     * The helper method utilized by the addEmployee method that checks if the date is valid, if the department code is valid, and if the wage is valid
     * @param employeeType character that tells us if the employee is part time, full time, or management
     * @param name of the employee
     * @param department that the employee belongs in
     * @param stringDate the date in which the employee was hired (as a string not a Date object)
     * @param wage how much the employee gets paid
     * @param managementCode tells us what type of management role the employee has (if applicable)
     * @return true if all conditions are valid and false if a condition is not valid
     */
    private boolean checkValidEmployee(Character employeeType, String name, String department, String stringDate, String wage, String managementCode){
        Date inputDate = new Date(stringDate);
        if(!(inputDate.isValid())){
            System.out.println(stringDate + " is not a valid date!");
            return false;
        }else if (!department.equals("CS") && !department.equals("ECE") && !department.equals("IT")){
            System.out.println("'" + department + "' is not a valid department code.");
            return false;
        }else if(wage.charAt(0) == '-'){
            if(employeeType == 'P'){
                System.out.println("Pay rate cannot be negative.");
            }else {
                System.out.println("Salary cannot be negative.");
            }
            return false;
        }
        return true;
    }

    /**
     * The helper method for the remove command. Removes the employee if they are found, states they do not exist if they are not found
     * @param company that we want to remove the employee from
     * @param name of the employee
     * @param department that the employee belongs in
     * @param stringDate the date in which the employee was hired (as a string not a Date object)
     */
    private void removeHelper(Company company, String name, String department, String stringDate){
        int index = findIndexOfEmployee(company, name, department, stringDate);
        if(index != NOT_FOUND){
            System.out.println("Employee removed."); 
            company.remove(company.getArray()[index]);
        }else{
            System.out.println("Employee doesn't exist.");
        }
    }
    
    /**
     * The helper method for the calculate command. Calls the processPayment method in the Company class and states the the calculation of employee payments are done
     * @param company that we want to process payments for
     */
    private void processPaymentHelper(Company company){
        company.processPayments();
        System.out.println("Calculation of employee payments is done.");
    }
    
    /**
     * The helper method for the set hours command. Checks to see that the input hour is valid, and changes the hours worked of an employee if it is valid, Method states that the hour is not valid as well as why its not valid otherwise
     * @param company that holds the employee in which we want to set the working hours for
     * @param name of the employee
     * @param department that the employee belongs in
     * @param stringDate the date in which the employee was hired (as a string not a Date object)
     * @param hours the amount of hours the employee worked
     */
    private void setHoursHelper(Company company, String name, String department, String stringDate, String hours){
        int inputHours = Integer.parseInt(hours);
        int index = findIndexOfEmployee(company, name, department, stringDate);
        if(inputHours > MAX_HOURS){
            System.out.println("Invalid Hours: over 100.");
        }else if(inputHours < MIN_HOURS){
            System.out.println("Working hours cannot be negative.");
        }else{
            if(index != NOT_FOUND){
                if(company.getArray()[index] instanceof Parttime){
                    Parttime temp = (Parttime) company.getArray()[index];
                    temp.changeHoursWorked(inputHours);
                    company.setHours(temp);
                }
                System.out.println("Working hours set.");
            }
        }
    }

    /**
     * A helper method that finds the index of an employee in the company employee array
     * @param company that we want to find the employee from
     * @param name of the employee
     * @param department that the employee belongs in
     * @param stringDate the date in which the employee was hired (as a string not a Date object)
     * @return int i holding the index of the employee in the array if found and NOT_FOUND (-1) if the employee is not found
     */
    private int findIndexOfEmployee(Company company, String name, String department, String stringDate){
        Date date = new Date(stringDate);
        Profile tempProfile = new Profile(name, department, date);
        for(int i = 0; i < company.getNumEmployee(); i++){
            if(company.getArray()[i].getProfile().equals(tempProfile)){
                return i;
            }
        }
        return NOT_FOUND;
    }

}