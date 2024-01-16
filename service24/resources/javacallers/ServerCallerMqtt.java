package javacallers;

import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;

public class ServerCallerMqtt {
	private MqttConnection mqtt;
	private String demotopic  = "unibo/qak/servicemath"; //"servicemathsynch/events";
	private String brokerAddr = "tcp://broker.hivemq.com"; // : 1883  OPTIONAL
	//"tcp://test.mosquitto.org"
	
	public void doJob() {
    	IApplMessage req = MsgUtil.buildRequest("tester", "dofibo", "dofibo(23)", "servicemath");
    	CommUtils.outgreen("send " + req);
    	String answer = sendMessageMqtt( req );  
    	CommUtils.outgreen(answer);   	
     }
    
    protected  String sendMessageMqtt( IApplMessage m  ) {
	        try {
            //CommUtils.outyellow("sendMessageMqtt  " + m );
            Interaction conn = MqttConnection.create("javacaller", brokerAddr, demotopic);
            CommUtils.outyellow("sendMessageMqtt conn " + conn + " for m="+m);
            if( m.isRequest() ) {
               String answer = conn.request(m.toString()); //sincrono
               return answer;                
           }else{
               conn.forward(m.toString());                
               return "sendMessageMqtt done";
           }
       }catch(Exception e){
    	   CommUtils.outred("ERROR " + e.getMessage() );
           return "sendMessageMqtt failed";
       }   			
    }  
    
    public static void main( String[] args) {
    	new ServerCallerMqtt().doJob();
    }
}
