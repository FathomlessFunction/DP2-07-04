import DataObjects.Sale;
import InterfaceObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class InterfaceController extends JFrame {

    //These are the various menus and navigation buttons, all of which are filled in (but aren't pretty)
    private HomePage homePage;
    private DisplayRecordMenu displayRecordMenu;
    private PredictSalesMenu predictSalesMenu;
    private ReturnHomeHotbar returnHomeHotbar;

    //these are still blank, skeleton classes, which now all have a constructor and their name on them.
    private AddRecordPage addRecordPage;
    private DisplaySalesRecordPage displaySalesRecordPage;
    //private MonthlySalesRecordPage monthlySalesRecordPage;
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
        displaySalesRecordPage = new DisplaySalesRecordPage();
        predictSalesMenu = new PredictSalesMenu();
        weeklySalesPredictionPage = new WeeklySalesPredictionPage();
        monthlySalesPredictionPage = new MonthlySalesPredictionPage();
        editRecordPage = new EditRecordPage();
        returnHomeHotbar = new ReturnHomeHotbar();

        //this is basically the init of what will be displayed at the start
        currentPage = homePage;
        add(returnHomeHotbar, BorderLayout.NORTH);
        add(currentPage, BorderLayout.CENTER);

        /**
         * The major menu listeners are below. These could be taken out of the constructor and
         * put into their own method in order to reduce clutter, but for now I have left them here.
         */

        // in home page, home button should not be visible
        if (currentPage == homePage)
            returnHomeHotbar.setVisible(false);

        //small listener for the "Back" button in the top left
        returnHomeHotbar.setReturnListener(() -> {
            changePage(homePage);
            // if returning to home page, home button should not be visible.
            returnHomeHotbar.setVisible(false);

        });

        //listeners for the HomePage, this connects all the buttons via enums in HomePage
        homePage.setMenuListener(new MenuListener() {
            public void menuSelection(Enum selection) {
                // in non-home pages, home button will be visible
                returnHomeHotbar.setVisible(true);

                if (selection == HomePage.MenuSelections.ADD_RECORD) {
                    changePage(addRecordPage);

                /*} else if (selection == HomePage.MenuSelections.EDIT_RECORD) {
                    changePage(editRecordPage);*/

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
                    displaySalesRecordPage = new DisplaySalesRecordPage(getList(tableWrapper,"week"), "week");
                } else if (selection == DisplayRecordMenu.MenuSelections.MONTHLY_RECORDS) {
                    //same as weekly
                    displaySalesRecordPage = new DisplaySalesRecordPage(getList(tableWrapper,"month"), "month");
                }

                displaySalesRecordPage.setListener(new EditListener() {
                    public void editClicked(Object [] sale) {
                        editRecordPage = new EditRecordPage(sale);

                        editRecordPage.setFormListener(new FormListener() {
                            @Override
                            public void formReceived(FormEvent event) {
                                String saleID = event.getSaleID();
                                int productID = event.getProductID();
                                String dateOfSale = event.getDateOfSale();
                                int numberSold = event.getNumberSold();
                                float amountPaid = event.getAmountPaid();
                                String saleStatus = event.getSaleStatus();

                                Sale saleToEdit = new Sale(saleID, productID, dateOfSale, numberSold, amountPaid, saleStatus);
                                derbyTableWrapper.editSalesRecord(event.getEntryID(), saleToEdit);

                                JOptionPane.showMessageDialog(null, "Record has been updated");
                                changePage(homePage);
                            }
                        });

                        changePage(editRecordPage);
                    }
                });

                changePage(displaySalesRecordPage);
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


        /**
         * I know this code seems a little more bloated than it needs to be, but it makes it much more clear
         * about what's going on. Can refactor this if the team would like me to.
         */
        addRecordPage.setFormListener(new FormListener() {
            public void formReceived(FormEvent event) {
                String saleID = event.getSaleID();
                int productID = event.getProductID();
                String dateOfSale = event.getDateOfSale();
                int numberSold = event.getNumberSold();
                float amountPaid = event.getAmountPaid();
                String saleStatus = event.getSaleStatus();

                Sale saleToAdd = new Sale(saleID, productID, dateOfSale, numberSold, amountPaid, saleStatus);
                derbyTableWrapper.addSale(saleToAdd);

                JOptionPane.showMessageDialog(null, "Record has been added successfully.");
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
        } else {
            endDateStringArray[1] = endDateStringArray[1]+1;
        }
        //creates new string in format "dd-mm-yyyy" for start date and end date
        String startDateString = dateStringArray[0] +"-"+dateStringArray[1]+"-"+dateStringArray[2];
        String endDateString = endDateStringArray[0]+"-"+endDateStringArray[1]+"-"+endDateStringArray[2];

        //gets list within date range
        List<Sale> saleList = tableWrapper.getSalesByDateRange(startDateString,endDateString);
        //2D array with size of saleList
        Object [][] salesArray = new Object[saleList.size()][8];

        String result;
        String tmp[];
        //for loop to assign list to object array
        for(int i = 0; i < saleList.size();i++)
        {
            salesArray[i][0] = saleList.get(i).getEntryID();
            salesArray[i][1] = saleList.get(i).getSaleID();
            salesArray[i][2] = tableWrapper.getProductByProductId(saleList.get(i).getProductID()).getProductName();
            salesArray[i][3] = saleList.get(i).getNumberSold();

            tmp = saleList.get(i).getDateOfSale().toString().split("-");
            result = tmp[2]+"-"+tmp[1]+"-"+tmp[0];

            salesArray[i][4] = result;
            salesArray[i][5] = saleList.get(i).getAmountPaid();
            salesArray[i][6] = saleList.get(i).getSaleStatus();
            salesArray[i][7] = saleList.get(i).getProductID();
        }
        return salesArray;
    }
}
