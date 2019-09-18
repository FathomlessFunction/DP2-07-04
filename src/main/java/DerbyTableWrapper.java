
import java.sql.*;

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

    // we don't need this anymore C:::
    //private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

    // here for injection for testing. (eg create connection class that just saves the SQL string and checks)
    private Statement statement;
    private Connection connection;

    // TODO: come up with table structure
    // TODO: then update this table creation logic
    private static final String CREATE_SALES_TABLE_SQL=
    //        "create table "+SALES_TABLE_NAME+"(" +
    //        "NAME VARCHAR(10) NOT NULL," +
    //        "AGE INT NOT NULL)";

            "create table "+SALES_TABLE_NAME+"("+ //28
            "EntryID INT NOT NULL,"+ //AUTO_INCREMENT //21
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
            "ProductID INT NOT NULL,"+ //AUTO_INCREMENT
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

    /**
     * Creates a product table with name PRODUCT_TABLE_NAME
     * @return true if successful (false if table already exists)
     */
    public boolean createSalesTable(){
        try {
            connection = DriverManager.getConnection(DATABASE_URL); // no username or password
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_SALES_TABLE_SQL);
            connection.close();

        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("(probably) Couldn't create sales table again as it already existed.\n");
            return false;
        }
        return true;
    }

    public boolean createProductsTable(){
        try {
            connection = DriverManager.getConnection(DATABASE_URL); // no username or password
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_PRODUCTS_TABLE_SQL);
            connection.close();

        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("(probably) Couldn't create sales table again as it already existed.\n");
            return false;
        }
        return true;
    }

    /**
     * Completely removes/drops the product table.
     * It's here for testing purposes. I don't recommend using this in production.
     * @return true if successful (false if table does not exist)
     */
    public boolean deleteSalesTable(){

        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            statement = connection.createStatement();
            statement.executeUpdate(DROP_SALES_TABLE_SQL);
            connection.close();
        } catch (SQLException e){
            //e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("(probably) Couldn't delete sales table as it didn't exist.\n");
            return false;
        }
        System.out.println("Successfully Removed the Table");
        return true;
    }

    public boolean deleteProductsTable(){

        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            statement = connection.createStatement();
            statement.executeUpdate(DROP_PRODUCTS_TABLE_SQL);
            connection.close();
        } catch (SQLException e){
            //e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("(probably) Couldn't delete sales table as it didn't exist.\n");
            return false;
        }
        System.out.println("Successfully Removed the Table");
        return true;
    }

    /**
     * injection for testing
     */
    public void setConnection(Connection connection){
        this.connection = connection;
    }

}
