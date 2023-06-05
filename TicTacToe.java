import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    private JFrame frame;
    private JPanel titlePanel, buttonPanel;
    private JLabel textField;
    private JButton[] buttons;
    private boolean player1Turn;
    private String currentPlayerSymbol;
    private String[] board;

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
        currentPlayerSymbol = "X";
        textField.setText("Player 1 (X) turn");

        board = new String[BOARD_SIZE * BOARD_SIZE];
        for (int i = 0; i < board.length; i++) {
            board[i] = "";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        int buttonIndex = -1;
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == clickedButton) {
                buttonIndex = i;
                break;
            }
        }

        if (buttonIndex != -1 && board[buttonIndex].isEmpty()) {
            board[buttonIndex] = currentPlayerSymbol;
            clickedButton.setText(currentPlayerSymbol);
            clickedButton.setForeground(currentPlayerSymbol.equals("X") ? Color.RED : Color.BLUE);
            clickedButton.setEnabled(false);
            player1Turn = !player1Turn;
            currentPlayerSymbol = player1Turn ? "X" : "O";
            textField.setText("Player " + (player1Turn ? "1 (X)" : "2 (O)") + " turn");

            if (checkForWin()) {
                for (JButton button : buttons) {
                    button.setEnabled(false);
                }
                textField.setText("Player " + (player1Turn ? "1 (X)" : "2 (O)") + " wins!");
            } else if (isBoardFull()) {
                textField.setText("It's a tie!");
            }
        }
    }

    private boolean checkForWin() {
        return checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin();
    }

    private boolean checkRowsForWin() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            int startIndex = i * BOARD_SIZE;
            if (!board[startIndex].isEmpty() && board[startIndex].equals(board[startIndex + 1]) && board[startIndex].equals(board[startIndex + 2])) {
                highlightWinningButtons(startIndex, startIndex + 2);
                return true;
            }
        }
        return false;
    }

    private boolean checkColumnsForWin() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            int startIndex = i;
            if (!board[startIndex].isEmpty() && board[startIndex].equals(board[startIndex + BOARD_SIZE]) && board[startIndex].equals(board[startIndex + 2 * BOARD_SIZE])) {
                highlightWinningButtons(startIndex, startIndex + 2 * BOARD_SIZE);
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalsForWin() {
        int startIndex = 0;
        if (!board[startIndex].isEmpty() && board[startIndex].equals(board[startIndex + BOARD_SIZE + 1]) && board[startIndex].equals(board[startIndex + 2 * BOARD_SIZE + 2])) {
            highlightWinningButtons(startIndex, startIndex + 2 * BOARD_SIZE + 2);
            return true;
        }

        startIndex = BOARD_SIZE - 1;
        if (!board[startIndex].isEmpty() && board[startIndex].equals(board[startIndex + BOARD_SIZE - 1]) && board[startIndex].equals(board[startIndex + 2 * (BOARD_SIZE - 1)])) {
            highlightWinningButtons(startIndex, startIndex + 2 * (BOARD_SIZE - 1));
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (String cell : board) {
            if (cell.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void highlightWinningButtons(int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            buttons[i].setBackground(new Color(0, 255, 0));
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
