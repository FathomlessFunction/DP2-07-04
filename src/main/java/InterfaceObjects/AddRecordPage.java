package InterfaceObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRecordPage extends JPanel {

        private JLabel saleIDLabel;
        private JLabel productIDLabel;
        private JLabel dateOfSaleLabel;
        private JLabel numberSoldLabel;
        private JLabel amountPaidLabel;
        private JLabel saleStatusLabel;

        private JTextField saleIDField;
        private JTextField productIDField;
        private JTextField dateOfSaleField;
        private JTextField numberSoldField;
        private JTextField amountPaidField;
        private JTextField saleStatusField;

        private JButton submitButton;

        private FormListener formListener;

    public AddRecordPage() {

        int fieldWidth = 10;

        saleIDLabel = new JLabel("Sale ID: ");
        productIDLabel = new JLabel("Product ID: ");
        dateOfSaleLabel = new JLabel("Date of Sale: ");
        numberSoldLabel = new JLabel("Quantity Sold: ");
        amountPaidLabel = new JLabel("Amount Paid: ");
        saleStatusLabel = new JLabel("Sale Status: ");

        saleIDField = new JTextField(fieldWidth);
        productIDField = new JTextField(fieldWidth);
        dateOfSaleField = new JTextField(fieldWidth);
        numberSoldField = new JTextField(fieldWidth);
        amountPaidField = new JTextField(fieldWidth);
        saleStatusField = new JTextField(fieldWidth);

        submitButton = new JButton("Submit");


        /**
         * Similarly to the area for this in the controller, this code is pretty bloated for clarity.
         * I am happy to refactor this later if people would like me to.
         */
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                String saleID = saleIDField.getText();
                int productID = Integer.valueOf(productIDField.getText());
                String dateOfSale = dateOfSaleField.getText();
                int numberSold = Integer.valueOf(numberSoldField.getText());
                float amountPaid = Float.valueOf(amountPaidField.getText());
                String saleStatus = saleStatusField.getText();

                FormEvent formEvent = new FormEvent(this);

                formEvent.setSaleID(saleID);
                formEvent.setProductID(productID);
                formEvent.setDateOfSale(dateOfSale);
                formEvent.setNumberSold(numberSold);
                formEvent.setAmountPaid(amountPaid);
                formEvent.setSaleStatus(saleStatus);

                if(formListener !=null) {
                    formListener.formReceived(formEvent);
                }

                saleIDField.setText("");
                productIDField.setText("");
                dateOfSaleField.setText("");
                numberSoldField.setText("");
                amountPaidField.setText("");
                saleStatusField.setText("");
            }
        });


        //please excuse how gross this code seems, but it does look very pretty in the gui.
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
        add(saleIDLabel, bagConstraints);

        bagConstraints.gridy = 2;
        add(productIDLabel, bagConstraints);

        bagConstraints.gridy = 3;
        add(dateOfSaleLabel, bagConstraints);

        bagConstraints.gridy = 4;
        add(numberSoldLabel, bagConstraints);

        bagConstraints.gridy = 5;
        add(amountPaidLabel, bagConstraints);

        bagConstraints.gridy = 6;
        add(saleIDLabel, bagConstraints);

        bagConstraints.gridy = 7;
        add(saleStatusLabel, bagConstraints);

        bagConstraints.gridx = 1;
        bagConstraints.gridy = 1;
        bagConstraints.anchor = GridBagConstraints.LINE_START;
        add(saleIDField, bagConstraints);

        bagConstraints.gridy = 2;
        add(productIDField, bagConstraints);

        bagConstraints.gridy = 3;
        add(dateOfSaleField, bagConstraints);

        bagConstraints.gridy = 4;
        add(numberSoldField, bagConstraints);

        bagConstraints.gridy = 5;
        add(amountPaidField, bagConstraints);

        bagConstraints.gridy = 6;
        add(saleIDField, bagConstraints);

        bagConstraints.gridy = 7;
        add(saleStatusField, bagConstraints);

        bagConstraints.gridy = 8;
        bagConstraints.weighty = 5;
        bagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(submitButton, bagConstraints);

    }

    public void setFormListener(FormListener listener) {
        this.formListener = listener;
    }
}
