package javacallers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class ServerCallerTCPNaive {
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
    
    public static void main( String[] args)   {
    	new ServerCallerTCPNaive().doJob();
    	//Thread.sleep(2000);
    	//CommUtils.outyellow("sendMessageTcp BYE "  );
    }
}
