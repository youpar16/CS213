package application;

/**
 * This class is an array-based container class that implements the employee database.
 * @author Andrew Park
 * @author Soorim Kim
 */
public class Company {
    private Employee[] emplist; // array-based implementation organizing the Object "Employee"s in a list
    private int numEmployee; // the number of Employees currently in the Company
    static final int NOT_FOUND = -1;
    static final int EMPLOYEE_CAPACITY = 4;
    static final int NONE = 0;

    /**
     * Default Constructor used to initialize an empty Company
     */
    public Company(){
        numEmployee = NONE;
        this.emplist = new Employee[EMPLOYEE_CAPACITY];
    }

    /**
     * Helper method used to find an employee in the company
     * @param employee you are trying to find
     * @return i index of Employee you are searching for in the emplist array, NOT_FOUND if unable to find the Employee 
     */
    private int find(Employee employee) {
        for(int i = 0; i < numEmployee; i++){
            if(emplist[i].equals(employee)){
                return i;
            }
        }
        return NOT_FOUND; 
     }

    /**
     * Helper method to grow the capacity of the emplist[] array by 4
     */
    private void grow() { 
        int newArraySize = (numEmployee/EMPLOYEE_CAPACITY + 1) * EMPLOYEE_CAPACITY;
        Employee[] tempArray = new Employee[numEmployee];
        for(int i = 0; i < numEmployee; i++){
            tempArray[i] = emplist[i];
        }
        this.emplist = new Employee[newArraySize];
        for(int i = 0; i < numEmployee; i++){
            emplist[i] = tempArray[i];
        }
    }

    /**
     * Helper method to add an Employee into the company
     * @param employee you want to add
     * @return true, if the employee was added successfully and false, otherwise
     */
    public boolean add(Employee employee) { 
        if(find(employee) == NOT_FOUND){
            if(numEmployee != 0 && numEmployee % EMPLOYEE_CAPACITY == 0){
                this.grow();
            }
            emplist[numEmployee] = employee;
            numEmployee++;
            return true;
        }
        return false;
    }

    /**
     * Helper method used to remove a employee from the employee list. After removing an employee, move the other employees down the line one space up to fill the gap
     * @param employee you want to remove
     * @return true, if employee was removed successfully and false, otherwise
     */
    public boolean remove(Employee employee) { 
        int employeeIndex = find(employee);
        if (employeeIndex != NOT_FOUND){
            while(employeeIndex < numEmployee - 1){
                emplist[employeeIndex] = emplist[employeeIndex + 1];
                employeeIndex++;
            }
            emplist[numEmployee - 1] = null;      
            numEmployee--;
            return true;
        }
        return false;
    }

    /**
     * Helper method used to set the hoursWorked data field for a Parttime employee
     * @param employee you want to change the hours of
     * @return true, if hours were changed successfully and false, otherwise
     */
    public boolean setHours(Employee employee) {
        if(employee instanceof Parttime){
            int index = find(employee);
            this.emplist[index] = employee;
            return true;
        }
        return false;
     }

     /**
      * Helper method used to process the payments of all the employees in emplist[]
      */
    public void processPayments() { 
        for(int i = 0; i < this.getNumEmployee(); i++){
            this.getArray()[i].calculatePayment();
        }
    }

    /**
     * Helper method that inputs to a returnString the earning statements for all employees
     * @return returnString that contains all of the .toString() of the employees in the company
     */
    public String print() { 
    	String returnString = "";
        for(int i = 0; i < numEmployee; i++){
            returnString +=  emplist[i].toString() + "\n";
        }    
        return returnString;
        
    }

    /**
     * Helper method that inputs to a returnString the earning statements for all employees categorized by department in the order: CS, ECE, then IT
     * This method categorizes the employees into three different arrays by department, sorts each one lexicographically, and combines them back into emplist[]
     * @return returnString that contains all of the .toString() of the employees in the company in order of department
     */
    public String printByDepartment() {
    	String returnString = "";

        int totalCS = 0;
        int totalECE = 0;
        int totalIT = 0;
        int CSCount = 0;
        int ECECount = 0;
        int ITCount = 0;
        for(int i = 0; i < numEmployee; i++){
            if(emplist[i].getProfile().getDepartment().equals("CS")){
                totalCS++;
            }else if(emplist[i].getProfile().getDepartment().equals("ECE")){
                totalECE++;
            }else{
                totalIT++;
            }
        }
        Employee[] CSArray = new Employee[totalCS];
        Employee[] ECEArray = new Employee[totalECE];
        Employee[] ITArray = new Employee[totalIT];
        for(int i = 0; i < this.numEmployee; i++){
            if(this.emplist[i].getProfile().getDepartment().equals("CS")){
                CSArray[CSCount] = this.emplist[i];
                CSCount++;
            }else if(this.emplist[i].getProfile().getDepartment().equals("ECE")){
                ECEArray[ECECount] = this.emplist[i];
                ECECount++;
            }else{
                ITArray[ITCount] = this.emplist[i];
                ITCount++;
            }
        }
        //lexicographically sort the employees by name within each department
        bubbleSort(CSArray, totalCS);
        bubbleSort(ECEArray, totalECE);
        bubbleSort(ITArray, totalIT);
        
        this.combineArrays(CSArray, ECEArray, ITArray, totalCS, totalECE, totalIT);

        for(int i = 0; i < numEmployee; i++){
            returnString +=  emplist[i].toString() + "\n";
        }    
        return returnString;
        
    }

