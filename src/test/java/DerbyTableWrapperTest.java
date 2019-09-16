import org.junit.Assert;
import org.junit.Test;

public class DerbyTableWrapperTest {


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
    public void doesSalesTableExistShouldReturnTrueWhenTableCreated(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        try{
            wrapper.createSalesTable();
            Assert.assertTrue(wrapper.doesSalesTableExist());
        } catch(Exception e){
            Assert.fail("exception was thrown while creating product table.");
        }
    }

    @Test
    public void doesSalesTableExistShouldReturnFalseWhenTableDropped(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        try{
            wrapper.deleteSalesTable();
            Assert.assertFalse(wrapper.doesSalesTableExist());
        } catch(Exception e){
            Assert.fail("exception was thrown while creating product table.");
        }
    }
}
