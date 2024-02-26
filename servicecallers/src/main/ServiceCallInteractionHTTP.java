package main;

import unibo.basicomm23.http.HttpConnection;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.BasicMsgUtil;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class ServiceCallInteractionHTTP {

	public void doJob()   {
		try {
			m2mHttp();
			mhiHttp();
		 }catch(Exception e){
			    CommUtils.outred("ERROR " + e.getMessage() );
	   }      
	}
	
	public void m2mHttp() throws Exception {
		IApplMessage req = BasicMsgUtil.buildRequest("clientJava", "dofibo", "dofibo(18)", "servicemath");
        String hostAddr="localhost:8088/RestApi/testHTTP";
        String entry   = req.toString();
        Interaction conn = ConnectionFactory.createClientSupport(
        		ProtocolType.http, hostAddr, entry);		
		String answer = conn.request(entry); 
		CommUtils.outmagenta( "ServiceCallInteractionHTTP | m2mHttp answer=" + answer  ); 		
	}
	
	public void mhiHttp() throws Exception {
        String hostAddr="localhost:8088";
        String entry   = "";
        Interaction conn = ConnectionFactory.createClientSupport(
        		ProtocolType.http, hostAddr, entry);				
		String answer = ((HttpConnection) conn).getHTTP(entry); 
		CommUtils.outmagenta( "ServiceCallInteractionHTTP | mhiHttp answer=\n" + answer  ); 		
	}
	
    public static void main( String[] args)     {
    	new ServiceCallInteractionHTTP().doJob(); 
    }
}
