import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** (same comment as in TextPanel)
 * A Panel is effectively just a way of grouping different components together.
 * Each panel we want will have it's own class, which has it's own extension of JPanel.
 * For instance, we may have an AddSalesRecordPanel, a ViewSalesReportPanel, ect.
 */

//This time, we must also implement ActionListener, as we have components here that require listeners to function.
public class ButtonPanel extends JPanel implements ActionListener {

    //Buttons are good. They detect when they're clicked on, and then do something.
    private JButton helloButton;
    private JButton worldButton;

    //This interface is used so that the buttons know where to place their text.
    private StringListener textListener;

    public ButtonPanel() {
        //The constructor for JButton gives it a label.
        helloButton = new JButton("Hello");
        worldButton = new JButton("World!");

        //Add action listener binds the button to a listener.
        //Multiple listeners can be bound to a single Button (or most interactive components)
        helloButton.addActionListener(this);
        worldButton.addActionListener(this);

        //each Panel should have its own layout manager.
        //This is the only place where I have used FlowLayout.
        //the 'LEFT' argument aligns the components from left to right.
        setLayout(new FlowLayout(FlowLayout.LEFT));

        //no layout location needs to be specified here, as FlowLayout is used.
        add(helloButton);
        add(worldButton);
    }

    //This method allows us to pass in anything that implements the StringListener interface.
    public void setStringListener(StringListener listener) {
        this.textListener = listener;
    }

    //actionPerformed is referred to as a 'listener', and will trigger when one of the buttons here is clicked.
    public void actionPerformed(ActionEvent event) {
        //Cast is here as there are many many things which can cause an action performed event.
        JButton clicked = (JButton)event.getSource();

        //Determines which button has been clicked, then adds text to that whatever is listening.
        if (clicked == helloButton){
            System.out.println("Hello button clicked!");

            //double checks to prevent printing to a listener which doesn't exist
            if (textListener != null) {
                textListener.textEmitted("Hello ");
            }
        }
        //all the same as above, just with the other button.
        else if (clicked == worldButton){
            System.out.println("World button clicked!");

            if (textListener != null) {
                textListener.textEmitted("World!");
            }
        }
    }
}
