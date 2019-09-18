import DataObjects.Product;
import DataObjects.Sale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * For testing Create, Retrieve, Update and Delete operations via DerbyTableWrapper
 *
 * note: this deletes and re-creates tables.
 */
public class DerbyTableWrapper_CRUDTest {

    @Before
    public void setup(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();

        // must exist for sales table creation
        wrapper.createProductsTable();
    }

    Product dummyProduct = new Product("fish",
            Float.parseFloat("4.23"), "food, edible");

    // DD-MM-YYYY
    Sale dummySale = new Sale("123",1,"11-11-2000",
            2,Float.parseFloat("12.3"),"PROCESSED");

    @Test
    public void shouldAddProductCorrectly(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();

        // returns true on successful addition
        Assert.assertTrue(wrapper.addProduct(dummyProduct));
    }

    @Test
    public void shouldAddSaleCorrectly(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();

        // return true on successful addition
        Assert.assertTrue(wrapper.addSale(dummySale));
    }

    @Test
    public void shouldFailAddingIfTableDoesntExist(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();

        wrapper.deleteSalesTable();
        wrapper.deleteProductsTable();

        Assert.assertFalse(wrapper.addProduct(dummyProduct));
        Assert.assertFalse(wrapper.addSale(dummySale));
    }

}
