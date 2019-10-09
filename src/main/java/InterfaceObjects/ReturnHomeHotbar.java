package InterfaceObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnHomeHotbar extends JPanel implements ActionListener {

    private JButton backButton;
    private ReturnListener backListener;

    public ReturnHomeHotbar() {
        //button name can be changed here
        backButton = new JButton("Return to Main Menu");

        // what the button will do when you click it
        backButton.addActionListener(this);

        // button appearance
        backButton.setBackground(Color.darkGray);
        backButton.setBorderPainted(true);
        backButton.setFocusPainted(false); // stops that outline appearing when the option has been selected before
        backButton.setForeground(Color.white);


        // button layout (align left)
        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(backButton);

    }

    //setter for the listener, this is how the controller accesses the button.
    public void setReturnListener(ReturnListener listener) {this.backListener = listener; }

    public void actionPerformed(ActionEvent event) {
        backListener.returnClicked();
    }
}
