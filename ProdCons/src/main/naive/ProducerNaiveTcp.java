package main.naive;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;

/*
 * ===========================================================================
 * Viene prima creato e poi attivato
 * 
	 * Usa una socket per connettersi alla porta 8888
	 * Invia una request  al Consumer
	 * Invia un  dispatch al Consumer
	 * Elabora la reply del consumer
 * ===========================================================================
 */
public class ProducerNaiveTcp {
protected String name;
protected int distance = 0;

protected Socket socket;
protected OutputStream outStream;
protected InputStream inStream;
protected DataOutputStream outputChannel;
protected BufferedReader inputChannel;
protected String host;
protected int port;
protected String msgId      = "";
protected String msgContent = "";
protected String msgSender  = "";
protected String msgReceiver= "";

	 public ProducerNaiveTcp(String name ,String host, int port ){
		 this.name   = name;
		 this.host   = host;
		 this.port   = port;
		 msgSender   = name;
		 msgId       = "distance";
		 msgReceiver = "consumer";
		 CommUtils.outgreen("producer " + name + " CREATED. The consumer port is:" + port);
	 }
	 
	 public void activate() {
		 setUpConn();
		 producerJob();
	 }//activate
	 
	 protected void setUpConn() {	 		
		 try {
			 socket    =  new Socket( host, port );
			 outStream = socket.getOutputStream();
			 inStream  = socket.getInputStream();
			 outputChannel = new DataOutputStream(outStream);
			 inputChannel  = new BufferedReader(new InputStreamReader(inStream));
		} catch (IOException e) {
			CommUtils.outred( name + " ERROR " + e.getMessage());
		}		  	 
	 }
	 
	 
	 protected void producerJob() {
		 new Thread() {
			 public void run() {
			  try {
				distance++;
				IApplMessage msg = CommUtils.buildDispatch(name, msgId, ""+distance, msgReceiver);
				distance++;
				IApplMessage req = CommUtils.buildRequest(name, msgId, ""+distance, msgReceiver);
			    CommUtils.outgreen("prod " + name + " SENDING request "  + req );				  
 			    outputChannel.writeBytes( req+"\n" );
				outputChannel.flush();
			    CommUtils.outgreen("prod " + name + " SENDING dispatch " + msg );				  
				outputChannel.writeBytes( msg+"\n" );
				outputChannel.flush();
  			    
				elabConsumerReply();
 				socket.close();
			  }catch (Exception e) {
				CommUtils.outred( name + " ERROR " + e.getMessage());
			  }
			 }//run
		 }.start();		 
	 }
	 
	 protected void elabConsumerReply() {
		try {
			CommUtils.outmagenta("prod " + name + " WAITS FOR REPLY ... "  );
			String	line = inputChannel.readLine() ; //Blocking
			CommUtils.outmagenta("prod " + name + " RECEIVES " + line);
		} catch (Exception e) {
			CommUtils.outred( name + " ERROR " + e.getMessage());
		}  				     	 
	 }			 
}
