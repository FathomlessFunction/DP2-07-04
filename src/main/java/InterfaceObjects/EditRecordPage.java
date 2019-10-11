package InterfaceObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditRecordPage extends JPanel {
    private JLabel title;

    private JLabel entryIDLable;
    private JLabel saleIDLabel;
    private JLabel productIDLabel;
    private JLabel dateOfSaleLabel;
    private JLabel numberSoldLabel;
    private JLabel amountPaidLabel;
    private JLabel saleStatusLabel;

    private JLabel entryIDField;
    private JLabel saleIDField;
    private JLabel productIDField;
    private JLabel dateOfSaleField;
    private JTextField numberSoldField;
    private JTextField amountPaidField;
    private JTextField saleStatusField;

    private JLabel invisibleProductID;

    private JButton submitButton;

    private FormListener formListener;

    public EditRecordPage() {
        //this is here for debugging
        add(new JLabel(this.getClass().getSimpleName()));
    }

    public EditRecordPage(Object [] sale) {
        title = new JLabel("Edit Page");

        int fieldWidth = 10;

        entryIDLable = new JLabel("Entry ID: ");
        saleIDLabel = new JLabel("Sale ID: ");
        productIDLabel = new JLabel("Product ID: ");
        dateOfSaleLabel = new JLabel("Date of Sale: ");
        numberSoldLabel = new JLabel("Quantity Sold: ");
        amountPaidLabel = new JLabel("Amount Paid: ");
        saleStatusLabel = new JLabel("Sale Status: ");

        System.out.println(sale[1].toString());

        entryIDField = new JLabel(sale[0].toString());
        saleIDField = new JLabel(sale[1].toString());
        productIDField = new JLabel(sale[2].toString());
        dateOfSaleField = new JLabel(sale[4].toString());

        numberSoldField = new JTextField(fieldWidth);
        amountPaidField = new JTextField(fieldWidth);
        saleStatusField = new JTextField(fieldWidth);

        numberSoldField.setText(sale[3].toString());
        amountPaidField.setText(sale[5].toString());
        saleStatusField.setText(sale[6].toString());

        invisibleProductID = new JLabel(sale[7].toString());

        submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                int entryID = Integer.valueOf(entryIDField.getText());
                String saleID = saleIDField.getText();
                int productID = Integer.valueOf(invisibleProductID.getText());
                String dateOfSale = dateOfSaleField.getText();
                int numberSold = Integer.valueOf(numberSoldField.getText());
                float amountPaid = Float.valueOf(amountPaidField.getText());
                String saleStatus = saleStatusField.getText();

                FormEvent formEvent = new FormEvent(this);

                formEvent.setEntryID(entryID);
                formEvent.setSaleID(saleID);
                formEvent.setProductID(productID);
                formEvent.setDateOfSale(dateOfSale);
                formEvent.setNumberSold(numberSold);
                formEvent.setAmountPaid(amountPaid);
                formEvent.setSaleStatus(saleStatus);

                if(formListener !=null) {
                    formListener.formReceived(formEvent);
                }
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints bagConstraints = new GridBagConstraints();

        bagConstraints.weightx = 1;
        bagConstraints.weighty = 0.1;
        bagConstraints.fill = GridBagConstraints.NONE;

        bagConstraints.gridx = 0;
        bagConstraints.gridy = 0;
        //this is here for debugging
        add(new JLabel(this.getClass().getSimpleName()), bagConstraints);

        bagConstraints.anchor = GridBagConstraints.LINE_END;
        bagConstraints.gridy = 1;
        add(entryIDLable, bagConstraints);

        bagConstraints.gridy = 2;
        add(saleIDLabel, bagConstraints);

        bagConstraints.gridy = 3;
        add(productIDLabel, bagConstraints);

        bagConstraints.gridy = 4;
        add(dateOfSaleLabel, bagConstraints);

        bagConstraints.gridy = 5;
        add(numberSoldLabel, bagConstraints);

        bagConstraints.gridy = 6;
        add(amountPaidLabel, bagConstraints);

        bagConstraints.gridy = 7;
        add(saleIDLabel, bagConstraints);

        bagConstraints.gridy = 8;
        add(saleStatusLabel, bagConstraints);

        bagConstraints.gridx = 1;
        bagConstraints.gridy = 1;
        bagConstraints.anchor = GridBagConstraints.LINE_START;
        add(entryIDField, bagConstraints);

        bagConstraints.gridy = 2;
        add(saleIDField, bagConstraints);

        bagConstraints.gridy = 3;
        add(productIDField, bagConstraints);

        bagConstraints.gridy = 4;
        add(dateOfSaleField, bagConstraints);

        bagConstraints.gridy = 5;
        add(numberSoldField, bagConstraints);

        bagConstraints.gridy = 6;
        add(amountPaidField, bagConstraints);

        bagConstraints.gridy = 7;
        add(saleIDField, bagConstraints);

        bagConstraints.gridy = 8;
        add(saleStatusField, bagConstraints);

        bagConstraints.gridy = 9;
        bagConstraints.weighty = 5;
        bagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(submitButton, bagConstraints);
    }

    public void setFormListener(FormListener listener) {
        this.formListener = listener;
    }

}
