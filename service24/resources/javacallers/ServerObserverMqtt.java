package javacallers;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.IApplMsgHandlerMqtt;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;

public class ServerObserverMqtt implements IApplMsgHandlerMqtt  {
	private String servicetopic  = "servertopic"; 
	private String brokerAddr = "tcp://broker.hivemq.com"; // : 1883  OPTIONAL
//	private final String destination = "servicemathcoded";
 	private final String sender      = "clientjava";
//	private final String msgid       = "dofibo";
//	private final String msgcontent  = "dofibo(39)";
	
    private MqttConnection connMqtt;
    
	public void doJob() {
	 try {
 		connMqtt = MqttConnection.create();
 		connMqtt.connect(sender, brokerAddr);
 		connMqtt.subscribe("servertopic", this);
 		//Thread.sleep(30000);
 		CommUtils.outyellow("ServerCallerMqtt | BYE"   );
      }catch(Exception e){
        CommUtils.outred("ERROR " + e.getMessage() );
     }     
	}
    
 /*
  * Metodi di IApplMsgHandlerMqtt
  */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void elaborate(IApplMessage message, Interaction conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		CommUtils.outblue("ServerCallerMqtt | messageArrived:" + message + " on topic:" + topic);
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
	
/*
 * 	Main
 */
    
    public static void main( String[] args) {
    	new ServerObserverMqtt().doJob();
    }

}
