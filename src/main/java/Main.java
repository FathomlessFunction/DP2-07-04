import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void displaySalesPage (JPanel panel){
        JLabel title = new JLabel("Display Sales Record");
        title.setBounds(170,50,300,55);
        panel.add(title);
    }

    public static void predictSalesPage (JPanel panel){
        JLabel title = new JLabel("Predict Sales Record");
        title.setBounds(170,50,300,55);
        panel.add(title);
    }

    public static void addSalesPage (JPanel panel){
        JLabel title = new JLabel("Add Sales Record");
        title.setBounds(170,50,300,55);
        panel.add(title);
    }

    public static void homePage(JPanel panel){
        JLabel title = new JLabel("People Health Pharmacy Inc.");
        title.setBounds(170,50,300,55);
        panel.add(title);

        JButton AddButton = new JButton("Add Sales Record");
        Color ButtonColor = new Color(0,102,0);
        AddButton.setBounds(150,120,200, 55);
        AddButton.setBackground(ButtonColor);
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JButton clicked = (JButton) actionEvent.getSource();
                if(clicked == AddButton){
                    Main.newWindow("add");
                    Main.addSalesPage(panel);
                }
            }
        });
        panel.add(AddButton);

        JButton DisplayButton = new JButton("Display Sales Record");
        DisplayButton.setBounds(150,200,200, 55);
        DisplayButton.setBackground(ButtonColor);
        DisplayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JButton clicked = (JButton) actionEvent.getSource();
                if(clicked == DisplayButton){
                    Main.newWindow("display");
                    Main.displaySalesPage(panel);
                }
            }
        });
        panel.add(DisplayButton);

        JButton PredictButton = new JButton("Predict Sales Record");
        PredictButton.setBounds(150,280,200, 55);
        PredictButton.setBackground(ButtonColor);
        PredictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JButton clicked = (JButton) actionEvent.getSource();
                if(clicked == PredictButton){
                    Main.newWindow("predict");
                    Main.predictSalesPage(panel);
                }
            }
        });
        panel.add(PredictButton);
    }

    public static void newWindow(String title){
        JFrame window = new JFrame(title);
        window.setVisible(true);
        window.setSize(500,500);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        Color background = new Color(0,153,0);
        panel.setBackground(background);
        window.add(panel);
        if(title == "home"){
            Main.homePage(panel);
        }
        if(title == "add"){
            Main.addSalesPage(panel);
        }
        if(title == "predict"){
            Main.predictSalesPage(panel);
        }
        if(title == "display"){
            Main.displaySalesPage(panel);
        }
    }

    public static void main(String[] args){
        System.out.println("Hello there Quizmo");

        Main.newWindow("home");
    }
}



