package main.enablers;

import main.naive.MainForProducersNaiveTcp;
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
 * Opera come oggetto che implementa IApplMsgHandler, cioè come gestore
 * dei messaggi che giungono al server
 * Per ogni messaggio ricevuto
	 * viene chiamato il metodo elaborate
	 * se il messaggio è una request invia reply
 * ===========================================================================
 */
public class ConsumerUsingEnablers implements IApplMsgHandler{
private String name;

protected ProtocolType protocol;
protected String host;
protected String entry;
protected Interaction connSupport;
protected String msgId      = "";
protected String msgContent = "";
protected String msgSender  = "";
protected String msgReceiver= "";
protected IServer server;

	 public ConsumerUsingEnablers(String name, int port, ProtocolType protocol){
		 this.name = name;
		 msgSender   = name;
		 CommUtils.outblue("cons " + name + " STARTS");
		  
	 }

	 protected void setUpServer() {
		 server = ServerFactory.create(
				 name,  MainForProducersNaiveTcp.port, MainForProducersNaiveTcp.protocol, this);
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
        }
	}
	
	protected void consumerJob( IApplMessage message) {
        CommUtils.outblue(name + "  | consumes " + 
	       message.msgContent() + " sent by " + message.msgSender() );		
	}
}
