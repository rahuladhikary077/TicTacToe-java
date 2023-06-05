import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    private JFrame frame;
    private JPanel titlePanel, buttonPanel;
    private JLabel textField;
    private JButton[] buttons;
    private boolean player1Turn;
    
    private static final int BOARD_SIZE = 3;

    TicTacToe() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        
        textField = new JLabel();
        textField.setBackground(new Color(25, 25, 25));
        textField.setForeground(new Color(25, 255, 0));
        textField.setFont(new Font("Ink Free", Font.BOLD, 32));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Tic-Tac-Toe");
        textField.setOpaque(true);
        
        titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 400, 100);
        titlePanel.add(textField);
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        
        buttons = new JButton[BOARD_SIZE * BOARD_SIZE];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }
        
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        
        initializeGame();
        
        frame.setVisible(true);
    }

    private void initializeGame() {
        player1Turn = true;
        textField.setText("Player 1 (X) turn");
        
        for (JButton button : buttons) {
            button.setEnabled(true);
            button.setText("");
            button.setBackground(new Color(238, 238, 238));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        
        if (player1Turn) {
            clickedButton.setText("X");
            clickedButton.setForeground(new Color(255, 0, 0));
            textField.setText("Player 2 (O) turn");
        } else {
            clickedButton.setText("O");
            clickedButton.setForeground(new Color(0, 0, 255));
            textField.setText("Player 1 (X) turn");
        }
        
        clickedButton.setEnabled(false);
        player1Turn = !player1Turn;
        
        checkForWin();
    }
    
    private void checkForWin() {
        // Check rows
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (checkRow(i))
                return;
        }
        
        // Check columns
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (checkColumn(i))
                return;
        }
        
        // Check diagonals
        if (checkDiagonal(true) || checkDiagonal(false))
            return;
        
        // Check for a tie
        if (checkForTie())
            return;
    }
    
    private boolean checkRow(int row) {
        int startIndex = row * BOARD_SIZE;
        String symbol = buttons[startIndex].getText();
        
        if (symbol.isBlank())
            return false;
        
        for (int i = startIndex + 1; i < startIndex + BOARD_SIZE; i++) {
            if (!buttons[i].getText().equals(symbol))
                return false;
        }
        
        highlightWinningButtons(startIndex, startIndex + BOARD_SIZE - 1);
        announceWinner(symbol);
        
        return true;
    }
    
    private boolean checkColumn(int column) {
        int startIndex = column;
        String symbol = buttons[startIndex].getText();
        
        if (symbol.isBlank())
            return false;
        
        for (int i = startIndex + BOARD_SIZE; i < BOARD_SIZE * BOARD_SIZE; i += BOARD_SIZE) {
            if (!buttons[i].getText().equals(symbol))
                return false;
        }
        
        highlightWinningButtons(startIndex, startIndex + BOARD_SIZE * (BOARD_SIZE - 1));
        announceWinner(symbol);
        
        return true;
    }
    
    private boolean checkDiagonal(boolean isMainDiagonal) {
        int startIndex = isMainDiagonal ? 0 : BOARD_SIZE - 1;
        int increment = isMainDiagonal ? BOARD_SIZE + 1 : BOARD_SIZE - 1;
        String symbol = buttons[startIndex].getText();
        
        if (symbol.isBlank())
            return false;
        
        for (int i = startIndex + increment; i < BOARD_SIZE * BOARD_SIZE; i += increment) {
            if (!buttons[i].getText().equals(symbol))
                return false;
        }
        
        highlightWinningButtons(startIndex, startIndex + increment * (BOARD_SIZE - 1));
        announceWinner(symbol);
        
        return true;
    }
    
    private boolean checkForTie() {
        for (JButton button : buttons) {
            if (button.getText().isBlank())
                return false;
        }
        
        announceWinner("Tie");
        
        return true;
    }
    
    private void highlightWinningButtons(int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            buttons[i].setBackground(new Color(0, 255, 0));
        }
        
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }
    
    private void announceWinner(String symbol) {
        if (symbol.equals("Tie")) {
            textField.setText("It's a tie!");
        } else {
            textField.setText(symbol + " wins!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToe();
            }
        });
    }
}
