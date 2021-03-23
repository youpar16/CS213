package src;
import java.util.Scanner;

/**
 * User-Interface class used to interacting with the users at the frontend, dealing with the inputs and outputs of the project
 * @author Andrew Park
 * @author Soorim Kim
 */
public class Kiosk {
    static final int NOT_FOUND = -1;
    public void run(){
        System.out.println("Library Kiosk running.");
        Library library = new Library();
        int serialNumber = 10001;
        while(true){
            Scanner scanner = new Scanner(System.in); //resource leak?
            String userInput;
            String[] parsedUserInput;
            userInput = scanner.nextLine();
            parsedUserInput = userInput.split(",");
            if(parsedUserInput[0].equals("Q")){
                scanner.close();
                System.out.println("Kiosk session ended.");
                System.exit(0);
            }else if(parsedUserInput[0].equals("A")){
                serialNumber = this.addHelper(library, serialNumber, userInput, parsedUserInput);
            }else if(parsedUserInput[0].equals("R")){
                this.removeHelper(library, serialNumber, userInput, parsedUserInput);
            }else if(parsedUserInput[0].equals("O")){
                this.checkOutHelper(library, serialNumber, userInput, parsedUserInput);
            }else if(parsedUserInput[0].equals("I")){
                this.returnHelper(library, serialNumber,userInput,parsedUserInput);
            }else if(parsedUserInput[0].equals("PA")){
                library.print();
            }else if(parsedUserInput[0].equals("PD")){
                library.printByDate();
            }else if(parsedUserInput[0].equals("PN")){
                library.printByNumber();
            }else{
                System.out.println("Invalid command!");
            } 
        }       
    }
    
    /**
     * Helper method for the add command that initializes a new book and adds it to the library if the date is valid
     * @param library that you want to add the book to
     * @param serialNumber of the book you want to add
     * @param userInput string containing the command, title, and date inputted in the command line
     * @param parsedUserInput an array where the userInput string was separated into command, title, date
     * @return int serial number that should be used next 
     */
    public int addHelper(Library library, int serialNumber, String userInput, String[] parsedUserInput){
        Book book = new Book();
        book.initializeBook(book, parsedUserInput[1], serialNumber, parsedUserInput[2]);
        if(book.getDate().isValid()){
            System.out.println(book.getName() + " added to the library.");
            serialNumber ++;
            library.add(book);
            return serialNumber;
        }else{
            System.out.println("Invalid Date!");
            return serialNumber;
        } 
    }
    
    /**
     * Helper method for the remove command that removes a book from the library if it is found, and states it is unavaiable if the book is not found
     * @param library that you want to add the book to
     * @param serialNumber of the book you want to find
     * @param userInput string containing the command and serial number inputted in the command line
     * @param parsedUserInput an array where the userInput string was separated into command and serial number 
     */
    public void removeHelper(Library library, int serialNumber, String userInput, String[] parsedUserInput){
        String removeSerialNumber = parsedUserInput[1];
        int index = findIndexofBook(removeSerialNumber, library);
        if(index != NOT_FOUND){
            library.remove(library.getBook()[index]);
            System.out.println("Book# "+removeSerialNumber+" removed.");                
        }else{
            System.out.println("Unable to remove, the library does not have this book.");
        }
    }
    
    /**
     * Helper method for the check out command that checks if the book to be checked out exists and if it has been checked out, and checks it out accordingly
     * @param library that you want to add the book to
     * @param serialNumber of the book you want to find
     * @param userInput string containing the command,title,and date inputted in the command line
     * @param parsedUserInput an array where the useInput string was separated into command and serial number
     */
    public void checkOutHelper(Library library, int serialNumber, String userInput, String[] parsedUserInput){
        String checkOutSerialNumber = parsedUserInput[1];
        int index = findIndexofBook(checkOutSerialNumber, library);
        if(index != NOT_FOUND){
            Boolean isCheckedOut = library.checkOut(library.getBook()[index]);
            if(isCheckedOut){
                System.out.println("You've checked out Book#"+checkOutSerialNumber+". Enjoy!");
            }else{
                System.out.println("Book#"+checkOutSerialNumber+" is not avaiable.");
            }
        }else{
            System.out.println("Book#"+checkOutSerialNumber+" is not avaiable.");
        }
    }
    
    /**
     * Helper method for the return command that finds if the book to be returned exists and if it has been checked out, and returns it accordingly
     * @param library that you want to add the book to
     * @param serialNumber of the book you want to find
     * @param userInput string containing the command,title,and date inputted in the command line
     * @param parsedUserInput an array where the userInput string was seperated into command and serial number
     */
    public void returnHelper(Library library, int serialNumber, String userInput, String[] parsedUserInput){
        String returnSerialNumber = parsedUserInput[1];
        int index = findIndexofBook(returnSerialNumber, library);
        if(index != NOT_FOUND){
            Boolean isReturned = library.returns(library.getBook()[index]);
            if(isReturned){
                System.out.println("Book#" + returnSerialNumber + " return has completed. Thanks!");
            }else{
                System.out.println("Unable to return Book#" + returnSerialNumber + ".");
            }
        }else{
            System.out.println("Unable to return Book#" + returnSerialNumber + ".");
        }
    }
    
    /**
     * A helper method that takes in a serial number of a book and finds the index of the book in a given library
     * @param serialNumber of the book you want to find
     * @param library that you are searching in to find the book
     * @return integer index of the book
     */
    public int findIndexofBook(String serialNumber, Library library){
        for(int i = 0; i < library.getNumBooks(); i++){
            if(library.getBook()[i].getSerialNumber().equals(serialNumber)){
                return i;
            }
        }
        return NOT_FOUND;  // ERROR: Book not found
    }
}

