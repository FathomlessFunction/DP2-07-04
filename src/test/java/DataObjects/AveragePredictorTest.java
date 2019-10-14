package DataObjects;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class AveragePredictorTest {

    private Sale makeSaleWith(String dateOfSale, int numberSold, String amountPaidFloatString){
        return new Sale("12", 1, dateOfSale, numberSold, Float.parseFloat(amountPaidFloatString), "SOLD");
    }

    private List<Sale> sales;

    @Test
    public void shouldPredictAveragesCorrectly(){

        int numSold1 = 2;
        String price1 = "10";
        int numSold2 = 4;
        String price2 = "30";
        int expectedNumSold = numSold1 + numSold2;
        float expectedProfit = Float.parseFloat(price1) + Float.parseFloat(price2);

        sales = new LinkedList<>();
        sales.add(makeSaleWith("12-12-2019", numSold1, price1));
        sales.add(makeSaleWith("12-12-2019", numSold2, price2));

        // all predictions will use the .predict() logic so this effectively tests all functions.
        // prediction for 1 day
        Prediction result = AveragePredictor.predict(sales, 1);

        Assert.assertEquals(expectedNumSold, result.getPredictedNumberOfSales());
        Assert.assertEquals(expectedProfit, result.getPredictedProfit(), 0.1);


    }

    @Test
    public void shouldPredictAveragesOverMultipleDaysCorrectly(){

        int numSold1 = 2;
        String price1 = "10";
        int numSold2 = 4;
        String price2 = "30";
        int expectedNumSold = numSold1 + numSold2;
        float expectedProfit = Float.parseFloat(price1) + Float.parseFloat(price2);

        sales = new LinkedList<>();
        sales.add(makeSaleWith("11-12-2019", numSold1, price1));
        sales.add(makeSaleWith("12-12-2019", numSold2, price2));

        // prediction for 2 days
        Prediction result = AveragePredictor.predict(sales, 1);
        Assert.assertEquals(3, result.getPredictedNumberOfSales());
        Assert.assertEquals(Float.parseFloat("20"), result.getPredictedProfit(), 0.2);
    }

    @Test
    public void shouldRoundResultsUp(){
        // ie, if predicted number of sales is 3.5, should predict 4
        // because this is used to predict the amount of stock that will be needed.

        int numSold1 = 2;
        String price1 = "10";
        int numSold2 = 3;
        String price2 = "20";
        int expectedNumSold = 3; // 2+3 = 5, over 2 days so average is 2.5. Should be rounded up to 3.
        float expectedProfit = (Float.parseFloat(price1) + Float.parseFloat(price2))/2;

        sales = new LinkedList<>();
        sales.add(makeSaleWith("13-12-2019", numSold1, price1));
        sales.add(makeSaleWith("12-12-2019", numSold2, price2));

        Prediction result = AveragePredictor.predict(sales, 1);

        Assert.assertEquals(expectedNumSold, result.getPredictedNumberOfSales());
        Assert.assertEquals(expectedProfit, result.getPredictedProfit(), 0.1);
    }

    @Test
    public void shouldNotCountReturnedProductsInPrediction(){

        int numSold1 = 2;
        String price1 = "10";
        int numSold2 = 3;
        String price2 = "20";
        int expectedNumSold = numSold1 / 2; // sale 2 will be returned so should not be used in prediction
        float expectedProfit = Float.parseFloat(price1)/2;

        sales = new LinkedList<>();
        sales.add(makeSaleWith("13-12-2019", numSold1, price1));

        Sale sale2 = makeSaleWith("12-12-2019", numSold2, price2);
        sale2.setSaleStatus("RETURNED");
        sales.add(sale2);

        Prediction result = AveragePredictor.predict(sales, 1);

        Assert.assertEquals(expectedNumSold, result.getPredictedNumberOfSales());
        Assert.assertEquals(expectedProfit, result.getPredictedProfit(), 0.1);
    }
}
