package javacallers;

import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.Connection;

public class ServerCallerCoap {
    
	public void doJob() {
    	IApplMessage req = MsgUtil.buildRequest("testercoap", "dofibo", "dofibo(20)", "servicemath");
    	CommUtils.outgreen("ServerCallerCoap | send " + req);
    	String answer = sendMessageCoap(req,"localhost", 8011);  
    	CommUtils.outgreen("ServerCallerCoap | answer " + answer);   	
     }
    
    protected  String sendMessageCoap(IApplMessage m, String addr, int port ) {
	        try {
	        Connection.trace = true;
            Interaction conn = CoapConnection.create(addr, ""+port  );
            CommUtils.outblue("ServerCallerCoap | sendMessageCoap conn " + conn + " for m="+m);
            if( m.isRequest() ) {
               String answer = conn.request(m.toString()); //sincrono
               //CommUtils.outblue("ServerCallerCoap | sendMessageCoap answer: " + answer);
               //conn.close();
               return answer;    
               
           }else{
               conn.forward(m.toString());    
               //conn.close();
               return "sendMessageCoap done";
           }
            
       }catch(Exception e){
    	   CommUtils.outred("ERROR " + e.getMessage() );
           return "sendMessageCoap failed";
       }   			
    }   
    
    public static void main( String[] args) throws InterruptedException {
    	new ServerCallerCoap().doJob();
    	//Thread.sleep(2000);
    	CommUtils.outyellow("sendMessageCoap BYE "  );
    }
}
