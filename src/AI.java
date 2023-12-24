import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class AI {

    public int pickBestMove(int piece) {
        Random random = new Random();
        int bestCol = random.nextInt(Connect4.gameBoard[0].length);
        int bestScore = Integer.MIN_VALUE;
        for (int colNum : validCols(Connect4.gameBoard)) {
            int[][] boardCopy = cloneArray(Connect4.gameBoard);
            dropDiscToCopy(colNum, piece, boardCopy);
            int score = scorePosition(piece, boardCopy);
            if (score > bestScore) {
                bestScore = score;
                bestCol = colNum;
            }
        }
        return bestCol;
    }

    private int scorePosition(int piece, int[][] boardCopy) {
        int rowCount = boardCopy.length;
        int columnCount = boardCopy[0].length;
        int windowLength = 4;

        int score = 0;

        //Center is better. Look at center column
        int[] centerCol = new int[boardCopy.length];
        for (int i = 0; i < boardCopy.length; i++) {
            centerCol[i] = boardCopy[i][boardCopy[0].length / 2];
        }
        score += 8 * countOccurrences(centerCol, piece);

        //horizontal
        for (int rowctr = 0; rowctr < rowCount; rowctr++) {
            int[] rowArray = boardCopy[rowctr];
            if (Arrays.stream(rowArray).allMatch(value -> value == 0)) {
                continue;
            }
            for (int colCtr = 0; colCtr <= columnCount - 4; colCtr++) {
                int[] window = Arrays.copyOfRange(rowArray, colCtr, colCtr + 4);
                score += evaluateWindow(window, piece);
            }
        }

        //vertical
        for (int colctr = 0; colctr < columnCount; colctr++) {
            int[] colArray = new int[columnCount];
            for (int i = 0; i < rowCount; i++) {
                colArray[i] = boardCopy[i][colctr];
            }
            if (Arrays.stream(colArray).allMatch(value -> value == 0)) {
                continue;
            }
            for (int rowCtr = 0; rowCtr <= rowCount - 4; rowCtr++) {
                int[] window = Arrays.copyOfRange(colArray, rowCtr, rowCtr + 4);
                score += evaluateWindow(window, piece);
            }
        }


        //positive slope diagonals
        for (int r = 0; r < rowCount - windowLength + 1; r++) {
            for (int c = 0; c < columnCount - windowLength + 1; c++) {
                int[] window = new int[windowLength];

                for (int i = 0; i < windowLength; i++) {
                    window[i] = boardCopy[r + i][c + i];
                }

                score += evaluateWindow(window, piece);
            }
        }


        //negative slope

        for (int r = 0; r < rowCount - windowLength + 1; r++) {
            for (int c = 0; c < columnCount - windowLength + 1; c++) {
                int[] window = new int[windowLength];

                for (int i = 0; i < windowLength; i++) {
                    window[i] = boardCopy[r + 3 - i][c + i];
                }

                score += evaluateWindow(window, piece);
            }
        }

        if(score >= 1000){
            return  1000;
        } else if (score<=-500) {
            return -500;
        }
        return score;
    }

    private int evaluateWindow(int[] window, int piece) {
        int score = 0;
        int opponentPiece = -piece;

        if (countOccurrences(window, piece) == 4) {
            return 1000;
        } else if (countOccurrences(window, piece) == 3 && countOccurrences(window, 0) == 1) {
            score += 10;
        } else if (countOccurrences(window, piece) == 2 && countOccurrences(window, 0) == 2) {
            score += 5;
        } else if (countOccurrences(window, opponentPiece) == 3 && countOccurrences(window, 0) == 1) {
            return -500;
        }
        return score;
    }

    private static int countOccurrences(int[] array, int number) {
        int count = 0;

        for (int value : array) {
            if (value == number) {
                count++;
            }
        }
        return count;
    }

    private ArrayList<Integer> validCols(int[][] gameBoard) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int ctr = 0; ctr < gameBoard[0].length; ctr++) {
            if (gameBoard[0][ctr] == 0) {
                result.add(ctr);
            }
        }
        return result;
    }

    private void dropDiscToCopy(int col, int piece, int[][] tempBoard) {
        for (int row = tempBoard.length - 1; row >= 0; row--) {
            if (tempBoard[row][col] == 0) {
                tempBoard[row][col] = piece;
                return;
            }
        }
    }

    public static int[][] cloneArray(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    private boolean isTerminalNode(int[][] board) {
        return GameManager.isGameOver(board, -1) || GameManager.isGameOver(board, 1) || validCols(board).size() == 0;
    }

    public HashMap<String, Integer> minimax(int[][] board, int depth, boolean maximizingPlayer) {
        ArrayList<Integer> validCols = validCols(board);
        boolean isTerminal = isTerminalNode(board);
        HashMap<String, Integer> result = new HashMap<>();

        if (isTerminal) {
            if (GameManager.isGameOver(board, -1)) {
                result.put("score", Integer.MAX_VALUE);
                return result;
            } else if (GameManager.isGameOver(board, 1)) {
                result.put("score", Integer.MIN_VALUE);
                return result;
            } else {
                result.put("score", 0);
                return result;
            }
        }
        if (depth == 0) {
            result.put("score", scorePosition(-1, board));
            return result;
        }
        Random random = new Random();
        if (maximizingPlayer) { //AI
            int value = Integer.MIN_VALUE;
            int column = random.nextInt(board[0].length);
            int newScore = 0;
            for (int col : validCols) {
                int[][] newBoard = cloneArray(board);
                dropDiscToCopy(col,-1,newBoard);
                newScore = minimax(newBoard,depth - 1, false).get("score");
                if (newScore > value) {
                    value = newScore;
                    column = col;
                }
            }
            result.put("score", value);
            result.put("column", column);

            return result;
        }
        else { //Player
            int value = Integer.MAX_VALUE;
            int column = random.nextInt(board[0].length);
            int newScore = 0;
            for (int col : validCols) {
                int[][] newBoard = cloneArray(board);
                dropDiscToCopy(col,1,newBoard);
                newScore = minimax(newBoard,depth - 1, true).get("score");
                if (newScore < value) {
                    value = newScore;
                    column = col;
                }
            }
            result.put("score", value);
            result.put("column", column);
            return result;
        }
    }
}

