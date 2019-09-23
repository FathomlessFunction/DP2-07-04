import javax.swing.*;

public class Main {
    private static DerbyTableWrapper wrapper;

    public static void main(String[] args){
        System.out.println("Hello there Quizmo");

        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        wrapper.createProductsTable();
        wrapper.createSalesTable();

        // Cody: This is just here for testing, it can be removed
        wrapper.deleteSalesTable();
        wrapper.deleteProductsTable();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceController(wrapper);
            }
        });
    }
}
