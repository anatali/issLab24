package javacallers;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;

public class ServerObserverMqtt implements MqttCallback  {
	private String serverouttopic  = "servertopic"; 
	private String brokerAddr      = "tcp://broker.hivemq.com"; // : 1883  OPTIONAL
 	private final String sender    = "clientjava";
	
      
	public void doJob() {
	 try {
		MqttConnection connMqtt = MqttConnection.create();
 		connMqtt.connect(sender, brokerAddr);
 		connMqtt.setCallback( this );
 		connMqtt.subscribe( serverouttopic );
 		//Thread.sleep(30000);
 		CommUtils.outyellow("ServerCallerMqtt | waiting ..."   );
      }catch(Exception e){
        CommUtils.outred("ERROR " + e.getMessage() );
     }     
	}
    
 /*
  * Metodi di MqttCallback
  */

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		CommUtils.outblue("ServerCallerMqtt | messageArrived:" + message + " on topic:" + topic);		
	}

	@Override
	public void connectionLost(Throwable cause) {
		CommUtils.outred("ServerCallerMqtt | connectionLost"   );		
	}


	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		CommUtils.outblue("ServerCallerMqtt | deliveryComplete:" + token  );
	}
	
/*
 * 	Main
 */
    
    public static void main( String[] args) {
    	new ServerObserverMqtt().doJob();
    }

}
