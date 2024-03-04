package main.interaction;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

/*
 * ===========================================================================
 * Viene prima creato e poi attivato
 * Usa ConnectionFactory.createClientSupport per creare una connessione
 * al server TCP sulla porta 8888
 * Invia una richiesta sulla connessione usando 
 * prima il metodo request (bloccante) 
 * e poi forward (con succesiva elabConsumerReply)
 * ===========================================================================
 */
public class ProducerUsingConnection {
protected String name;
protected String pfx        = "        ";

protected ProtocolType protocol;
protected String host;
protected String entry;
protected String msgId      = "distance";
protected String msgContent = "";
protected String msgSender  = "";
protected String msgReceiver= "consumer";

protected Interaction conn;  //CONNECTION

	 public ProducerUsingConnection(String name,String host,int port,ProtocolType protocol){
		 this.name     = name;
		 this.protocol = protocol;
		 this.host     = host;
		 this.entry    = ""+port;
		 msgSender   = name;
		 CommUtils.outgreen(pfx  + name + " CREATED. consumer port=" + entry);
	 }
	 
	 protected void setUpConn() {	 		 
		 conn = ConnectionFactory.createClientSupport(protocol, host, entry);		 
	 }
	 
	 public void activate() {
		 setUpConn();
		 producerJob();
	 }//activate
	 
	 protected void producerJob() {
		 new Thread() {
			 public void run() {
			  try {				 
				IApplMessage msg = CommUtils.buildDispatch(name, msgId, "10", msgReceiver);				 
				IApplMessage req = CommUtils.buildRequest( name, msgId, "50", msgReceiver);
//INVIO RICHIESTA BLOCCANTE				    
				CommUtils.outmagenta(pfx  + name + " SENDING request synch "  + req + " " + Thread.currentThread().getName());				  
			    IApplMessage answer = conn.request(req);  //bloccante
			    CommUtils.outmagenta(pfx  + name + " answer= " + answer+ " " + Thread.currentThread().getName());
//INVIO RICHIESTA ASINCRONA			    
				CommUtils.outgreen(pfx  + name + " SENDING request asynch "  + req + " " + Thread.currentThread().getName());				  			    
 			    conn.forward(req);   
//INVIO DISPATCH			 
			    CommUtils.outgreen(pfx  + name + " SENDING dispatch " + msg + " " + Thread.currentThread().getName());				  
			    conn.forward(msg);
			    CommUtils.delay(500);  //simulate some other work ...
//ELABORAZIONE DELLA RISPOSTA ALLA RICHIESTA ASINCRONA   			    
 				elabConsumerReply();
//TERMINAZIONE 			
			    CommUtils.outgreen(pfx  + name + " TERMINATES " + Thread.currentThread().getName());				   
			  }catch (Exception e) {
				CommUtils.outred( name + " ERROR " + e.getMessage());
			  }			  
			  
			  
			 }//run
		 }.start();		 
	 }
	 
	 protected void elabConsumerReply() {
		try {
			CommUtils.outmagenta(pfx  + name + " WAITS FOR REPLY ... "  );
			String reply = conn.receiveMsg(); //Blocking
			CommUtils.outmagenta(pfx  + name + " RECEIVES " + reply + " " + Thread.currentThread().getName());
		} catch (Exception e) {
			CommUtils.outred( pfx + name + " ERROR " + e.getMessage());
		}  				     	 
	 }
			 
}
