package InterfaceObjects;

import java.util.EventObject;


/**
 * TLDR this class is a custom made listener, which takes fields to move into the database.
 */
public class FormEvent extends EventObject {

    private int entryID;

    private String saleID;
    private int productID;
    private String dateOfSale;
    private int numberSold;
    private float amountPaid;
    private String saleStatus;

    public FormEvent(Object source) {
        super(source);
    }

    public int getEntryID(){
        return entryID;
    }

    public void setEntryID(int entryID){
        this.entryID = entryID;
    }

    public String getSaleID() {
        return saleID;
    }

    public void setSaleID(String saleID) {
        this.saleID = saleID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(String dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public int getNumberSold() {
        return numberSold;
    }

    public void setNumberSold(int numberSold) {
        this.numberSold = numberSold;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }
}
