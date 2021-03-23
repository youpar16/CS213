
import static org.junit.Assert.*;
import org.junit.Test;
/**
 * Test class that tests the add, remove, and setHours method in the Company class
 * @author Andrew Park
 * @author Soorim Kim
 */
public class CompanyTest {
    @Test
    public void testAdd(){
        Company testCompany = new Company();
        Date testDate = new Date("7/1/2020"); // valid date
      
        Employee test1 = new Parttime("Doe,Jane", "CS", testDate, 45.9);
        assertTrue(testCompany.add(test1)); // adding a new Parttime to the list
        assertFalse(testCompany.add(test1)); // adding an existing Parttime to the list

        Employee test2 = new Fulltime("Doe,Jane", "ECE", testDate, 10000);
        assertTrue(testCompany.add(test2)); // adding a new Fulltime to the list
        assertFalse(testCompany.add(test2)); // adding an existing Fulltime to the list

        Employee test3 = new Management("Doe,Jane", "IT", testDate, 20000, 1);
        assertTrue(testCompany.add(test3)); // adding a new Management to the list
        assertFalse(testCompany.add(test3)); // adding an existing Management to the list

        Employee test4 = new Parttime("Doe,John", "CS", testDate, 38);
        assertTrue(testCompany.add(test4));

        Employee test5 = new Management("Doe,John", "ECE", testDate, 20000, 1);
        assertTrue(testCompany.add(test5)); // able to successfully grow() array
    }
    
    @Test
    public void testRemove(){
        Company testCompany = new Company();
        Date testDate = new Date("7/1/2020"); // valid date

        Employee test1 = new Parttime("Doe,Jane", "CS", testDate, 45.9);
        assertFalse(testCompany.remove(test1)); // removing a Parttime that does not exist
        testCompany.add(test1);
        assertTrue(testCompany.remove(test1)); // removing a Parttime that exists
        
        Employee test2 = new Fulltime("Doe,Jane", "ECE", testDate, 10000);
        assertFalse(testCompany.remove(test2)); // removing a Fulltime that does not exist
        testCompany.add(test2);
        assertTrue(testCompany.remove(test2)); // removing a Fulltime that exists
        
        Employee test3 = new Management("Doe,Jane", "IT", testDate, 20000, 1);
        assertFalse(testCompany.remove(test3)); // removing a Manager that does not exist
        testCompany.add(test3);
        assertTrue(testCompany.remove(test3)); // removing a Manager that exists
    }

    @Test 
    public void testSetHours(){
        Company testCompany = new Company();
        Date testDate = new Date("7/1/2020"); // valid date

        Employee test1 = new Fulltime("Doe,Jane", "CS", testDate, 10000);
        testCompany.add(test1);
        assertFalse(testCompany.setHours(test1)); // setting hours of a Fulltime which cannot happen
        
        Employee test2 = new Management("Doe,Jane", "ECE", testDate, 10000, 1);
        testCompany.add(test2);
        assertFalse(testCompany.setHours(test2)); // setting hours of a Management which cannot happen
        
        Employee test3 = new Parttime("Doe,Jane", "IT", testDate, 45.9);
        testCompany.add(test3);
        assertTrue(testCompany.setHours(test3)); // setting hours of a Parttime that exists
    }
}