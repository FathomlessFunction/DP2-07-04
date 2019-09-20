import javax.swing.*;
import java.awt.*;

/**
 * This class is where the majority of the action happens. It is an extension of the stock JFrame
 * class., allowing us to use the stock methods from it.
 * This class just needs to be run within main, and Swing does the rest of the lifting.
 */

public class GUIcontroller extends JFrame {

    //See textPanel class for information on it
    private TextPanel textPanel;
    private ButtonPanel buttonPanel;

    //Basically everything is in the constructor.
    public GUIcontroller() {
        //Calls the constructor from the super class. We'll add our program name below.
        super("Prototype Program");

        //set layout is required
        setLayout(new BorderLayout());

        //This sets the default size of the window, but is not actually required (but very recommended)
        setSize(900,600);

        //These are required for us to be able to see and close the program.
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //See specific classes for information on them
        textPanel = new TextPanel();
        buttonPanel = new ButtonPanel();

        //This is a bit more complex. This here sets the buttonPanel to listen to an anonymous StringListener.
        //In this case, that stringListener simply calls the appendText method from within buttonPanel.
        //I'm still getting my head around this convention, but it supports very, very loose coupling.
        buttonPanel.setStringListener(new StringListener() {
            public void textEmitted(String text) {
                textPanel.appendText(text);
            }
        });

        //The add command adds a Swing component into the current panel (or, in this case, the default frame)
        add(textPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);
    }


}
