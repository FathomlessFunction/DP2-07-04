import javax.swing.*;
import java.awt.*;

/** (same comment as in ButtonPanel)
 * A Panel is effectively just a way of grouping different components together.
 * Each panel we want will have it's own class, which has it's own extension of JPanel.
 * For instance, we may have an AddSalesRecordPanel, a ViewSalesReportPanel, ect.
 */

public class TextPanel extends JPanel {

    //a textArea is simply a place where text can exist. Try typing into it on the program!
    //There are more advanced functions for creating text fields to add to the database.
    private JTextArea textArea;

    public TextPanel() {
        textArea = new JTextArea();

        //each Panel should have its own layout manager.
        setLayout(new BorderLayout());

        //same use of Add as seen in GUIcontroller.
        //a JScrollPane can be wrapped onto basically any swing component.
        //Try adding too much text in the program, you'll see the scroll bars appear.
        add(textArea, BorderLayout.CENTER);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void appendText(String text){
        //Append simply adds text to the text area.
        textArea.append(text);
    }

}
