package main.conway;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import unibo.basicomm23.utils.CommUtils;

public class ConwayIO  {
    private static int CELL_SIZE = 45;
    private static Rectangle[][]  cells   ;
    private static Stage stage;
    private GridPane gridPane;
    private Scene scene;
    private Button bstart;
    private Button bclear;

    private int rows=0;
    private int cols=0;
    //private Life life;
    private LifeController cc;

    public ConwayIO(  int rows, int cols, LifeController cc ){
        //this.life = game;
        this.cc   = cc;
        this.rows = rows; //life.getRowsNum();
        this.cols = cols; //life.getColsNum();
        //stage     = primaryStage;
        CommUtils.outyellow("ConwayIO | created " + rows + " " + cols);
        createScene(   );
    }

    public static void setTheStage(Stage primaryStage){
        stage     = primaryStage;
    }
    /*
    public void init(){
        CommUtils.outyellow("ConwayIO | init");
    }
     */
    protected void createScene( ){
        gridPane = new GridPane();
        scene    = new Scene( gridPane, cols*CELL_SIZE+CELL_SIZE, rows*CELL_SIZE+CELL_SIZE);

        stage.setTitle("Conway's Game of Life");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);

        bstart = new Button("Start");
        gridPane.add(bstart,  0, rows+1);
        bclear = new Button("Clear");
        gridPane.add(bclear,  1, rows+1);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                boolean isStartStop = e.getSource().toString().contains("Start")
                        || e.getSource().toString().contains("Stop");
                //CommUtils.outyellow("ConwayIO | EventHandler isStartStop=" + isStartStop);
                if( isStartStop ){
                    boolean isStart = e.getSource().toString().contains("Start");
                    CommUtils.outyellow("ConwayIO | EventHandler isStartStop isStart=" + isStart);
                    cc.startStopClicked(isStart,bstart);
                }
                else {
                    CommUtils.outyellow("ConwayIO | EventHandler:" + e.getSource().toString());
                    cc.clearTheCells();
                }
            }
        };

        bstart.setOnAction(event);
        bclear.setOnAction(event);

       // Creazione delle gui per le celle della griglia
        createCells(    );
        stage.show();
    }


    protected void createCells(  ){
        cells = new Rectangle[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = new Rectangle(CELL_SIZE, CELL_SIZE);
                cells[row][col].setId(row+","+col);
                cells[row][col].setFill( Color.WHITE );
                cells[row][col].setStroke( Color.BLACK );

                gridPane.add( cells[row][col], col, row ) ;

                cells[row][col].addEventHandler(MouseEvent.MOUSE_CLICKED,
                        e ->  cc.cellClicked(e)
                );
            }//col
        }//row
    }//createCells

    /*  //In ConwayOutput
    public static void setCellColor( int i, int j, int state){
        if( state == 0 ) cellOff(i,j);
        else if( state == 1 ) cellOn(i,j);
    }

     */
    public static void cellOn(int i, int j){
        if( cells == null ) return;
        cells[i][j].setFill(Color.RED);
    }
    public static void cellOff(int i, int j){
        if( cells == null ) return;
        cells[i][j].setFill(Color.WHITE);
    }
}
