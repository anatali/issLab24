package javacallers;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttAnswerHandler;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;

public class MqttDemoEssential {
private String demotopic  = "servicemathsynch/events";
private String brokerAddr = "tcp://broker.hivemq.com"; // : 1883  OPTIONAL
//"tcp://test.mosquitto.org"


private final String caller    = "demo";
private final String receiver  = "servicemath";
private final String requestId = "fibo";

//String sender, String msgId, String payload, String dest
//private String helloMsg  = CommUtils.buildDispatch(caller, "cmd",    "hello",    "someone").toString();
private String aRequest  = CommUtils.buildRequest(caller,  requestId,"fibo(23)",  receiver).toString();
//private String aReply    = Utils.buildReply(receiver,  requestId, "ANSWER",   caller).toString();

 
private MqttConnection mqtt;


public MqttDemoEssential() {
	init();
}

protected void init() {
	mqtt = MqttConnection.create("demo", brokerAddr, demotopic);
	MqttAnswerHandler h = new MqttAnswerHandler("demoH", null, mqtt.getQueue() );
	mqtt.subscribe(demotopic, h);
}

public void simulateReceiver(String name) {
	new Thread() {
		public void run() {
		try {
 			ColorsOut.outappl("receiver STARTS with " + mqtt, ColorsOut.GREEN);
			String inputMNsg = mqtt.receiveMsg();
			ColorsOut.outappl("receiver RECEIVED:"+ inputMNsg, ColorsOut.GREEN);
 		} catch (Exception e) {
			ColorsOut.outerr("receiver  | Error:" + e.getMessage());
	 	}
		}//run
	}.start();
}



protected void end() {
	ColorsOut.outappl( " | disconnect ", ColorsOut.CYAN);
	//mqtt.unsubscribe( topic );	
	mqtt.disconnect();
}

public void doSendReceive() {
	simulateReceiver("r1");
	//SENDER part
	try {

		//mqtt.forward(helloMsg);	//OK
		mqtt.request(aRequest);
		CommUtils.delay(1000);
		end();  //Se no si ha poi un  connectionLost  
		ColorsOut.outappl("doSendReceive BYE BYE" , ColorsOut.BLUE);
 	} catch (Exception e) {
		e.printStackTrace();
	}
}

/*
 * -------------------------------------------------
 * REQUEST - RESPONSE
 * -------------------------------------------------
 */


public void simulateCalled( String name ) {
	new Thread() {
		public void run() {
		try {
 			ColorsOut.outappl(name + "| STARTS with " + mqtt, ColorsOut.GREEN);
			String inputMNsg = mqtt.receiveMsg();  //Si blocca sulla coda popolata da 
			ColorsOut.outappl(name + "| RECEIVED:"+ inputMNsg, ColorsOut.BLACK);
//Elaborate and send answer			
 			IApplMessage msgInput = new ApplMessage(inputMNsg);
			ColorsOut.outappl(name + " | input=" + msgInput + " topic="+demotopic, ColorsOut.GREEN);
			String caller = msgInput.msgSender();
			String reqId  = msgInput.msgId();
			String myReply = CommUtils.buildReply(name ,  reqId, "ANSWER", caller  ).toString();
			String content = "time('" + java.time.LocalTime.now() + "')";
 			String answer  = myReply.replace("ANSWER", content );  
			ColorsOut.outappl(name + "| replies:"+ answer, ColorsOut.GREEN);
			mqtt.reply(answer);  			
 		} catch (Exception e) {
			ColorsOut.outerr(name + " | Error:" + e.getMessage());
	 	}
		}//run 
	}.start(); 
}

public void doRequestRespond() {
	simulateCalled( receiver );
//	simulateCalled( "called2");
	//Caller part 	
	try {
		String req1    = CommUtils.buildRequest(caller,  requestId,"getTime",  "called1").toString();
		String answer1 = mqtt.request(req1);	 //blocking
		CommUtils.outblue(caller + " RECEIVED answer1:"+ answer1 );
//		String req2 = Utils.buildRequest(caller,  requestId,"getTime",  "called2").toString();
//		String answer2 = mqtt.request(req2);	   //blocking
//		Colors.outappl(caller + " RECEIVED answer2:"+ answer2, Colors.BLACK);
 	} catch (Exception e) {
		e.printStackTrace();
	}
}

 
	public static void main(String[] args) throws Exception  {
		//RadarSystemConfig.mqttBrokerAddr  = "tcp://localhost:1883";  
		//RadarSystemConfig.mqttBrokerAddr  = "tcp://broker.hivemq.com";
		 //mqttBrokerAddr  = "tcp://test.mosquitto.org";
		//RadarSystemConfig.mqttBrokerAddr  = "tcp://mqtt.eclipse.org:1883";  //NO
		MqttDemoEssential appl = new MqttDemoEssential();	
		
 		appl.doSendReceive();
		//appl.doRequestRespond();
  		
  		System.exit(0);
 	}
 
}
