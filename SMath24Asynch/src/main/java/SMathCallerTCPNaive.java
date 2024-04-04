package main.java;

import java.io.*;
import java.net.Socket;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.BasicMsgUtil;

public class SMathCallerTCPNaive {
	private final String destination = "smathsynch";
	private final String sender      = "clientjava";
	private final String hostAddr    = "localhost";
	private final int    port        = 8033;
	private final String msgid       = "dofibo";
	private final String msgcontent  = "dofibo(33)";
	private Socket socket   ;

	public void doJob() {
		try {
			socket           =  new Socket( hostAddr, port );
			IApplMessage req = BasicMsgUtil.buildRequest(sender,msgid,msgcontent,destination);
			sendUsingTcp( req  );
			receiveAnswer( );
			socket.close();
		}catch(Exception e){
			CommUtils.outred("ERROR " + e.getMessage() );
		}
	}

	protected void receiveAnswer( ) throws Exception {
		InputStream inStream        = socket.getInputStream();
		BufferedReader inputChannel = new BufferedReader(new InputStreamReader(inStream));
		String	answer   = inputChannel.readLine() ;
		IApplMessage msg = new ApplMessage(answer);		
		CommUtils.outblue("ServerCallerTCPNaive | answer: " + answer);
		CommUtils.outblue("ServerCallerTCPNaive | content: " + msg.msgContent());
	}
	
	protected  void sendUsingTcp( IApplMessage req ) throws Exception{
		CommUtils.outblue("ServerCallerTCPNaive | req: " + req);
		OutputStream outStream  = socket.getOutputStream();
		DataOutputStream outputChannel = new DataOutputStream(outStream);
		outputChannel.writeBytes(req+"\n" );
		outputChannel.flush();
	}

    public static void main( String[] args)   {
    	new SMathCallerTCPNaive().doJob(); 
    }
}
