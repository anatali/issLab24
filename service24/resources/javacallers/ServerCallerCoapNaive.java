package javacallers;

import it.unibo.kactor.MsgUtil;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;
 
 

public class ServerCallerCoapNaive {
    private final String destination = "servicemathcoded";
    private final String sender      = "clientcoapjava";
    private final String hostAddr    = "localhost";
    private final int    port        = 8011;
    private final String msgid       = "dofibo";
    private final String msgcontent  = "dofibo(28)";

    private CoapClient client;
    private String url;
    private String path = "ctxservice/servicemathcoded";
    private CoapResponse response;

    public void doJob() {
        try {
            sendUsingCoap(   );
            String answer = receiveAnswer( );
            CommUtils.outblue(  "    ServerCallerCoapNaive | answer="  + answer );
        }catch(Exception e){
            CommUtils.outred("ERROR " + e.getMessage() );
        }
    }
 
    protected  void sendUsingCoap(   ) {
        try {
            IApplMessage req = 
            	MsgUtil.buildRequest(sender, msgid, msgcontent, destination);
            url  = "coap://"+hostAddr+":"+port + "/"+ path;
            client  = new CoapClient( url );
            CommUtils.outyellow(  "    ServerCallerCoapNaive |  " + client );
             response = client.put(req.toString(), MediaTypeRegistry.TEXT_PLAIN);
        }catch(Exception e){
            CommUtils.outred("ERROR " + e.getMessage() );
        }
    }
    
    protected String receiveAnswer( ) {
        if( response != null ) {
            CommUtils.outyellow(  "    ServerCallerCoapNaive |  " 
                    +" RESPONSE CODEEEE: " + response.getCode() + " answer=" + response.getResponseText()  );
            String answer = response.getResponseText();
            return answer;
        }else {
            CommUtils.outred(  "    ServerCallerCoapNaive | "  +" RESPONSE NULL " );
            return null;
        }   	
    }
      
    public static void main( String[] args) throws InterruptedException {
    	new ServerCallerCoapNaive().doJob();  
    	//Thread.sleep(2000);
    	CommUtils.outyellow("sendUsingCoap BYE "  );
    }
}
