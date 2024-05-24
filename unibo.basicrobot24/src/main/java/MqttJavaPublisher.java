package main.java;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import unibo.basicomm23.utils.CommUtils;

public class MqttJavaPublisher {
	private String topic  = "unibodisiplan";	
	private String brokerAddr="wss://test.mosquitto.org:8081"; 
	private MqttClient client;
	private String sonarevent = "msg(sonardata,event,javaclient,none,sonardata(D),0)";
	
	public MqttJavaPublisher() {
		
		
	}

    public void doJob() {
        try {
        	client = new MqttClient(brokerAddr, "clientjava", new MemoryPersistence());
    		client.connect();
  			CommUtils.outblue("client CONNECTED");
  			for( int i=1; i<4; i++) {
  				String msg = sonarevent.replace("D",(""+i*10) );
	  			sendMessageMqtt(msg);
	  			CommUtils.delay(4000);
  			}
  			System.exit(0);
        }catch(Exception e){
          CommUtils.outred("ERROR " + e.getMessage() );
        }
      }
    
    protected  void sendMessageMqtt( String m  )
            throws MqttSecurityException, MqttException {
      CommUtils.outblue("client sendMessageMqttd " + m);
	  MqttMessage mqttmsg = new MqttMessage();
	  mqttmsg.setQos(2);
	  mqttmsg.setPayload(m.getBytes());
	  client.publish(topic, mqttmsg);
	  //CommUtils.outblue("client HAS published " + mqttmsg);
	}    
    
    public static void main( String[] args)   {
        new MqttJavaPublisher().doJob();

      }    
    
}
