import DataObjects.CSVReport;
import DataObjects.Sale;
import InterfaceObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private WeeklySalesPredictionPage weeklySalesPredictionPage;
    private MonthlySalesPredictionPage monthlySalesPredictionPage;
    private EditRecordPage editRecordPage;

    private DerbyTableWrapper derbyTableWrapper;

    //getList now saves the current saleList as a private variable, for the CSV reports.
    private List<Sale> saleList;

    //this is here so that the Interface knows what page it should be displaying
    private JPanel currentPage;
    private List<JPanel> previousPage; // change to an array/list

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

        previousPage = new ArrayList<JPanel>();

        //this is basically the init of what will be displayed at the start
        currentPage = homePage; // use array/list?
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
        returnHomeHotbar.setReturnListener(new MenuListener() {
            @Override
            public void menuSelection(Enum selection) {
                if (selection == ReturnHomeHotbar.ReturnSelections.HOME) {
                    changePage(homePage, false);

                } else if (selection == ReturnHomeHotbar.ReturnSelections.BACK) {
                    JPanel tmp = previousPage.get(previousPage.size()-1);
                    previousPage.remove(previousPage.size()-1);
                    changePage(tmp, true);

                }
            }

        });

        //listeners for the HomePage, this connects all the buttons via enums in HomePage
        homePage.setMenuListener(new MenuListener() {
            public void menuSelection(Enum selection) {
                // in non-home pages, home button will be visible

                if (selection == HomePage.MenuSelections.ADD_RECORD) {
                    changePage(addRecordPage, false);

                } else if (selection == HomePage.MenuSelections.DISPLAY_RECORD) {
                    displayRecordMenu = new DisplayRecordMenu();
                    changePage(displayRecordMenu, false);

                    displayRecordMenu.setMenuListener(new MenuListener() {
                        public void menuSelection(Enum selection) {

                            if (selection == DisplayRecordMenu.MenuSelections.WEEKLY_RECORDS) {
                                //need to create a new table with new data each call
                                //getList function returns an array of sales
                                displaySalesRecordPage = new DisplaySalesRecordPage(getList(tableWrapper, "week"), "week");
                            } else if (selection == DisplayRecordMenu.MenuSelections.MONTHLY_RECORDS) {
                                //same as weekly
                                displaySalesRecordPage = new DisplaySalesRecordPage(getList(tableWrapper, "month"), "month");
                            } else if (selection == DisplayRecordMenu.MenuSelections.DATE_RANGE) {
                                //same as weekly
                                displaySalesRecordPage = new DisplaySalesRecordPage(getList(tableWrapper, "range"), "range");
                            }

                            displaySalesRecordPage.setEditListener(new EditListener() {
                                public void editClicked(Object[] sale) {
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
                                            changePage(homePage, false);
                                        }
                                    });

                                    changePage(editRecordPage, false);
                                }
                            });

                            displaySalesRecordPage.setCSVListener(new CSVListener() {
                                public void exportCSVClicked() {
                                    CSVReport csvReport = new CSVReport(saleList);
                                    JFileChooser chooser = new JFileChooser();

                                    //This code has been taken from the oracle tutorial on how to use a file chooser
                                    int returnValue = chooser.showSaveDialog(InterfaceController.this);

                                    if (returnValue == JFileChooser.APPROVE_OPTION) {

                                        String filePath = chooser.getSelectedFile().getPath();
                                        if (csvReport.writeToFile(filePath)) {
                                            JOptionPane.showMessageDialog(null, "Write Successful!\n File Location: " + filePath + ".csv");
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Write Failed.");
                                        }
                                    }
                                }
                            });

                            changePage(displaySalesRecordPage, false);
                        }
                    });

                    //same as above, but for DisplayRecordMenu

                    //same as above, but for PredictSalesMenu
                    predictSalesMenu.setMenuListener(new MenuListener() {
                        public void menuSelection(Enum selection) {
                            if (selection == PredictSalesMenu.MenuSelections.WEEKLY_PREDICTION) {
                                changePage(weeklySalesPredictionPage, false);
                            } else if (selection == PredictSalesMenu.MenuSelections.MONTHLY_PREDICTION) {
                                changePage(monthlySalesPredictionPage, false);
                            }
                        }
                    });
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
    private void changePage(JPanel newPage, boolean back) {
        invalidate();

        if (!back) {
            previousPage.add(currentPage);
        }

        if (newPage == homePage) {
            previousPage.clear();
            returnHomeHotbar.setVisible(false);
        } else {
            returnHomeHotbar.setVisible(true);
        }

        //cycles to the new page and puts it into the center.
        remove(currentPage);
        currentPage = newPage;
        add(currentPage, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private Object[][] getList(DerbyTableWrapper tableWrapper, String length)  {

        //get dates from display record menu
        LocalDate[] dates = displayRecordMenu.getDates();

        //set end date range to either +6 days or +1 month
        if (length == "week"){
            dates[1] = dates[1].plusWeeks(1);
        } else if (length =="month"){
            dates[1] = dates[1].plusMonths(1);
        }

        String startDate;
        String endDate;

        String[] splitStart;
        String[] splitEnd;
        splitStart = dates[0].toString().split("-");
        splitEnd = dates[1].toString().split("-");

        startDate = splitStart[2]+"-"+splitStart[1]+"-"+splitStart[0];
        endDate = splitEnd[2]+"-"+splitEnd[1]+"-"+splitEnd[0];
        System.out.println(endDate);

        //gets list within date range
        saleList = tableWrapper.getSalesByDateRange(startDate,endDate);
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
