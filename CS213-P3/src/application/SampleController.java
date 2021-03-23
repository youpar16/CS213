package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


/**
 * Class used to handle the inputs to the GUI, deals with the inputs and outputs of the project
 * @author Andrew Park
 * @author Soorim Kim
 */
public class SampleController {

    @FXML
    private DatePicker date;

    @FXML
    private TextField name, hourlyRate, hoursWorked, annualSalary;

    @FXML
    private ToggleGroup dept, type, managerType;

    @FXML
    private RadioButton partTimeButton, fullTimeButton, managementButton, CSButton, ITButton, ECEButton, managerButton, departmentheadButton, directorButton;

    @FXML
    private Button setHoursButton, removeButton, addButton, clearButton;

    @FXML
    private MenuItem importButton, exportButton, printEmployeeButton, printDepartmentButton, printDateButton, computeButton;
    
    @FXML
    private TextArea messageArea;
    
    private static final int NOT_FOUND = -1;
	private static final int NO_EMPLOYEES = 0;
	private static final int NOT_A_MANAGER = 0;
	private static final int LOWEST_WAGE = 0;
	private static final int MANAGER = 1;
    private static final int DEPARTMENT_HEAD = 2;
    private static final int DIRECTOR = 3;
    private static final int MAX_HOURS = 100;
    private static final int MIN_HOURS = 0;
    
    private Company company = new Company();

    
    /**
     * Processes the add command. Initializes a variable for each input field and calls the addHelper method using all the input values.
     * @param event
     */
    @FXML
    void add(ActionEvent event) {
    	try {
    		String inputName = name.getText();
    		//get date
    		String inputDate = date.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    		Date inputDateValue = new Date(inputDate);
    		if(inputName.isEmpty()) {
    			messageArea.appendText("Please enter in all required fields.\n");
    			return;
    		}
    		if(!inputDateValue.isValid()) {
    			messageArea.appendText(inputDate + " is not a valid date!\n");
    			return;
    		}
    		//get employee type
    		RadioButton selectedEmployeeType = (RadioButton) type.getSelectedToggle();
    		String selectedEmployeeTypeValue = selectedEmployeeType.getText();

    		//get department type
    		RadioButton selectedDepartment = (RadioButton) dept.getSelectedToggle();
    		String selectedDepartmentValue = selectedDepartment.getText();
    		
    		//get manager type 
    		String selectedManagerTypeValue;
			int selectedManagerInt;
    		if(selectedEmployeeTypeValue.equals("Manager")){
				RadioButton selectedManagerType = (RadioButton) managerType.getSelectedToggle();
				selectedManagerTypeValue = selectedManagerType.getText();
				if(selectedManagerTypeValue.equals("Manager")) {
					selectedManagerInt = MANAGER;
				}else if(selectedManagerTypeValue.equals("Department Head")) {
					selectedManagerInt = DEPARTMENT_HEAD;
				}else {
					selectedManagerInt = DIRECTOR;
				}
			}else{
				selectedManagerInt = NOT_A_MANAGER;
			}
    		double inputWage;
    		if(selectedEmployeeTypeValue.equals("Part Time")) {
				inputWage = Double.parseDouble(hourlyRate.getText());
				if (inputWage < LOWEST_WAGE){
					messageArea.appendText("Pay rate cannot be negative.\n");
					return;
				}
    		}else{
    			inputWage = Double.parseDouble(annualSalary.getText());
				if (inputWage < LOWEST_WAGE) {
					messageArea.appendText("Salary cannot be negative.\n");
					return;
				}
			}
    		addHelper(inputName, selectedDepartmentValue, selectedEmployeeTypeValue, inputDateValue, selectedManagerInt, inputWage);
    		    		
    	}catch (NumberFormatException e) {
    		messageArea.appendText("Please enter a number.\n");
    	}catch (NullPointerException e) {
    		messageArea.appendText("Please enter in all required fields.\n");
    	}catch (Exception e) {
    		messageArea.appendText("Invalid try again.\n");
    	}
    	
    }

    
    /**
     * Processes the remove command. Removes the employee if they are found, states they do not exist if they are not found
     * @param event
     */
    @FXML
    void remove(ActionEvent event) {
		try{
	    	if(company.getNumEmployee() == NO_EMPLOYEES) {
	    		messageArea.appendText("Employee database is empty.\n");
				return;
	    	}else {
	    		String inputName = name.getText();
	    		//get date
	    		String inputDate = date.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	    		Date inputDateValue = new Date(inputDate);
	    		if(inputName.isEmpty()) {
	    			messageArea.appendText("Please enter in all required fields.\n");
	    			return;
	    		}
	    		if(!inputDateValue.isValid()) {
	    			messageArea.appendText(inputDate + " is not a valid date!\n");
	    			return;
	    		}
	    		//get department type
	    		RadioButton selectedDepartment = (RadioButton) dept.getSelectedToggle();
	    		String selectedDepartmentValue = selectedDepartment.getText();
	    		
	    		int index = findIndexOfEmployee(company, inputName, selectedDepartmentValue, inputDateValue);
	            if(index != NOT_FOUND){
	                messageArea.appendText("Employee removed.\n"); 
	                company.remove(company.getArray()[index]);
	            }else{
	                messageArea.appendText("Employee does not exist.\n");
	            }
	    	}
		}catch (NullPointerException e) {
    		messageArea.appendText("Please enter in all required fields.\n");
    	}catch (Exception e) {
    		messageArea.appendText("Invalid try again.\n");
    	}
    }
    

