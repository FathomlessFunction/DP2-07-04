package DataObjects;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class CSVReportTest {
    CSVReport testReport;

    private List<Sale> getSampleList(){
        List<Sale> toReturn = new LinkedList<Sale>();
        // public Sale(int entryID, String saleID, int productID, String dateOfSaleString,
        //                int numberSold, float amountPaid, String saleStatus){
        Sale toAdd = new Sale(1, "saleID", 1, "12-12-2019",
                12, Float.parseFloat("11.22"), "STATUS1");
        toAdd.setProductCategory("category1");

        toReturn.add(toAdd);

        toAdd = new Sale(2, "saleID2", 2, "13-12-2019",
                17, Float.parseFloat("42.24"), "STATUS2");
        toAdd.setProductCategory("category2");

        toReturn.add(toAdd);

        return toReturn;
    }

    @Test
    public void convertingListToCSVStringWithValidParametersShouldNotThrowError(){
        try{
            CSVReport testReport = new CSVReport(getSampleList());
            testReport.toString();
        } catch (Exception e){
            Assert.fail("got an exception.");
        }

    }

    @Test
    public void shouldSetColumnsBooleanCorrectlyBasedOnInstantiation(){
        // should be whatever we pass into constructor here
        CSVReport testReport = new CSVReport(getSampleList(), false);
        Assert.assertFalse("constructor should have set columntitles to false.", testReport.hasColumnTitles());

    }

    @Test
    public void csvReportGenerationShouldHaveColumnTitlesByDefault(){
        testReport = new CSVReport(getSampleList());
        Assert.assertTrue("csv report should have columntitles by default", testReport.hasColumnTitles());
    }

    @Test
    public void convertingListToCSVStringResultsInCSVStringWithExactParametersFromList(){
        List<Sale> listOfSales = getSampleList();

        testReport = new CSVReport(listOfSales);
        String csvString = testReport.toString();

        // should contain stuff from both records
        Assert.assertTrue(csvString.contains(listOfSales.get(0).getSaleStatus()));
        Assert.assertTrue(csvString.contains(listOfSales.get(1).getSaleStatus()));

        // should contain everything from 1 record
        // NOTE: leaving date format as default until requested otherwise.
        Assert.assertTrue(csvString.contains(listOfSales.get(1).getEntryID() + ""));
        Assert.assertTrue(csvString.contains(listOfSales.get(1).getSaleID()));
        Assert.assertTrue(csvString.contains(listOfSales.get(1).getProductID()+ ""));
        Assert.assertTrue(csvString.contains(listOfSales.get(1).getDateOfSale()+"")); // date format
        Assert.assertTrue(csvString.contains(listOfSales.get(1).getNumberSold()+""));
        Assert.assertTrue(csvString.contains(listOfSales.get(1).getAmountPaid()+""));
        Assert.assertTrue(csvString.contains(listOfSales.get(1).getSaleStatus()+""));
        Assert.assertTrue(csvString.contains(listOfSales.get(1).getProductCategory()));

    }

    @Test
    public void convertingListToCSVStringShouldNotAddWhiteSpaceToSaleParameters(){
        List<Sale> listOfSales = getSampleList();
        testReport = new CSVReport(listOfSales);

        // checking 1 field to check if whitespace has been added
        // logic will loop so this will cover all
        Assert.assertTrue(testReport.toString().contains(
                '"'+listOfSales.get(1).getSaleStatus()+'"')
        );
    }

    @Test
    public void convertingListToCSVResultsInCorrectlyFormedCSVString(){
        // checking the CSV string has delimiters and newlines as expected
        // manual testing will be used to open file to ensure the CSV is well formed and opens
        List<Sale> listOfSales = getSampleList();
        testReport = new CSVReport(listOfSales);

        //System.out.println(testReport.toString()); // here to check output

        // string should be quoted, is checked in previous test though
        Assert.assertTrue(testReport.toString().contains(
                '"'+listOfSales.get(1).getSaleStatus()+'"')
        );

        // should start new line after entry field
        Assert.assertTrue(testReport.toString().contains(testReport.getNewLine()+""));

        // numbers should not be quoted.
        Assert.assertFalse(testReport.toString().contains(listOfSales.get(1).getEntryID()+'"'+""));
        Assert.assertTrue(testReport.toString().contains(listOfSales.get(1).getEntryID()+",")); // assuming entry ID is not at end

    }

    @Test
    public void convertingListToCSVMatchesManuallyBuiltCSVString(){
        // not doing this as this would be bad testing practice
        // and would make code very inflexible (eg if order changed)
    }

    @Test
    public void convertingListToCSVStringWithColumnTitlesShouldResultInCSVWithColumnTitles(){
        testReport = new CSVReport(getSampleList());

        // 'date' string is not contained in dummy data
        // so if it exists in csv string, titles must exist.
        Assert.assertTrue(testReport.toString().toLowerCase().contains("date"));
    }

}