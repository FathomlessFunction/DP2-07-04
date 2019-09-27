import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import DataObjects.Product;
import DataObjects.Sale;
import InterfaceObjects.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.text.ParseException;

public class InterfaceController extends JFrame {

    //These are the various menus and navigation buttons, all of which are filled in (but aren't pretty)
    private HomePage homePage;
    private DisplayRecordMenu displayRecordMenu;
    private PredictSalesMenu predictSalesMenu;
    private ReturnHotbar returnHotbar;

    //these are still blank, skeleton classes, which now all have a constructor and their name on them.
    private AddRecordPage addRecordPage;
    private WeeklySalesRecordPage weeklySalesRecordPage;
    private MonthlySalesRecordPage monthlySalesRecordPage;
    private WeeklySalesPredictionPage weeklySalesPredictionPage;
    private MonthlySalesPredictionPage monthlySalesPredictionPage;
    private EditRecordPage editRecordPage;

    private DerbyTableWrapper derbyTableWrapper;

    //this is here so that the Interface knows what page it should be displaying
    private JPanel currentPage;

    public InterfaceController(DerbyTableWrapper tableWrapper) {
        //Unsure what our program name will be, this is just a stopgap
        super("Pharmacy Sales Reporting Software");

        //this can be changed to whatever layout we need
        setLayout(new BorderLayout());

        //default size
        setSize(800,600);

        //absolutely required
        setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        derbyTableWrapper = tableWrapper;

        // this is used to override the window closing function, if you need to have certain functions run at
        // program closing put it in here
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                derbyTableWrapper.deleteSalesTable();
                derbyTableWrapper.deleteProductsTable();

                dispose();
                System.exit(0);
            }
        });

        //These are here so that they're immediately usable, also reduces loading times later down the line
        homePage = new HomePage();
        addRecordPage = new AddRecordPage();
        displayRecordMenu = new DisplayRecordMenu();
        //weeklySalesRecordPage = new WeeklySalesRecordPage(salesArray);
        //monthlySalesRecordPage = new MonthlySalesRecordPage();
        predictSalesMenu = new PredictSalesMenu();
        weeklySalesPredictionPage = new WeeklySalesPredictionPage();
        monthlySalesPredictionPage = new MonthlySalesPredictionPage();
        editRecordPage = new EditRecordPage();
        returnHotbar = new ReturnHotbar();

        //this is basically the init of what will be displayed at the start
        currentPage = homePage;
        add(returnHotbar, BorderLayout.NORTH);
        add(currentPage, BorderLayout.CENTER);

        /**
         * The major menu listeners are below. These could be taken out of the constructor and
         * put into their own method in order to reduce clutter, but for now I have left them here.
         */

        //small listener for the "Back" button in the top left
        returnHotbar.setReturnListener(new ReturnListener() {
            public void returnClicked() {
                changePage(homePage);
                }
        });

        //listeners for the HomePage, this connects all the buttons via enums in HomePage
        homePage.setMenuListener(new MenuListener() {
            public void menuSelection(Enum selection) {

                if (selection == HomePage.MenuSelections.ADD_RECORD) {
                    changePage(addRecordPage);

                } else if (selection == HomePage.MenuSelections.EDIT_RECORD) {
                    changePage(editRecordPage);

                } else if (selection == HomePage.MenuSelections.DISPLAY_RECORD) {
                    changePage(displayRecordMenu);

                } else  if (selection == HomePage.MenuSelections.PREDICT_RECORD) {
                    changePage(predictSalesMenu);
                }
            }
        });

        //same as above, but for DisplayRecordMenu
        displayRecordMenu.setMenuListener(new MenuListener() {
            public void menuSelection(Enum selection) {

                if (selection == DisplayRecordMenu.MenuSelections.WEEKLY_RECORDS) {
                    //need to create a new table with new data each call
                    //getList function returns an array of sales
                    changePage(new WeeklySalesRecordPage(getList(tableWrapper,"week")));

                } else if (selection == DisplayRecordMenu.MenuSelections.MONTHLY_RECORDS) {
                    //same as weekly
                    changePage(new MonthlySalesRecordPage(getList(tableWrapper,"month")));
                }
            }
        });

        //same as above, but for PredictSalesMenu
        predictSalesMenu.setMenuListener(new MenuListener() {
            public void menuSelection(Enum selection) {
                if (selection == PredictSalesMenu.MenuSelections.WEEKLY_PREDICTION) {
                    changePage(weeklySalesPredictionPage);
                } else if (selection == PredictSalesMenu.MenuSelections.MONTHLY_PREDICTION) {
                    changePage(monthlySalesPredictionPage);
                }
            }
        });
    }

    /**
     * This uses some Swing methods that I'm not entirely sure on the function of, but without
     * the invalidate, revalidate, and repaint, the new panel won't display correctly.
     *
     * @param newPage the panel to change to
     */
    private void changePage(JPanel newPage) {
        invalidate();

        //cycles to the new page and puts it into the center.
        remove(currentPage);
        currentPage = newPage;
        add(currentPage, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private Object[][] getList(DerbyTableWrapper tableWrapper, String length)  {
        //get input from drop lists in display record menu
        int[] dateStringArray = displayRecordMenu.getDate();
        int[] endDateStringArray = displayRecordMenu.getDate();

        //set end date range to either +6 days or +1 month
        //no error checking involved e.g. if input is 31st will set days to 37th
        if (length == "week"){
            endDateStringArray[0] = endDateStringArray[0]+6;
        } else
        {
            endDateStringArray[1] = endDateStringArray[1]+1;
        }
        //creates new string in format "dd-mm-yyyy" for start date and end date
        String startDateString = dateStringArray[0] +"-"+dateStringArray[1]+"-"+dateStringArray[2];
        String endDateString = endDateStringArray[0]+"-"+endDateStringArray[1]+"-"+endDateStringArray[2];

        //gets list within date range
        List<Sale> saleList = tableWrapper.getSalesByDateRange(startDateString,endDateString);
        //2D array with size of saleList
        Object [][] salesArray = new Object[saleList.size()][6];

        //for loop to assign list to object array
        for(int i = 0; i < saleList.size();i++)
        {
            salesArray[i][0] = saleList.get(i).getSaleID();
            salesArray[i][1]= saleList.get(i).getProductID();
            salesArray[i][2]=  saleList.get(i).getNumberSold();
            salesArray[i][3]=  saleList.get(i).getDateOfSale();
            salesArray[i][4]=  saleList.get(i).getAmountPaid();
            salesArray[i][5]=  saleList.get(i).getSaleStatus();
        }
        return salesArray;
    }
}
