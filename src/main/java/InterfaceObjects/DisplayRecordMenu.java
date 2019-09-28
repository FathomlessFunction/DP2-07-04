package InterfaceObjects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.List;

public class DisplayRecordMenu extends JPanel implements ActionListener {

    public enum MenuSelections
    {
        WEEKLY_RECORDS, MONTHLY_RECORDS;
    }
    //button variables
    private JButton weeklyButton;
    private JButton monthlyButton;
    private JButton dateButton;
    private JComboBox<Integer> dayMenu;
    private JComboBox<Integer> monthMenu;
    private  JComboBox<Integer> yearMenu;
    private int dayDate;
    private int monthDate;
    private int yearDate;

    private MenuListener listener;

    public DisplayRecordMenu() {
        //change the button names from here
        weeklyButton = new JButton("Weekly Sales Record");
        monthlyButton = new JButton("Monthly Sales Record");
        dateButton = new JButton("Submit Date Range");

        //sets the values of the drop down menus
        dayMenu = new JComboBox<Integer>();
        for (int i=1;i<=31;i++){
            dayMenu.addItem(i);
        }
        monthMenu = new JComboBox<Integer>();
        for (int i=1;i<=12;i++){
            monthMenu.addItem(i);
        }
        yearMenu = new JComboBox<Integer>();
        for (int i=2000;i<=2019;i++){
            yearMenu.addItem(i);
        }
        //adds action listeners
        weeklyButton.addActionListener(this);
        monthlyButton.addActionListener(this);
        dateButton.addActionListener(this);

        //don't like the look of box layout, will address later
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //this is here for debugging
        add(new JLabel(this.getClass().getSimpleName()));

        //adds buttons and drop down menus
        add(weeklyButton);
        add(monthlyButton);
        add(dayMenu);
        add(monthMenu);
        add(yearMenu);
        add(dateButton);
    }

    public int[] getDate(){
        //allows InterfaceController to access input of drop down menus
        //create array to pass the 3 values
        int[] dates = new int[3];
        dates[0] = dayDate;
        dates[1] = monthDate;
        dates[2] = yearDate;
        //make sure not empty
        if (dates == null){
            dates = new int[]{11, 11, 2001};
        };
        //return array
        return dates;
    }

    //setter for the listener, this is how the controller accesses the buttons.
    public void setMenuListener(MenuListener listener) { this.listener = listener; }

    public void actionPerformed(ActionEvent event) {
        //can tell which button is clicked
        JButton clicked = (JButton)event.getSource();

        if (clicked == weeklyButton) {
            listener.menuSelection(MenuSelections.WEEKLY_RECORDS);
        } else if (clicked == monthlyButton) {
            listener.menuSelection(MenuSelections.MONTHLY_RECORDS);
        } else if (clicked == dateButton){
            //set new values from drop down menus when dateButton clicked
            dayDate = (Integer) dayMenu.getSelectedItem();
            monthDate = (Integer) monthMenu.getSelectedItem();
            yearDate = (Integer) yearMenu.getSelectedItem();
            }
    }
}
