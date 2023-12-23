import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Connect4 extends JFrame {
    public static int[][] gameBoard;
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private JButton[][] buttons;
    JLabel winner = new JLabel();
    AI ai = new AI();


    public Connect4() {
        gameBoard = new int[ROWS][COLS];
        setTitle("Connect 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(ROWS+1, COLS));


        buttons = new JButton[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setBackground(Color.WHITE);
                buttons[row][col].setEnabled(true);
                buttons[row][col].addActionListener(new ButtonClickListener(col));
                add(buttons[row][col]);

            }
        }

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBoard();
            }
        });
        add(resetButton);
        add(winner);
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void resetBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setBackground(Color.WHITE);
                buttons[row][col].setEnabled(true);
                gameBoard = new int[ROWS][COLS];
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int col;

        public ButtonClickListener(int col) {
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!GameManager.isGameOver(gameBoard,-1) && !GameManager.isGameOver(gameBoard,1)){
                dropDisc(col,1);
            }
            if (!GameManager.isGameOver(gameBoard,-1) && !GameManager.isGameOver(gameBoard,1)){
                //dropDisc(ai.pickBestMove(-1),-1);
                dropDisc(ai.minimax(gameBoard,6,true).get("column"),-1);
            }
            if (GameManager.isGameOver(gameBoard,-1) || GameManager.isGameOver(gameBoard,1)){
                resetBoard();
            }
        }
    }

    private void dropDisc(int col, int piece) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (gameBoard[row][col]==0) {
                buttons[row][col].setText(Integer.toString(piece));
                gameBoard[row][col] = piece;
                if (piece == 1){
                    buttons[row][col].setBackground(Color.BLUE);

                }
                else if(piece==-1){
                    buttons[row][col].setBackground(Color.RED);

                }
                buttons[row][col].setEnabled(false);
                break;
            }
        }
        if (GameManager.isGameOver(gameBoard,piece)){
            System.out.println("GAME OVER");
            if (piece == 1){
                winner.setText("Blue Wins");
                winner.setForeground(Color.BLUE);
            }
            else {
                winner.setText("Red Wins");
                winner.setForeground(Color.RED);
            }
            JOptionPane.showMessageDialog(null, winner);

        }
    }

}