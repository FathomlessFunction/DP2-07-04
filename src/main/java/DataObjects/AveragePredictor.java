package DataObjects;

import java.util.List;

/**
 * very basic prediction logic
 * just uses averages
 * ie, takes average of last few timespans, and says that is the predicted sale number/profit for the next timespan.
 *
 */
public class AveragePredictor{

    private static final String SOLD_TAG = "SOLD"; // sale status string when it has been sold.


    /**
     *
     * @param sales list of sales data to use for prediction
     * @param timeSpanDays time span you want to predict with. eg 7 days for a weekly prediction
     * @return Prediction
     */
    public static Prediction predict(List<Sale> sales, int timeSpanDays) {
        return null;
    }

    public static Prediction predictWeekly(List<Sale> sales) {
        return null;
    }

    public static Prediction predictMonthly(List<Sale> sales) {
        return null;
    }
}
