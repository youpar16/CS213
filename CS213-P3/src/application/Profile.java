package application;

/**
 * Define the profile of an employee
 * @author Andrew Park
 * @author Soorim Kim
 */
public class Profile {
    private String name; //employee’s name in the form “lastname,firstname”
    private String department; //department code: CS, ECE, IT
    private Date dateHired;

    /**
     * A constructor for the Profile class that creates a Profile object with the given parameters
     * @param name of the employee
     * @param department that the employee belongs in
     * @param dateHired date in which the employee was hired
     */
    public Profile(String name, String department, Date dateHired){
        this.name = name;
        this.department = department;
        this.dateHired = dateHired;
    }

    /**
     * The toString method returns the string that gives us information about an employee
     * @return string
     */
    @Override
    public String toString() { 
        return this.name + "::" + this.department + "::" + this.dateHired.getDateString();
    }

    /**
     * Compares name, department, and dateHired data fields of two Profile objects
     * @return true if two objects are equal and false, otherwise
     */
    @Override
    public boolean equals(Object obj) { 
        if(obj instanceof Profile){
            Profile input = (Profile) obj; 
            if(this.name.equals(input.name) && this.department.equals(input.department) && this.dateHired.compareTo(input.dateHired) == 0){
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to retrieve the name variable
     * @return String name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Helper method to retrieve the object datePublished
     * @return date object datePublished
     */
    public String getDepartment(){
        return this.department;
    }

    /**
     * Helper method to retrieve the date variable
     * @return Date dateHired
     */
    public Date getDateHired(){
        return this.dateHired;
    }
}