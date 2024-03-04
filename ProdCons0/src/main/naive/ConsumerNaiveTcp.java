package main.naive;
import java.net.ServerSocket;
import java.net.Socket;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.IApplMsgHandler;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.tcp.TcpConnection;
 
/*
 * ===========================================================================
 * In quanto TCP server:
 * 
	 * E' un ente attivo realizato con un Thread
	 * Usa una ServerSocket sulla porta 8888
	 * Attende  una nuova connessione 
	 * Crea un TcpConnectionHandler per gestire i messaggi sulla connessione 
	 * poneendo se se stesso come handler applicativo dei messaggi
 * 
 * In quanto handler applicativo:
	 * elabora il messaggio ricevuto sulla connessione
	 * invia una reply se il messaggio è una richiesta
 * ===========================================================================
 */

public class ConsumerNaiveTcp extends Thread implements IApplMsgHandler{
	private ServerSocket serversock;
	protected String name;
	protected boolean stopped = false;
	protected int port;
	protected Interaction conn;

	public ConsumerNaiveTcp(String name, int port) {
		super(name);
	    try {
			this.name             = getName();
			this.port             = port;
		    serversock            = new ServerSocket( port );
		    start();
		}catch (Exception e) { 
			CommUtils.outred(getName() + " |  ERROR: " + e.getMessage());
		}		
	}
	
	@Override
	public void run() {
	      try {
	      	CommUtils.outyellow(getName() + " | STARTING on "  + port );
			while( ! stopped ) {
				//Accept a connection				 
				CommUtils.outyellow(getName() + " |  waiting on port " + port );
		 		Socket sock          = serversock.accept();
				CommUtils.outyellow(getName()
						+ " |  " + port + " accepted connection on "+ sock.getPort()
						+ " with userDefHandler=" + this.getName()
						+ " thname=" + Thread.currentThread().getName() );
 		 		conn     = new TcpConnection(sock);
		 		//Create a message handler on the connection
		 		new TcpConnectionHandler( this, conn );		//thread
			}//while
		  }catch (Exception e) {  //Scatta quando la deactive esegue: serversock.close();
			  CommUtils.outred(getName() + " | probably socket closed: " + e.getMessage() );
		  }
	}

	@Override
	public void elaborate(IApplMessage message, Interaction conn) {
        CommUtils.outcyan(name + "  | elaborate " + message );
        
        consumerJob( message );
         
        if( message.isRequest()) sendReply( message,conn );
	}	
	
	protected void sendReply(IApplMessage message, Interaction conn) {
        String outMsg = "ack("+message.msgContent()+")";
        try {
			IApplMessage reply = CommUtils.buildReply( name, "ack", outMsg, message.msgSender());
			//IApplMessage reply = CommUtils.prepareReply( message, outMsg);
			conn.forward(reply);
		} catch (Exception e) {
			CommUtils.outred( name + " ERROR " + e.getMessage());
		}				
	}

	protected void consumerJob( IApplMessage message) {
        CommUtils.outblue(name + "  | consumes " + 
	       message.msgContent() + " sent by " + message.msgSender() );		
	}
	

}
