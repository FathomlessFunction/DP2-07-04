import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
    private static String SALES_TABLE_NAME="sales";

    private static final String DATABASE_URL = "jdbc:derby://localhost:1527/"+DATABASE_NAME+";" +
            "create=true;" +
            "user=USER_NAME;" +
            "password=PASSWORD";
    //private static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

    Statement statement;
    Connection connection;

    private static final String CREATE_TABLE_SQL=
            "create table pharmacy."+SALES_TABLE_NAME+"(" +
            "NAME VARCHAR(10) NOT NULL," +
            "AGE INT NOT NULL";

    private static final String DROP_TABLE_SQL=
            "";

    // silly table SQL for now just to check functionality

    public DerbyTableWrapper(){
        // what is table structure
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            //Log.print("ERROR: BAD JDBC DRIVER NAME IN DERBYTABLEWRAPPER");
            e.printStackTrace();
        }

    }

    /**
     * Checks if the sales table has been created or not
     * @return true if sales table exists.
     */
    public boolean doesSalesTableExist(){
        // TODO: implement
        return false;
    }

    /**
     * Creates a product table with name PRODUCT_TABLE_NAME
     * @return true if successful
     */
    public boolean createSalesTable(){

        try {

            connection = DriverManager.getConnection(DATABASE_URL); // no username or password
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_TABLE_SQL);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

         System.out.println("made a table c:");
        return true;
    }

    /**
     * Completely removes the product table.
     * It's here for testing purposes. I don't recommend using this in production.
     * @return true if successful
     */
    public boolean deleteSalesTable(){

        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            statement = connection.createStatement();
            statement.executeUpdate(DROP_TABLE_SQL);
        } catch (SQLException e){
            e.printStackTrace();
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
