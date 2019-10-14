package InterfaceObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnHomeHotbar extends JPanel implements ActionListener {

    public enum ReturnSelections
    {
        BACK, HOME;
    }

    private JButton homeButton;
    private JButton returnButton;
    private MenuListener listener;

    public ReturnHomeHotbar() {
        //button name can be changed here
        homeButton = new JButton("Return to Main Menu");
        returnButton = new JButton("Return to Previous Page");

        // what the button will do when you click it
        homeButton.addActionListener(this);
        returnButton.addActionListener(this);

        // button appearance
        homeButton.setBackground(Color.darkGray);
        homeButton.setBorderPainted(true);
        homeButton.setFocusPainted(false); // stops that outline appearing when the option has been selected before
        homeButton.setForeground(Color.white);

        returnButton.setBackground(Color.darkGray);
        returnButton.setBorderPainted(true);
        returnButton.setFocusPainted(false);
        returnButton.setForeground(Color.white);

        // button layout (align left)
        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(homeButton);
        add(returnButton);

    }

    //setter for the listener, this is how the controller accesses the button.
    public void setReturnListener(MenuListener listener) {this.listener = listener; }

    public void actionPerformed(ActionEvent event) {
        JButton clicked = (JButton)event.getSource();

        if (clicked == homeButton) {
            listener.menuSelection(ReturnSelections.HOME);

        } else if (clicked == returnButton) {
            listener.menuSelection(ReturnSelections.BACK);
        }
    }
}
