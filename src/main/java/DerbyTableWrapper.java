
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

    private static String DATABASE_NAME="PHARMACY";
    private static String SALES_TABLE_NAME="pharmacy.sales";

    // this is the directory in which the table information will be stored
    // so it'll appear in your C/Users/MyDB folder.
    private static final String DATABASE_URL="jdbc:derby:MyDB\\Demo;create=true";

    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

    Statement statement;
    Connection connection;

    // TODO: come up with table structure
    // TODO: then update this table creation logic
    private static final String CREATE_TABLE_SQL=
            "create table "+SALES_TABLE_NAME+"(" +
            "NAME VARCHAR(10) NOT NULL," +
            "AGE INT NOT NULL)";

    private static final String DROP_TABLE_SQL=
            "DROP TABLE "+SALES_TABLE_NAME;

    // silly table SQL for now just to check functionality

    public DerbyTableWrapper(){

        try {
            connection = DriverManager.getConnection(DATABASE_URL);
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
            statement.executeUpdate(CREATE_TABLE_SQL);
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
     * Completely removes the product table.
     * It's here for testing purposes. I don't recommend using this in production.
     * @return true if successful (false if table does not exist)
     */
    public boolean deleteSalesTable(){

        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            statement = connection.createStatement();
            statement.executeUpdate(DROP_TABLE_SQL);
            connection.close();
        } catch (SQLException e){
            //e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("(probably) Couldn't delete sales table as it didn't exist.\n");
            return false;
        }
        return true;
    }

    /**
     * injection for testing
     */
    public void setConnection(Connection connection){
        this.connection = connection;
    }

}
