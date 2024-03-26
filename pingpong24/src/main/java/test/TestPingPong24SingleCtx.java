package main.java.test;
 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.After;
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
 * TestPingPong24SingleCtx
 * ===========================================================================
 */
public class TestPingPong24SingleCtx {
private static Interaction connSupport;

/*
 * Utilty to show the output of a process activated with Runtime.getRuntime().exec
 * See activateSystemUsingGradle
 */
public static void showOutput(Process proc, String color){
	new Thread(){
		public void run(){
			try {
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			ColorsOut.outappl("Here is the standard output of the command:\n", color);
			while (true){
				String s = stdInput.readLine();
				if ( s != null ) ColorsOut.outappl( s, color );
			} 
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}.start();
}

 
/*
 *  Method to activate the system
 *  using gradle
 */
public static void activateSystemUsingGradle() { 
	Thread th = new Thread(){
		public void run(){
			try {
				CommUtils.outmagenta("TestPingPong24SingleCtx activateSystemUsingGradle ");
				Process p = Runtime.getRuntime().exec("./gradlew.bat runAll");
				showOutput(p,ColorsOut.BLACK);
			} catch ( Exception e) {
				CommUtils.outred("TestPingPong24SingleCtx activate ERROR " + e.getMessage());
			}
		}
	};
	th.start();
}  

/*
 *  Method to activate the system
 *  using the distribution
 */
public static void activateSystemUsingDeploy() { 
	Thread th = new Thread(){
		public void run(){
			try {
				CommUtils.outmagenta("TestPingPong24SingleCtx activateSystemUsingDeploy ");
				Process p = Runtime.getRuntime().exec("./src/main/java/test/pingpongexec.bat");
				showOutput(p,ColorsOut.BLACK);
			} catch ( Exception e) {
				CommUtils.outred("TestPingPong24SingleCtx activate ERROR " + e.getMessage());
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
		CommUtils.outmagenta("TestPingPong24SingleCtx activate ");
		activateSystemUsingGradle();
		//activateSystemUsingDeploy();
	}
/*
 * After each test	
 */
	@After
	public void down() { 
		CommUtils.outcyan("end of test ");
	}

/*
 * Connect with the pingobserver
 * Request info to the pingobserver
 * Assertion on the answer of the pingobserver
 */
	@Test
	public void testPingPongSystem() {
		IApplMessage req  = CommUtils.buildRequest( "tester", "info", "info(X)", "pingobserver");
 		try {
  			 CommUtils.outmagenta("testPingPongSystem ======================================= ");
			while( connSupport == null ) {
 				connSupport = ConnectionFactory.createClientSupport(ProtocolType.tcp, "localhost", "8014");
 				CommUtils.outcyan("testPingPongSystem another connect attempt ");
 				Thread.sleep(1000);
 			}
 			CommUtils.outcyan("CONNECTED to pingobserver " + connSupport);
			IApplMessage reply = connSupport.request(req);
			CommUtils.outcyan("testPingPongSystem reply="+reply);
			String answer = reply.msgContent();
			assertEquals(answer, "obsinfo(5)");
		} catch (Exception e) {
			CommUtils.outred("testPingPong ERROR " + e.getMessage());
			fail("testRequest " + e.getMessage());
		}
	}
}
