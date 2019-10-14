package DataObjects;

/**
 * here to contain the data that we calculate
 * for sale trend prediction
 */
public class Prediction {
    // predicted number of sales
    private int predictedSaleNumber;

    // predicted profit
    private float predictedProfit;

    public Prediction(int predictedSaleNumber, float predictedProfit){
        this.predictedSaleNumber = predictedSaleNumber;
        this.predictedProfit = predictedProfit;
    }

    public int getPredictedNumberOfSales(){
        return predictedSaleNumber;
    }

    public float getPredictedProfit(){
        return predictedProfit;
    }
}
