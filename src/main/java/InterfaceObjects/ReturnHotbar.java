package InterfaceObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnHotbar extends JPanel implements ActionListener {

    private JButton backButton;
    private ReturnListener listener;

    public ReturnHotbar() {
        //button name can be changed here
        backButton = new JButton("Back");

        backButton.addActionListener(this);

        //this keeps the button nicely out of the way
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(backButton);
    }

    //setter for the listener, this is how the controller accesses the button.
    public void setReturnListener(ReturnListener listener) {this.listener = listener; }

    public void actionPerformed(ActionEvent event) {
        listener.returnClicked();
    }
}
