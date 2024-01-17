package javacallers;

import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.tcp.TcpConnection;
import unibo.basicomm23.utils.CommUtils;

public class ServerCallerTCP {
    
	public void doJob() {
    	IApplMessage req = MsgUtil.buildRequest("tester", "dofibo", "dofibo(38)", "servicemath");
    	CommUtils.outgreen("send " + req);
    	String answer = sendMessageTcp(req,"localhost", 8011);  
    	CommUtils.outgreen(answer);   	
     }
    
    protected  String sendMessageTcp(IApplMessage m, String addr, int port ) {
	        try {
            //CommUtils.outyellow("sendMessageTcp  " + m );
            Interaction conn = new TcpConnection(addr, port);
            CommUtils.outyellow("sendMessageTcp conn " + conn + " for m="+m);
            if( m.isRequest() ) {
               String answer = conn.request(m.toString()); //sincrono
               CommUtils.outblue("sendMessageTcp answer: " + answer);
               return answer;    
               
           }else{
               conn.forward(m.toString());                
               return "sendMessageTcp done";
           }
       }catch(Exception e){
    	   CommUtils.outred("ERROR " + e.getMessage() );
           return "sendMessageTcp failed";
       }   			
    }   
    
    public static void main( String[] args) throws InterruptedException {
    	new ServerCallerTCP().doJob();
    	Thread.sleep(2000);
    	CommUtils.outyellow("sendMessageTcp BYE "  );
    }
}
