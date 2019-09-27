import DataObjects.Product;
import DataObjects.Sale;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * Provides the functionality of Derby database
 * Will offer the following functionality:
 * -    Create table
 * -    Create
 * -    Retrieve
 * -    Update
 * -    Delete (x) not needed
 *
 * @author MistaMishMash
 *
 * **/

public class DerbyTableWrapper {

    private boolean testMode = false;

    // this is the directory in which the table information will be stored
    // so it'll appear in a MyDB folder in the root of this project.
    // it's gitignored so it won't spam up our repo.
    private static final String DATABASE_URL="jdbc:derby:MyDB\\Demo;create=true";

    // here for injection for testing. (eg create connection class that just saves the SQL string and checks)
    private Statement statement;
    private Connection connection;

    public DerbyTableWrapper(){

        // just here to ensure the connection works. can be deleted.
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////// FOR SETTING MODE / TABLE NAMES /////////////////////////////////////////////////

    /**
     * sets the wrapper to use the test schema, rather than the production schema (pharmacy)]
     * this is so that the test suite does not destroy our production tables and data.
     *
     * As such, this should only ever get called in the Junit files. Do not call it anywhere else.
     */
    public void setTestMode() {
        this.testMode = true;
    }

    /**
     * returns the current sales table name, based off of the wrapper's mode. (test or production)
     * @return
     */
    public String getSalesTableName(){

        String SalesTableName="pharmacy.sales";
        String TestSalesTableName="test.sales";

        if (testMode)
            return TestSalesTableName;
        return SalesTableName;
    }

    /**
     * returns the current product table name, based off of the wrapper's mode.
     * @return
     */
    public String getProductsTableName(){

        String ProductsTableName="pharmacy.products";
        String TestProductsTableName="test.products";

        if (testMode)
            return TestProductsTableName;
        return ProductsTableName;
    }

    ////////////////////////////////////// CREATE TABLE ////////////////////////////////////////////////////////////

    /**
     * Creates a product table with name getSalesTableName()
     *
     * NOTE: to create sales table, the product table must be created first!
     *
     * @return true if successful (false if table already exists)
     */
    public boolean createSalesTable(){

        String CreateSalesTableSql=
                "create table "+getSalesTableName()+"("+ //28
                        "EntryID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"+ //AUTO_INCREMENT //21
                        "SaleID VARCHAR(10) NOT NULL,"+ //28
                        "ProductID INT NOT NULL,"+ //23
                        "DateOfSale DATE,"+ //23
                        "NumberSold INT NOT NULL,"+ //24
                        "AmountPaid FLOAT NOT NULL,"+ //26
                        "SaleStatus VARCHAR(16),"+ //23
                        "PRIMARY KEY (EntryID),"+ //22
                        "FOREIGN KEY (ProductID) REFERENCES "+getProductsTableName()+""+ //45
                        ")";

        return executeUpdateWithSQLString(CreateSalesTableSql,
                "(probably) Couldn't create sales table again as it already existed.\n");
    }

    /**
     * creates a product table in your current schema. (test if in test mode, pharmacy if in prod)
     *
     * @return true if successful
     */
    public boolean createProductsTable(){

        String CreateProductTableSql=
                "create table "+getProductsTableName()+" ("+
                        "ProductID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"+ //AUTO_INCREMENT
                        "ProductName VARCHAR(32),"+
                        "PricePerUnit FLOAT NOT NULL,"+
                        "ProductCategory VARCHAR(16),"+
                        "PRIMARY KEY (ProductID)"+
                        ")";

        return executeUpdateWithSQLString(CreateProductTableSql,
                "(probably) Couldn't create products table again as it already existed.\n");

    }

    ////////////////////////////////////// DELETE TABLE ////////////////////////////////////////////////////////////

    /**
     * Completely removes/drops the product table.
     * It's here for testing purposes. I don't recommend using this in production.
     *
     * @return true if successful (false if table does not exist)
     */
    public boolean deleteSalesTable(){

        String DropSalesTableSql=
                "DROP TABLE "+getSalesTableName();

        return executeUpdateWithSQLString(DropSalesTableSql,
                "(probably) Couldn't delete sales table as it didn't exist.\n");

    }

    public boolean deleteProductsTable(){

        String DropProductsTableSql=
                "DROP TABLE "+getProductsTableName();

        return executeUpdateWithSQLString(DropProductsTableSql,
                "(probably) Couldn't delete product table as it didn't exist.\n");

    }

    ////////////////////////////////////// ADD RECORDS //////////////////////////////////////////////////////////////

    /**
     * Adds a product to the product table
     *
     * @param productToAdd Product object you want to add to the table
     * @return true if successful
     */
    public boolean addProduct(Product productToAdd) {

        String sqlInsertProduct =
                "insert into "+getProductsTableName()+" " +
                "(ProductName, PricePerUnit, ProductCategory) " +
                "values (?, ?, ?)";

        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertProduct);

            preparedStatement.setString(1, productToAdd.getProductName());
            preparedStatement.setFloat(2, productToAdd.getPricePerUnit());
            preparedStatement.setString(3, productToAdd.getProductCategory());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                return true;
            } else {
                System.out.println("Attempted to add new product to products table, but " +
                        "update affected 0 rows in table.\n" +
                        productToAdd);
                return false;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Failed to add Product to product table\n" +
                    "Product: "+productToAdd);
            return false;
        }
    }

    /**
     * adds a sale to the sales table
     * Note: can't choose the sale's EntryID; that is autogenerated by the table.
     *
     * IMPORTANT: will fail if you try to add a sale with a productID of a product that does not exist.
     *
     * @param saleToAdd Sale object you want to add to the table
     * @return true if successful
     */
    public boolean addSale(Sale saleToAdd){

        String sqlInsertSale =
                "insert into "+getSalesTableName()+" " +
                        "(SaleID, ProductID, DateOfSale, NumberSold, AmountPaid, SaleStatus)" +
                        "VALUES" +
                        "(?, ?, ?, ?, ?, ?)";
        try{
            connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertSale);

            preparedStatement.setString(1, saleToAdd.getSaleID());
            preparedStatement.setInt(2, saleToAdd.getProductID());
            preparedStatement.setDate(3, saleToAdd.getDateOfSale());
            preparedStatement.setInt(4, saleToAdd.getNumberSold());
            preparedStatement.setFloat(5, saleToAdd.getAmountPaid());
            preparedStatement.setString(6, saleToAdd.getSaleStatus());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1){
                return true;
            } else {
                System.out.println("attempted to add new Sale to salesTable but update affected " +
                        "0 rows in table.\n"
                        + saleToAdd);
                return false;
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Failed to add Sale to sales table\n" +
                    "Sale: "+saleToAdd);
            return false;
        }
    }

    ////////////////////////////////////// RETRIEVING RECORDS ////////////////////////////////////////////////////////

    /**
     * selects all from product table.
     *
     * @return results as a list of Product objects
     */
    public List<Product> getProducts() {
        String selectProductSQL = "SELECT * FROM "+getProductsTableName();
        return getProductWithSQLString(selectProductSQL);
    }

    /**
     * selects all from sales table,
     *
     * @return results as a list of Sale objects
     */
    public List<Sale> getSales() {
        String selectSaleSQL = "SELECT * FROM "+getSalesTableName();
        return getSalesWithSQLString(selectSaleSQL);
    }

    /**
     * selects all sales from the table between the provided date range
     * format of date is dd-MM-yyyy
     *
     * @param startDateString the start date string (string dd-MM-yyyy) to filter by. (might not be inclusive)
     * @param endDateString the end date string(dd-MM-yyyy) to filter by. (might not be inclusive)
     * @return list of results as Sale objects
     */
    public List<Sale> getSalesByDateRange(String startDateString, String endDateString) {
        // convert date strings
        Date startDate = convertDateStringToDate(startDateString);
        Date endDate = convertDateStringToDate(endDateString);

        String selectSaleSQL = "SELECT * FROM "+getSalesTableName()+" WHERE DateOfSale " +
                "BETWEEN '"+startDate+"' AND '"+endDate+"'";
        return getSalesWithSQLString(selectSaleSQL);
    }

    /**
     * selects all sales from the sales table that are of the passed category string.
     * returned sales items will include product category.
     *
     * @param category the category string to filter by
     * @return list of results as Sale objects
     */
    public List<Sale> getSalesByProductCategory(String category) {
        String selectSaleSQL = "select a.EntryID, a.SaleID, a.DateOfSale, a.NumberSold, " +
                "a.AmountPaid, a.SaleStatus, a.ProductID, b.ProductCategory " +
                "from "+getSalesTableName()+" as a " +
                "INNER JOIN "+getProductsTableName()+" as b ON "
                +"a.ProductID=b.ProductID " +
                "WHERE ProductCategory LIKE '%"+category+"%'";

        return getSalesWithSQLString(selectSaleSQL);
    }

    /**
     * searches for sales with corresponding product category that is within the date range.
     * returned sales items will include product category.
     *
     * @param category the category string to filter by
     * @param startDateString the start date string (dd-MM-yyyy) to filter by. (might not be inclusive)
     * @param endDateString the end date string (dd-MM-yyyy) to filter by. (might not be inclusive)
     */
    public List<Sale> getSalesByProductCategoryAndDateRange(String category,
                                                            String startDateString, String endDateString){
        // convert date strings
        Date startDate = convertDateStringToDate(startDateString);
        Date endDate = convertDateStringToDate(endDateString);

        String selectSaleSQL = "SELECT * FROM (select a.EntryID, a.SaleID, a.DateOfSale, a.NumberSold, " +
                "a.AmountPaid, a.SaleStatus, a.ProductID, b.ProductCategory " +
                "from "+getSalesTableName()+" as a " +
                "INNER JOIN "+getProductsTableName()+" as b ON "
                +"a.ProductID=b.ProductID " +
                "WHERE ProductCategory LIKE '%"+category+"%') c " +
                "WHERE DateOfSale " +
                "BETWEEN '"+startDate+"' AND '"+endDate+"'";

        return getSalesWithSQLString(selectSaleSQL);
    }

    ////////////////////////////////////// EDITING RECORDS ////////////////////////////////////////////////////////////

    /**
     * Edit an existing sale record with the data available in the passed in sale record
     *
     * @param entryID the entry ID of the sales record you wish to update (unique)
     * @param sale Sale object containing the data you wish to update the record with
     *
     * @return number of records modified (ideally will be 1)
     */
    public int editSalesRecord(int entryID, Sale sale) {
        String updateSalesSql = "update "+getSalesTableName()+
                " set SaleID = ?, ProductID = ?, DateOfSale = ?, NumberSold = ?, AmountPaid = ?, SaleStatus = ?" +
                " where EntryID = ?";

        try{
            connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(updateSalesSql);

            preparedStatement.setInt(7, entryID);

            preparedStatement.setString(1, sale.getSaleID());
            preparedStatement.setInt(2, sale.getProductID());
            preparedStatement.setDate(3, sale.getDateOfSale());
            preparedStatement.setInt(4, sale.getNumberSold());
            preparedStatement.setFloat(5, sale.getAmountPaid());
            preparedStatement.setString(6, sale.getSaleStatus());

            int rowsChanged = preparedStatement.executeUpdate();

            connection.close();

            return rowsChanged;

        } catch (SQLException e){
            System.out.println("an error occured while updating a sale with entryID "+entryID);
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /**
     * Edit an existing product record with the data from the passed in Product object
     *
     * @param productID the ID of the product you wish to edit. Should exist, or you'll get an error
     * @param product contains the up-to-date data you want to update the record with.
     *
     * @return number of records modified
     */
    public int editProductRecord(int productID, Product product) {

        String updateProductSql = "update "+getProductsTableName()+"" +
                " set ProductName = ?, PricePerUnit = ?, ProductCategory = ?" +
                " where ProductID = ?";

        try{
            connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(updateProductSql);

            preparedStatement.setInt(4, productID);

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setFloat(2, product.getPricePerUnit());
            preparedStatement.setString(3, product.getProductCategory());

            int rowsChanged = preparedStatement.executeUpdate();

            connection.close();

            return rowsChanged;

        } catch (SQLException e){
            System.out.println("an error occured while updating a product with productID "+productID);
            System.out.println(e.getMessage());
            return 0;
        }
    }

    ////////////////////////////////////// PRIVATE FUNCTIONS //////////////////////////////////////////////////////////

    private Date convertDateStringToDate(String dateString){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            java.util.Date date = format.parse(dateString);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            return sqlDate;
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("failed to convert date string to sql date\n" +
                    "datestring= "+dateString);
            return null;
        }
    }

    /**
     * executes passed SQL string as a query
     * converts resultset to list of sale
     * returns result
     */
    private List<Sale> getSalesWithSQLString(String selectSaleSQL){
        try{
            connection = DriverManager.getConnection(DATABASE_URL);
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(selectSaleSQL);

            List<Sale> toReturn = getSaleListFromResultSet(results);
            connection.close();
            return toReturn;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Could not retrieve sales from sales table.");
            return null;
        }
    }

    /**
     * executes passed SQL string as query
     * converts result set to list of product
     * returns list as result
     */
    private List<Product> getProductWithSQLString(String selectProductSQL){
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(selectProductSQL);

            List<Product> toReturn = getProductListFromResultSet(results);
            connection.close();

            return toReturn;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Could not retrieve products from product table.");
            return null; // returning null as the program should probably crash if this happens.
            //return products;
        }
    }

    /**
     * converts passed resultset to a list of product objects
     */
    private List<Product> getProductListFromResultSet(ResultSet results) throws SQLException {
        List<Product> products = new LinkedList<Product>();
        // int productID, String productName, Float pricePerUnit, String productCategory
        while (results.next()) {
            Product result = new Product(results.getInt("productID"),
                    results.getString("ProductName"),
                    results.getFloat("PricePerUnit"),
                    results.getString("ProductCategory"));
            products.add(result);
        }
        return products;
    }

    /**
     * retrieves product from products table via its unique, automatically generated Product ID
     * @param productID the product ID of the product you want to retrieve
     * @return the product if one was found. Else, null.
     */
    public Product getProductByProductId(int productID) {
        // TODO: implement
        return null;
    }

    /**
     * converts passed resultset to a list of sale objects
     */
    private List<Sale> getSaleListFromResultSet(ResultSet results) throws SQLException {
        List<Sale> sales = new LinkedList<>();
        while (results.next()){
            Sale result = new Sale(
                    results.getInt("EntryID"),
                    results.getString("SaleID"),
                    results.getInt("ProductID"),
                    results.getDate("DateOfSale"),
                    results.getInt("NumberSold"),
                    results.getFloat("AmountPaid"),
                    results.getString("SaleStatus")
            );
            try{
                result.setProductCategory(results.getString("ProductCategory"));
            } catch(SQLException e){
                // just means this wasn't a join against Product table. All g.
                // (I'm being lazy here)
            }
            sales.add(result);
        }
        return sales;
    }

    /**
     * to remove code duplication
     *
     * @param sqlString to execute
     * @param errorMessage message in the case of an SQL Exception
     * @return true if successful
     */
    private boolean executeUpdateWithSQLString(String sqlString, String errorMessage){
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            statement = connection.createStatement();
            statement.executeUpdate(sqlString);
            connection.close();
        } catch (SQLException e){
            //e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(errorMessage);
            return false;
        }
        return true;
    }

}
