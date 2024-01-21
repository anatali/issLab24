package javacallers;

import it.unibo.kactor.MsgUtil;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.Connection;

public class ServerCallerCoapOld {
    private final String destination = "servicemathcoded";
    private final String sender      = "clientjava";
    private final String hostAddr    = "localhost";
    private final int    port        = 8011;
    private final String msgid       = "dofibo";
    private final String msgcontent  = "dofibo(39)";

    protected CoapConnection conn;
    protected CoapClient client;
    protected String url;
    protected String path = "ctxservice/servicemath";

    public void doJob() {
        try {
            url  = "coap://"+hostAddr+":"+port + "/"+ path;
            conn = new CoapConnection(hostAddr+":"+port,path);
            //sendUsingCoap(   );
            //receiveAnswer( );

        }catch(Exception e){
            CommUtils.outred("ERROR " + e.getMessage() );
        }
    }


	public void dorequest(String path) {
    	IApplMessage req = MsgUtil.buildRequest("testercoap", "dofibo", "dofibo(18)", "servicemath");
    	CommUtils.outgreen("ServerCallerCoap | send " + req);
    	String answer = sendUsingCoap(req,"localhost", 8011,path);  
    	CommUtils.outgreen("ServerCallerCoap | answer " + answer);   	
	}
	public void emitAlerm(String path) {		
      	IApplMessage alarmev = MsgUtil.buildEvent("testercoap", "alarm", "alarm(tsunami)" );
      	sendUsingCoap(alarmev,"localhost", 8011, path);  
	}

    protected  String sendUsingCoap( IApplMessage m  ) {
        try {
            IApplMessage req = MsgUtil.buildRequest("testercoap", msgid, msgcontent, destination);
            String query = m.msgContent();
            String param = "?q="+query;
            client          = new CoapClient( url );
            client.setURI(url+param);
            CoapResponse response = client.put(query, MediaTypeRegistry.TEXT_PLAIN);
             if( response != null ) {
                CommUtils.outyellow(  "    +++ CoapConn | request=" + query
                        +" RESPONSE CODEEEE: " + response.getCode() + " answer=" + response.getResponseText()  );
                String answer = response.getResponseText();
                return answer;
            }else {
                CommUtils.outred(  "    +++ CoapConn | request=" + query +" RESPONSE NULL " );
                return null;
            }
        }catch(Exception e){
            CommUtils.outred("ERROR " + e.getMessage() );
            return "sendUsingCoap failed";
        }

    }
    protected  String sendUsingCoap(IApplMessage m, String addr, int port, String path ) {
	        try {
	        Connection.trace = true;
            Interaction conn = CoapConnection.create(addr+":"+port, path ); //ctxservice/servicemath
            CommUtils.outblue("ServerCallerCoap | sendUsingCoap conn " + conn + " for m="+m);
            if( m.isRequest() ) {
               String answer =  conn.request(m.toString()); //sincrono
               //CommUtils.outblue("ServerCallerCoap | sendUsingCoap answer: " + answer);
               conn.close();
               return  answer;                   
           }else{
               conn.forward(m.toString());    
               conn.close();
               return "sendUsingCoap done " + m;
           }
            
       }catch(Exception e){
    	   CommUtils.outred("ERROR " + e.getMessage() );
           return "sendUsingCoap failed";
       }   			
    }   
    
    public static void main( String[] args) throws InterruptedException {
//    	new ServerCallerCoap().dorequest("ctxservice");
//    	new ServerCallerCoap().emitAlerm("ctxservice");
    	new ServerCallerCoapOld().dorequest("ctxservice/servicemath");
    	//Thread.sleep(2000);
    	CommUtils.outyellow("sendUsingCoap BYE "  );
    }
}
