package main.java.test;

import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.tcp.TcpConnection;
import unibo.basicomm23.utils.CommUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.fail;
 
public class TestEclipseServicemath24Synch {

	@BeforeClass
	public static void initSystemTotest(){
		CommUtils.outblue( "initSystemTotest"   );
        try {
        	//Process proc = Runtime.getRuntime().exec("C:\\Didattica2024\\issLab24\\servicemath24Synch\\run.bat");
			//Process proc = Runtime.getRuntime().exec("it.unibo.ctxservice.MainCtxserviceKt");
			//it.unibo.ctxservice.MainCtxserviceKt.main();
        	startTheService();
			CommUtils.outblue( "activated	"   );
        	//show( proc );
        	Thread.sleep(3000);
        	//testCalls();
		} catch ( Exception e) {
			fail("startTheService " + e.getMessage());
		}
	}
	
	private static void show(Process proc) {
	    new Thread(){
	    public void run(){
	        try{
				BufferedReader stdInput = new BufferedReader(new  InputStreamReader(proc.getInputStream()));
		
				BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		
				// Read the output from the command
				System.out.println("Here is the standard output of the command:\n");
				String s = null;
				while ((s = stdInput.readLine()) != null) {
				    System.out.println(s);
				}
		
				// Read any errors from the attempted command
				System.out.println("Here is the standard error of the command (if any):\n");
				while ((s = stdError.readLine()) != null) {
				    System.out.println(s);
				}
			        }catch (Exception e) {
			            fail("startTheService " + e.getMessage());
			        }
			    }
	    }.start();
	    CommUtils.outblue( "started"   );
		
	}

	private static void startTheService(){
	    new Thread(){
	    public void run(){
	        try{
	            CommUtils.outblue( "memory:" + Runtime.getRuntime().totalMemory() );
	            CommUtils.outblue( "processors:" + Runtime.getRuntime().availableProcessors() );
	            //Runtime.getRuntime().exec("it.unibo.ctxservice.MainCtxserviceKt.main()");
	            //it.unibo.ctxservice.MainCtxserviceKt.main();
	            //String[] cmd = {"ls", "-l"};
	            //Runtime.getRuntime().exec("notepad.exe");
	            Process proc = Runtime.getRuntime().exec("C:\\Didattica2024\\issLab24\\servicemath24Synch\\run.bat");
	            show( proc );
	            CommUtils.outblue( "done"   );
	        }catch (Exception e) {
	            fail("startTheService " + e.getMessage());
	        }
	    }
	    }.start();
	    CommUtils.outblue( "started"   );
	}

	@Test
	public void testCalls(){
	    //startTheService();
	      IApplMessage req35 = MsgUtil.buildRequest("tester", "dofibo", "dofibo(35)", "servicemath");
	      IApplMessage req6  = MsgUtil.buildRequest("tester", "dofibo", "dofibo(6)", "servicemath");
	    Interaction conn;
		try {
			conn = TcpConnection.create("localhost", 8021);
		    String answer35 = conn.request(req35.toString());
		    IApplMessage answreq35 = new ApplMessage(answer35);
		    CommUtils.outblue( "answreq35=" + answreq35 );
		    String answer6 = conn.request(req6.toString());
		    IApplMessage answreq6 = new ApplMessage(answer6);
		    CommUtils.outblue( "answreq6=" + answreq6 );
		} catch (Exception e) {
			fail("testCalls " + e.getMessage());
		}
	}
	
//	public static void main(String[] args) {
//		new TestEclipseServicemath24Synch().initSystemTotest();
//	}
}
