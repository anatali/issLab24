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
    private final String msgcontent  = "dofibo(39)";

    //protected CoapConnection conn;
    protected CoapClient client;
    protected String url;
    protected String path = "ctxservice/servicemathcoded";
    protected CoapResponse response;

    public void doJob() {
        try {
            url  = "coap://"+hostAddr+":"+port + "/"+ path;
            //conn = new CoapConnection(hostAddr+":"+port,path);
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
            String query = req.msgContent();
            String param = "?q="+query;
            client          = new CoapClient( url );
            CommUtils.outyellow(  "    ServerCallerCoapNaive |  " + client );
            client.setURI(url+param);
            CommUtils.outyellow(  "    ServerCallerCoapNaive |  " + (url+param) );
            response = client.put(query, MediaTypeRegistry.TEXT_PLAIN);
            
//             if( response != null ) {
//                CommUtils.outyellow(  "    ServerCallerCoapNaive | request=" + query
//                        +" RESPONSE CODEEEE: " + response.getCode() + " answer=" + response.getResponseText()  );
//                String answer = response.getResponseText();
//                return answer;
//            }else {
//                CommUtils.outred(  "    ServerCallerCoapNaive | request=" + query +" RESPONSE NULL " );
//                return null;
//            }
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
    	new ServerCallerCoapNaive().doJob(); //dorequest("ctxservice/servicemath");
    	//Thread.sleep(2000);
    	CommUtils.outyellow("sendUsingCoap BYE "  );
    }
}
