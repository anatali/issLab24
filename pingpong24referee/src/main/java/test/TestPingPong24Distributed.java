package main.java.test;
 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;

//import it.unibo.ctxprodcons.MainCtxprodconsKt; //Solo in IntelliJ
//import it.unibo.ctxping.MainCtxpingKt;
//import it.unibo.ctxpong.MainCtxpongKt;
//import it.unibo.ctxreferee.MainCtxrefereeKt;
import unibo.basicomm23.utils.ConnectionFactory;


/*
 * ===========================================================================
 * TestPingPong24Distributed 
 * ===========================================================================
 */
public class TestPingPong24Distributed {
private static Interaction connSupport;
private static Process procPing;
private static Process procPong;
private static Process procReferee;

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

 
public static void activatePing() {
	Thread th = new Thread(){
		public void run(){
			CommUtils.outmagenta("TestPingPong24 activatePing ");
			try {
				procPing = Runtime.getRuntime().exec("./src/main/java/test/pingexec.bat");
				showOutput(procPing,ColorsOut.BLUE);
			} catch ( Exception e) {
				CommUtils.outred("TestPingPong24 activatePing ERROR " + e.getMessage());
			}
		}
	};
	th.start();	
}
public static void activatePong() {
	Thread th = new Thread(){
		public void run(){
			CommUtils.outmagenta("TestPingPong24 activatePong ");
			try {
				procPong = Runtime.getRuntime().exec("./src/main/java/test/pongexec.bat");
				showOutput(procPong,ColorsOut.GREEN);
			} catch ( Exception e) {
				CommUtils.outred("TestPingPong24 activatePing ERROR " + e.getMessage());
			}
		}
	};
	th.start();	
}

/*
 *  
 */
public static void activateReferee() { 
//	Thread th = new Thread(){
//		public void run(){
			try {
				CommUtils.outmagenta("activateReferee starts the referee ");
				procReferee = Runtime.getRuntime().exec("./src/main/java/test/ppgreferee.bat");
				showOutput(procReferee,ColorsOut.BLACK);
//  				Thread.sleep(8000);
			} catch ( Exception e) {
				CommUtils.outred("TestPingPong24 activate ERROR " + e.getMessage());
			}
//		}
//	};
//	th.start();
}  

public static void activateRefereeUsingGradle() { 
	CommUtils.outgreen("activateRefereeUsingGradle ");		
	Thread th1 = new Thread(){
		public void run(){
			try {
				procReferee = Runtime.getRuntime().exec("./gradlew.bat runReferee -x test");
				showOutput(procReferee,ColorsOut.GREEN);									
			} catch ( Exception e) {
				CommUtils.outred("activateCallerUsingGradle ERROR " + e.getMessage());
			}
		}
	};
	th1.start();
}  

	@BeforeClass
	public static void activate() {
		CommUtils.outmagenta("TestPingPong24 activate ");
		activatePing();		
 		activatePong();
		activateReferee();
 		//activateRefereeUsingGradle(); //da evitare ...
 
	}
	
	@AfterClass
	public static void down() { 
		CommUtils.outmagenta("end of test ");
		procReferee.destroy();
		procPing.destroy();
		procPong.destroy();
		try {
			CommUtils.outcyan("end of test - taskkill /F /IM java.exe");
			Runtime.getRuntime().exec("taskkill /F /IM java.exe");
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
 
	@Test
	public void testPingPongReferee() {
		IApplMessage req  = CommUtils.buildRequest( "tester", "info", "info(X)", "referee");
 		try {
 			//Thread.sleep(4000);
 			 CommUtils.outmagenta("testPingPongReferee ======================================= ");
			while( connSupport == null ) {
 				connSupport = ConnectionFactory.createClientSupport(ProtocolType.tcp, "localhost", "8013");
 				CommUtils.outmagenta("testPingPong another connect attempt ");
 				Thread.sleep(200);
 			}
			Thread.sleep(1000);
 			CommUtils.outmagenta("CONNECTED to referee. Doing:" + req);
			IApplMessage reply = connSupport.request(req);
			CommUtils.outmagenta("testPingPongReferee reply="+reply);
			String answer = reply.msgContent();
			assertEquals(answer, "obsinfo(8)");
//			Thread.sleep(1000);
		} catch (Exception e) {
			CommUtils.outred("testPingPongReferee ERROR " + e.getMessage());
			fail("testRequest " + e.getMessage());
		}
	}


 


	protected void readLogFile() throws IOException {	
		String line;
		IApplMessage m;
	      File myObj = new File("Testlog.txt");
	      Scanner myReader = new Scanner(myObj);
	      line = myReader.nextLine();
	      m = new ApplMessage(line);
	      CommUtils.outblue( ""+m  );
	      assertEquals(m.msgContent(), "distance(20)");
	      line = myReader.nextLine();
	      m = new ApplMessage(line);
	      CommUtils.outblue( ""+m  );
	      assertEquals(m.msgContent(), "distance(30)");	      
	      myReader.close();
	}


}
