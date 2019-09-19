import DataObjects.Product;
import DataObjects.Sale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
        wrapper.createSalesTable();
    }

    Product dummyProduct = new Product("fish",
            Float.parseFloat("4.23"), "food, edible");

    // DD-MM-YYYY
    Sale dummySale = new Sale("123",1,"11-11-2000",
            2,Float.parseFloat("12.3"),"PROCESSED");

    ///////////////////////// ADDING RECORDS /////////////////////////////////////

    @Test
    public void shouldAddProductCorrectly(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();

        // returns true on successful addition
        Assert.assertTrue(wrapper.addProduct(dummyProduct));
    }

    @Test
    public void shouldAddSaleCorrectly(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();

        // product must exist for sale to exist due to foreign key
        Assert.assertTrue(wrapper.addProduct(dummyProduct));

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

    //////////////////////////////////// DISPLAY RECORDS ////////////////////

    @Test
    public void shouldRetrieveProductCorrectly(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        wrapper.deleteSalesTable();
        wrapper.deleteProductsTable();
        wrapper.createProductsTable();
        wrapper.createSalesTable();
        // fresh start

        wrapper.addProduct(dummyProduct);

        // check all traits of added product are retrieved as they were sent.
        List<Product> products = wrapper.getProducts();
        Assert.assertEquals("result list size mismatch.",
                1, products.size());

        // check retrieved product is correct
        Assert.assertEquals("product category mismatch",
                dummyProduct.getProductCategory(), products.get(0).getProductCategory());
        Assert.assertEquals("product name mismatch.",
                dummyProduct.getProductName(), products.get(0).getProductName());
        Assert.assertEquals("product ID mismatch",
                1, products.get(0).getProductID());
        // productID is auto generated. will be 1 if first entry in table.

        Assert.assertEquals("price per uni mismatch",
                dummyProduct.getPricePerUnit(), products.get(0).getPricePerUnit(), 0.1);
        // note: the float assertion may not work because float.
    }

    @Test
    public void shouldRetrieveSalesCorrectly(){
        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        wrapper.deleteSalesTable();
        wrapper.createSalesTable();

        wrapper.addProduct(dummyProduct); // must exist to add sale referencing its productID
        wrapper.addSale(dummySale);

        // check all traits of added product are retrieved as they were sent.
        List<Sale> sales = wrapper.getSales();
        Assert.assertEquals("sales list size mismatch."
                ,1, sales.size()); // 1 sale should be retrieved

        // check retrieved product is correct
        Assert.assertEquals("sale status mismatch.",
                dummySale.getSaleStatus(), sales.get(0).getSaleStatus());
        Assert.assertEquals("date of sale mismatch.",
                dummySale.getDateOfSale(), sales.get(0).getDateOfSale());
        Assert.assertEquals("number sold mismatch.",
                dummySale.getNumberSold(), sales.get(0).getNumberSold());
        Assert.assertEquals("product ID mismatch.",
                dummySale.getProductID(), sales.get(0).getProductID());
        Assert.assertEquals("sales ID mismatch.",
                dummySale.getSaleID(), sales.get(0).getSaleID());
        Assert.assertEquals("entry ID mismatch.",
                1, sales.get(0).getEntryID()); // auto generated
        Assert.assertEquals("amount paid mismatch.",
                dummySale.getAmountPaid(), sales.get(0).getAmountPaid(), 0.1);
    }

    @Test
    public void shouldRetrieveSaleByDateRangeCorrectly(){
        // not sure if this will work with date string....
        DerbyTableWrapper wrapper = new DerbyTableWrapper();

        wrapper.addSale(dummySale); // "11-11-2000"
        Sale anotherSale1 = new Sale("22", 1, "11-12-2019",
                1, Float.parseFloat("11.3"), "Processed");
        Sale anotherSale2 = new Sale("22", 1, "29-12-2000",
                1, Float.parseFloat("11.4"), "Processed");
        wrapper.addSale(anotherSale1);
        wrapper.addSale(anotherSale2); // should both get filtered out

        List<Sale> sales = wrapper.getSalesByDateRange("11-10-2000","30-11-2000");

        // should only retrieve dummy sale. The others are out of range.
        Assert.assertEquals(1, sales.size());
        Assert.assertEquals(sales.get(0).getDateOfSale(), dummySale.getDateOfSale());
    }

    @Test
    public void shouldRetrieveSaleByProductCategoryCorrectly(){
        // currently only designed for 1 product filter. ( 1 string, eg school)
        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        wrapper.deleteProductsTable();
        wrapper.addProduct(dummyProduct); // category = "food, edible"

        wrapper.addProduct(new Product("pencil", Float.parseFloat("12.32"),
                "school, stationary, derp"));

        wrapper.addSale(dummySale); // "11-11-2000"
        Sale saleWithFood = new Sale("22", 1, "11-12-2019",
                1, Float.parseFloat("11.3"), "Processed");
        Sale saleWithStationary = new Sale("22", 2, "29-12-2000",
                1, Float.parseFloat("11.4"), "Processed1");
        Sale saleWithStationary2 = new Sale("12", 2, "11-12-1900",
                2, Float.parseFloat("22.52"), "Processed2");
        wrapper.addSale(saleWithFood);
        wrapper.addSale(saleWithStationary);
        wrapper.addSale(saleWithStationary2);

        // check all traits of added product are retrieved as they were sent.
        List<Sale> salesWithStationary = wrapper.getSalesByProductCategory("stationary");

        // should only retrieve dummy sale. The others are out of range.
        Assert.assertEquals(2, salesWithStationary.size());
        // check the retrieved records are the ones we expect by checking a random, unique field.
        Assert.assertEquals(salesWithStationary.get(0).getDateOfSale(), saleWithStationary.getDateOfSale());
        Assert.assertEquals(salesWithStationary.get(1).getSaleStatus(), saleWithStationary2.getSaleStatus());
    }

}
