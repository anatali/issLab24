package main.java;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.BasicMsgUtil;
import unibo.basicomm23.utils.CommUtils;

public class MapMqttClient {
//    private final String destination = "servicemath";
//    private final String sender      = "clientmqtt";
//    private final String msgid       = "dofibo";
//    private final String msgcontent  = "dofibo(33)";
    private MqttClient client;

/*1*/private String servicetopic       = "unibo/qak/servicemath";
/*2*/private String serviceanswertopic = "unibodisi";
/*3*/private String brokerAddr="wss://test.mosquitto.org:8081"; 
     //private String brokerAddr="tcp://broker.hivemq.com";//:1883  OPTIONAL

    public void doJob() {
      try {
/*4*/  client = new MqttClient(
          brokerAddr, "answerconsumer", new MemoryPersistence());
/*5*/  client.connect();
CommUtils.outblue("client CONNECTED");
///*6*/  IApplMessage req = BasicMsgUtil.buildRequest(
//                sender, msgid, msgcontent, destination);
///*7*/  sendMessageMqtt( req );
/*8*/  receiveAnswer( );
      System.exit(0);
      }catch(Exception e){
        CommUtils.outred("ERROR " + e.getMessage() );
      }
    }

    protected  void sendMessageMqtt( IApplMessage m  )
                throws MqttSecurityException, MqttException {
      MqttMessage mqttmsg = new MqttMessage();
      mqttmsg.setQos(2);
      mqttmsg.setPayload(m.toString().getBytes());
      client.publish(servicetopic, mqttmsg);
    }

    protected void receiveAnswer( ) throws Exception {
/*9*/ MqttMsgHandler msgHandler = new MqttMsgHandler();
/*10*/client.setCallback(msgHandler);
/*11*/client.subscribe( serviceanswertopic );
/*12*/String answer = msgHandler.getMsg(); //waits ...
      CommUtils.outblue("receiveAnswer : " + answer );
      client.disconnect();
      client.close();
    }

    public static void main( String[] args)   {
      new MapMqttClient().doJob();

    }
}
