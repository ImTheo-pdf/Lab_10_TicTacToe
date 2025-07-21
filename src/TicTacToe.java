import java.util.Scanner;
/*
 * PSEUDOCODE OUTLINE:
 * 1. Clear the board
 * 2. Set player to "X"
 * 3. Loop until win or tie:
 *    a. Display board
 *    b. Get row and column input (1-3), subtract 1 to convert to 0-2
 *    c. While move is invalid, re-prompt
 *    d. Place player symbol on board
 *    e. Increment move count
 *    f. If move count >= 5, check for win or tie
 *       - If win, display board, show winner, ask to play again
 *       - If tie, display board, announce tie, ask to play again
 *    g. Toggle player ("X" <-> "O")
 */

public class TicTacToe {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board = new String[ROWS][COLS];

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean playAgain;

        do {
            clearBoard();
            String player = "X";
            int moveCount = 0;
            boolean gameOver = false;

            while (!gameOver) {
                display();
                int row = SafeInput.getRangedInt(in, player + " - Enter row", 1, ROWS) - 1;
                int col = SafeInput.getRangedInt(in, player + " - Enter col", 1, COLS) - 1;

                while (!isValidMove(row, col)) {
                    System.out.println("Invalid move. Cell already taken.");
                    row = SafeInput.getRangedInt(in, player + " - Enter row", 1, ROWS) - 1;
                    col = SafeInput.getRangedInt(in, player + " - Enter col", 1, COLS) - 1;
                }

                board[row][col] = player;
                moveCount++;

                if (moveCount >= 5 && isWin(player)) {
                    display();
                    System.out.println(player + " WINS");
                    gameOver = true;
                } else if (moveCount == 9 && isTie()) {
                    display();
                    System.out.println("It's a tie.");
                    gameOver = true;
                } else {
                    player = player.equals("X") ? "O" : "X";
                }
            }

            playAgain = SafeInput.getYNConfirm(in, "Play again?");
        } while (playAgain);

        System.out.println("Thanks for playing");
    }


    private static void clearBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = " ";
            }
        }
    }

    private static void display() {
        System.out.println("\nCurrent Board:");
        for (int r = 0; r < ROWS; r++) {
            System.out.print(" ");
            for (int c = 0; c < COLS; c++) {
                System.out.print(board[r][c]);
                if (c < COLS - 1) System.out.print(" | ");
            }
            System.out.println();
            if (r < ROWS - 1) {
                System.out.println("---+---+---");
            }
        }
    }

    private static boolean isValidMove(int row, int col) {
        return board[row][col].equals(" ");
    }

    private static boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagnalWin(player);
    }

    private static boolean isRowWin(String player) {
        for (int r = 0; r < ROWS; r++) {
            if (board[r][0].equals(player) && board[r][1].equals(player) && board[r][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isColWin(String player) {
        for (int c = 0; c < COLS; c++) {
            if (board[0][c].equals(player) && board[1][c].equals(player) && board[2][c].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagnalWin(String player) {
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
                (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    private static boolean isTie() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c].equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }
}