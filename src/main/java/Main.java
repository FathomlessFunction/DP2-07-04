public class Main {
    private static DerbyTableWrapper wrapper;

    public static void main(String[] args){
        System.out.println("Hello there Quizmo");

        DerbyTableWrapper wrapper = new DerbyTableWrapper();
        wrapper.createSalesTable();

    }
}
