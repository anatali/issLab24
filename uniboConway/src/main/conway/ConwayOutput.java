package main.conway;

public class ConwayOutput {

    public void setCellColor( int i, int j, int state ){
        if( state == 0 ) ConwayIO.cellOff(i,j);
        else ConwayIO.cellOn(i,j);
    }
}
