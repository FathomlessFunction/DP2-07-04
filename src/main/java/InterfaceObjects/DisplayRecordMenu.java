package InterfaceObjects;

import DataObjects.DerbyTableWrapper;
import DataObjects.Product;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DisplayRecordMenu extends JPanel implements ActionListener {

    public enum MenuSelections
    {
        WEEKLY_RECORDS, MONTHLY_RECORDS, DATE_RANGE;
    }
    //button variables
    private JButton weeklyButton;
    private JButton monthlyButton;
    private JButton dateRangeButton;
    private DatePicker fmPicker;
    private DatePicker toPicker;
    private LocalDate fmDate;
    private LocalDate toDate;
    private JComboBox<String> productIDDropDown;
    private static final String pattern = "dd-mm-yyyy";

    private DisplayListener listener;

    public DisplayRecordMenu() {
        //change the button names from here
        weeklyButton = new JButton("Weekly Sales Record");
        monthlyButton = new JButton("Monthly Sales Record");
        dateRangeButton = new JButton("Selected Date Range");

        //adds action listeners
        weeklyButton.addActionListener(this);
        monthlyButton.addActionListener(this);
        dateRangeButton.addActionListener(this);

        //this is here for debugging
        add(new JLabel(this.getClass().getSimpleName()));

        //adds buttons and drop down menus
        add(weeklyButton);
        add(monthlyButton);
        add(dateRangeButton);

        // drop down menu setup
        // grab products from table.
        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        List<String> productCats = new LinkedList<String>();
        // add default option to list
        productCats.add(Product.getNoProductCat());
        productCats.addAll(wrapper.getProductCategories());
        productIDDropDown = new JComboBox(productCats.toArray());
        productIDDropDown.setVisible(true);
        add(productIDDropDown);

        JFXPanel fxPanel = new JFXPanel();
        add(fxPanel);

        Platform.runLater(() -> {

            fxPanel.setScene(getScene());
        });
    }

    private Scene getScene() {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // Title
        Label title1 = new Label("Select start and end date range");
        //Label title2 = new Label("within the selected date range.");
        VBox titleVb = new VBox();
        titleVb.setAlignment(Pos.CENTER);
        titleVb.getChildren().addAll(title1);

        // From and to date pickers

        Label pickLabel1 = new Label("From date:");
        fmPicker = new DatePicker(LocalDate.now());
        fmPicker.setEditable(false);
        grid.add(pickLabel1, 0, 1);
        grid.add(fmPicker, 1, 1);

        Label pickLabel2 = new Label("To date:");
        toPicker = new DatePicker(LocalDate.now());
        toPicker.setEditable(false);
        grid.add(pickLabel2, 0, 2);
        grid.add(toPicker, 1, 2);

        // Vbox and scene
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(15, 15, 15, 15));
        vbox.getChildren().addAll(titleVb, grid);

        return new Scene(vbox);
    }

    public LocalDate[] getDates(){
        LocalDate[] dates = new LocalDate[2];

        dates[0] = fmDate;
        dates[1] = toDate;

        return dates;
    }

    //setter for the listener, this is how the controller accesses the buttons.
    public void setMenuListener(DisplayListener listener) { this.listener = listener; }

    public void actionPerformed(ActionEvent event) {
        //can tell which button is clicked
        JButton clicked = (JButton)event.getSource();

        String selectedProductFilter = (String)productIDDropDown.getSelectedItem();

        fmDate = fmPicker.getValue();
        toDate = toPicker.getValue();
        System.out.println(fmDate);
        System.out.println(toDate);
        if (clicked == weeklyButton) {
            listener.menuSelection(MenuSelections.WEEKLY_RECORDS, selectedProductFilter);
        } else if (clicked == monthlyButton) {
            listener.menuSelection(MenuSelections.MONTHLY_RECORDS, selectedProductFilter);
        } else if (clicked == dateRangeButton) {
            listener.menuSelection(MenuSelections.DATE_RANGE, selectedProductFilter);
        }
    }

}
