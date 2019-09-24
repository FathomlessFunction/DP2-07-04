import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import InterfaceObjects.*;

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

        derbyTableWrapper = tableWrapper;

        //this can be changed to whatever layout we need
        setLayout(new BorderLayout());

        //default size
        setSize(800,600);

        //absolutely required
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //These are here so that they're immediately usable, also reduces loading times later down the line
        homePage = new HomePage();
        addRecordPage = new AddRecordPage();
        displayRecordMenu = new DisplayRecordMenu();
        weeklySalesRecordPage = new WeeklySalesRecordPage();
        monthlySalesRecordPage = new MonthlySalesRecordPage();
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
                    changePage(weeklySalesRecordPage);

                } else if (selection == DisplayRecordMenu.MenuSelections.MONTHLY_RECORDS) {
                    changePage(monthlySalesRecordPage);
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
}
