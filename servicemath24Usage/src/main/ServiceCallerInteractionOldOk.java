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

public class ServiceCallerInteractionOldOk {
 	private Interaction conn ;
 	private IApplMessage req = BasicMsgUtil.buildRequest("tester", "dofibo", "dofibo(21)", "servicemath");
	
	public void doJob() {
	 try {
		//http, ws, tcp, udp, coap, mqtt, bluetooth, serial
		sendRequest(ProtocolType.tcp);
 		sendRequest(ProtocolType.mqtt);
 		sendRequest(ProtocolType.coap);
		// sendRequest(ProtocolType.http);
		Thread.sleep(10000);
		conn.close();
		CommUtils.outblue( "ServiceCallerInteraction | BYE"   ); 
    	System.exit(0);
	 }catch(Exception e){
	    CommUtils.outred("ERROR " + e.getMessage() );
	 }      
   }
    
	protected void sendRequest(ProtocolType protocol) throws Exception{
		switch( protocol ) {
			case tcp : {
				//conn = TcpConnection.create("localhost", 8011);
				conn = ConnectionFactory.createClientSupport(protocol, "localhost", "8011");
				//sendRequestSynch( req, conn, protocol );
				//sendRequestAsynch( req, conn, protocol );
				
				break;
			}
			case coap : {
				String path = "ctxservice/servicemath";
				//conn = CoapConnection.create("localhost:8011", path);
				conn = ConnectionFactory.createClientSupport(protocol, "localhost:8011", path);
				//conn.trace = true;
				//sendRequestSynch( req, conn, protocol );
				//sendRequestAsynch( req, conn, protocol );
				break;
			}
			case mqtt : {
				String servicetopic = "unibo/qak/servicemath"; //"servicemathsynch/events";
				String brokerAddr = "tcp://broker.hivemq.com"; // : 1883  OPTIONAL //"tcp://test.mosquitto.org"
 				//conn = MqttConnection.create("javacaller", brokerAddr, servicetopic);
 				conn = ConnectionFactory.createClientSupport(protocol, brokerAddr, servicetopic);
 				//sendRequestSynch( req, conn, protocol);
				//sendRequestAsynch( req, conn, protocol );
				break;
			}
			case http : {
				conn = HttpConnection.create( "localhost:8088" );
				//sendRequest( req, conn, protocol ); //FA POST => 405,"error":"Method Not Allowed"
				String answer = conn.request("/"); 
				CommUtils.outblue( protocol + " | answer=" + answer );   	
				break;
			}
			//WS connection not allowed. Ma potrei?
			default:{
				
			}
		}
		if( protocol != ProtocolType.http && conn != null ) 
			sendRequestSynch( req, conn, protocol );
			//sendRequestAsynch( req, conn, protocol );	
	}
    protected  void sendRequestSynch( IApplMessage m, Interaction conn, ProtocolType protocol  ) throws Exception {
		String answer = conn.request(req.toString()); 
		CommUtils.outmagenta( protocol + " | answer=" + answer );   	
    }  

    protected  void sendRequestAsynch( IApplMessage m, Interaction conn, ProtocolType protocol  ) throws Exception {
    	if(protocol==ProtocolType.mqtt) ((MqttConnection) conn).setupConnectionForAnswer("answ_dofibo_tester"); 
		conn.forward(req.toString()); 
		String answer = conn.receiveMsg();
		CommUtils.outmagenta( protocol + " | sendRequestAsynch answer=" + answer  );   	
    }  
    
    public static void main( String[] args) {
    	new ServiceCallerInteractionOldOk().doJob();
    }

}
