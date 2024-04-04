package main.java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

/*
 * ===========================================================================
 * Smath24synch_0TestOneCaller 
 * ===========================================================================
 */

public class SmathasynchTestOneCaller {
	private static Interaction connSupport;
	private static Process psmath;
	private static Process pcallers;
	
	/*
	 * Utilty to show the output of a process activated with Runtime.getRuntime().exec
	 */
	public static void showOutput(Process proc, String color){
		new Thread(){
			public void run(){
				try {
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
				//ColorsOut.outappl("Here is the standard output of the command:\n", color);
				while (true){
					String s = stdInput.readLine();
					if ( s != null ) ColorsOut.outappl( s, color );
				} 
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	 
	/*
	 *  Methods to activate the system
	 */	
  	public static void activateServiceUsingGradle() { 
		CommUtils.outblack("activateServiceUsingGradle ");
		Thread th = new Thread(){
			public void run(){
				try {
					Process p = Runtime.getRuntime().exec("./gradlew.bat runSmathsynch -x test");
					showOutput(p,ColorsOut.BLACK);	
 				} catch ( Exception e) {
					CommUtils.outred("activateServiceUsingGradle ERROR " + e.getMessage());
				}
			}
		};
		th.start();
	}
	
	public static void activateCallerUsingGradle() { 
		CommUtils.outgreen("activateCallerUsingGradle ");		
		Thread th1 = new Thread(){
			public void run(){
				try {
					pcallers = Runtime.getRuntime().exec("./gradlew.bat runOnecaller -x test");
					showOutput(pcallers,ColorsOut.GREEN);									
				} catch ( Exception e) {
					CommUtils.outred("activateCallerUsingGradle ERROR " + e.getMessage());
				}
			}
		};
		th1.start();
	}  
	
	public static void activateServiceUsingDeploy() { 
		Thread th = new Thread(){
			public void run(){
				try {
					CommUtils.outmagenta("activateSystemUsingDeploy ");
					psmath = Runtime.getRuntime().exec("./src/main/java/test/smath24asynch.bat");
					showOutput(psmath,ColorsOut.BLACK);
				} catch ( Exception e) {
					CommUtils.outred("activateServiceUsingDeploy ERROR " + e.getMessage());
				}
			}
		};
		th.start();
	}  
 

	
	/*
	 * Before all the tests
	 */
		@BeforeClass
		public static void activate() {
			//CommUtils.outmagenta("activate ");
  			//activateServiceUsingDeploy();
			//activateCallersUsingDeploy();  //TODO			
			//activateServiceUsingGradle();
			activateCallerUsingGradle();
 
			CommUtils.delay(8000); //Give time to the application and callers to start ....
 		}
	/*
	 * After   test	
	 */
		@AfterClass
		public static void down() { 
			CommUtils.outcyan("end of test - doing destroy");
			if( pcallers != null ) {
				pcallers.destroy();
				if (pcallers.isAlive()) {
					CommUtils.outcyan("pcallers - doing destroyForcibly");
					pcallers.destroyForcibly();
				}
			}
			if( psmath != null ) {
				psmath.destroy();
				if (psmath.isAlive()) {
					CommUtils.outcyan("psmath - doing destroyForcibly");
					psmath.destroyForcibly();
				}
			}
			try {
				CommUtils.outcyan("end of test - taskkill /F /IM java.exe");
				Runtime.getRuntime().exec("taskkill /F /IM java.exe");
			} catch (IOException e) {
 				e.printStackTrace();
			}
		}

		
/*
* 
*/
		@Test
		public void testSmath() {
			IApplMessage req  = CommUtils.buildRequest( "tester", "info", "info(X)", "callerobserver");
	 		try {
 	  			CommUtils.outmagenta("testSmath connects via TCP with localhost:8035 ========================== ");
				while( connSupport == null ) {
	 				connSupport = ConnectionFactory.createClientSupport(ProtocolType.tcp, "localhost", "8035");
	 				CommUtils.outcyan("testSmath another connect attempt ");
	 				Thread.sleep(1500);
	 			}
	 			CommUtils.outcyan("CONNECTED to callerobserver with:" + connSupport);
				IApplMessage reply = connSupport.request(req);
				CommUtils.outcyan("smath reply="+reply);
				String answer = reply.msgContent();
 				assertEquals( answer,"obsinfo(true)" );
				Thread.sleep(2000);
			} catch (Exception e) {
				CommUtils.outred("testSmath ERROR " + e.getMessage());
				fail("testSmath " + e.getMessage());
			}
		}

}
