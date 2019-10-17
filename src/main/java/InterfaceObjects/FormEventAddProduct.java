package InterfaceObjects;

import java.util.EventObject;


/**
 * TLDR this class is a custom made listener, which takes fields to move into the database.
 */
public class FormEventAddProduct extends EventObject {

    private int entryID;

    private String productName;
    private float pricePerUnit;
    private String productCategory;

    public FormEventAddProduct(Object source) {
        super(source);
    }

    public int getEntryID(){
        return entryID;
    }

    public void setEntryID(int entryID){
        this.entryID = entryID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(float pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}
