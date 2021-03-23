package src;

/** 
 * Defines the abstract data type Book, which encapsulates the data fields and methods of a book.
 * @author Andrew Park
 * @author Soorim Kim
 */
public class Book {
    private String number; //a 5-digit serial number unique to the book private String name;
    private String name;
    private boolean checkedOut;
    private Date datePublished;

    /**
     * The equals method compares the serial number of two Books
     * @param obj object in which you want to compare the serial number with
     * @return true if the serial numbers of the books are the same, false if the serial numbers are different
     */
    @Override
    public boolean equals(Object obj){
        Book input = (Book) obj; 
        if(this.number.equals(input.number)){
            return true;
        }
        return false;
    }

    /**
     * The toString method returns a textual representation of a book in the following format, Serial#::Title::Date::Availability
     * @return a String that organizes the book's information in the given format: Serial#::Title::Date::Availability
     */
    @Override
    public String toString() {
        String outputString = "Book#";
        if(this.checkedOut == true){
            outputString += this.number + "::" + this.name + "::" + this.datePublished.inputDate + "::" + "is checked out.";
            return outputString;
        }else{
            outputString += this.number + "::" + this.name + "::" + this.datePublished.inputDate + "::" + "is avaiable.";
            return outputString;
        } 
    }

    /**
     * Helper method to retrieve the variable number
     * @return string variable number
     */
    public String getSerialNumber(){
        return this.number;
    }

    /**
     * Helper method to retrieve the variable name
     * @return string variable name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Helper method to retrieve the variable checkedOut
     * @return boolean variable checkedOut
     */
    public boolean isCheckedOut(){
        return this.checkedOut;
    }

    /**
     * Helper method to retrieve the object datePublished
     * @return date object datePublished
     */
    public Date getDate(){
        return this.datePublished;
    }

    /**
     * Helper method that takes in a book's details and initializes it
     * @param book that you want to initialize
     * @param name of the book
     * @param serialNumber of the book
     * @param date of the book
     */
    public void initializeBook(Book book, String name, int serialNumber, String date){
        book.name = name;
        book.number = String.valueOf(serialNumber);
        book.checkedOut = false;
        book.datePublished = new Date(date);
    }

    /**
     * Helper method to change a book's checked out boolean to true
     * @param book that you want to change the checkedOut boolean of
     */
    public void changeCheckedOut(Book book){
        book.checkedOut = true;
    }

    /**
     * Helper method to change a book's checked out boolean to false
     * @param book that you want to change the checkedOut boolean of
     */
    public void returningBook(Book book){
        book.checkedOut = false;
    }
}
