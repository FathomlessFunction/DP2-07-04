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
    private char delimiter = ','; // cause its a CSV
    private char newLine = '\n';

    // by default, will have column titles
    public CSVReport(List<Sale> sales){
        this(sales, true);
    }

    // if you want to not give the csv list titles (handy for testing)
    public CSVReport(List<Sale> sales, boolean hasColumnTitles){

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
        return null;
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

    public void setNewLine(char newLine){
        this.newLine = newLine;
    }
}
