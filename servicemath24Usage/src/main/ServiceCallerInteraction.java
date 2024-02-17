package main;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.http.HttpConnection;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.tcp.TcpConnection;
import unibo.basicomm23.utils.BasicMsgUtil;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.Connection;
import unibo.basicomm23.utils.ConnectionFactory;

public class ServiceCallerInteraction {
 	private Interaction conn ;
 	private IApplMessage req = BasicMsgUtil.buildRequest("tester", "dofibo", "dofibo(21)", "servicemath");
	
	public void doJob() {
	 try {
		//http, ws, tcp, udp, coap, mqtt, bluetooth, serial
		 /*3*/  selectAndSend(ProtocolType.tcp);
		 /*4*/  selectAndSend(ProtocolType.mqtt);
		 /*5*/  selectAndSend(ProtocolType.coap);
		 /*6*/  Thread.sleep(5000);
		 /*7*/  conn.close();
		 /*8*/  System.exit(0);
	 }catch(Exception e){
	    CommUtils.outred("ERROR " + e.getMessage() );
	 }      
   }
    
    protected void selectAndSend(
            ProtocolType protocol) throws Exception{
        String hostAddr="";
        String entry="";
        switch( protocol ) {
    /*1*/ case tcp : {
            hostAddr = "localhost";
            entry    = "8011";
            break;
          }
    /*2*/  case coap : {
            Connection.trace = true;
            hostAddr = "localhost:8011";
            entry    = "ctxservice/servicemath";
            break;
          }
    /*3*/ case mqtt : {
            hostAddr = "tcp://broker.hivemq.com";
            entry    = "unibo/qak/servicemath";
             break;
          }
          default:{               
          }
        }//switch
    /*9*/ conn = ConnectionFactory.createClientSupport(
                             protocol, hostAddr, entry);             
    /*10*/ sendRequestSynch( req, conn, protocol );
    /*11*/ //sendRequestAsynch( req, conn, protocol );	
    }  
	
	protected  void sendRequestSynch( IApplMessage m, Interaction conn, ProtocolType protocol  ) throws Exception {
		String answer = conn.request(req.toString()); 
		CommUtils.outmagenta( protocol + " | answer=" + answer );   	
    }  

    protected  void sendRequestAsynch( IApplMessage m, Interaction conn, ProtocolType protocol  ) throws Exception {
    	if(protocol==ProtocolType.mqtt) 
    		((MqttConnection) conn).setupConnectionForAnswer("answ_dofibo_tester"); 
		conn.forward(req.toString()); 
		String answer = conn.receiveMsg();
		CommUtils.outmagenta( protocol + " | sendRequestAsynch answer=" + answer  );   	
    }  
    
    public static void main( String[] args) {
    	new ServiceCallerInteraction().doJob();
    }

}
