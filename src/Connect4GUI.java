import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connect4GUI extends JFrame {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private JButton[][] buttons;

    public Connect4GUI() {
        setTitle("Connect 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(ROWS + 1, COLS));

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

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void resetBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setBackground(Color.WHITE);
                buttons[row][col].setEnabled(true);
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
            dropDisc(col);
        }
    }

    private void dropDisc(int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (buttons[row][col].getText().equals("")) {
                buttons[row][col].setText("X");
                buttons[row][col].setBackground(Color.BLUE);
                buttons[row][col].setEnabled(false);
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Connect4GUI());
    }
}