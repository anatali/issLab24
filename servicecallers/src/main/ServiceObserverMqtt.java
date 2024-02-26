package main;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;

/*1*/public class ServiceObserverMqtt 
                              implements MqttCallback{
/*2*/private String serverouttopic="servicemathouttopic"; 
/*3*/  private String brokerAddr  =
                             "tcp://broker.hivemq.com"; 
/*4*/  private final String sender="clientjava";
  private MqttConnection connMqtt;
    
  public void doJob() {
   try {
    MqttConnection connMqtt = MqttConnection.create();
/*5*/connMqtt.connect(sender, brokerAddr);
    CommUtils.outblue("ServiceObserverMqtt | connetced to broker"  );
/*6*/connMqtt.setCallback( this );
/*7*/connMqtt.subscribe( serverouttopic );
   }catch(Exception e){
      CommUtils.outred("ERROR " + e.getMessage() );
   }     
  }            
  /*
  * Metodi di IApplMsgHandlerMqtt
  */
  @Override
  /*8*/public void messageArrived(String topic, 
           MqttMessage message) throws Exception {
    CommUtils.outblue(
      "ServerCallerMqtt|arrived:"+message+" on:"+topic);            
  }         
  @Override
  public void connectionLost(Throwable cause) {
    // TODO Auto-generated method stub           
  }
  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
    // TODO Auto-generated method stub            
  }
  
  public static void main( String[] args) {
      new ServiceObserverMqtt().doJob();
  }
}
