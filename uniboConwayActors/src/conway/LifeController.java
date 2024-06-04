package conway;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import unibo.basicomm23.utils.CommUtils;

import java.util.concurrent.TimeUnit;

public class LifeController {
    private int generationTime = 1000;
    public Button startStop;
    public Slider slider;
//    private  Life life;

//    public LifeController(Life game){ //,GridPane gameGrid, Button startStop
//         this.life      = game;
//    }
    public void cellClicked(MouseEvent e) {
        Rectangle source = (Rectangle)e.getSource();
        //dalla scena alle coordinate. Alternativa: cellClicked in LifeController
        CommUtils.outblue("LifeController | cellClicked  " + ((Rectangle)e.getSource()).getId()  );

        String[] coord = source.getId().split(",");
        int x = Integer.parseInt( coord[0] );
        int y = Integer.parseInt( coord[1] );

        CommUtils.outred("e:"+x + " " +y );
//        life.switchCellState(x,y);
    }

    public void startStopClicked(Boolean isStart, Button startStop) {
        AnimationTimer runAnimation = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                long dt = TimeUnit.MILLISECONDS.toNanos((long) generationTime);
                if ((now - lastUpdate) >= dt) {
                    //CommUtils.outred("LifeController | runAnimation " + isStart );
//                    life.play();
                    lastUpdate = now;
                }
                if( startStop.getText().equals("Start") ){
                    CommUtils.outred("LifeController | runAnimation STOPPED "  );
                    this.stop();
                }
            }
        };

        if( isStart ){
            runAnimation.start();
            startStop.setText("Stop");
        } else {
            //CommUtils.outred("LifeController | STOPPED "  );
            startStop.setText("Start");
        }
    }

    protected void clearTheCells(){
        CommUtils.outyellow("LifeController | clearTheCells " );
//        life.initGrids();
    }

}
