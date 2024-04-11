package main;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.BasicMsgUtil;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class ServiceCallerInteraction {
 	private Interaction conn ;
 	private String  nfibo = "25";
 	private String payload="dofibo(N)".replace("N", nfibo);
 	//private IApplMessage req       = BasicMsgUtil.buildRequest("clientJava", "dofibo", payload, "servicemath");
 	private IApplMessage req       = BasicMsgUtil.buildRequest("clientJava", "dofibo", payload, "smathasynchfacade");
 	private String mqttAnswerTopic = "answ_dofibo_clientJava";
	
	public void doJob() {
	 try {
		//http, ws, tcp, udp, coap, mqtt, bluetooth, serial
		 /*3*/  selectAndSend(ProtocolType.tcp);
		 /*4*/  selectAndSend(ProtocolType.mqtt);
		 /*5*/  selectAndSend(ProtocolType.coap);
//		 /*5*/  selectAndSend(ProtocolType.ws);
		 /*6*/  Thread.sleep(5000);
		 /*7*/  System.exit(0);
	 }catch(Exception e){
	    CommUtils.outred("ERROR " + e.getMessage() );
	 }      
   }

    protected void selectAndSend(
            ProtocolType protocol) throws Exception{
        String hostAddr="";
        String entry   ="";
        switch( protocol ) {
    /*1*/ case tcp : {
            hostAddr = "localhost";
            entry    = "8033";
            break;
          }
    /*2*/  case coap : {
            hostAddr = "localhost:8033";
            //entry    = "ctxservice/servicemath";
            entry    = "ctxsmathfacade/smathasynchfacade";
            break;
          }
    /*3*/ case mqtt : {
            hostAddr = "tcp://broker.hivemq.com";
            //entry    = "unibo/qak/servicemath";
            entry    = "unibo/qak/smathasynchfacade";
             break;
          }
//    /*4*/ case ws : {
//    	     hostAddr = "localhost:8088/accessgui";
//    	     entry    = nfibo; //"request/"+nfibo;  
//    	     break;
//  	      }
          default:{               
          }
        }//switch
        /*5*/ conn = ConnectionFactory.createClientSupport(
                protocol, hostAddr, entry);
		/*6*/  //((Connection)conn).trace = false;
		/*7*/  sendRequestSynch( req, conn, protocol );
		/*8*/  sendRequestAsynch( req, conn, protocol );
		/*9*/ conn.close();
    }
  	
	protected  void sendRequestSynch( IApplMessage m, Interaction conn, ProtocolType protocol  ) throws Exception {
		String answer = "todo";
		if(protocol==ProtocolType.ws) answer = conn.request("request/"+nfibo); 
		else  answer = conn.request(req.toString()); 
		CommUtils.outmagenta( protocol + " | sendRequestSynch answer=" + answer );   	
    }  

    protected  void sendRequestAsynch( IApplMessage m, Interaction conn, ProtocolType protocol  ) throws Exception {
    	if(protocol==ProtocolType.mqtt) 
    		((MqttConnection) conn).setupConnectionForAnswer(mqttAnswerTopic);    
    	if(protocol==ProtocolType.ws) conn.forward(  nfibo );
    	else conn.forward(req.toString()); 
		String answer = conn.receiveMsg();
		CommUtils.outmagenta( protocol + " | sendRequestAsynch answer=" + answer  );   	
    }  
    
    
    protected void useHTTP() {
   	 try {
        String hostAddr="localhost:8088/RestApi/testHTTP";
        String entry   = req.toString();
        //conn = HttpConnection.create( "localhost:8088" );
        conn = ConnectionFactory.createClientSupport(ProtocolType.http, hostAddr, "");
		String answer = conn.request(entry); 
		CommUtils.outmagenta( "ServiceCallerInteraction | useHTTP answer=" + answer  ); 
	 }catch(Exception e){
		CommUtils.outred("ERROR " + e.getMessage() );
	 }     	
    }
    
    public static void main( String[] args) {
    	new ServiceCallerInteraction().doJob();
    	//new ServiceCallerInteraction().useHTTP();
    }

}
