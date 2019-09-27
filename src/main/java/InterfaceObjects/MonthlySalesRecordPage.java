package InterfaceObjects;

import javax.swing.*;
import java.awt.*;

public class MonthlySalesRecordPage extends JPanel {
    //same as WeeklySalesRecordPage
    public MonthlySalesRecordPage(Object [][] salesArray) {
        //this is here for debugging
        add(new JLabel(this.getClass().getSimpleName()));
        //sets the column names
        String[] columnNames = {"Entry ID",
                "Sale ID",
                "Product",
                "Amount Sold",
                "Date Of Sale",
                "Total Price",
                "Sale Status"};
        //creates the JTable(Object [][], String[]) and populates it with data
        JTable monthlyTable = new JTable(salesArray,columnNames);
        //Allows it to be scrollable
        monthlyTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        monthlyTable.setFillsViewportHeight(true);


        JScrollPane scrollPane = new JScrollPane(monthlyTable);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(scrollPane);
    }
}
