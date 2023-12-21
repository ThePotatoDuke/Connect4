public class GameManager {
    private static boolean checkHorizontal(int[][] matrix, int color) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length - 3; col++) {
                if (matrix[row][col] == color && matrix[row][col + 1] == color && matrix[row][col + 2] == color && matrix[row][col + 3] == color) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkVertical(int[][] matrix, int color) {
        for (int col = 0; col < matrix[0].length; col++) {
            for (int row = 0; row < matrix.length - 3; row++) {
                if (matrix[row][col] == color && matrix[row + 1][col] == color && matrix[row + 1][col] == color && matrix[row + 3][col] == color) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkDiagonal(int[][] matrix, int color) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows - 3; i++) { //positive diagonal
            for (int j = 0; j < cols - 3; j++) {
                if (matrix[i][j] == color && matrix[i + 1][j + 1] == color &&
                        matrix[i + 2][j + 2] == color && matrix[i + 3][j + 3] == color) {
                    return true;
                }
            }
            for (int j = 3; j < cols; j++) { //negative diagonal
                if (matrix[i][j] == color && matrix[i + 1][j - 1] == color &&
                        matrix[i + 2][j - 2] == color && matrix[i + 3][j - 3] == color) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isGameOver(int[][] matrix, int color) {
        return checkDiagonal(matrix, color) || checkHorizontal(matrix, color) || checkVertical(matrix, color);

    }
}