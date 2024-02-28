package main.naive;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.IApplMsgHandler;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

/*
 * ===========================================================================
 * Ente attivo per la ricezione di messaggi su una connessione Interaction
 * Trasforma ogni messaggio ricevuto da String a ApplMessage
 * e invoca l'handler applicativo dato nel costruttore
 * ===========================================================================
 */
public class TcpConnectionHandler extends Thread{
private IApplMsgHandler handler ;
private Interaction  conn;

public TcpConnectionHandler(IApplMsgHandler handler, Interaction conn ) {
		this.handler = handler;
		this.conn    = conn;
 		this.start();
}
 	
	@Override 
	public void run() {
		String name = handler.getName();
			CommUtils.outyellow(getName() + " | TcpConnectionHandler  STARTS with user-handler=" + name + " conn=" + conn );
			while( true ) {
			try {
				//CommUtils.outblue(name + " | waits for message  ...");
			    String msg = conn.receiveMsg();
				CommUtils.outyellow(name + "  | TcpConnectionHandler received:" + msg   );
			    if( msg == null ) {
			    	break;
			    } else{ 
			    	IApplMessage m = new ApplMessage(msg);
			    	handler.elaborate( m, conn ); 
			    }
			}catch( Exception e) {
				CommUtils.outred( getName() + "  | TcpConnectionHandler: " + e.getMessage()  );
			}
        }
		CommUtils.outyellow(getName() + " | TcpConnectionHandler BYE"   );
	}
}
