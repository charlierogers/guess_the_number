package guessthenumber;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import num.RandomInteger;
import screen.Dim;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {
    
    int panelWidth, panelHeight;
    JButton resetButton, guessButton;
    JLabel directions;
    JLabel guessLabel, firstDigitLabel, secondDigitLabel, thirdDigitLabel, digitsCorrectLabel, digitsCorrectPlaceLabel;
    JLabel guessNumberLabel;
    JLabel digitsCorrectDisplay, digitsCorrectPlaceDisplay;
    JTextField guessEntries[] = new JTextField [3];
    JTextField testField;
    ArrayList oldJTextFields, oldJLabels;
    Dim dim;
    RandomInteger randomInt;
    int digits[] = new int [3];
    int guessNumber;
    int digitsCorrect, digitsCorrectPlace;
    int textFieldYPos;
    
    public Board(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        setSize(panelWidth, panelHeight);
        setBackground(new Color(204, 229, 255));
        setFocusable(true);
        setDoubleBuffered(true);
        setLayout(null);
        dim = new Dim(panelWidth, panelHeight, 832, 648);
        randomInt = new RandomInteger();
        oldJTextFields = new ArrayList();
        oldJLabels = new ArrayList();
        
        
        //add resetButton
        resetButton = new JButton("Generate New Random Number");
        resetButton.addActionListener(this);
        resetButton.setSize(dim.adjW(250), dim.adjH(30));
        resetButton.setLocation((panelWidth / 2 - dim.adjW(125)), dim.adjH(100));
        add(resetButton);
        
        //add directions 
        directions = new JLabel("<html>Directions: In this game, a random three digit number is generated. "
                + "Each digit is a number from 1 to 9, and there are no repeated digits. "
                + "Enter guesses for each digit, and the game will tell you how many digits are correct"
                + " and how many digits are in the correct place. Try to guess the three digit number in "
                + "5 or fewer guesses based on this information.</html>");
        directions.setSize(dim.adjW(700), dim.adjH(60));
        directions.setLocation(dim.adjW(30), dim.adjH(10));
        add(directions);
        
        //add guessLabel
        guessLabel = new JLabel("Guess #");
        guessLabel.setSize(dim.adjW(50), dim.adjH(30));
        guessLabel.setLocation(dim.adjW(30), dim.adjH(150));
        add(guessLabel);
        
        //add firstDigitLabel
        firstDigitLabel = new JLabel("1st Digit");
        firstDigitLabel.setSize(dim.adjW(50), dim.adjH(30));
        firstDigitLabel.setLocation(dim.adjW(110), dim.adjH(150));
        add(firstDigitLabel);
        
        //add secondDigitLabel
        secondDigitLabel = new JLabel("2nd Digit");
        secondDigitLabel.setSize(dim.adjW(50), dim.adjH(30));
        secondDigitLabel.setLocation(dim.adjW(190), dim.adjH(150));
        add(secondDigitLabel);
        
        //add thirdDigitLabel
        thirdDigitLabel = new JLabel("3rd Digit");
        thirdDigitLabel.setSize(dim.adjW(50), dim.adjH(30));
        thirdDigitLabel.setLocation(dim.adjW(270), dim.adjH(150));
        add(thirdDigitLabel);
        
        //add digitsCorrectLabel
        digitsCorrectLabel = new JLabel("# of Digits Correct");
        digitsCorrectLabel.setSize(dim.adjW(110), dim.adjH(30));
        digitsCorrectLabel.setLocation(dim.adjW(350), dim.adjH(150));
        add(digitsCorrectLabel);
        
        //add digitsCorrectPlaceLabel
        digitsCorrectPlaceLabel = new JLabel("# of Digits in Correct Place");
        digitsCorrectPlaceLabel.setSize(dim.adjW(150), dim.adjH(30));
        digitsCorrectPlaceLabel.setLocation(dim.adjW(490), dim.adjH(150));
        add(digitsCorrectPlaceLabel);
        
        //add guessNumberLabel
        guessNumberLabel = new JLabel();
        guessNumberLabel.setSize(dim.adjW(50), dim.adjH(30));
        add(guessNumberLabel);
        
        //add guessEntries elements
        for (int i = 0; i < guessEntries.length; i++) {
            guessEntries[i] = new JTextField();
            guessEntries[i].setSize(dim.adjW(50), dim.adjH(30));
            guessEntries[i].addKeyListener(new Keys());
            add(guessEntries[i]);
        }
        
        //test text field
        testField = new JTextField();
        testField.setLocation(200, 300);
        testField.setSize(60, 25);
        add(testField);
        
        //add digitsCorrectDisplay
        digitsCorrectDisplay = new JLabel();
        digitsCorrectDisplay.setSize(dim.adjW(50), dim.adjH(30));
        add(digitsCorrectDisplay);
        
        //add digitsCorrectPlaceDisplay
        digitsCorrectPlaceDisplay = new JLabel();
        digitsCorrectPlaceDisplay.setSize(dim.adjW(50), dim.adjH(30));
        add(digitsCorrectPlaceDisplay);
        
        //add guessButton
        guessButton = new JButton("Enter Guess");
        guessButton.setSize(dim.adjW(120), dim.adjH(30));
        guessButton.addActionListener(this);
        add(guessButton);
        
        reset();
    }
    
    private void reset() {
        generateRandomDigits();
        guessNumber = 1;
        
        //clear out old entries
        if (!oldJLabels.isEmpty()) {
            for (int i = 0; i < oldJLabels.size(); i++) {
                remove((JLabel) oldJLabels.get(i));
            }
            oldJLabels.clear();
        }
        if (!oldJTextFields.isEmpty()) {
            for (int i = 0; i < oldJTextFields.size(); i++) {
                remove((JTextField) oldJTextFields.get(i));
            }
            oldJTextFields.clear();
        }
        
        //set up live entry row in default position and make sure labels and text fields are blank
        textFieldYPos = 190;
        guessNumberLabel.setLocation(dim.adjW(50), dim.adjH(textFieldYPos));
        guessNumberLabel.setText(Integer.toString(guessNumber));
        for (int i = 0; i < guessEntries.length; i++) {
            guessEntries[i].setLocation(dim.adjW(110 + i * 80), dim.adjH(textFieldYPos));
            guessEntries[i].setBackground(Color.white);
            guessEntries[i].setText(null);
            guessEntries[i].setEditable(true);
        }
        digitsCorrectDisplay.setLocation(dim.adjW(400), dim.adjH(textFieldYPos));
        digitsCorrectDisplay.setText(null);
        digitsCorrectPlaceDisplay.setLocation(dim.adjW(560), dim.adjH(textFieldYPos));
        digitsCorrectPlaceDisplay.setText(null);
        guessButton.setLocation(dim.adjW(670), dim.adjH(textFieldYPos));
    }
    
    private void generateRandomDigits() {
        digits[0] = randomInt.getRandomInteger(1, 9);
        digits[1] = randomInt.getRandomInteger(1, 9);
        digits[2] = randomInt.getRandomInteger(1, 9);
        checkForOverlap();  //make sure none of the random digits are the same
    }
    
    private void checkForOverlap() {
        if (digits[0] == digits[1] || digits[0] == digits[2] || digits[1] == digits[2]) {
            generateRandomDigits();
        }
    }
    
    private void scoreGuesses() {
        digitsCorrect = 0;
        digitsCorrectPlace = 0;
        boolean guessesValid = false;
        int guesses[] = new int [3];
        try {
            guesses[0] = Integer.parseInt(guessEntries[0].getText());
        } catch (NumberFormatException e) {
            guessEntries[0].setBackground(Color.red);
        }
        try {
            guesses[1] = Integer.parseInt(guessEntries[1].getText());
        } catch (NumberFormatException e) {
            guessEntries[1].setBackground(Color.red);
        }
        try {
            guesses[2] = Integer.parseInt(guessEntries[2].getText());
        } catch (NumberFormatException e) {
            guessEntries[2].setBackground(Color.red);
        }
        
        //make sure all entry fields are white, not red, at beginning of validity tests
        for (int i = 0; i < guessEntries.length; i++) {
            guessEntries[i].setBackground(Color.white);
        }
        
        //perform two tests to check validity of entries
        int numPassedTestOne = 0;
        int numPassedTestTwo = 0;
        
        //see if guess values are valid - test 2 (must be 1 through 9 test)
        for (int i = 0; i < guesses.length; i++) {
            if (guesses[i] >= 1 && guesses[i] <= 9) {
                numPassedTestOne++;
            } else {
                guessEntries[i].setBackground(Color.red);
            }
        }
        
        //see if guess values are valid - test 3 (must be three different integers)
        for (int i = 0; i < guesses.length; i++) {
            for (int j = 0; j < guesses.length; j++) {
                if (i != j) {
                    if (guesses[i] != guesses[j]) {
                        numPassedTestTwo++;
                    } else {
                        guessEntries[i].setBackground(Color.red);
                        guessEntries[j].setBackground(Color.red);
                    }
                }
            }
        }
        
        //make final validity decision
        if (numPassedTestOne == 3 && numPassedTestTwo == 6) {
            guessesValid = true;
        }
        
        if (guessesValid) {
            for (int i = 0; i < digits.length; i++) {
                for (int j = 0; j < guesses.length; j++) {
                    if (digits[i] == guesses[j]) {
                        digitsCorrect++;
                        if (i == j) {
                            digitsCorrectPlace++;
                        }
                    }
                }
            }
            digitsCorrectDisplay.setText(Integer.toString(digitsCorrect));
            digitsCorrectPlaceDisplay.setText(Integer.toString(digitsCorrectPlace));
            setUpNextEntryRow();
        }
    }
    
    private void setUpNextEntryRow() {
        if (digitsCorrectPlace == 3) {  
            //player guessed the correct number
            for (int i = 0; i < guessEntries.length; i++) {
                guessEntries[i].setBackground(Color.green);
            }
        } else {
            //player didn't guess the correct number
            guessNumber++;
            
            if (guessNumber > 10) {
                for (int i = 0; i < guessEntries.length; i++) {
                    guessEntries[i].setBackground(Color.red);
                    guessEntries[i].setEditable(false);
                }
                for (int i = 0; i < oldJLabels.size(); i++) {
                    JLabel temp = (JLabel) oldJLabels.get(i);
                    temp.setBackground(Color.red);
                }
                for (int i = 0; i < oldJTextFields.size(); i++) {
                    JTextField temp = (JTextField) oldJTextFields.get(i);
                    temp.setBackground(Color.red);
                }
            } else {
                //move everything one row down
                textFieldYPos += 40;

                //move guessNumberLabel down and replace with new
                JLabel tempGuessNumberLabel;
                tempGuessNumberLabel = new JLabel(guessNumberLabel.getText());
                tempGuessNumberLabel.setSize(guessNumberLabel.getSize());
                tempGuessNumberLabel.setLocation(guessNumberLabel.getLocation());
                add(tempGuessNumberLabel);
                oldJLabels.add(tempGuessNumberLabel);
                guessNumberLabel.setLocation(dim.adjW(50), dim.adjH(textFieldYPos));
                guessNumberLabel.setText(Integer.toString(guessNumber));

                //move guessEntries down and replace with new
                for (int i = 0; i < guessEntries.length; i++) {
                    JTextField tempGuessEntries;
                    tempGuessEntries = new JTextField(guessEntries[i].getText());
                    tempGuessEntries.setSize(guessEntries[i].getSize());
                    tempGuessEntries.setLocation(guessEntries[i].getLocation());
                    tempGuessEntries.setEditable(false);
                    add(tempGuessEntries);
                    oldJTextFields.add(tempGuessEntries);
                    guessEntries[i].setLocation(dim.adjW(110 + i * 80), dim.adjH(textFieldYPos));
                    guessEntries[i].setText(null);
                }

                //move digitsCorrectDisplay down and replace with new
                JLabel tempDigitsCorrectDisplay;
                tempDigitsCorrectDisplay = new JLabel(digitsCorrectDisplay.getText());
                tempDigitsCorrectDisplay.setSize(digitsCorrectDisplay.getSize());
                tempDigitsCorrectDisplay.setLocation(digitsCorrectDisplay.getLocation());
                add(tempDigitsCorrectDisplay);
                oldJLabels.add(tempDigitsCorrectDisplay);
                digitsCorrectDisplay.setLocation(dim.adjW(400), textFieldYPos);
                digitsCorrectDisplay.setText(null);

                //move digitsCorrectPlaceDisplay down and replace with new
                JLabel tempDigitsCorrectPlaceDisplay;
                tempDigitsCorrectPlaceDisplay = new JLabel(digitsCorrectPlaceDisplay.getText());
                tempDigitsCorrectPlaceDisplay.setSize(digitsCorrectPlaceDisplay.getSize());
                tempDigitsCorrectPlaceDisplay.setLocation(digitsCorrectPlaceDisplay.getLocation());
                add(tempDigitsCorrectPlaceDisplay);
                oldJLabels.add(tempDigitsCorrectPlaceDisplay);
                digitsCorrectPlaceDisplay.setLocation(dim.adjW(560), dim.adjH(textFieldYPos));
                digitsCorrectPlaceDisplay.setText(null);

                //move guessButton down
                guessButton.setLocation(dim.adjW(670), dim.adjH(textFieldYPos));

                guessEntries[0].grabFocus();
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equals("Generate New Random Number")) {
            reset();
        }
        
        if (e.getActionCommand().equals("Enter Guess")) {
            scoreGuesses();
        }
    }
    
    private class Keys implements KeyListener {
        
        public Keys() {
            
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                scoreGuesses();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {
            
        }
    }
}
