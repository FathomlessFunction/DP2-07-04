package DataObjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
     * Use this if you want to predict with a specific time span
     * eg   if you want a prediction for 28-day long months
     *      or for a variable date range and length.
     *
     * @param sales list of sales data to use for prediction
     * @param timeSpanDays time span you want to predict with. eg 7 days for a weekly prediction.
     * 
     * @return Prediction
     */
    public static Prediction predict(List<Sale> sales, int timeSpanDays) {
        Integer salesTotal = 0;
        Double profitTotal = 0.0;

        Date earliestDate = null;
        Date latestDate = null;

        // loop through all sales, add up amount sold and total profit for each separate day.
        for (Sale sale : sales){

            // check if date is earlier or later than any seen previously and store if true.
            if (earliestDate == null){
                earliestDate = sale.getDateOfSale();
                latestDate = sale.getDateOfSale();
            } else {
                if (sale.getDateOfSale().after(latestDate))
                    latestDate = sale.getDateOfSale();
                if (sale.getDateOfSale().before(earliestDate))
                    earliestDate = sale.getDateOfSale();
            }

            // new total profit
            if (sale.getSaleStatus().equals(SOLD_TAG)){
                salesTotal += sale.getNumberSold();
                profitTotal += sale.getAmountPaid();

            }

        }

        // calculate how many 'date ranges' occurred in provided sale data range
        // math.ceil to round up.
        int daysBetween = (int) Math.ceil(ChronoUnit.DAYS.between(
                LocalDate.parse(earliestDate.toString()), LocalDate.parse(latestDate.toString()))) + 1;
        

        // average daily profit = total / daysBetween
        // profit for a timespan = days per timespan * daily profit

        // math.ceil to round up
        // cast to float to make the calculation export a float, which can then be rounded up
        int numberSoldPrediction = (int) Math.ceil((float)salesTotal / daysBetween * timeSpanDays);

        double profitPrediction = profitTotal / daysBetween * timeSpanDays;

        // round profit to be 2 decimal places
        BigDecimal bd = new BigDecimal(profitPrediction);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        return new Prediction(numberSoldPrediction, bd.floatValue());
    }

    public static Prediction predictWeekly(List<Sale> sales) {
        return predict(sales, 7);
    }

    public static Prediction predictMonthly(List<Sale> sales) {
        return predict(sales, 30);
    }
}
