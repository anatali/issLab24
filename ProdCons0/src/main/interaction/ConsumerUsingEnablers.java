package main.interaction;

import java.io.FileWriter;
import java.io.IOException;

import unibo.basicomm23.enablers.ServerFactory;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.IApplMsgHandler;
import unibo.basicomm23.interfaces.IServer;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
 
/*
 * ===========================================================================
 * Viene prima creato e poi attivato
 * Usa ServerFactory.create per creare un server TCP sulla porta 8888
 * Opera come oggetto che implementa IApplMsgHandler, cioe' come gestore
 * dei messaggi che giungono al server
 * Per ogni messaggio ricevuto
	 * viene chiamato il metodo elaborate
	 * se il messaggio e' una request, invia reply
 * ===========================================================================
 */
public class ConsumerUsingEnablers implements IApplMsgHandler{
protected String name;
protected ProtocolType protocol;
protected String host;
protected String entry;
protected Interaction connSupport;
protected String msgId      = "";
protected String msgContent = "";
protected String msgSender  = "";
protected String msgReceiver= "";
protected IServer server;
protected FileWriter myWriter  ; //Per il log/testing

	 public ConsumerUsingEnablers(String name, int port, ProtocolType protocol){
		 this.name = name;
		 msgSender   = name;
		 CommUtils.outblue("cons " + name + " STARTS");	  
	 }

	 protected void setUpServer() {
		 server = ServerFactory.create(name,  8888, ProtocolType.tcp, this);
		 server.start();
		  
	 }

	 public void activate() {
		 setUpServer();
	 }//activate

/*
 * Since implements IApplMsgHandler
 */
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void elaborate(IApplMessage message, Interaction conn) {
        CommUtils.outcyan(name + "  | elaborate " + message + " " + Thread.currentThread().getName());
        consumerJob( message );
        if( message.isRequest() ) {
	        String outMsg = "ack("+message.msgContent()+")";
	        try {
				//IApplMessage reply = CommUtils.buildReply( name, "ack", outMsg, message.msgSender());
				IApplMessage reply = CommUtils.prepareReply( message, outMsg);
				conn.forward(reply); 
	        } catch (Exception e) {
	        	CommUtils.outred( name + " ERROR " + e.getMessage());
	        }
        }else writeLog(message.toString());
	}
	
	protected void consumerJob( IApplMessage message) {
        CommUtils.outblue(name + "  | consumes " + 
	       message.msgContent() + " sent by " + message.msgSender() );		
	}
	
	/*
	 * Scrittura di informazione sul log file:
	 * un primo modo per rendere il componente OSSERVABILE
	 */
	protected void writeLog(String s) {
		try {
			if( myWriter == null ) myWriter = new FileWriter("TestLog.txt");
			 myWriter.append(s+"\n");
			 myWriter.flush();
		} catch (Exception e) {
 			e.printStackTrace();
		}
	}	
}
