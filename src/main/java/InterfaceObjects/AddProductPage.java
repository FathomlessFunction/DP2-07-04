package InterfaceObjects;

import DataObjects.DerbyTableWrapper;
import DataObjects.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddProductPage extends JPanel {

    private JLabel productName;
    private JLabel pricePerUnit;
    private JLabel productCategory;

    private JTextField productNameField;
    private JTextField pricePerUnitField;
    private JTextField productCategoryField;

    private JButton submitButton;

    private FormListenerAddProduct formListener;

    public AddProductPage() {

        int fieldWidth = 10;

        productName = new JLabel("Product Name: ");
        pricePerUnit = new JLabel("Price Per Unit: ");
        productCategory = new JLabel("Product Category: ");

        productNameField = new JTextField(fieldWidth);
        pricePerUnitField = new JTextField(fieldWidth);
        productCategoryField = new JTextField(fieldWidth);

        submitButton = new JButton("Submit");


        /**
         * Similarly to the area for this in the controller, this code is pretty bloated for clarity.
         * I am happy to refactor this later if people would like me to.
         */
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                // no checking of product ID drop down as a product will always be selected
                if (!checkFloat(pricePerUnitField.getText())) {
                    JOptionPane.showMessageDialog(null, "The price per unit must be a number.");
                } else {
                    FormEventAddProduct formEvent = new FormEventAddProduct(this);

                    formEvent.setProductName(productNameField.getText());
                    formEvent.setPricePerUnit(/*Float.valueOf(pricePerUnit.getText())*/(float)69.69);
                    formEvent.setProductCategory(productCategory.getText());

                    if (formListener != null) {
                        formListener.formReceived(formEvent);
                    }

                    productNameField.setText("");
                    pricePerUnitField.setText("");
                    productCategoryField.setText("");
                }
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
        add(productName, bagConstraints);

        bagConstraints.gridy = 2;
        add(pricePerUnit, bagConstraints);

        bagConstraints.gridy = 3;
        add(productCategory, bagConstraints);

        bagConstraints.gridx = 1;
        bagConstraints.gridy = 1;
        bagConstraints.anchor = GridBagConstraints.LINE_START;
        add(productNameField, bagConstraints);

        bagConstraints.gridy = 2;
        add(pricePerUnitField, bagConstraints);

        bagConstraints.gridy = 3;
        add(productCategoryField, bagConstraints);

        bagConstraints.gridy = 8;
        bagConstraints.weighty = 5;
        bagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(submitButton, bagConstraints);

    }

    private boolean checkInt(String toCheck) {
        try {
            Integer.parseInt(toCheck);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean checkFloat(String toCheck) {
        try {
            Float.parseFloat(toCheck);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public void setFormListener(FormListenerAddProduct listener) {
        this.formListener = listener;
    }

}

