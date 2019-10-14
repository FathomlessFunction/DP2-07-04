package DataObjects;

import java.util.List;

public interface IPredictTrends {

    // returns prediction based off of passed list of sales.
    public Prediction predict(List<Sale> sales, int timeSpanDays);

    // timeSpanDays = 7
    public Prediction predictWeekly(List<Sale> sales);

    // timeSpanDays = 30
    public Prediction predictMonthly(List<Sale> sales);
}
