import javax.swing.*;

public class Main {

    public static void main(String[] args){
        System.out.println("Hello there Quizmo");

        /** The following resources have been invaluable in learning how to use Swing:
         * Complete Java GUI/Swing Tutorial. Beginner to Expert.
         * (BEWARE, THIS VIDEO IS 5 HOURS LONG. THIS EXAMPLE WAS MADE FROM THE FIRST HOUR WORTH OF TUTORIALS)
         * https://www.youtube.com/watch?v=WRwPVZ4jmNY
         * Visual Guide to Swing Components:
         * https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html
         * Visual Guide to Layout Managers
         * https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
         * Swing API
         * https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html
         */

        //invokeLater is a not 100% necessary, but it is supposed to improve Swing's
        //ability to run without crashing. While maybe not necessary here, it is an absolute
        //requirement when multithreading is involved. Have left it in because it isn't really
        //in our way and should make the program more stable.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUIcontroller();
            }
        });
    }

}