    /**
     * Helper method implemented by printByDepartment() used to combine the three sorted arrays back into emplist[]
     * @param CSArray array of all the employees in the CS department
     * @param ECEArray array of all the employees in the ECE department
     * @param ITArray array of all the employees in the IT department
     * @param totalCS total number of employees in the CS department
     * @param totalECE total number of employees in the ECE department
     * @param totalIT total number of employees in the IT department
     */
    private void combineArrays(Employee[] CSArray, Employee[] ECEArray, Employee[] ITArray, int totalCS, int totalECE, int totalIT){
        int indexCounter = 0;
            for(int i = 0; i < totalCS; i++){
                this.emplist[indexCounter] = CSArray[i];
                indexCounter++;
            }
            for(int i = 0; i < totalECE; i++){
                this.emplist[indexCounter] = ECEArray[i];
                indexCounter++;
            }
            for(int i = 0; i < totalIT; i++){
                this.emplist[indexCounter] = ITArray[i];
                indexCounter++;
            }
    }

    /**
     * Method that inputs to a returnString the list of employees by ascending date of employment. If two employees share the same date, they will be lexicographically sorted by name
     * @return returnString that contains all of the .toString() of the employees in the company in order of date hired
     */
    public String printByDate() { 
    	String returnString = "";

        bubbleSortDate(emplist, numEmployee);
        for(int i = 0; i < numEmployee; i++){
            returnString +=  emplist[i].toString() + "\n";
        }    
        return returnString;
        
    }
    
    /**
    * Helper method used by the printByDate() method that utilizes bubble sort in order to sort the employee array in order of by date hired
    * @param emplist array of all the employee objects
    * @param numEmployee count of total number of employees in emplist[]
    */
    private void bubbleSortDate(Employee[] emplist, int numEmployee){
        for(int i = 0; i < numEmployee - 1; i++){
            for(int j = 0; j < numEmployee - i - 1; j++){
                if(compareDate(emplist, j, j + 1)){
                    Employee hold = emplist[j];
                    emplist[j] = emplist[j + 1];
                    emplist[j + 1] = hold;
                }
            }
        }
    }
    /**
     * Helper method used by the printByDepartment() method that utilizes bubble sort in order to sort an array lexicographically by name
     * @param array that we want to sort
     * @param totalInDepartment number of employees in that department
     */
    private void bubbleSort(Employee[] array, int totalInDepartment){
        for(int i = 0; i < totalInDepartment - 1; i++){
            for(int j = 0; j < totalInDepartment - i - 1; j++){
                if(array[j].getProfile().getName().compareToIgnoreCase(array[j + 1].getProfile().getName()) > 0){
                    Employee hold = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = hold;
                }
            }
        }
    }

    /**
     * Helper method used by the bubbleSortDate method that compares two dates
     * @param emplist array of all the employee objects
     * @param i index of the first employee we are comparing
     * @param j index of the second employee we are comparing
     * @return true, if date of index i is greater than the date of index j and false, otherwise
     */
    private boolean compareDate(Employee[] emplist, int i, int j){
        if(emplist[i].getProfile().getDateHired().getYear() > emplist[j].getProfile().getDateHired().getYear()){
            return true;
        }else if(emplist[i].getProfile().getDateHired().getYear() == emplist[j].getProfile().getDateHired().getYear()){
            if(emplist[i].getProfile().getDateHired().getMonth() > emplist[j].getProfile().getDateHired().getMonth()){
                return true;
            }else if (emplist[i].getProfile().getDateHired().getMonth() == emplist[j].getProfile().getDateHired().getMonth()){
                if(emplist[i].getProfile().getDateHired().getDay() == emplist[j].getProfile().getDateHired().getDay()){
                    return compareName(emplist[i].getProfile().getName(), emplist[j].getProfile().getName());
                }else if(emplist[i].getProfile().getDateHired().getDay() > emplist[j].getProfile().getDateHired().getDay()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method that compares two names lexicographically in the case that two employees were hired on the same date
     * @param employee1 name of the first employee
     * @param employee2 name of the second employee
     * @return true if employee1 comes first and false if employee2 comes first
     */
    private boolean compareName(String employee1, String employee2){
        if(employee1.compareToIgnoreCase(employee2) > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Helper method to retrieve variable numEmployee
     * @return integer variable numEmployee
     */
    public int getNumEmployee(){
        return this.numEmployee;
    }

    /**
     * Helper method to retrieve array emplist[]
     * @return array emplist[]
     */
    public Employee[] getArray(){
        return this.emplist;
    }

    
	/**
	 * Helper method utilized by the export button to return a string of all of the employees in the company
	 * @return String export which contains all of the employees in the company 
	 */
	public String exportDatabase(){
		String export = print();
		return export;
	}

}