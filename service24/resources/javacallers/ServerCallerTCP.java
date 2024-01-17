package javacallers;

import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.tcp.TcpConnection;
import unibo.basicomm23.utils.CommUtils;

public class ServerCallerTCP {
    
	public void doJob() {
    	IApplMessage req = MsgUtil.buildRequest("tester", "dofibo", "dofibo(41)", "servicemath");
    	CommUtils.outgreen("ServerCallerTCP | send " + req);
    	String answer = sendMessageTcp(req,"localhost", 8011);  
    	CommUtils.outgreen("ServerCallerTCP | answer " + answer);   	
     }
    
    protected  String sendMessageTcp(IApplMessage m, String addr, int port ) {
	        try {
            Interaction conn = new TcpConnection(addr, port);
            //CommUtils.outblue("ServerCallerTCP | sendMessageTcp conn " + conn + " for m="+m);
            if( m.isRequest() ) {
               String answer = conn.request(m.toString()); //sincrono
               //CommUtils.outblue("ServerCallerTCP | sendMessageTcp answer: " + answer);
               conn.close();
               return answer;    
               
           }else{
               conn.forward(m.toString());    
               conn.close();
               return "sendMessageTcp done";
           }
            
       }catch(Exception e){
    	   CommUtils.outred("ERROR " + e.getMessage() );
           return "sendMessageTcp failed";
       }   			
    }   
    
    public static void main( String[] args) throws InterruptedException {
    	new ServerCallerTCP().doJob();
    	//Thread.sleep(2000);
    	CommUtils.outyellow("sendMessageTcp BYE "  );
    }
}
