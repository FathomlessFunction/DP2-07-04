package InterfaceObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnHomeHotbar extends JPanel implements ActionListener {

    private JButton homeButton;
    private ReturnListener homeButtonListener;

    public ReturnHomeHotbar() {
        //button name can be changed here
        homeButton = new JButton("Return to Main Menu");

        // what the button will do when you click it
        homeButton.addActionListener(this);

        // button appearance
        homeButton.setBackground(Color.darkGray);
        homeButton.setBorderPainted(true);
        homeButton.setFocusPainted(false); // stops that outline appearing when the option has been selected before
        homeButton.setForeground(Color.white);


        // button layout (align left)
        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(homeButton);

    }

    //setter for the listener, this is how the controller accesses the button.
    public void setReturnListener(ReturnListener listener) {this.homeButtonListener = listener; }

    public void actionPerformed(ActionEvent event) {
        homeButtonListener.returnClicked();
    }
}
