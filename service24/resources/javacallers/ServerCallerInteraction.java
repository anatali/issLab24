package javacallers;
import it.unibo.kactor.MsgUtil;
import org.eclipse.californium.core.CoapClient;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.tcp.TcpConnection;
import unibo.basicomm23.utils.CommUtils;
 

public class ServerCallerInteraction {
    private final String destination = "servicemathcoded";
    private final String sender      = "clientcoapjava";
    private final String hostAddr    = "localhost";
    private final int    port        = 8011;
    private final String msgid       = "dofibo";
    private final String msgcontent  = "dofibo(38)";

    private Interaction connCoap;
    private Interaction  connTcp;
    private String path = "ctxservice/servicemathcoded";

    public void doJob() {
        try {            
            connCoap = CoapConnection.create(hostAddr+":"+port,path);
            connTcp  = TcpConnection.create( hostAddr,port );
            dorequest();
            //receiveAnswer( );
            connCoap.close();
        }catch(Exception e){
            CommUtils.outred("ERROR " + e.getMessage() );
        }
    }


	public void dorequest() throws Exception {
    	IApplMessage req = MsgUtil.buildRequest(sender, msgid, msgcontent, destination);
    	CommUtils.outgreen("ServerCallerInteraction | send " + req);

    	String answerTcp = connTcp.request( req.toString() );  
    	CommUtils.outcyan("ServerCallerInteraction | answer " + answerTcp);   	
   	
    	String answerCoap = connCoap.request(req.toString());  
    	CommUtils.outblue("ServerCallerInteraction | answer " + answerCoap);   	    	
	}
	
	public void emitAlerm(String path) throws Exception {		
      	IApplMessage alarmev = MsgUtil.buildEvent(sender, "alarm", "alarm(tsunami)" );
      	 
      	connCoap.forward(alarmev.toString());
	}

     public static void main( String[] args)   {
    	new ServerCallerInteraction().doJob(); 
    	//Thread.sleep(2000);
    	CommUtils.outyellow("ServerCallerInteraction BYE "  );
    }
}
