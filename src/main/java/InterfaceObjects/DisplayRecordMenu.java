package InterfaceObjects;

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

public class DisplayRecordMenu extends JPanel implements ActionListener {

    public enum MenuSelections
    {
        WEEKLY_RECORDS, MONTHLY_RECORDS, REPORT_PAGE;
    }
    //button variables
    private JButton weeklyButton;
    private JButton monthlyButton;
    private JButton dateButton;
    private JComboBox<Integer> dayMenu;
    private JComboBox<Integer> monthMenu;
    private  JComboBox<Integer> yearMenu;
    private DatePicker fmPicker;
    //private DatePicker inPicker;
    private DatePicker toPicker;
    private LocalDate fmDate;
    private LocalDate toDate;
    private static final String pattern = "dd-mm-yyyy";
    private int dayDate;
    private int monthDate;
    private int yearDate;

    private MenuListener listener;

    public DisplayRecordMenu() {
        //change the button names from here
        weeklyButton = new JButton("Weekly Sales Record");
        monthlyButton = new JButton("Monthly Sales Record");


        //adds action listeners
        weeklyButton.addActionListener(this);
        monthlyButton.addActionListener(this);


        //don't like the look of box layout, will address later
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //this is here for debugging
        add(new JLabel(this.getClass().getSimpleName()));

        //adds buttons and drop down menus
        add(weeklyButton);
        add(monthlyButton);

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

   /* public int[] getDate(){
        //allows InterfaceController to access input of drop down menus
        //create array to pass the 3 values
        int[] dates = new int[3];
        dates[0] = dayDate;
        dates[1] = monthDate;
        dates[2] = yearDate;
        //make sure not empty
        if (dates == null){
            dates = new int[]{1, 1, 2001};
        };

        //return array
        return dates;
    }*/

    public String[] getDates(){
        String[] dates = new String[2];
        //convert fmDate java.time.LocalDate to Java.lang.string
        dates[0] = fmDate.toString();
        dates[1] = toDate.toString();

        return dates;
    }

    //setter for the listener, this is how the controller accesses the buttons.
    public void setMenuListener(MenuListener listener) { this.listener = listener; }

    public void actionPerformed(ActionEvent event) {
        //can tell which button is clicked
        JButton clicked = (JButton)event.getSource();
        //dayDate = (Integer) dayMenu.getSelectedItem();
        //monthDate = (Integer) monthMenu.getSelectedItem();
        //yearDate = (Integer) yearMenu.getSelectedItem();
        fmDate = fmPicker.getValue();
        toDate = toPicker.getValue();
        System.out.println(fmDate);
        System.out.println(toDate);
        if (clicked == weeklyButton) {
            listener.menuSelection(MenuSelections.WEEKLY_RECORDS);
        } else if (clicked == monthlyButton) {
            listener.menuSelection(MenuSelections.MONTHLY_RECORDS);
        }
        // else if (clicked == dateButton) {
        //set new values from drop down menus when dateButton clicked
        //    dayDate = (Integer) dayMenu.getSelectedItem();
        //    monthDate = (Integer) monthMenu.getSelectedItem();
        //    yearDate = (Integer) yearMenu.getSelectedItem();
        //}
    }
}
