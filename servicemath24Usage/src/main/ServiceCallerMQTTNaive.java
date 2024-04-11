package main;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.BasicMsgUtil;
import unibo.basicomm23.utils.CommUtils;

public class ServiceCallerMQTTNaive {
	private final String destination = "smathasynchfacade"; //"servicemath";
	private final String sender      = "clientmqtt";
	private final String msgid       = "dofibo";
	private final String msgcontent  = "dofibo(30)";

	private String servicetopic       = "unibo/qak/"+destination;  
	private String serviceanswertopic = "answ_dofibo_clientmqtt";
	private String brokerAddr = "tcp://broker.hivemq.com"; // : 1883  OPTIONAL
	//"tcp://test.mosquitto.org"
	private MqttClient client;

	
	public void doJob() {
       try {
       	client = new MqttClient(brokerAddr, "answerconsumer", new MemoryPersistence());
       	client.connect();
        CommUtils.outyellow("doJob client " + client + " connected" );
    	IApplMessage req = BasicMsgUtil.buildRequest(
    			sender, msgid, msgcontent, destination);
    	CommUtils.outblack("send " + req);
    	sendMessageMqtt( req );  
    	receiveAnswer( );
        //dorequest(m);   
    	System.exit(0);
       }catch(Exception e){
         CommUtils.outred("ERROR " + e.getMessage() );
       }    
    }

    protected  void sendMessageMqtt( IApplMessage m  ) throws MqttPersistenceException, MqttException   {
      //CommUtils.outyellow("sendMessageMqtt  " + m );
      MqttMessage mqttmsg = new MqttMessage();
      mqttmsg.setQos(2);
      mqttmsg.setPayload(m.toString().getBytes());		 
      client.publish(servicetopic, mqttmsg);			 
    }
    
    //MqttConnection � una abstraction
    protected void dorequest(IApplMessage m) throws Exception {
    	MqttConnection mqttconn = MqttConnection.create(sender,brokerAddr,servicetopic );
        CommUtils.outyellow("dorequest | conn " + mqttconn + " for m="+m);
        IApplMessage answer = mqttconn.request(m);
        CommUtils.outblue("dorequest | answer=" + answer );
        System.exit(0);
    }
    
    protected void receiveAnswer( ) throws Exception   {      
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
    	
    }

    public static void main( String[] args)   {
    	new ServiceCallerMQTTNaive().doJob(); 
    }
}
