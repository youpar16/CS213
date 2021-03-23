package src;

/**
 * The Library class is the container class that defines the abstract data type Library to hold library catalog and its operations
 * @author Andrew Park
 * @author Soorim Kim
 */
public class Library {
    private Book[] books; // array-based implementation organizing the Object "Book"s into a 'catalog'
    private int numBooks; // the number of books currently in the bag
    static final int NOT_FOUND = -1;
    static final int BOOK_CAPACITY = 4;
    //default constructor to create an empty bag
    public Library() {
        this.numBooks = 0;
        this.books = new Book[4];
    }
    
    /**
     * Helper method used to find a book in the catalog
     * @param book you want to find
     * @return the index of the book you are searching for in the array-based implementation of the bag data structure
     */
    private int find(Book book) { 
        for(int i = 0; i < numBooks; i++){
            if(books[i].getSerialNumber().equals(book.getSerialNumber())){
                return i;
            }
        }
        return NOT_FOUND;  // ERROR: Book not found
    }
     
    /**
     * Helper method to grow the capacity of the catalog by 4
     */
    private void grow() { 
        int newArraySize = (numBooks/BOOK_CAPACITY + 1) * BOOK_CAPACITY;
        Book[] tempArray = new Book[numBooks];
        for(int i = 0 ; i < numBooks ; i++){
            tempArray[i] = books[i];
        }
        this.books = new Book[newArraySize];
        for(int i = 0 ; i < numBooks ; i++){
            books[i] = tempArray[i];
        }
    }
     
    /**
     * Helper method to add a book into the catalog
     * @param book you want to add
     */
    public void add(Book book) { 
        if(numBooks != 0 && numBooks%BOOK_CAPACITY == 0){
            this.grow();
        }
        books[numBooks] = book;
        numBooks++;
    }
    
    /**
     * Method used to remove a book from the catalog. After removing a book, move the books down the line one space up to fill the gap
     * @param book you want to remove
     * @return true if book was removed, false if book does not exist
     */
    public boolean remove(Book book) {
        int bookIndex = find(book);
        if (bookIndex != NOT_FOUND){
            while(bookIndex < numBooks-1){
                books[bookIndex] = books[bookIndex + 1];
                bookIndex++;
            }
            books[numBooks-1] = null;      
            numBooks--;
            return true;
        }
        return false; //placeholder
    }
    
    /**
     * Method used to checkout a book from the catalog
     * @param book you want to checkout
     * @return true if book is avaiable for checkout, false if book is not avaiable for checkout
     */
    public boolean checkOut(Book book) {
        int bookIndex = find(book);
        if(bookIndex != NOT_FOUND){
            if(book.isCheckedOut() == false){
                book.changeCheckedOut(book);
                return true;
            }
            return false;
        } 
        return false;
    }

    /**
     * Method used to return a book to the catalog
     * @param book you want to return
     * @return true if book is returned, false if book does not belong to the catalog or if it was never checked out
     */
    public boolean returns(Book book) {
        int bookIndex = find(book); 
        if(bookIndex != NOT_FOUND){
            if(books[bookIndex].isCheckedOut() == true){
                book.returningBook(book);
                return true;
            }            
        }
        return false; // Book does not exist in catalog
    }
    
    /**
     * Method that prints the list of books in the bag currently
     */
    public void print() {
        if(numBooks == 0){
            System.out.println("Library catalog is empty!");
        }else{
            System.out.println("**List of books in the library.");
            for(int i = 0; i < numBooks ; i++){
                System.out.println(books[i].toString()); 
            } 
            System.out.println("**End of list");
   
        }
    }
    
    /**
     * Method that prints the list of books by ascending date of publishment. If two books share the same date, it will be lexicographically sorted
     */
    public void printByDate() { 
        if(numBooks == 0){
            System.out.println("Library catalog is empty!");
        }else{
            System.out.println("**List of books by the dates published.");
            bubbleSortDate(books, numBooks);
            for(int i = 0; i < numBooks; i++){
                System.out.println(books[i].toString());
            }
            System.out.println("**End of list");
        }
        
    }
    
    /**
     * Method that prints the list of books by ascending serial number
     */
    public void printByNumber() {
        if(numBooks == 0){
            System.out.println("Library catalog is empty!");
        }else{
            System.out.println("**List of books by the book numbers.");
            bubbleSortNumber(books,numBooks);
            for(int i = 0; i < numBooks; i++){
                System.out.println(books[i].toString());
            }
            System.out.println("**End of list");
        }
    }
     
    /**
     * A helper method used by the printByNumber() method that utilizes bubble sort in order to sort the book array in order by serial number
     * @param books array of all the book objects
     * @param numBooks count of total number of books in books[]
     */
    public void bubbleSortNumber(Book[] books, int numBooks){
        for(int i = 0; i < numBooks - 1;i++){
            for(int j = 0; j < numBooks - i - 1; j++){
                if(Integer.parseInt(books[j].getSerialNumber()) > Integer.parseInt(books[j+1].getSerialNumber())){
                    Book hold = books[j];
                    books[j] = books[j+1];
                    books[j+1] = hold;
                }
            }
        }
    }
    
    /**
    * A helper method used by the printByDate() method that utilizes bubble sort in order to sort the book array in order of by date published
    * @param books array of all the book objects
    * @param numBooks count of total number of books in books[]
    */
    public void bubbleSortDate(Book[] books, int numBooks){
        for(int i = 0; i < numBooks - 1; i++){
            for(int j = 0; j < numBooks - i - 1; j++){
                if(compareDate(books, j, j+1)){
                    Book hold = books[j];
                    books[j] = books[j+1];
                    books[j+1] = hold;
                }
            }
        }
    }
    /**
     * Helper method used by the bubbleSortDate method that compares two dates
     * @param books array of all the book objects
     * @param i index of the first book we are comparing
     * @param j index of the second book we are comparing
     * @return true if date of index i is greater than the date of index j
     */
    public boolean compareDate(Book[] books, int i, int j){
        if(books[i].getDate().getYear() > books[j].getDate().getYear()){
            return true;
        }else if(books[i].getDate().getYear() == books[j].getDate().getYear()){
            if(books[i].getDate().getMonth() > books[j].getDate().getMonth()){
                return true;
            }else if (books[i].getDate().getMonth() == books[j].getDate().getMonth()){
                if(books[i].getDate().getDay() == books[j].getDate().getDay()){
                    return compareName(books[i].getName(), books[j].getName());
                }else if(books[i].getDate().getDay() > books[j].getDate().getDay()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method that compares two titles lexicographically in the case that two books have the same date
     * @param bookName1 title of the first book
     * @param bookName2 title of the second book
     * @return true if bookName1 comes first, false if bookname 2 comes first
     */
    public boolean compareName(String bookName1, String bookName2){
        if(bookName1.compareToIgnoreCase(bookName2)>0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Helper method to retrieve array books[]
     * @return array books[]
     */
    public Book[] getBook(){
        return this.books;
    }

    /**
     * Helper method to retrieve variable numBooks
     * @return integer variable numBooks
     */
    public int getNumBooks(){
        return this.numBooks;
    }
}