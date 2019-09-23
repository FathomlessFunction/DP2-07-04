package InterfaceObjects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayRecordMenu extends JPanel implements ActionListener {

    public enum MenuSelections
    {
        WEEKLY_RECORDS, MONTHLY_RECORDS;
    }

    private JButton weeklyButton;
    private JButton monthlyButton;

    private MenuListener listener;

    public DisplayRecordMenu() {
        //change the button names from here
        weeklyButton = new JButton("Weekly Sales Record");
        monthlyButton = new JButton("Monthly Sales Record");

        weeklyButton.addActionListener(this);
        monthlyButton.addActionListener(this);

        //using box layout currently but we'll probably change it later for something more pretty
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //this is here for debugging
        add(new JLabel(this.getClass().getSimpleName()));

        add(weeklyButton);
        add(monthlyButton);
    }

    //setter for the listener, this is how the controller accesses the buttons.
    public void setMenuListener(MenuListener listener) { this.listener = listener; }

    public void actionPerformed(ActionEvent event) {
        JButton clicked = (JButton)event.getSource();

        if (clicked == weeklyButton) {
            listener.menuSelection(MenuSelections.WEEKLY_RECORDS);

        } else if (clicked == monthlyButton) {
            listener.menuSelection(MenuSelections.MONTHLY_RECORDS);
        }
    }
}
