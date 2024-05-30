package main.conway;

import javafx.application.Application;
import javafx.stage.Stage;
 

public class MainConway extends Application{

    protected void configureTheSystem(){
        ConwayOutput outdev = new ConwayOutput();
        Life life           = new Life(12,16, outdev);
        LifeController cc   = new LifeController(life);
        new ConwayIO(life.getRowsNum(), life.getColsNum(), cc );
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConwayIO.setTheStage(primaryStage);
        configureTheSystem();
    }

    public static void main(String[] args) {
        //sysUtil.aboutThreads("MainConway | STARTS " );
        launch();
    }

}