package InterfaceObjects;

import DataObjects.Product;
import DataObjects.Sale;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DisplaySalesRecordPage extends JPanel {

    public DisplaySalesRecordPage(Object [][] salesArray, String length) {
        //this is here for debugging
        if (length == "week"){
            add(new JLabel("Weekly sales record display"));
        } else {
            add(new JLabel("Monthly sales record display"));
        }
        //add(new JLabel(this.getClass().getSimpleName()));
        //sets the column names
        String[] columnNames = {"Entry ID",
                "Sale ID",
                "Product",
                "Amount Sold",
                "Date Of Sale",
                "Total Price",
                "Sale Status"};
        //creates the JTable(Object [][], String[]) and populates it with data
        JTable weeklyTable = new JTable(salesArray,columnNames);
        //Allows it to be scrollable
        weeklyTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        weeklyTable.setFillsViewportHeight(true);

        //create scroll with table inside
        JScrollPane scrollPane = new JScrollPane(weeklyTable);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //add scroll
        add(scrollPane);
    }
}
