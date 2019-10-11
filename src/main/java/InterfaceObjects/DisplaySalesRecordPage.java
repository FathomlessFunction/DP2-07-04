package InterfaceObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplaySalesRecordPage extends JPanel implements ActionListener {

    private JComboBox<Integer> recordSelect;
    private JLabel title, DDLable;
    private JButton edit;
    private Object [][] array;
    private Object [] tmp;

    private EditListener listener;

    public DisplaySalesRecordPage() {

    }

    public DisplaySalesRecordPage(Object [][] salesArray, String length) {
        //this is here for debugging
        array = salesArray;

        Object[][] tmpArray = new Object[array.length][7];
        for (int i = 0; i < array.length; i++) {
            tmpArray[i][0] = array[i][0];
            tmpArray[i][1] = array[i][1];
            tmpArray[i][2] = array[i][2];
            tmpArray[i][3] = array[i][3];
            tmpArray[i][4] = array[i][4];
            tmpArray[i][5] = array[i][5];
            tmpArray[i][6] = array[i][6];
        }

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
        JTable table = new JTable(tmpArray,columnNames);
        //Allows it to be scrollable
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //create scroll with table inside
        JScrollPane scrollPane = new JScrollPane(table);

        edit = new JButton("Edit");
        edit.addActionListener(this);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(title))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(DDLable)
                        .addComponent(recordSelect)
                        .addComponent(edit))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPane))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(title)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(DDLable)
                        .addComponent(recordSelect)
                        .addComponent(edit))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(scrollPane))
        );

        //add scroll
        //add(scrollPane);
    }

    public void setListener(EditListener listener) { this.listener = listener; }

    public void actionPerformed(ActionEvent event) {
        JButton clicked = (JButton)event.getSource();

        if (clicked == edit) {
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
            listener.editClicked(tmp);
        }
    }

}
