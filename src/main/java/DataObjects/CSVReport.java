package DataObjects;

import java.util.List;

/**
 * represents a CSV report of all sales
 * features:
 *      - converts list of Sales to CSV string
 *      - writes to file at specified location
 */
public class CSVReport {
    private boolean hasColumnTitles;
    private static final char delimiter = ','; // cause its a CSV
    private static final char newLine = '\n';
    private static final char quotes = '"';

    List<Sale> salesList;

    private String csvString;

    // by default, will have column titles
    public CSVReport(List<Sale> sales){
        this(sales, true);
    }

    // if you want to not give the csv string column titles, set boolean to false
    public CSVReport(List<Sale> sales, boolean hasColumnTitles){
        this.hasColumnTitles = hasColumnTitles;
        this.salesList = sales;

        convertToCSVString();
    }

    /**
     * generates a string, generated from passed list of sales.
     * to avoid re-calculation, string is generated at instantiation,
     * so don't change the list after creating this object.
     *
     * @return csv string representing list of sales.
     */
    @Override
    public String toString(){
        return csvString;
    }

    /**
     * outputs the csv string representation of passed Sale list
     * to a file, at the path specified
     * @param filePath output file location
     * @return true if successful
     */
    public boolean writeToFile(String filePath){
        return false;
    }

    /**
     * checking whether or not generated CSV has column titles
     * @return true if generated csv will have column names
     */
    public boolean hasColumnTitles(){
        return hasColumnTitles;
    }

    public char getNewLine(){
        return newLine;
    }

    /**
     * converts salesList to a CSV string representing its contents.
     * will have column titles if hasColumnTitles is true
     * stores result in csvString
     */
    protected void convertToCSVString(){

        StringBuilder toReturn = new StringBuilder();

        // put in column titles
        if (hasColumnTitles){
            String[] titles = {
                    "Entry ID",
                    "Sale ID",
                    "Product ID",
                    "Number Sold",
                    "Amount Paid",
                    "Sale Status",
                    "Date of Sale",
                    "Product Category"
            };

            // Product category value will only be populated in product category filter searches, I think. Unsure

            boolean beginning = true;
            for (String title : titles){
                if (!beginning)
                    toReturn.append(delimiter);
                else
                    beginning = false;
                toReturn.append(quotes + title + quotes);
            }
            toReturn.append(newLine);
        }

        // plonk in Sales values
        for (Sale sale : salesList){
            boolean beginning = true;
            for (Object value : sale.getValues()){

                // append delimiter in between values
                if (!beginning)
                    toReturn.append(delimiter);
                else
                    beginning = false;


                if (value instanceof Integer || value instanceof Float){
                    toReturn.append(value);
                }
                else if (value instanceof String)
                    toReturn.append(quotes + (String)value + quotes);

            }
            toReturn.append(newLine);

        }

        // done, store in private field
        csvString = toReturn.toString();
    }
}
