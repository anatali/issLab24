package main.resources.robotvirtual;

public interface IVrobotMovesAsynch extends IVrobotMoves{
    public void stepAsynch(int time) throws Exception;
    public void setTrace(boolean v);
}
