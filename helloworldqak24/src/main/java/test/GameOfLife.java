package main.java.test;

public class GameOfLife {
	
    public static void main(String[] args) {
        int rows = 10;
        int cols = 10;
        int[][] board = new int[rows][cols];
        
        // Inizializzazione della board
        initializeBoard(board, rows, cols);
        
        // Esecuzione del gioco per 10 generazioni
        for (int generation = 0; generation < 10; generation++) {
            System.out.println("Generazione: " + (generation + 1));
            printBoard(board, rows, cols);
            board = nextGeneration(board, rows, cols);
        }
    }
    
    public static void initializeBoard(int[][] board, int rows, int cols) {
        // Inizializzazione casuale della board
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = Math.random() > 0.5 ? 1 : 0;
            }
        }
    }
    
    public static void printBoard(int[][] board, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] == 1 ? "O " : ". ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public static int[][] nextGeneration(int[][] board, int rows, int cols) {
        int[][] newBoard = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int neighbors = countNeighbors(board, i, j, rows, cols);
                
                if (board[i][j] == 1) {
                    if (neighbors < 2 || neighbors > 3) {
                        newBoard[i][j] = 0;
                    } else {
                        newBoard[i][j] = 1;
                    }
                } else {
                    if (neighbors == 3) {
                        newBoard[i][j] = 1;
                    } else {
                        newBoard[i][j] = 0;
                    }
                }
            }
        }
        
        return newBoard;
    }
    
    public static int countNeighbors(int[][] board, int x, int y, int rows, int cols) {
        int count = 0;
        
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                
                int newX = x + i;
                int newY = y + j;
                
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols) {
                    count += board[newX][newY];
                }
            }
        }
        
        return count;
    }
}
