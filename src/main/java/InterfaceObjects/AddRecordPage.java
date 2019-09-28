package InterfaceObjects;

import javax.swing.*;
import java.awt.*;

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
}
