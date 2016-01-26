package guessthenumber;

import javax.swing.*;
import screen.ProportionalWindow;


public class GuessTheNumber extends JFrame {
    
    ProportionalWindow propWin;
    double widthProp, heightProp;
    
    public GuessTheNumber() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setTitle("Guess the Number");
        propWin = new ProportionalWindow();
        widthProp = .65;
        heightProp = .81;
        setSize(propWin.getProportionalWidth(widthProp), propWin.getProportionalHeight(heightProp));
        add(new Board(propWin.getProportionalWidth(widthProp), propWin.getProportionalHeight(heightProp)));
        setVisible(true);
    }

   
    public static void main(String[] args) {
        GuessTheNumber bg = new GuessTheNumber();
    }
}
