package main.resources.gui;

import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.ws.WsConnection;

import java.util.Observable;
import java.util.Observer;

import it.unibo.kactor.ActorBasic;
//import main.resources.robotvirtual.IVrobotMoves;
import main.resources.robotvirtual.VrobotLLMoves24;

/*
 */

public class VrobotHLCmdGuiLocal implements  Observer{
private String[] buttonLabels  = new String[] {"p", "w", "s", "l", "r", "h" };
	private	VrobotLLMoves24 vrsupport ;
	private WsConnection conn;

	public VrobotHLCmdGuiLocal(VrobotLLMoves24 vrsupport) {
		this.vrsupport = vrsupport;
//		GuiUtils.showSystemInfo();
		ButtonAsGui concreteButton = ButtonAsGui.createButtons( "", buttonLabels );
		concreteButton.addObserver( this );
//		try {
//			vrsupport = VrobotHLMovesActors24.create( );
//			vrsupport.connect("localhost", null);
////			connect("localhost");
//		} catch (Exception e) {
//			CommUtils.outred("VrobotHLCmdGuiLocal creation error" + e.getMessage());
//		}
	}
	
    protected void connect(String vitualRobotIp ) {
        this.conn = WsConnection.create(vitualRobotIp+":8091");
        //((WsConnection) conn).addObserver(this);
//        if( owner != null )
//        	toApplMsg = "msg(wenvinfo, dispatch, support, RECEIVER, CONTENT, 0)"
//               .replace("RECEIVER",owner.getName());
//        else
//            toApplMsg = "msg(wenvinfo, dispatch, support, RECEIVER, CONTENT, 0)"
//            .replace("RECEIVER","cmdgui");
        	
       CommUtils.aboutThreads("     VrobotHLCmdGuiLocal |");
       CommUtils.outyellow(
               "     VrobotHLCmdGuiLocal | CREATED in " + Thread.currentThread().getName());
    }
 	@Override
	public void update( Observable o , Object arg ) {
		try {
			String move = arg.toString();
			CommUtils.outgreen("GUI input move=" + move);
			if( move.equals("exit")) System.exit(1);
			switch( move ){
				case "w" :  {vrsupport.forward(1800);break;}
				case "s"  : {vrsupport.backward(1800);break;}
				case "l"  : {vrsupport.turnLeft();break;}
				case "r"  : {vrsupport.turnRight();break;}
				case "h"  : {vrsupport.halt();break;}
				case "p"  : {vrsupport.step(350);break;}
			}
		} catch (Exception e) {
			CommUtils.outred(" StartStopGuiLocal | update ERROR:" + e.getMessage() );;
		}	
	}
	
	public static void main( String[] args) {
 		new VrobotHLCmdGuiLocal( VrobotLLMoves24.create("localhost",null )  );
    }
}

