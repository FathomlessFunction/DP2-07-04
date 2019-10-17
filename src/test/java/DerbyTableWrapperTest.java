import DataObjects.DerbyTableWrapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * for testing derby wrapper table creation and deletion
 *
 * note: this deletes and re-creates tables.
 * IN THE TEST SCHEMA NOW. HOORAY.
 */
public class DerbyTableWrapperTest {

    DerbyTableWrapper wrapper;

    @Before
    public void setup(){
        wrapper = new DerbyTableWrapper();
        wrapper.setTestMode();

        wrapper.createProductsTable(); // products table must exist for sales table to be created.
        wrapper.deleteSalesTable();
    }

    @After
    public void cleanup(){
        wrapper.deleteProductsTable(); // products table must exist for sales table to be created.
        wrapper.deleteSalesTable();
    }

    @Test
    public void shouldUseDifferentTableForTestMode(){
        // such that running tests will not delete production tables
        // by default, should use pharmacy schema

        // only here to test it in not-test mode for a few lines.
        // this should not happen in any other tests.

        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        // when not in test mode, should use pharmacy schema
        Assert.assertFalse(wrapper.getProductsTableName().toLowerCase().contains("test"));
        Assert.assertTrue(wrapper.getProductsTableName().toLowerCase().contains("pharmacy"));
        Assert.assertFalse(wrapper.getSalesTableName().toLowerCase().contains("test"));
        Assert.assertTrue(wrapper.getSalesTableName().toLowerCase().contains("pharmacy"));

        wrapper.setTestMode();

        // after setting test mode, should use test schema
        Assert.assertTrue(wrapper.getProductsTableName().toLowerCase().contains("test"));
        Assert.assertFalse(wrapper.getProductsTableName().toLowerCase().contains("pharmacy"));
        Assert.assertTrue(wrapper.getSalesTableName().toLowerCase().contains("test"));
        Assert.assertFalse(wrapper.getSalesTableName().toLowerCase().contains("pharmacy"));

    }

    @Test
    public void shouldCreateSalesTableWithoutException() {
        try {
            Assert.assertTrue(wrapper.createSalesTable());
        } catch (Exception e){
            Assert.fail("exception was thrown while creating product table.");
        }
    }

    @Test
    public void ifCantCreateTableShouldReturnFalse(){
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
    public void shouldDeleteSalesTableWithoutException() {
        try{
            wrapper.createSalesTable();
            Assert.assertTrue(wrapper.deleteSalesTable());
        } catch(Exception e){
            Assert.fail("exception was thrown while dropping product table.");
        }
    }

    @Test
    public void ifCantDeleteTableShouldReturnFalse(){
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
