package InterfaceObjects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PredictSalesMenu extends JPanel implements ActionListener {

    public enum MenuSelections
    {
        WEEKLY_PREDICTION, MONTHLY_PREDICTION;
    }

    private JButton weeklyButton;
    private JButton monthlyButton;

    private MenuListener listener;

    public PredictSalesMenu() {
        //change the button names from here
        weeklyButton = new JButton("Predict Weekly Sales");
        monthlyButton = new JButton("Predict Monthly Sales");

        weeklyButton.addActionListener(this);
        monthlyButton.addActionListener(this);

        //using box layout currently but we'll probably change it later for something more pretty
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //this is here for debugging
        add(new JLabel(this.getClass().getSimpleName()));

        add(weeklyButton);
        add(monthlyButton);
    }

    //setter for the listener, this is how the controller accesses the button.
    public void setMenuListener(MenuListener listener) { this.listener = listener; }

    public void actionPerformed(ActionEvent event) {
        JButton clicked = (JButton)event.getSource();

        if (clicked == weeklyButton) {
            listener.menuSelection(MenuSelections.WEEKLY_PREDICTION);

        } else if (clicked == monthlyButton) {
            listener.menuSelection(MenuSelections.MONTHLY_PREDICTION);
        }


    }
}