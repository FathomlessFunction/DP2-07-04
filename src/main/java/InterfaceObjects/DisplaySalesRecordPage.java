package InterfaceObjects;

import DataObjects.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;

public class DisplaySalesRecordPage extends JPanel implements ActionListener {

    private JComboBox<Integer> recordSelect;
    private JLabel title, DDLable, productFilterDisplayText,totalSalesText,totalReturnsText,totalPriceText;
    private JButton editButton;
    private JButton csvButton;
    private Object [][] array;
    private Object [] tmp;
    private Integer totalReturns;
    private Integer totalSales;
    private Double totalPrice;

    private EditListener editListener;
    private CSVListener csvListener;

    public DisplaySalesRecordPage(Object [][] salesArray, String length, String filterString) {
        //this is here for debugging
        array = salesArray;
        //initialise values
        totalSales = array.length;
        totalPrice  = 0.00;
        totalReturns = 0;

        //transfer array to be implemented into swing
        Object[][] tmpArray = new Object[array.length][7];
        for (int i = 0; i < array.length; i++) {
            tmpArray[i][0] = array[i][0];
            tmpArray[i][1] = array[i][1];
            tmpArray[i][2] = array[i][2];
            tmpArray[i][3] = array[i][3];
            tmpArray[i][4] = array[i][4];
            tmpArray[i][5] = array[i][5];
            tmpArray[i][6] = array[i][6];
            //increments if the item has been returned
            if (array[i][6].equals("RETURNED")){
                totalReturns++;
            }
            //adds all the prices together of each sale
            totalPrice = totalPrice + Double.parseDouble(array[i][5].toString());
        }

        //set title of page
        DDLable = new JLabel("EntryID Selection: ");
        if (length == "week"){
            title = new JLabel("Weekly sales record display");
        } else if (length == "month") {
            title = new JLabel("Monthly sales record display");
        } else {
            title = new JLabel("Date Range sales record display");
        }
        //set text for JLabels
        totalSalesText = new JLabel("Total Sales: " + totalSales);
        totalReturnsText = new JLabel("Total Returns: " + totalReturns);
        totalPriceText = new JLabel("Total Price: $" + totalPrice.floatValue());

        if (filterString.equals(Product.getNoProductCat()))
            productFilterDisplayText = new JLabel("No product filter applied");
        else
            productFilterDisplayText = new JLabel("Filtering sales with product category = " + filterString);

        recordSelect = new JComboBox<Integer>();
        for (int i = 0; i < salesArray.length; i++){
            recordSelect.addItem(Integer.parseInt(salesArray[i][0].toString()));
        }

        //sets the column names
        String[] columnNames = {"Entry ID",
                "Sale ID",
                "Product",
                "Amount Sold",
                "Date Of Sale",
                "Total Price",
                "Sale Status"};
        //creates the JTable(Object [][], String[]) and populates it with data
        JTable table = new JTable(tmpArray,columnNames);
        //Allows it to be scrollable
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //create scroll with table inside
        JScrollPane scrollPane = new JScrollPane(table);

        editButton = new JButton("Edit");
        editButton.addActionListener(this);

        csvButton = new JButton("Export to CSV");
        csvButton.addActionListener(this);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(title)
                        .addComponent(totalSalesText)
                        .addComponent(totalReturnsText)
                        .addComponent(totalPriceText))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(productFilterDisplayText))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DDLable)
                        .addComponent(recordSelect)
                        .addComponent(editButton)
                        .addComponent(csvButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPane))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(title)
                        .addComponent(totalSalesText)
                        .addComponent(totalReturnsText)
                        .addComponent(totalPriceText))
                        .addComponent(productFilterDisplayText)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(DDLable)
                        .addComponent(recordSelect)
                        .addComponent(editButton)
                        .addComponent(csvButton))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(scrollPane))
        );
    }

    public void setEditListener(EditListener listener) { this.editListener = listener; }
    public void setCSVListener(CSVListener listener) { this.csvListener = listener; }

    public void actionPerformed(ActionEvent event) {
        JButton clicked = (JButton)event.getSource();

        if (clicked == editButton) {
            tmp = new Object[8];
            for (int i = 0; i < array.length; i++) {
                if (array[i][0].equals(recordSelect.getSelectedItem())){
                    tmp[0] = array[i][0];
                    tmp[1] = array[i][1];
                    tmp[2] = array[i][2];
                    tmp[3] = array[i][3];
                    tmp[4] = array[i][4];
                    tmp[5] = array[i][5];
                    tmp[6] = array[i][6];
                    tmp[7] = array[i][7];
                    break;
                }
            }
            editListener.editClicked(tmp);
        } else if (clicked == csvButton) {
            csvListener.exportCSVClicked();
        }
    }

}
