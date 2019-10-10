package InterfaceObjects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JPanel implements ActionListener {

    public enum MenuSelections
    {
        ADD_RECORD, DISPLAY_RECORD, PREDICT_RECORD;
    }

    private JButton addButton;
    private JButton displayButton;
    private JButton predictButton;

    private MenuListener listener;

    public HomePage() {
        //change the button names from here
        addButton = new JButton("Add a Sales Record");
        displayButton = new JButton("Display/Edit Sales Records");
        predictButton = new JButton("Predict Sales Records");

        addButton.addActionListener(this);
        displayButton.addActionListener(this);
        predictButton.addActionListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //this is here for debugging
        add(new JLabel(this.getClass().getSimpleName()));

        add(addButton);
        add(displayButton);
        add(predictButton);
    }

    public void setMenuListener(MenuListener listener) { this.listener = listener; }

    public void actionPerformed(ActionEvent event) {
        JButton clicked = (JButton)event.getSource();

        if (clicked == addButton) {
            listener.menuSelection(MenuSelections.ADD_RECORD);

        } else if (clicked == displayButton) {
            listener.menuSelection(MenuSelections.DISPLAY_RECORD);

        } else if (clicked == predictButton) {
            listener.menuSelection(MenuSelections.PREDICT_RECORD);
        }
    }
}
