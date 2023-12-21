import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AI {

    public int pickBestMove(int piece){
        Random random = new Random();
        int bestCol = random.nextInt(Connect4.gameBoard[0].length);
        int bestScore = 0;
        for (int colNum:validCols(Connect4.gameBoard)) {
            int[][] boardCopy = cloneArray(Connect4.gameBoard);
            dropDiscToCopy(colNum,piece,boardCopy);
            int score = scorePosition(piece,boardCopy);
            if (score>bestScore){
                bestScore=score;
                bestCol=colNum;
            }
        }
        return bestCol;
    }
    private int scorePosition(int piece, int[][] boardCopy){
        int score = 0;
        for (int rowctr = 0; rowctr < boardCopy.length; rowctr++){
            int[] rowArray = boardCopy[rowctr];
            if (Arrays.stream(rowArray).allMatch(value -> value==0)){
                continue;
            }
            for (int colCtr= 0; colCtr < boardCopy[0].length-3; colCtr++){
                int[] window = Arrays.copyOfRange(rowArray,colCtr,colCtr+4);
                if (countOccurrences(window,piece) ==4){
                    score += 100;
                } else if (countOccurrences(window,piece)==3 && countOccurrences(window,0) == 1 ) {
                    score += 10;
                }

            }
        }
        for (int colctr = 0; colctr < boardCopy[0].length; colctr++){
            int[] colArray = new int[boardCopy.length];
            for (int i = 0; i < boardCopy.length; i++) {
                colArray[i] = boardCopy[i][colctr];
            }
            if (Arrays.stream(colArray).allMatch(value -> value==0)){
                continue;
            }
            for (int colCtr= 0; colCtr < boardCopy.length-3; colCtr++){
                int[] window = Arrays.copyOfRange(colArray,colCtr,colCtr+4);
                if (countOccurrences(window,piece) ==4){
                    score += 100;
                } else if (countOccurrences(window,piece)==3 && countOccurrences(window,0) == 1 ) {
                    score += 10;
                }

            }
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
    private ArrayList<Integer> validCols(int[][] gameBoard){
        ArrayList<Integer> result = new ArrayList<>();
        for (int ctr=0; ctr < gameBoard[0].length; ctr++){
            if (gameBoard[0][ctr] == 0 ){
                result.add(ctr);
            }
        }
        return result;
    }

    private void dropDiscToCopy(int col, int piece, int[][] tempBoard ) {
        for (int row = tempBoard.length - 1; row >= 0; row--) {
            if (tempBoard[row][col] == 0) {
                tempBoard[row][col]=piece;
                return;
            }
        }
    }
    private static int[][] cloneArray(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }
}

