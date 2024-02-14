package main;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
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
	private String serviceanswertopic = "answ_dofibo_clientmqtt";
	private String brokerAddr = "tcp://broker.hivemq.com"; // : 1883  OPTIONAL
	//"tcp://test.mosquitto.org"
	private MqttClient client;

	
	public void doJob() {
    	IApplMessage req = BasicMsgUtil.buildRequest(sender, "dofibo", "dofibo(37)", "servicemath");
    	CommUtils.outblack("send " + req);
    	sendMessageMqtt( req );  
     }

    protected  void sendMessageMqtt( IApplMessage m  ) {
        try {
        //CommUtils.outyellow("sendMessageMqtt  " + m );
        	client = new MqttClient(brokerAddr, "answerconsumer", new MemoryPersistence());
        	client.connect();
            CommUtils.outyellow("sendMessageMqtt client " + client + " connected" );
            MqttMessage mqttmsg = new MqttMessage();
            mqttmsg.setQos(2);
            mqttmsg.setPayload(m.toString().getBytes());		 
			client.publish(servicetopic, mqttmsg);
 
			receiveAnswer( ); 
	        //dorequest(m);   
        }catch(Exception e){
        	CommUtils.outred("ERROR " + e.getMessage() );
        }
    }
    
    //MqttConnection è una abstraction
    protected void dorequest(IApplMessage m) throws Exception {
    	MqttConnection mqttconn = MqttConnection.create(sender,brokerAddr,servicetopic );
        CommUtils.outyellow("dorequest | conn " + mqttconn + " for m="+m);
        IApplMessage answer = mqttconn.request(m);
        CommUtils.outblue("dorequest | answer=" + answer );
        System.exit(0);
    }
    
    protected void receiveAnswer( ) throws Exception {      
//    	MqttClient client = new MqttClient(brokerAddr, "answerconsumer", new MemoryPersistence());
//    	client.connect();
//        CommUtils.outyellow("receiveAnswer client " + client + " connected" );
     	MqttMsgHandler msgHandler = new MqttMsgHandler();
    	client.setCallback(msgHandler);
    	client.subscribe( serviceanswertopic );
    	String answer = msgHandler.getMsg(); //waits ...
        CommUtils.outblue("receiveAnswer : " + answer );
    	client.disconnect();
    	client.close();
    	System.exit(0);
    }

    public static void main( String[] args)   {
    	new ServiceCallerMQTTNaive().doJob(); 
    }
}
