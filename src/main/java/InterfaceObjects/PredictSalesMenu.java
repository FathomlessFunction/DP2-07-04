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
import java.util.LinkedList;
import java.util.List;

public class PredictSalesMenu extends JPanel implements ActionListener {

    public enum MenuSelections
    {
        WEEKLY_PREDICTION, MONTHLY_PREDICTION, DATE_RANGE;
    }

    private JButton weeklyButton;
    private JButton monthlyButton;
    private JButton dateRangeButton;
    private DatePicker fromPicker;
    private DatePicker toPicker;
    private LocalDate fromDate;
    private LocalDate toDate;
    private JComboBox<String> productIDDropDown;

    //this will need to be changed to a DisplayListener soon
    private DisplayListener listener;

    public PredictSalesMenu() {
        //change the button names from here
        weeklyButton = new JButton("Predict Weekly Sales");
        monthlyButton = new JButton("Predict Monthly Sales");
        dateRangeButton = new JButton("Selected Date Range");

        weeklyButton.addActionListener(this);
        monthlyButton.addActionListener(this);
        dateRangeButton.addActionListener(this);

        //this is here for debugging
        add(new JLabel(this.getClass().getSimpleName()));

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
        fromPicker = new DatePicker(LocalDate.now());
        fromPicker.setEditable(false);
        grid.add(pickLabel1, 0, 1);
        grid.add(fromPicker, 1, 1);

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

        dates[0] = fromDate;
        dates[1] = toDate;

        return dates;
    }

    //setter for the listener, this is how the controller accesses the button.
    public void setDisplayListener(DisplayListener listener) { this.listener = listener; }

    public void actionPerformed(ActionEvent event) {
        JButton clicked = (JButton)event.getSource();

        String selectedProductFilter = (String)productIDDropDown.getSelectedItem();

        fromDate = fromPicker.getValue();
        toDate = toPicker.getValue();
        System.out.println(fromDate);
        System.out.println(toDate);
        if (clicked == weeklyButton) {
            listener.menuSelection(MenuSelections.WEEKLY_PREDICTION, selectedProductFilter);
        } else if (clicked == monthlyButton) {
            listener.menuSelection(MenuSelections.MONTHLY_PREDICTION, selectedProductFilter);
        } else if (clicked == dateRangeButton) {
            listener.menuSelection(MenuSelections.DATE_RANGE, selectedProductFilter);
        }
    }
}