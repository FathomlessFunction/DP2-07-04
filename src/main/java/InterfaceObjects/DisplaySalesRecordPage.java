package InterfaceObjects;

import DataObjects.Product;
import DataObjects.Sale;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DisplaySalesRecordPage extends JPanel {

    private JComboBox<Integer> recordSelect;
    private JLabel title, DDLable;

    public DisplaySalesRecordPage(Object [][] salesArray, String length) {
        //this is here for debugging
        DDLable = new JLabel("EntryID Selection: ");
        if (length == "week"){
            title = new JLabel("Weekly sales record display");
        } else {
            title = new JLabel("Monthly sales record display");
        }

        recordSelect = new JComboBox<Integer>();
        for (int i = 0; i < salesArray.length; i++){
            recordSelect.addItem(Integer.parseInt(salesArray[i][0].toString()));
        }

        //add(recordSelect);

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
        JTable table = new JTable(salesArray,columnNames);
        //Allows it to be scrollable
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //create scroll with table inside
        JScrollPane scrollPane = new JScrollPane(table);

        GroupLayout layout = new GroupLayout(this);
        setLayout(new GroupLayout(this));

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                    .addComponent(title)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(title)
        );

        //add scroll
        //add(scrollPane);
    }
}
