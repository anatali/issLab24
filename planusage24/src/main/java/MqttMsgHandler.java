package main.java;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

/*1*/public class MqttMsgHandler implements MqttCallback{
private MqttMessage message = null;

  @Override
  public void connectionLost(Throwable cause) {
    // TODO Auto-generated method stub
  }

  @Override
/*2*/public void messageArrived(
        String topic, MqttMessage msg) throws Exception {
    this.message = msg;
  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
    // TODO Auto-generated method stub
  }

/*3*/public synchronized String getMsg() throws Exception {
    while( message == null ) {
      CommUtils.outyellow("MqttMsgHandler|waits ... ");
      Thread.sleep(1000);
    }
     IApplMessage answer = new ApplMessage(message.toString());
     return answer.msgContent();
    // return message.toString();
  }
}
