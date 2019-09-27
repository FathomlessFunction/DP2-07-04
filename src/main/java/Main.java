import DataObjects.Product;
import DataObjects.Sale;

import javax.swing.*;

public class Main {
    private static DerbyTableWrapper wrapper;

    public static void main(String[] args){
        System.out.println("Hello there Quizmo");

        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        wrapper.createProductsTable();
        wrapper.createSalesTable();

        //test input data
        Product testProd = new Product("fish", Float.parseFloat("4.23"), "food");
        Sale testSale1 = new Sale("3",1,"06-11-2000", 2,Float.parseFloat("12.3"),"PROCESSED");
        Sale testSale2 = new Sale("3",1,"11-11-2000", 2,Float.parseFloat("12.3"),"PROCESSED");
        Sale testSale3 = new Sale("63",1,"16-11-2000", 2,Float.parseFloat("12.3"),"PROCESSED");
        Sale testSale4 = new Sale("63",1,"22-11-2000", 2,Float.parseFloat("12.3"),"PROCESSED");
        Sale testSale5 = new Sale("63",1,"28-11-2000", 2,Float.parseFloat("12.3"),"PROCESSED");
        Sale testSale6 = new Sale("63",1,"02-12-2000", 2,Float.parseFloat("12.3"),"PROCESSED");
        Sale testSale7 = new Sale("63",1,"08-12-2000", 2,Float.parseFloat("12.3"),"PROCESSED");
        Sale testSale8 = new Sale("63",1,"13-12-2000", 2,Float.parseFloat("12.3"),"PROCESSED");

        //adds data to wrapper
        wrapper.addProduct(testProd);
        wrapper.addSale(testSale1);
        wrapper.addSale(testSale2);
        wrapper.addSale(testSale3);
        wrapper.addSale(testSale4);
        wrapper.addSale(testSale5);
        wrapper.addSale(testSale6);
        wrapper.addSale(testSale7);
        wrapper.addSale(testSale8);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceController(wrapper);
            }
        });

        // Cody: This is just here for testing, it can be removed
        //wrapper.deleteSalesTable();
        //wrapper.deleteProductsTable();
    }
}