    /**
     * Processes the set hours command. Checks to see that the input hour is valid, and changes the hours worked of an employee if it is valid, Method states that the hour is not valid as well as why its not valid otherwise
     * @param event
     */
    @FXML
    void setHour(ActionEvent event) {
		try{
			if(company.getNumEmployee() == NO_EMPLOYEES) {
	    		messageArea.appendText("Employee database is empty.\n");
				return;
	    	}else {
		    	String inputName = name.getText();
				//get date
				String inputDate = date.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
				Date inputDateValue = new Date(inputDate);
				if(inputName.isEmpty()) {
	    			messageArea.appendText("Please enter in all required fields.\n");
	    			return;
	    		}
	    		if(!inputDateValue.isValid()) {
	    			messageArea.appendText(inputDate + " is not a valid date!\n");
	    			return;
	    		}
	    		
	    		//get employee type
	    		RadioButton selectedDepartment = (RadioButton) dept.getSelectedToggle();
	    		String selectedDepartmentString = selectedDepartment.getText();
	    		
	    		//get employee type
	    		RadioButton selectedEmployeeType = (RadioButton) type.getSelectedToggle();
	    		String selectedEmployeeTypeValue = selectedEmployeeType.getText();
				if (!selectedEmployeeTypeValue.equals("Part Time")){
					messageArea.appendText("Employee must be Part Time to set hours.\n");
					return;
				}
				int inputHours = Integer.parseInt(hoursWorked.getText());
				if(inputHours < MIN_HOURS){
					messageArea.appendText("Working hours cannot be negative.\n");
					return;
				}else if(inputHours > MAX_HOURS){
					messageArea.appendText("Invalid hours: over 100.\n");
					return;
				}
				int index = findIndexOfEmployee(company, inputName, selectedDepartmentString, inputDateValue);
		        if(index != NOT_FOUND){
		            if(company.getArray()[index] instanceof Parttime){
		                Parttime temp = (Parttime) company.getArray()[index];
		                temp.changeHoursWorked(inputHours);
		                company.setHours(temp);
		            }
		            messageArea.appendText("Working hours set.\n");
		        }else{
					messageArea.appendText("Employee does not exist.\n");
				}
	        }
		}catch (NumberFormatException e) {
    		messageArea.appendText("Please enter a number.\n");
    	}catch (NullPointerException e) {
    		messageArea.appendText("Please enter in all required fields.\n");
    	}catch (Exception e) {
    		messageArea.appendText("Invalid try again.\n");
    	}
    }
    
    
    /**
     * A helper method that finds the index of an employee in the company employee array
     * @param company that we want to find the employee from
     * @param name of the employee
     * @param department that the employee belongs in
     * @param the date in which the employee was hired (as a Date object)
     * @return int i holding the index of the employee in the array if found and NOT_FOUND (-1) if the employee is not found
     */
    private int findIndexOfEmployee(Company company, String name, String department, Date date){
        Profile tempProfile = new Profile(name, department, date);
        for(int i = 0; i < company.getNumEmployee(); i++){
            if(company.getArray()[i].getProfile().equals(tempProfile)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    
    /**
     * Disables Hourly Rate, Hours Worked, and the Manger Type Radio Buttons and Enables Annual Salary once Employee Type Full Time is selected. Also clears hourly rate and hours worked
     * @param event
     */
    @FXML
    void fullTimeSelected(ActionEvent event) {
    	annualSalary.setDisable(false);
    	hourlyRate.setDisable(true);
    	hoursWorked.setDisable(true);
		managerButton.setDisable(true);
		departmentheadButton.setDisable(true);
		directorButton.setDisable(true);
		hourlyRate.clear();
		hoursWorked.clear();
    }

    /**
     * Disables Hourly Rate, Hours Worked, and Enables Annual Salary, and the Manager Type Radio Buttons once Employee Type Manager is selected. Also clears hourly rate and hours worked
     * @param event
     */
    @FXML
    void managementSelected(ActionEvent event) {
		annualSalary.setDisable(false);
    	hourlyRate.setDisable(true);
    	hoursWorked.setDisable(true);
		managerButton.setDisable(false);
		departmentheadButton.setDisable(false);
		directorButton.setDisable(false);
		hourlyRate.clear();
		hoursWorked.clear();
    }

    /**
     * Disables Annual Salary and Manager Type Radio Buttons and Enables Hourly Rate and Hours Worked once Employee Type Part Time is selected. Also clears annual salary
     * @param event
     */
    @FXML
    void partTimeSelected(ActionEvent event) {
		annualSalary.setDisable(true);
		hourlyRate.setDisable(false);
		hoursWorked.setDisable(false);
		managerButton.setDisable(true);
		departmentheadButton.setDisable(true);
		directorButton.setDisable(true);
		annualSalary.clear();
    }
    
    /**
     * Method that appends the earning statements for all employees once the Print:All Employee option is clicked
     * @param event
     */
    @FXML
    void print(ActionEvent event) {
		String output = company.print();
		if (output.isEmpty()){
			messageArea.appendText("Employee database is empty.\n");
		}else {
			messageArea.appendText("--Printing earning statements for all employees--\n");
			messageArea.appendText(output);
		}
    }

    /**
     * Method that appends the list of employees by ascending date of employment. If two employees share the same date, they will be lexicographically sorted by name
     * @param event
     */
    @FXML
    void printByDate(ActionEvent event) {
		String output = company.printByDate();
		if (output.isEmpty()){
			messageArea.appendText("Employee database is empty.\n");
		}else {
			messageArea.appendText("--Printing earning statements by date hired--\n");
			messageArea.appendText(output);
		}
    }

    /**
     * Method that appends the list of employees categorized by department in the order: CS, ECE, then IT
     * @param event
     */
    @FXML
    void printByDepartment(ActionEvent event) {
		String output = company.printByDepartment();
		if (output.isEmpty()){
			messageArea.appendText("Employee database is empty.\n");
		}else {
			messageArea.appendText("--Printing earning statements by department--\n");
			messageArea.appendText(output);
		}
    }
    
    /**
     * Method that clears all the text fields in the GUI
     * @param event
     */
    @FXML
    void clear(ActionEvent event) {
		name.clear();
		hourlyRate.clear();
		hoursWorked.clear();
		annualSalary.clear();
		date.setValue(null);
    }
    
    /**
     * Processes the Payment:Compute command. Calls the processPayment method in the Company class and states the the calculation of employee payments are done
     * @param event
     */
    @FXML
    void calculatePayment(ActionEvent event) {
    	if(company.getNumEmployee() == NO_EMPLOYEES) {
    		messageArea.appendText("Employee database is empty.\n");
    	}else {
	    	company.processPayments();
			messageArea.appendText("Calculation of employee payments is done.\n");
    	}
    }
    
    /**
     * Exports a .txt file containing the earning statements for all employees in the Company
     * @param event
     */
    @FXML
    void exportFile(ActionEvent event) {
		try{
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Open Target File for the Export");
			chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
					new ExtensionFilter("All Files", "*.*"));
			Stage stage = new Stage();
			File targeFile = chooser.showSaveDialog(stage); //get the reference of the target file
			//write code to write to the file.
			PrintWriter pw = new PrintWriter(targeFile);
			String inputString = company.exportDatabase();
			pw.print(inputString); //write to file
			pw.close();
		}catch(FileNotFoundException e) {
    		messageArea.appendText("File not found.\n");
    	}catch (Exception e) {
    		messageArea.appendText("Invalid try again.\n");
    	}	
    }

    /**
     * Imports a .txt file containing information about employees and adds them to the Company.
     * @param event
     */
    @FXML
    void importFile(ActionEvent event) {
    	try {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Open Source File for the Import");
			chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
					new ExtensionFilter("All Files", "*.*"));
			Stage stage = new Stage();
			File sourceFile = chooser.showOpenDialog(stage); //get the reference of the source file
			//write code to read from the file.
			Scanner sc = new Scanner(sourceFile); 
			while (sc.hasNextLine()){
				String inputLine = sc.nextLine();
				String[] parsedInputLine;
	            parsedInputLine = inputLine.split(",");
				String selectedEmployeeTypeValue;
				int selectedManagerInt = NOT_A_MANAGER;
	            if(parsedInputLine[0].equals("P")) {
	            	selectedEmployeeTypeValue = "Part Time";
	            }else if(parsedInputLine[0].equals("F")){
	            	selectedEmployeeTypeValue = "Full Time";
				}else{
					selectedEmployeeTypeValue = "Manager";
					selectedManagerInt = Integer.parseInt(parsedInputLine[5]);
				}
	            String inputDate = parsedInputLine[3];
	            Date inputDateValue = new Date(inputDate);
	            addHelper(parsedInputLine[1], parsedInputLine[2], selectedEmployeeTypeValue, inputDateValue, selectedManagerInt, Double.parseDouble(parsedInputLine[4]));
	            			
			}
			sc.close();
    	}catch(FileNotFoundException e) {
    		messageArea.appendText("File not found.\n");
    	}catch (Exception e) {
    		messageArea.appendText("Invalid try again.\n");
    	}	

    }
    
    /**
     * Initializes an employee and adds them to the Company
     * @param inputName
     * @param selectedDepartmentValue
     * @param selectedEmployeeTypeValue
     * @param inputDateValue
     * @param selectedManagerInt
     * @param inputWage
     */
    private void addHelper(String inputName, String selectedDepartmentValue, String selectedEmployeeTypeValue, Date inputDateValue, int selectedManagerInt, double inputWage) {
    	Employee employee = new Employee();
			if(selectedEmployeeTypeValue.equals("Part Time")) {
				employee = new Parttime(inputName, selectedDepartmentValue, inputDateValue, inputWage);
			}else if(selectedEmployeeTypeValue.equals("Manager")){
				employee = new Management(inputName, selectedDepartmentValue, inputDateValue, inputWage, selectedManagerInt);
			}else{
				employee = new Fulltime(inputName, selectedDepartmentValue, inputDateValue, inputWage);
			}
	        if(company.add(employee)){
	            messageArea.appendText("Employee added.\n");
	        }else{
	        	messageArea.appendText("Employee is already in the list.\n");
	        }
    }
		

}
