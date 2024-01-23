package javacallers;

import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;

public class ServerCallerMqtt {
	private String servicetopic  = "unibo/qak/servicemathcoded"; 
	private String brokerAddr = "tcp://broker.hivemq.com"; // : 1883  OPTIONAL
	private final String destination = "servicemathcoded";
	private final String sender      = "clientjava";
	private final String msgid       = "dofibo";
	private final String msgcontent  = "dofibo(39)";
	
    private Interaction connMqtt;
    
	public void doJob() {
	 try {
 		connMqtt = MqttConnection.create("javacaller", brokerAddr, servicetopic);
    	dorequest();
     }catch(Exception e){
        CommUtils.outred("ERROR " + e.getMessage() );
     }     
	}
    
    protected  void dorequest(   ) throws Exception {
        //CommUtils.outyellow("sendMessageMqtt  " + m );
    	IApplMessage req = MsgUtil.buildRequest(sender, msgid, msgcontent, destination);
    	CommUtils.outyellow("ServerCallerMqtt | sendMessageMqtt connMqtt=" + connMqtt + " for req="+req);
        String answerMqtt = connMqtt.request(req.toString());  
        CommUtils.outblue("ServerCallerMqtt | answer:" + answerMqtt);               
    }  
    
    public static void main( String[] args) {
    	new ServerCallerMqtt().doJob();
    }
}
