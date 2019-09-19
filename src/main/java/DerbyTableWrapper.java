import DataObjects.Product;
import DataObjects.Sale;

import java.sql.*;
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

    // pharmacy = schema name
    // sales = table name
    private static String SALES_TABLE_NAME="pharmacy.sales";//14
    private static String PRODUCTS_TABLE_NAME="pharmacy.products";

    // this is the directory in which the table information will be stored
    // so it'll appear in a MyDB folder in the root of this project.
    // it's gitignored so it won't spam up our repo.
    private static final String DATABASE_URL="jdbc:derby:MyDB\\Demo;create=true";

    // here for injection for testing. (eg create connection class that just saves the SQL string and checks)
    private Statement statement;
    private Connection connection;

    private static final String CREATE_SALES_TABLE_SQL=
            "create table "+SALES_TABLE_NAME+"("+ //28
            "EntryID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"+ //AUTO_INCREMENT //21
            "SaleID VARCHAR(10) NOT NULL,"+ //28
            "ProductID INT NOT NULL,"+ //23
            "DateOfSale VARCHAR(10),"+ //23
            "NumberSold INT NOT NULL,"+ //24
            "AmountPaid FLOAT NOT NULL,"+ //26
            "SaleStatus VARCHAR(16),"+ //23
            "PRIMARY KEY (EntryID),"+ //22
            "FOREIGN KEY (ProductID) REFERENCES "+PRODUCTS_TABLE_NAME+""+ //45
            ")";

    private static final String CREATE_PRODUCTS_TABLE_SQL=
            "create table "+PRODUCTS_TABLE_NAME+" ("+
            "ProductID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"+ //AUTO_INCREMENT
            "ProductName VARCHAR(32),"+
            "PricePerUnit FLOAT NOT NULL,"+
            "ProductCategory VARCHAR(16),"+
            "PRIMARY KEY (ProductID)"+
            ")";


    private static final String DROP_SALES_TABLE_SQL=
            "DROP TABLE "+SALES_TABLE_NAME;

    private static final String DROP_PRODUCTS_TABLE_SQL=
            "DROP TABLE "+PRODUCTS_TABLE_NAME;


    public DerbyTableWrapper(){

        // just here to ensure the connection works. can be deleted.
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTestMode(){

    }

    /**
     * Creates a product table with name PRODUCT_TABLE_NAME
     *
     * NOTE: to create sales table, the product table must be created first!
     *
     * @return true if successful (false if table already exists)
     */
    public boolean createSalesTable(){

        return executeUpdateWithSQLString(CREATE_SALES_TABLE_SQL,
                "(probably) Couldn't create sales table again as it already existed.\n");
    }

    public boolean createProductsTable(){

        return executeUpdateWithSQLString(CREATE_PRODUCTS_TABLE_SQL,
                "(probably) Couldn't create products table again as it already existed.\n");

    }

    /**
     * Completely removes/drops the product table.
     * It's here for testing purposes. I don't recommend using this in production.
     * @return true if successful (false if table does not exist)
     */
    public boolean deleteSalesTable(){

        return executeUpdateWithSQLString(DROP_SALES_TABLE_SQL,
                "(probably) Couldn't delete sales table as it didn't exist.\n");

    }

    public boolean deleteProductsTable(){

        return executeUpdateWithSQLString(DROP_PRODUCTS_TABLE_SQL,
                "(probably) Couldn't delete product table as it didn't exist.\n");

    }

    /**
     * Adds a product to the product table
     *
     * @return true if successful
     */
    public boolean addProduct(Product productToAdd) {

        String sqlInsertProduct =
                "insert into "+PRODUCTS_TABLE_NAME+" " +
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
     *
     * IMPORTANT: will fail if you try to add a sale with a productID of a product that does not exist.
     *
     * @return true if successful
     */
    public boolean addSale(Sale saleToAdd){

        String sqlInsertSale =
                "insert into "+SALES_TABLE_NAME+" " +
                        "(SaleID, ProductID, DateOfSale, NumberSold, AmountPaid, SaleStatus)" +
                        "VALUES" +
                        "(?, ?, ?, ?, ?, ?)";
        try{
            connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertSale);

            preparedStatement.setString(1, saleToAdd.getSaleID());
            preparedStatement.setInt(2, saleToAdd.getProductID());
            preparedStatement.setString(3, saleToAdd.getDateOfSale());
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

    public List<Product> getProducts() {
        String selectProductSQL = "SELECT * FROM "+PRODUCTS_TABLE_NAME;
        List<Product> products = new LinkedList<Product>();
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(selectProductSQL);

            // int productID, String productName, Float pricePerUnit, String productCategory
            while (results.next()){
                Product result = new Product(results.getInt("productID"),
                        results.getString("ProductName"),
                        results.getFloat("PricePerUnit"),
                        results.getString("ProductCategory"));
                products.add(result);
            }
            return products;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Could not retrieve products from product table.");
            return null; // returning null as the program should probably crash if this happens.
            //return products;
        }
    }

    public List<Sale> getSales() {
        String selectSaleSQL = "SELECT * FROM "+SALES_TABLE_NAME;
        List<Sale> sales = new LinkedList<Sale>();
        try{
            connection = DriverManager.getConnection(DATABASE_URL);
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(selectSaleSQL);

            while (results.next()){
                Sale result = new Sale(
                        results.getInt("EntryID"),
                        results.getString("SaleID"),
                        results.getInt("ProductID"),
                        results.getString("DateOfSale"),
                        results.getInt("NumberSold"),
                        results.getFloat("AmountPaid"),
                        results.getString("SaleStatus")
                );
                sales.add(result);
            }
            return sales;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Could not retrieve sales from sales table.");
            return null;
        }
    }

    // this might not work with strings.
    public List<Sale> getSalesByDateRange(String startDate, String endDate) {
        return null;
    }

    public List<Sale> getSalesByProductCategory(String category) {
        return null;
    }
}
