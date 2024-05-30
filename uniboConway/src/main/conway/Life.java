package main.conway;

import javafx.scene.Scene;
import unibo.basicomm23.utils.CommUtils;

public class Life {
    //La struttura
    private int rows=10;
    private int cols=20;
    private static int[][] grid;
    private static int[][] nextGrid;
    private ConwayOutput outdev;

    public Life( int rowsNum, int colsNum, ConwayOutput outdev ) {
        this.rows   = rowsNum;
        this.cols   = colsNum;
        this.outdev = outdev;
        initializeGrids();   //crea la struttura a griglia
        initGrids();        //pone 0 lo stato di tutte le celle
        //initIO();
    }

    public int getRowsNum(){
        return rows;
    }
    public int getColsNum(){
        return cols;
    }

    protected void  initializeGrids() {
        grid     = new int[rows][cols];
        nextGrid = new int[rows][cols];  //???
        CommUtils.outyellow("Life | initializeGrids done");
    }

    protected void initGrids() {
         for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j]     = 0;
                setCellState(   i,   j, false );
                nextGrid[i][j] = 0;
            }
        }
        CommUtils.outyellow("Life | initGrids done");
    }


    protected int countNeighborsLive(int row, int col) {
        int count = 0;
        if (row-1 >= 0) {
            if (grid[row-1][col] == 1) count++;
        }
        if (row-1 >= 0 && col-1 >= 0) {
            if (grid[row-1][col-1] == 1) count++;
        }
        if (row-1 >= 0 && col+1 < cols) {
            if (grid[row-1][col+1] == 1) count++;
        }
        if (col-1 >= 0) {
            if (grid[row][col-1] == 1) count++;
        }
        if (col+1 < cols) {
            if (grid[row][col+1] == 1) count++;
        }
        if (row+1 < rows) {
            if (grid[row+1][col] == 1) count++;
        }
        if (row+1 < rows && col-1 >= 0) {
            if (grid[row+1][col-1] == 1) count++;
        }
        if (row+1 < rows && col+1 < cols) {
            if (grid[row+1][col+1] == 1) count++;
        }
        return count;
    }

    public void play() {
        CommUtils.outgreen("Life play "    );
        computeNextGen();
        copyAndDisplayGrid();
    }

    protected void computeNextGen() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int n = countNeighborsLive(i,j);
                applyRules(i, j, n);
            }
        }
    }

    protected void copyAndDisplayGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = nextGrid[i][j];
                outdev.setCellColor(  i,  j, grid[i][j] );
                nextGrid[i][j] = 0;
            }
        }
    }

    protected void applyRules(int row, int col, int numNeighbors) {
        //int numNeighbors = countNeighborsLive(row, col);
        //CELLA VIVA
        if (grid[row][col] == 1) {
            if (numNeighbors < 2) { //muore per isolamento
                nextGrid[row][col] = 0;
            } else if (numNeighbors == 2 || numNeighbors == 3) { //sopravvive
                nextGrid[row][col] = 1;
            } else if (numNeighbors > 3) { //muore per sovrappopolazione
                nextGrid[row][col] = 0;
            }
        }
        //CELLA MORTA
        else if (grid[row][col] == 0) {
            if (numNeighbors == 3) { //riproduzione
                nextGrid[row][col] = 1;
            }
        }
        //CommUtils.outgreen("Life applyRules " + nextGrid   );
    }

    public void switchCellState(int i, int j){
        if( grid[i][j] == 0) setCellState(i,j,true);
        else if( grid[i][j] == 1) setCellState(i,j,false);
    }

    public  void setCellState( int i, int j, boolean alive ) {
        //CommUtils.outcyan("Life setGrid | " + alive + " " + i + " " + j);
        if (alive) {
            grid[i][j] = 1;
            ConwayIO.cellOn(i,j);
        } else {
            grid[i][j] = 0;
            ConwayIO.cellOff(i,j);
        }
    }

    public  int[][] getGrid() {
        return grid;
    }

}
