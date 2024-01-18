package javacallers;

import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.Connection;

public class ServerCallerCoap {
    
	public void dorequest(String path) {
    	IApplMessage req = MsgUtil.buildRequest("testercoap", "dofibo", "dofibo(18)", "servicemath");
    	CommUtils.outgreen("ServerCallerCoap | send " + req);
    	String answer = sendMessageCoap(req,"localhost", 8011,path);  
    	CommUtils.outgreen("ServerCallerCoap | answer " + answer);   	
	}
	public void emitAlerm(String path) {		
      	IApplMessage alarmev = MsgUtil.buildEvent("testercoap", "alarm", "alarm(tsunami)" );
      	sendMessageCoap(alarmev,"localhost", 8011, path);  
	}
    
    protected  String sendMessageCoap(IApplMessage m, String addr, int port, String path ) {
	        try {
	        Connection.trace = true;
            Interaction conn = CoapConnection.create(addr+":"+port, path ); //ctxservice/servicemath
            CommUtils.outblue("ServerCallerCoap | sendMessageCoap conn " + conn + " for m="+m);
            if( m.isRequest() ) {
               String answer =  conn.request(m.toString()); //sincrono
               //CommUtils.outblue("ServerCallerCoap | sendMessageCoap answer: " + answer);
               conn.close();
               return  answer;                   
           }else{
               conn.forward(m.toString());    
               conn.close();
               return "sendMessageCoap done " + m;
           }
            
       }catch(Exception e){
    	   CommUtils.outred("ERROR " + e.getMessage() );
           return "sendMessageCoap failed";
       }   			
    }   
    
    public static void main( String[] args) throws InterruptedException {
    	new ServerCallerCoap().dorequest("ctxservice");
    	new ServerCallerCoap().emitAlerm("ctxservice");
    	new ServerCallerCoap().dorequest("ctxservice/servicemath");
    	//Thread.sleep(2000);
    	CommUtils.outyellow("sendMessageCoap BYE "  );
    }
}
