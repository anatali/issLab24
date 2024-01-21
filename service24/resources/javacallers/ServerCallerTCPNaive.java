package javacallers;

import java.io.*;
import java.net.Socket;
import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class ServerCallerTCPNaive {
	private final String destination = "servicemathcoded";
	private final String sender      = "clientjava";
	private final String hostAddr    = "localhost";
	private final int    port        = 8011;
	private final String msgid       = "dofibo";
	private final String msgcontent  = "dofibo(39)";
	private Socket socket   ;

	public void doJob() {
		try {
			socket  =  new Socket( hostAddr, port );
			sendUsingTcp(   );
			receiveAnswer( );
			socket.close();
		}catch(Exception e){
			CommUtils.outred("ERROR " + e.getMessage() );
		}
	}

	protected void receiveAnswer( ) throws Exception {
		InputStream inStream    = socket.getInputStream();
		BufferedReader inputChannel = new BufferedReader(new InputStreamReader(inStream));
		String	answer = inputChannel.readLine() ;
		CommUtils.outblue("ServerCallerTCPNaive | answer: " + answer);
	}
	protected  void sendUsingTcp( ) throws Exception{
		IApplMessage req = MsgUtil.buildRequest(sender,msgid,msgcontent,destination);
		OutputStream outStream  = socket.getOutputStream();
		DataOutputStream outputChannel = new DataOutputStream(outStream);
		outputChannel.writeBytes(req+"\n" );
		outputChannel.flush();
	}

	/*
    private String destination = "servicemathcoded";
	public void doJob() {
    	//IApplMessage req = MsgUtil.buildRequest("tester", "dofibo", "dofibo(41)", destination);
    	sendMessageTcp("dofibo(41)","localhost", 8011);  
     }
    
    protected  void sendMessageTcp(String m, String addr, int port ) {
	   try {
		   IApplMessage req             =  MsgUtil.buildRequest("client", "dofibo", m, destination);
	        Socket socket           =  new Socket( addr, port );
			OutputStream outStream  = socket.getOutputStream();
			InputStream inStream    = socket.getInputStream();
			DataOutputStream outputChannel = new DataOutputStream(outStream);
			BufferedReader inputChannel    = new BufferedReader(new InputStreamReader(inStream));
            CommUtils.outblue("ServerCallerTCPNaive | sendMessageTcp m="+m);
            outputChannel.writeBytes(req+"\n" );
			outputChannel.flush();
			String	answer = inputChannel.readLine() ;
			CommUtils.outblue("ServerCallerTCPNaive | sendMessageTcp answer: " + answer);    
			socket.close();
       }catch(Exception e){
    	   CommUtils.outred("ERROR " + e.getMessage() );    	    
       }   			
    }   
    */
    public static void main( String[] args)   {
    	new ServerCallerTCPNaive().doJob();
    	//Thread.sleep(2000);
    	//CommUtils.outyellow("sendMessageTcp BYE "  );
    }
}
