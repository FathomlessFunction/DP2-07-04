import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DerbyTableWrapperTest {

    @Before
    public void setup(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        wrapper.deleteSalesTable();
    }


    @Test
    public void shouldCreateProductTableWithoutException() {
        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        try {
            Assert.assertTrue(wrapper.createSalesTable());
        } catch (Exception e){
            Assert.fail("exception was thrown while creating product table.");
        }
    }

    @Test
    public void ifCantCreateTableShouldReturnFalse(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        try{
            // creating table twice - second time should definitely return false
            // as table should already be created
            wrapper.createSalesTable();
            Assert.assertFalse(wrapper.createSalesTable());
        } catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void shouldDeleteProductTableWithoutException() {
        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        try{
            wrapper.createSalesTable();
            Assert.assertTrue(wrapper.deleteSalesTable());
        } catch(Exception e){
            Assert.fail("exception was thrown while dropping product table.");
        }
    }

    @Test
    public void ifCantDeleteTableShouldReturnFalse(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        try{
            // deleting table twice - second time should definitely return false
            // as table should already be dropped.
            wrapper.deleteSalesTable();
            Assert.assertFalse(wrapper.deleteSalesTable());
        } catch (Exception e){
            Assert.fail();
        }

    }
}
