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

    DerbyTableWrapper wrapper;

    @Before
    public void setup(){
        wrapper = new DerbyTableWrapper();
        wrapper.setTestMode();

        // must exist for sales table creation
        wrapper.deleteSalesTable();
        wrapper.deleteProductsTable();
        wrapper.createProductsTable();
        wrapper.createSalesTable();
    }

    Product dummyProduct = new Product("fish",
            Float.parseFloat("4.23"), "food");

    // DD-MM-YYYY
    Sale dummySale = new Sale("123",1,"11-11-2000",
            2,Float.parseFloat("12.3"),"PROCESSED");

    ///////////////////////// ADDING RECORDS /////////////////////////////////////

    @Test
    public void shouldAddProductCorrectly(){
        // returns true on successful addition
        Assert.assertTrue(wrapper.addProduct(dummyProduct));
    }

    @Test
    public void shouldAddSaleCorrectly(){

        // product must exist for sale to exist due to foreign key
        Assert.assertTrue(wrapper.addProduct(dummyProduct));

        // return true on successful addition
        Assert.assertTrue(wrapper.addSale(dummySale));
    }

    /*
    @Test
    public void shouldFailAddingIfTableDoesntExist(){

        wrapper.deleteSalesTable();
        wrapper.deleteProductsTable();

        Assert.assertFalse(wrapper.addProduct(dummyProduct));
        Assert.assertFalse(wrapper.addSale(dummySale));
    }*/

    //////////////////////////////////// DISPLAY RECORDS ////////////////////

    @Test
    public void shouldRetrieveProductCorrectly(){
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
    public void shouldRetrieveProductViaProductIDCorrectly(){
        wrapper.deleteSalesTable();
        wrapper.deleteProductsTable();
        wrapper.createProductsTable();

        wrapper.addProduct(dummyProduct); // should have product id = 1
        Product dummyProductTwo= new Product("derp", Float.parseFloat("11.22"), "dummy2cat");
        wrapper.addProduct(dummyProductTwo); // should have product id = 2

        Product retrieved1 = wrapper.getProductByProductId(1);
        Assert.assertEquals(1, retrieved1.getProductID());
        Assert.assertEquals(dummyProduct.getProductCategory(), retrieved1.getProductCategory());

        Product retrieved2 = wrapper.getProductByProductId(2);
        Assert.assertEquals(2, retrieved2.getProductID());
        Assert.assertEquals(dummyProductTwo.getProductCategory(), retrieved2.getProductCategory());

        Product retrieved3 = wrapper.getProductByProductId(3); // shouldn't exist, should return null.
        Assert.assertNull(retrieved3);

    }

    @Test
    public void shouldRetrieveSalesCorrectly(){

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

        wrapper.addProduct(dummyProduct);
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
        wrapper.addProduct(dummyProduct); // category = "food, edible"

        wrapper.addProduct(new Product("pencil", Float.parseFloat("12.32"),
                "stationary"));

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
        Assert.assertEquals(saleWithStationary.getDateOfSale(), salesWithStationary.get(0).getDateOfSale());
        Assert.assertEquals(saleWithStationary2.getSaleStatus(), salesWithStationary.get(1).getSaleStatus());
        Assert.assertEquals("stationary", salesWithStationary.get(0).getProductCategory());
    }

    @Test
    public void shouldRetrieveSaleByProductAndDateRangeCorrectly(){
        wrapper.addProduct(dummyProduct);
        wrapper.addProduct(new Product("balloon", Float.parseFloat("12.31"), "categoryX"));
        wrapper.addSale(new Sale("12345", 2, "19-08-2019", 1, Float.parseFloat("12.99"), "Processed1"));
        wrapper.addSale(new Sale("12345",2, "19-09-2019", 1, Float.parseFloat("12.99"), "Processed2"));
        wrapper.addSale(new Sale("77777",1,"19-09-2019", 1, Float.parseFloat("1.99"), "Processed3"));
        wrapper.addSale(new Sale("88888", 2, "01-01-1990", 1, Float.parseFloat("42.89"), "Processed4"));

        // first two sales listed here both involved product ID 2 and fall within this year
        // third should not show up in query as it involves another product
        // fourth should not show up in query as it does not lie in date range.

        // for ease of use I am using the sale status column to identify each sale entry

        List<Sale> results = wrapper.getSalesByProductCategoryAndDateRange("categoryX", "01-01-2019", "20-12-2019");
        // size of results
        Assert.assertEquals("unexpected size of results", 2, results.size());

        // first entry
        Assert.assertEquals("unexpected returned result value", "Processed1", results.get(0).getSaleStatus());
        Assert.assertEquals("unexpected returned result value", "categoryX", results.get(0).getProductCategory());

        // second entry
        Assert.assertEquals("Processed2",results.get(1).getSaleStatus());
        Assert.assertEquals("categoryX", results.get(1).getProductCategory());
    }

    ////////////////////////////////////// UPDATE RECORDS ////////////////////////////////////////

    @Test
    public void shouldUpdateSaleCorrectly(){

        /// fresh start, clean sales table
        wrapper.deleteSalesTable();
        wrapper.createSalesTable();

        String gonnaUpdateStatusTo = "UPDATE";

        Sale s = new Sale("SALEID",1,"23-09-2019",2, Float.parseFloat("12.22"), "SOLD");

        wrapper.addProduct(dummyProduct);
        wrapper.addSale(s);

        // first check that our sales record does NOT have the value we're gonna update to.
        Assert.assertFalse(wrapper.getSales().get(0).getSaleStatus().equals(gonnaUpdateStatusTo));

        // update the object to have the new stuff in it
        s.setSaleStatus(gonnaUpdateStatusTo);
        s.setNumberSold(1000);
        s.setSaleID("123");
        s.setAmountPaid(Float.parseFloat("44.44"));
        s.setDateOfSale("01-01-2019");

        // then edit the record
        // should change 1 row
        Assert.assertEquals(1, wrapper.editSalesRecord(1, s));

        // then ensure the record has been updated!
        Assert.assertTrue(wrapper.getSales().get(0).getSaleStatus().equals(gonnaUpdateStatusTo));
        Assert.assertEquals(1000, wrapper.getSales().get(0).getNumberSold());
        Assert.assertEquals("123", wrapper.getSales().get(0).getSaleID());
        Assert.assertEquals(Float.parseFloat("44.44"), wrapper.getSales().get(0).getAmountPaid(), 0.01);
        Assert.assertEquals(s.getDateOfSale(), wrapper.getSales().get(0).getDateOfSale());

    }

    @Test
    public void shouldUpdateProductCorrectly(){
        /// fresh start, clean table
        wrapper.deleteSalesTable();
        wrapper.deleteProductsTable();
        wrapper.createProductsTable();
        wrapper.createSalesTable();

        String gonnaUpdateCategoryTo = "UPDATE";

        Product p = new Product("FISH", Float.parseFloat("42.99"), "CATEGORY");

        wrapper.addProduct(dummyProduct);

        // first check that our sales record does NOT have the value we're gonna update to.
        Assert.assertFalse(wrapper.getProducts().get(0).getProductCategory().equals(gonnaUpdateCategoryTo));

        // update the object to have the new stuff in it
        p.setProductCategory(gonnaUpdateCategoryTo);
        p.setPricePerUnit(Float.parseFloat("99.99"));
        p.setProductName("UPDATE");

        // then edit the record
        // should change 1 row
        Assert.assertEquals(1, wrapper.editProductRecord(1, p));

        // then ensure the record has been updated!
        Assert.assertTrue(wrapper.getProducts().get(0).getProductCategory().equals(gonnaUpdateCategoryTo));
        Assert.assertEquals(Float.parseFloat("99.99"), wrapper.getProducts().get(0).getPricePerUnit(), 0.01);
        Assert.assertEquals("UPDATE",wrapper.getProducts().get(0).getProductName());
    }

    @Test
    public void editShouldModifyNoRowsIfEntryIDDoesNotExist(){
        /// fresh start, clean sales table. record should not exist.
        wrapper.deleteSalesTable();
        wrapper.createSalesTable();

        Sale s = new Sale("SALEID",1,"23-09-2019",2, Float.parseFloat("12.22"), "SOLD");

        // wrapper.addSale(s); // not adding sale ooo

        Assert.assertEquals("no records should exist in newly created sales table.", 0, wrapper.getSales().size()); // no records should exist in table.

        Assert.assertEquals("should modify zero rows if record does not exist", 0, wrapper.editSalesRecord(1, s));

    }

    @Test
    public void shouldFailToUpdateIfUpdatingSaleWithProductIDThatDoesNotExist(){
        wrapper.deleteSalesTable();
        wrapper.createSalesTable();
        wrapper.addProduct(dummyProduct);

        Sale s = new Sale("SALEID",1,"23-09-2019",2, Float.parseFloat("12.22"), "SOLD");

        wrapper.addSale(s);

        // update sale with product ID that doesn't exist in product table
        s.setProductID(1111111);

        Assert.assertEquals("should modify zero rows if editing with invalid product ID", 0, wrapper.editSalesRecord(1, s));

        // should result in message:
        // an error occured while updating a sale with entryID 1
        // UPDATE on table 'SALES' caused a violation of foreign key constraint 'SQL190923211610741' for key (1111111).  The statement has been rolled back.

        // as of 23/9/2019 it does. c:
    }

}
