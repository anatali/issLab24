package main.java.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.After;
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
 *  Test after requirement analysis
 *  The TestUnit works as a caller
 * ===========================================================================
 */

public class Smath24synch_0Test {
	private static Interaction connSupport;
	private static Process psmath;
	
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
		CommUtils.outmagenta("activateServiceUsingGradle ");
		Thread th = new Thread(){
			public void run(){
				try {
					psmath = Runtime.getRuntime().exec("./gradlew.bat run -x test");
					showOutput(psmath,ColorsOut.BLUE);	
 				} catch ( Exception e) {
					CommUtils.outred("Smath24_0TestManyCallers activate ERROR " + e.getMessage());
				}
			}
		};
		th.start();
	}
 	
	public static void activateServiceUsingDeploy() { 
		Thread th = new Thread(){
			public void run(){
				try {
					CommUtils.outmagenta("activateSystemUsingDeploy ");
					psmath = Runtime.getRuntime().exec("./src/main/java/test/smath24synch.bat");
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
			CommUtils.outmagenta("activate ");
			activateServiceUsingDeploy();  
			//activateServiceUsingGradle();
			//CommUtils.delay(8000); //GIve time to the application and callers to start ....
 		}
	/*
	 * After   test	
	 */
		@AfterClass
		public static void down() { 
			CommUtils.outcyan("end of test ");
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
			} catch (Exception e) {
 				e.printStackTrace();
			}
		}

		
/*
* 
*/
		@Test
		public void testSmath() {
			IApplMessage req  = CommUtils.buildRequest( "tester", "dofibo", "dofibo(8)", "smathsynch");
	 		try {
 	  			CommUtils.outmagenta("testSmath connects via TCP with localhost:8033 ========================== ");
				while( connSupport == null ) {
	 				connSupport = ConnectionFactory.createClientSupport(ProtocolType.tcp, "localhost", "8033");
	 				CommUtils.outcyan("testSmath another connect attempt ");
	 				Thread.sleep(1500);
	 			}
	 			CommUtils.outcyan("CONNECTED to smath with:" + connSupport);
				IApplMessage reply = connSupport.request(req); //blocking
				CommUtils.outcyan("smath reply="+reply);
				String answer = reply.msgContent();
				boolean check=answer.startsWith("fibodone(tester,8,21"); //fibodone(tester,8,34,1507,0)
				assertTrue( answer, check );
				Thread.sleep(1500);
			} catch (Exception e) {
				CommUtils.outred("testSmath ERROR " + e.getMessage());
				fail("testSmath " + e.getMessage());
			}
		}

}
