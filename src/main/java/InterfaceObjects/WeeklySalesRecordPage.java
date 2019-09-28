package InterfaceObjects;

import DataObjects.Product;
import DataObjects.Sale;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WeeklySalesRecordPage extends JPanel {

    public WeeklySalesRecordPage(Object [][] salesArray) {
        //this is here for debugging
        add(new JLabel(this.getClass().getSimpleName()));
        //sets the column names
        String[] columnNames = {"Sale ID",
                "Date Of Sale",
                "Product",
                "Quantity",
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
