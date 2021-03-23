
import static org.junit.Assert.*;

import org.junit.Test;
import java.text.DecimalFormat;
/**
 * Test class that tests the calculatePayment method in the Management class
 * @author Andrew Park
 * @author Soorim Kim
 */
public class ManagementTest {
	@Test
    public void testCalculatePayment(){
        Company testCompany = new Company();
        Date testDate = new Date("7/1/2020"); // valid date
        DecimalFormat df = new DecimalFormat("#,###.00");
      
        Employee test1 = new Management("Doe,Jane", "CS", testDate, 10000, 1); // Manager
        Employee test2 = new Management("Doe,Jane", "ECE", testDate, 10000, 2); // Department Head
        Employee test3 = new Management("Doe,Jane", "IT", testDate, 10000, 3); // Director
        testCompany.add(test1);
        testCompany.add(test2);
        testCompany.add(test3);
        
        for(int i = 0; i < testCompany.getNumEmployee(); i++){
            testCompany.getArray()[i].calculatePayment();
        }
        assertEquals(576.92, Double.parseDouble(df.format(testCompany.getArray()[0].getPayment())), 0.001); // calculate payments of a Manager
        assertEquals(750.00, Double.parseDouble(df.format(testCompany.getArray()[1].getPayment())), 0.001); // calculate payments of a Department Head
        assertEquals(846.15, Double.parseDouble(df.format(testCompany.getArray()[2].getPayment())), 0.001); // calculate payments of a Director
    }
}