package main.resources.gui;


import unibo.basicomm23.examples.ActorNaiveCaller;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction2021;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import java.util.Observable;
import java.util.Observer;

/*

 */

public class CmdConsoleRemoteTodo extends ActorNaiveCaller implements  Observer{
private String[] buttonLabels  = new String[] {"start", "stop", "resume", "getpath" };
private String myName;
private IApplMessage curMsg;
private IApplMessage applRunningRequest, applGetPathRequest ;

	public CmdConsoleRemoteTodo(String name, ProtocolType protocol, String hostAddr, String entry) {
		super(name, protocol,   hostAddr,   entry);
		ButtonAsGui concreteButton = ButtonAsGui.createButtons( "", buttonLabels );
		concreteButton.addObserver( this );
		this.myName        = name;
		applRunningRequest = CommUtils.buildRequest("gui", "isrunning", "isrunning", myName);
		applGetPathRequest = CommUtils.buildRequest("gui", "getpath", "getpath", myName);
		/*
		try {
			conn = ConnectionFactory.createClientSupport(protocol,"localhost", port);
		} catch (Exception e) {
			CommUtils.outred(" CmdConsoleRemote | ERROR:" + e.getMessage() );
		}*/
 	}


 	@Override
	public void update( Observable o , Object arg ) {
		try {
			if( connSupport == null ){
				CommUtils.outred(" CmdConsoleRemote | Please start Appl1HTTPSprint3"   );
				return;
			}
			String move = arg.toString();
			if( move.equals("getpath")) {
				String answer = connSupport.request(applRunningRequest.toString());
				CommUtils.outyellow("application ruuning: " + answer);
				String curPathAnswer = connSupport.request(applGetPathRequest.toString());
				CommUtils.outyellow("application path: " + curPathAnswer);
				IApplMessage answMsg = new ApplMessage(answer);
				IApplMessage pathMsg = new ApplMessage(curPathAnswer);
				String path          = pathMsg.msgContent();
				if (answMsg.msgContent().equals("true")) {
					CommUtils.outmagenta("CURRENT PATH="+ path);
				}else{
					String spath = CommUtils.restoreFromConvertToSend(path);
	 				CommUtils.outmagenta("FINAL PATH="+ spath);
				}
			}else {
				curMsg = CommUtils.buildDispatch("gui", move, move, myName);
				CommUtils.outyellow("forward: " + curMsg);
				connSupport.forward(curMsg.toString());
			}
		} catch (Exception e) {
			CommUtils.outred(" CmdConsoleRemote | update ERROR:" + e.getMessage() );;
		}	
	}
	

	@Override
	protected void body() throws Exception {
		connect();
	   CommUtils.outmagenta("CmdConsoleRemote simply reacts to buttons");
	}

	public static void main( String[] args) {
		new CmdConsoleRemoteTodo( "cmdconsole", ProtocolType.tcp, "localhost", "8030" );
	}

}

