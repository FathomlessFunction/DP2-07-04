
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
    private static String SALES_TABLE_NAME="pharmacy.sales";

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
    private static final String CREATE_TABLE_SQL=
            "create table "+SALES_TABLE_NAME+"(" +
            "NAME VARCHAR(10) NOT NULL," +
            "AGE INT NOT NULL)";

    private static final String DROP_TABLE_SQL=
            "DROP TABLE "+SALES_TABLE_NAME;


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
     * Completely removes/drops the product table.
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
