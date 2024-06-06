package conway;

import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.MsgUtil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import unibo.basicomm23.utils.CommUtils;
import java.util.Vector;

public class ConwayIOTwostep  extends Application{

    //Variabili statiche per consentire accesso entro start
	//POSESSORE DEL DISPOSITIVO
    private  static ActorBasic displayOwnerActor ;
    //DICHIRAZIONE RELATIVE ALLA GRIGLIA
    private static Rectangle[][]  cells   ;
    private static int rows=0;
    private static int cols=0;
    private static int cellsize = 45;
    //DICHIRAZIONE COMPONENTI DELLA SCENA
        private Stage stage;
        private GridPane gridPane;
        private Scene scene;
        private Button bstart;
        private Button bclear;

        protected Label rlbl;
        protected Label clbl;

        private TextField  rowsn;
        private  TextField colsn ;
        private Button submit ;
        
        
    public ConwayIOTwostep(     ){
        super();
    }

    public  void initialize(ActorBasic caller){
        displayOwnerActor = caller;
        launch(new String[] {});
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
        stage     = primaryStage;
		//createScene( );
        createSceneInitial( );
	}
	@Override
	public void stop(){
	    CommUtils.outmagenta("ConwayIO | stop: Stage is closing");
	    //displayOwnerActor.emit("gameover", "gameover(master)", null);   //per finire l'appendice remota
	    System.exit(0);  
	}	

	protected EventHandler<ActionEvent> submitevent = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
        	rows = Integer.parseInt(rowsn.getText() )   ;
        	cols = Integer.parseInt(colsn.getText() )   ;
            String s = "{\"rowsNum\":RN, \"colsNum\":CN, \"cellsize\":45}".replace("RN", rowsn.getText()).replace("CN", colsn.getText());
            //CommUtils.outblue("s="+s) ;
            GridSupport.saveOnFile(s,"gridConfig.json");
            
