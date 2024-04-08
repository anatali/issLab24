package main.java.utils;

import unibo.basicomm23.utils.CommUtils;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text; 
import javafx.scene.control.Button;

public class ActorIO extends Application{
	protected static TextArea   outarea   ;
	protected static Text   outtext   ;
	protected  Stage stage;
//	protected  Scene scene;
	protected static Button myButton;
	protected static boolean  launched = false;
	
	public ActorIO()  {
	}
	public  void initialize(){
		if( !launched ) {
			launch(new String[] {});
			launched = true;
		}
	}
	
	protected void setOutArea() {
        outarea   = new TextArea( );
        outarea.setPrefColumnCount(15);
        outarea.setPrefHeight(280);
        outarea.setPrefWidth(800);	
 
		myButton = new Button("Clear");
        myButton.setOnAction(e -> {
            System.out.println("Clear!");
            outarea.setText("");
        });
        //An input field with SEND button should send a dispatch to the facade
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
        stage     = primaryStage;
        setOutArea();
		createScene( stage  );
	}	
	
	@Override
	public void stop(){
 	    System.exit(0);  
	}
	
	protected void createScene( Stage stage ){
		GridPane root  = new GridPane( );
 		root.add(outarea,0,0 );
 		root.add(myButton,0,1 );
 		 
        Scene scene = new Scene( root, 600, 300);
        stage.setTitle("actorgui");
        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(true);
    }
	
	public void write( String s ) {
		if(s==null) return;  //defensive
		String outs = s.replace("show(", "").replace("out(", "");
		outs  = outs.substring(0, outs.lastIndexOf(")"));
		outarea.appendText(outs+"\n");
	}
	
    public void simulateButtonPress() { //(Button button
        // Puoi chiamare il metodo fire() per simulare la pressione del pulsante
    	if( myButton != null ) myButton.fire();
    	else CommUtils.outred("ActorIO |  simulateButtonPress null  "  );
    }
	
}
