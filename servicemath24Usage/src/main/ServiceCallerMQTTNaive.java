package main;

import org.eclipse.paho.client.mqttv3.MqttClient;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.BasicMsgUtil;
import unibo.basicomm23.utils.CommUtils;

public class ServiceCallerMQTTNaive {
	private final String destination = "servicemath";
	private final String sender      = "clientmqtt";
	private final String hostAddr    = "localhost";
	private final int    port        = 8011;
	private final String msgid       = "dofibo";
	private final String msgcontent  = "dofibo(33)";

	private String servicetopic       = "unibo/qak/servicemath";  
	private String serviceanswertopic = "unibo/qak/servicemath";
	private String brokerAddr = "tcp://broker.hivemq.com"; // : 1883  OPTIONAL
	//"tcp://test.mosquitto.org"
	private MqttConnection mqttconn;

	
	public void doJob() {
    	IApplMessage req = BasicMsgUtil.buildRequest("tester", "dofibo", "dofibo(37)", "servicemath");
    	CommUtils.outblack("send " + req);
    	String answer = sendMessageMqtt( req );  
    	CommUtils.outblack(answer); 
    	System.exit(0);
     }

    protected  void sendMessageMqtt( IApplMessage m  ) {
        try {
        //CommUtils.outyellow("sendMessageMqtt  " + m );
        	mqttconn = MqttConnection.create(sender,brokerAddr,servicetopic );
	        CommUtils.outyellow("sendMessageMqtt conn " + mqttconn + " for m="+m);
	        mqttconn.publish( servicetopic, m.toString() );
        	
 
        }catch(Exception e){
        	CommUtils.outred("ERROR " + e.getMessage() );
        
        }
    }
        
    protected void receiveAnswer( ) throws Exception {      
    	MqttClient c = mqttconn.setupConnectionForAnswer(  serviceanswertopic );
    }
 
}
