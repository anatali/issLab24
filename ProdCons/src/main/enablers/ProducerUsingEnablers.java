package main.enablers;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

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
public class ProducerUsingEnablers {
protected String name;
protected int distance = 0;

protected ProtocolType protocol;
protected String host;
protected String entry;
protected Interaction connSupport;

protected String msgId      = "";
protected String msgContent = "";
protected String msgSender  = "";
protected String msgReceiver= "";

	 public ProducerUsingEnablers(String name,String host,int port,ProtocolType protocol){
		 this.name     = name;
		 this.protocol = protocol;
		 this.host     = host;
		 this.entry    = ""+port;
		 msgSender   = name;
		 msgId       = "distance";
		 msgReceiver = "consumer";
		 CommUtils.outgreen("prod " + name + " CREATED. consumer port=" + entry);
	 }
	 
	 protected void setUpConn() {	 		 
		 connSupport = ConnectionFactory.createClientSupport(protocol, host, entry);		 
	 }
	 
	 public void activate() {
		 setUpConn();
		 producerJob();
	 }//activate
	 
	 protected void producerJob() {
		 new Thread() {
			 public void run() {
			  try {
				distance++;
				IApplMessage msg = CommUtils.buildDispatch(name, msgId, ""+distance, msgReceiver);
				distance++;
				IApplMessage req = CommUtils.buildRequest(name, msgId, ""+distance, msgReceiver);
			    
				CommUtils.outgreen("prod " + name + " SENDING request "  + req + " " + Thread.currentThread().getName());				  
//			    IApplMessage answer = connSupport.request(req);  //bloccante
//			    CommUtils.outmagenta("prod " + name + " answer= " + answer);
			    
 			    connSupport.forward(req);  //bloccante
			  
				 
			    CommUtils.outgreen("prod " + name + " SENDING dispatch " + msg + " " + Thread.currentThread().getName());				  
			    connSupport.forward(msg);
   			    
 				elabConsumerReply();
 
			  }catch (Exception e) {
				CommUtils.outred( name + " ERROR " + e.getMessage());
			  }			  
			  
			  
			 }//run
		 }.start();		 
	 }
	 
	 protected void elabConsumerReply() {
		try {
			CommUtils.outmagenta("prod " + name + " WAITS FOR REPLY ... "  );
			String reply = connSupport.receiveMsg(); //Blocking
			 CommUtils.outmagenta("prod " + name + " RECEIVES " + reply + " " + Thread.currentThread().getName());
		} catch (Exception e) {
			CommUtils.outred( name + " ERROR " + e.getMessage());
		}  				     	 
	 }
			 
}
