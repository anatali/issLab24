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

public class ServiceCallerInteraction {
 	private Interaction conn ;
 	private IApplMessage req = BasicMsgUtil.buildRequest("tester", "dofibo", "dofibo(17)", "servicemath");
	
	public void doJob() {
	 try {
		//http, ws, tcp, udp, coap, mqtt, bluetooth, serial
		sendRequest(ProtocolType.tcp);
		sendRequest(ProtocolType.mqtt);
		sendRequest(ProtocolType.coap);
		// sendRequest(ProtocolType.http);
		//Thread.sleep(3000);
    	System.exit(0);
	 }catch(Exception e){
	    CommUtils.outred("ERROR " + e.getMessage() );
	 }      
   }
    
	protected void sendRequest(ProtocolType protocol) throws Exception{
		switch( protocol ) {
			case tcp : {
				conn = TcpConnection.create("localhost", 8011);
				sendRequest( req, conn, protocol );
				break;
			}
			case http : {
				conn = HttpConnection.create( "localhost:8088" );
				//sendRequest( req, conn, protocol ); //FA POST => 405,"error":"Method Not Allowed"
				String answer = conn.request("/"); 
				CommUtils.outblue( protocol + " | answer=" + answer );   	
				break;
			}
			case coap : {
				Connection.trace = true;
				String path = "ctxservice/servicemath";
				conn = CoapConnection.create("localhost:8011", path);
				sendRequest( req, conn, protocol );
				break;
			}
			case mqtt : {
				String servicetopic = "unibo/qak/servicemath"; //"servicemathsynch/events";
				String brokerAddr = "tcp://broker.hivemq.com"; // : 1883  OPTIONAL
				//"tcp://test.mosquitto.org"
 				conn = MqttConnection.create("javacaller", brokerAddr, servicetopic);
//				String answer = conn.request(req.toString()); 
//				CommUtils.outblue("mqtt answwr=" + answer );
				sendRequest( req, conn, protocol);
				break;
			}
			//WS connection not allowed
			default:{
				
			}
		}
			
	}
    protected  void sendRequest( IApplMessage m, Interaction conn, ProtocolType protocol  ) throws Exception {
		String answer = conn.request(req.toString()); 
		CommUtils.outblue( protocol + " | answer=" + answer );   	
    }  
    
    public static void main( String[] args) {
    	new ServiceCallerInteraction().doJob();
    }

}
