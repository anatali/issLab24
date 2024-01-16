package javacallers;

import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.tcp.TcpConnection;
import unibo.basicomm23.utils.CommUtils;

public class ServerCaller {
//	private Interaction conn;
	
    public ServerCaller(){
//        try {
//			conn = new TcpConnection("localhost", 8011);
//	        CommUtils.outmagenta("ServerCaller conn="+conn);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
    
//    protected  String sendMessage(IApplMessage m ) {
//	   try {
//		   CommUtils.outyellow("sendMessage  " + m );
//           conn.request(m.toString());
//           if( m.isRequest() ) {
//               String answer = conn.request(m.toString()); //sincrono
//               return answer;
//                
//           }else{
//               conn.forward(m.toString());                
//               return "sendMessage done";
//           }
//       }catch(Exception e){
//    	   CommUtils.outred("ERROR " + e.getMessage() );
//           return "sendMessage failed";
//       }   			
//    } 
    
//    protected void testServer8(){
//        try {
//         	IApplMessage req = MsgUtil.buildRequest("tester", "dofibo", "dofibo(8)", "service");
//        	String answer    =  sendMessage(req );
//         	CommUtils.outgreen(answer);
//         } catch (Exception e) {
//        	 CommUtils.outred("ERROR " + e.getMessage() );
//        }
//    }
//    
//    protected void testServer40(){
//        try {
//        	IApplMessage req = MsgUtil.buildRequest("tester", "dofibo", "dofibo(40)", "service");
//        	String answer    =  sendMessage(req );
//         	CommUtils.outblue(answer);
//         } catch (Exception e) {
//        	 CommUtils.outred("ERROR " + e.getMessage() );
//        }
//    }    

    public void doJob() {
    	new Thread() {
    		public void run() {
             	try {
             		//Interaction conn = new TcpConnection("localhost", 8011);
             		IApplMessage req = MsgUtil.buildRequest("tester", "dofibo", "dofibo(8)", "service");
             		CommUtils.outgreen("send " + req);
             		String answer = sendMessageTcp(req,"localhost", 8011); //conn.request(req.toString());
             		CommUtils.outgreen(answer);
				} catch (Exception e) {
					CommUtils.outred("ERROR " + e.getMessage() );
				}
    		}
    	}.start();
    	
    	new Thread() {
    		public void run() {
            	try {
            		//Interaction conn = new TcpConnection("localhost", 8011);
            		IApplMessage req = MsgUtil.buildRequest("tester", "dofibo", "dofibo(40)", "service");
            		CommUtils.outblue("send " + req);
            		String answer = sendMessageTcp(req,"localhost", 8011); //conn.request(req.toString());
             		CommUtils.outblue(answer);
				} catch (Exception e) {
					CommUtils.outred("ERROR " + e.getMessage() );
				}
     		}
    	}.start();
    	
    	new Thread() {
    		public void run() {
            	try {
            		//Interaction conn = new TcpConnection("localhost", 8011);
            		IApplMessage req = MsgUtil.buildRequest("tester", "dofibo", "dofibo(35)", "service");
            		CommUtils.outcyan("send " + req);
              		String answer = sendMessageTcp(req,"localhost", 8011); //conn.request(req.toString());
             		CommUtils.outcyan(answer);
				} catch (Exception e) {
					CommUtils.outred("ERROR " + e.getMessage() );
				}
     		}
    	}.start();
    }
    
    protected  String sendMessageTcp(IApplMessage m, String addr, int port ) {
	        try {
            //CommUtils.outyellow("sendMessageTcp  " + m );
            Interaction conn = new TcpConnection(addr, port);
            CommUtils.outyellow("sendMessageTcp conn " + conn + " for m="+m);
            if( m.isRequest() ) {
               String answer = conn.request(m.toString()); //sincrono
               return answer;                
           }else{
               conn.forward(m.toString());                
               return "sendMessageTcp done";
           }
       }catch(Exception e){
    	   CommUtils.outred("ERROR " + e.getMessage() );
           return "sendMessageTcp failed";
       }   			
    }  
    
    public static void main( String[] args) {
    	new ServerCaller().doJob();
    }
}
