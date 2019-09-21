import javax.swing.*;
import java.awt.*;
import InterfaceObjects.*;

public class InterfaceController extends JFrame {

    //these are all currently empty skeleton JPanels, which we'll need later on
    private HomePage homePage;
    private AddRecordPage addRecordPage;
    private DisplayRecordMenu displayRecordMenu;
    private WeeklySalesRecordPage weeklySalesRecordPage;
    private MonthlySalesRecordPage monthlySalesRecordPage;
    private PredictSalesMenu predictSalesMenu;
    private WeeklySalesPredictionPage weeklySalesPredictionPage;
    private MonthlySalesPredictionPage monthlySalesPredictionPage;


    public InterfaceController() {
        //Unsure what our program name will be, this is just a stopgap
        super("Pharmacy Sales Reporting Software");

        //this can be changed to whatever layout we need
        setLayout(new BorderLayout());

        //default size
        setSize(800,600);

        //absolutely required
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //These are just here so that they're immediately usable
        homePage = new HomePage();
        addRecordPage = new AddRecordPage();
        displayRecordMenu = new DisplayRecordMenu();
        weeklySalesRecordPage = new WeeklySalesRecordPage();
        monthlySalesRecordPage = new MonthlySalesRecordPage();
        predictSalesMenu = new PredictSalesMenu();
        weeklySalesPredictionPage = new WeeklySalesPredictionPage();
        monthlySalesPredictionPage = new MonthlySalesPredictionPage();




    }
}
