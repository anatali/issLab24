package main;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class MqttMsgHandler implements MqttCallback{
private MqttMessage message = null;
	
	public synchronized String getMsg() throws Exception  {
	        //CommUtils.outyellow("MqttMsgHandler | getMsg " + message   );
			while( message == null ) {
				CommUtils.outyellow("MqttMsgHandler | waits ... ");
				Thread.sleep(1000);
			}
			//CommUtils.outyellow("MqttMsgHandler | message= " + message);
			IApplMessage answer = new ApplMessage(message.toString());
			return answer.msgContent();
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage msg) throws Exception {
 		CommUtils.outmagenta("MqttMsgHandler | messageArrived:" + msg + " on topic="+topic  );
 		this.message = msg;		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}

}