            createScene( );
            sendMsgToOwner("guicmd", "guicmd(activatealone)");
        }
    };

    protected void createSceneGuiParams(){
    	gridPane = new GridPane();
    	scene    = new Scene( gridPane, 300, 40*2);

        stage.setTitle("DISPLAY TWO STEPS");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        
        rlbl = new Label("rows");
        clbl = new Label("cols");
        rowsn = new TextField("5");
        rowsn.setPrefColumnCount(2);
        colsn = new TextField("5");
        colsn.setPrefColumnCount(2);
        
        submit = new Button("Submit");         

        gridPane.add(rlbl,   0, 0);
        gridPane.add(rowsn,  1, 0);
        gridPane.add(clbl,   0, 1);
        gridPane.add(colsn,  1, 1);
        gridPane.add(submit, 0, 2);
        
         	
    }
    protected void createSceneInitial( ){
    	
    	createSceneGuiParams();
        // action event submitevent
 
        // SUBMIT button
        submit.setOnAction(submitevent); 
        //invia a displayOwnerActor (griddiaplay) guicmd(activatealone)    
                 
        stage.show(); 
    }

	protected void setGameInput() {

//        bstart = new Button("Start");
//        gridPane.add(bstart,  0, rows+1);
//        bclear = new Button("Clear");
//        gridPane.add(bclear,  1, rows+1);
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               String evstr = e.getSource().toString();
               if(evstr.contains("Start")){
                  bstart.setText("Stop");
                  sendMsgToOwner("guicmd", "guicmd(startthegame)");
               }
               else if(evstr.contains("Stop")){
                  bstart.setText("Start");
                  sendMsgToOwner("guicmd", "guicmd(stopthegame)");
               }
               else if(evstr.contains("Clear")){
                  sendMsgToOwner("guicmd", "guicmd(clear)");  
               }
            }
         };		
         bstart.setOnAction(event);
         bclear.setOnAction(event);

	}

    protected void createScene( ){
    	CommUtils.outblue("createScene ................" ) ;
        gridPane = new GridPane();
        scene    = new Scene( gridPane, cols*cellsize+cellsize, rows*cellsize+cellsize*3);

        stage.setTitle("GLMDisplayObserver");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);

        bstart = new Button("Start");
        gridPane.add(bstart,  0, rows+1);
        bclear = new Button("Clear");
        gridPane.add(bclear,  1, rows+1);


          
        setGameInput();
        // Creazione delle gui per le celle della griglia
        createCells(    );
        stage.show();

    }


    protected void createCells(  ){
        cells = new Rectangle[rows][cols];
        //CommUtils.outmagenta("ConwayIO | createCells  " +   cells );
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = new Rectangle(cellsize, cellsize);
                cells[row][col].setId(row+","+col);
                cells[row][col].setFill( Color.WHITE );
                cells[row][col].setStroke( Color.BLACK );

                gridPane.add( cells[row][col], col, row ) ;

                cells[row][col].addEventHandler(MouseEvent.MOUSE_CLICKED,
                        e -> {
                            Vector<Integer> xy = cellClicked(e);
                            int x = xy.get(0);
                            int y = xy.get(1);
                            CommUtils.outred("ConwayIO | cellClicked  " + x + ":" + y + " " +displayOwnerActor );
                            sendMsgToOwner("guicmd","guicmd(click("+x+","+y+"))");
                        }
                );
            }//col
        }//row
    }//createCells

    public Vector<Integer> cellClicked(MouseEvent e) {
        Vector<Integer> outv = new Vector<Integer>();
        Rectangle source = (Rectangle)e.getSource();
        //dalla scena alle coordinate. Alternativa: cellClicked in LifeController
        //CommUtils.outyellow("ConwayIO | cellClicked  " + ((Rectangle)e.getSource()).getId()  );
        String[] coord = source.getId().split(",");
        int x = Integer.parseInt( coord[0] );
        int y = Integer.parseInt( coord[1] );
        outv.add(x);
        outv.add(y);
        //CommUtils.outred("ConwayIO e:"+x + " " +y );
        return outv;
    }

    public void clearCells() {
    	//CommUtils.outblue("ConwayIO | clearCells  " + rows + " " + cols );
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cellOff(row,col);
            }
        }
    }
    
    public  void cellOn(int i, int j){
    	//CommUtils.outblue("ConwayIO | cellOn  " + i + " " + j   );
        if( cells == null ) return;
        cells[i][j].setFill(Color.RED);
    }
    public  void cellOff(int i, int j){
    	//CommUtils.outblue("ConwayIO | cellOff  " + i + " " + j + " " + cells );
        if( cells == null ) return;
        cells[i][j].setFill(Color.WHITE);
    }
    
    public  void switchColor(int i, int j){
    	if( cells == null ) return;
    	//CommUtils.outcyan("switchColor:"+cells[i][j].getFill() + " " + (cells[i][j].getFill() == Color.WHITE ));
    	if( cells[i][j].getFill() == Color.WHITE ) cells[i][j].setFill(Color.RED);
    	else if( cells[i][j].getFill() == Color.RED )   cells[i][j].setFill(Color.WHITE);
    }

    //ADDED since Actors
    public void sendMsgToOwner(String msgId, String msgPayload) {
    	CommUtils.outcyan("ConwayIO sendMsgToOwner:" + msgId + " " + msgPayload + " " + displayOwnerActor);
    	try {
    	MsgUtil.sendMsg("gui",msgId, msgPayload,displayOwnerActor,null);
  //  	val m = MsgUtil.buildDispatch( displayOwnerActor.getName(), msgId, msgPayload, displayOwnerActor.getName() );
  //  	GlobalScope.launch{ displayOwnerActor.send( m ); }
    	}catch(Exception e) {
    		CommUtils.outred("ConwayIO sendMsgToOwner ERROR:" +e.getMessage());
    	}
 
    }
    


}
