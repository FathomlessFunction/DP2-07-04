package InterfaceObjects;

import DataObjects.Prediction;
import DataObjects.Sale;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SalesPredictionPage extends JPanel {

    private JLabel dateFilterLabel;
    private JLabel productFilterLabel;
    private JLabel soldLabel;
    private JLabel profitLabel;
    private JLabel predictedSoldLabel;
    private JLabel predictedProfitLabel;

    private JTextField dateFilterOutput;
    private JTextField productFilterOutput;
    private JTextField soldOutput;
    private JTextField profitOutput;
    private JTextField predictedSoldOutput;
    private JTextField predictedProfitOutput;

    private List<Sale> saleList;
    private Prediction prediction;
    private String lengthString;
    private String filterString;

    public SalesPredictionPage(List<Sale> saleList, Prediction prediction, String length, String filter) {

        this.saleList = saleList;
        this.prediction = prediction;
        this.lengthString = length;
        this.filterString = filter;

        int fieldWidth = 20;
        int productsSold = 0;
        float profitsMade = 0;

        for (Sale sale : saleList) {
            if (!sale.getSaleStatus().matches("RETURNED")) {
                productsSold++;
                profitsMade = profitsMade + sale.getAmountPaid();
            }
        }

        dateFilterLabel = new JLabel("Date Filter:");
        productFilterLabel = new JLabel("Product Filter:");
        soldLabel = new JLabel("Products Sold:");
        profitLabel = new JLabel("Profits Made:");
        predictedSoldLabel = new JLabel("Predicted Sales:");
        predictedProfitLabel = new JLabel("Predicted Profit:");

        dateFilterOutput = new JTextField(fieldWidth);
        dateFilterOutput.setText(lengthString);
        dateFilterOutput.setEditable(false);

        productFilterOutput = new JTextField(fieldWidth);
        productFilterOutput.setText(filterString);
        productFilterOutput.setEditable(false);

        soldOutput = new JTextField(fieldWidth);
        soldOutput.setText(Integer.toString(productsSold));
        soldOutput.setEditable(false);

        profitOutput = new JTextField(fieldWidth);
        profitOutput.setText(Float.toString(profitsMade));
        profitOutput.setEditable(false);

        predictedProfitOutput = new JTextField(fieldWidth);
        predictedProfitOutput.setText(Float.toString(prediction.getPredictedProfit()));
        predictedProfitOutput.setEditable(false);

        predictedSoldOutput = new JTextField(fieldWidth);
        predictedSoldOutput.setText(Float.toString(prediction.getPredictedNumberOfSales()));
        predictedSoldOutput.setEditable(false);

        setLayout(new GridBagLayout());
        GridBagConstraints bagConstraints = new GridBagConstraints();

        bagConstraints.weightx = 1;
        bagConstraints.weighty = 0.1;
        bagConstraints.fill = GridBagConstraints.NONE;


        bagConstraints.gridx = 0;
        bagConstraints.anchor = GridBagConstraints.LINE_END;

        bagConstraints.gridy = 0;
        add(dateFilterLabel, bagConstraints);

        bagConstraints.gridy = 1;
        add(productFilterLabel, bagConstraints);

        bagConstraints.gridy = 2;
        add(soldLabel, bagConstraints);

        bagConstraints.gridy = 3;
        add(profitLabel, bagConstraints);

        bagConstraints.gridy = 4;
        add(predictedSoldLabel, bagConstraints);

        bagConstraints.gridy = 5;
        add(predictedProfitLabel, bagConstraints);


        bagConstraints.gridx = 1;
        bagConstraints.anchor = GridBagConstraints.LINE_START;

        bagConstraints.gridy = 0;
        add(dateFilterOutput, bagConstraints);

        bagConstraints.gridy = 1;
        add(productFilterOutput, bagConstraints);

        bagConstraints.gridy = 2;
        add(soldOutput, bagConstraints);

        bagConstraints.gridy = 3;
        add(profitOutput, bagConstraints);

        bagConstraints.gridy = 4;
        add(predictedSoldOutput, bagConstraints);

        bagConstraints.gridy = 5;
        add(predictedProfitOutput, bagConstraints);

        //This is just blank space, it shifts everything else upwards.
        bagConstraints.gridy = 6;
        bagConstraints.weighty = 10;
        add(new JLabel(""), bagConstraints);

    }
}
