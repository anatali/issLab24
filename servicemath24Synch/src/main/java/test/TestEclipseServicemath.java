package main.java.test;

import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.MsgUtil;
import it.unibo.kactor.sysUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.tcp.TcpConnection;
import unibo.basicomm23.utils.CommUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//occupazione porte netstat -anb >c:\ports.txt

public class TestEclipseServicemath {
	private static Interaction conn;
	private static String serviceName = "servicemath";
	private static int servicePort = 8011;
	private static Process proc;
	
	private IApplMessage req35 = 
			MsgUtil.buildRequest("tester", "dofibo", "dofibo(35)", serviceName);
	private IApplMessage req6  = 
			MsgUtil.buildRequest("tester", "dofibo", "dofibo(6)", serviceName);
 
	@BeforeClass
	public static void initSystemTotest(){
		CommUtils.outblue( "initSystemTotest"   );
        try {
            CommUtils.outgreen( "memory:" + Runtime.getRuntime().totalMemory() );
            CommUtils.outgreen( "processors:" + Runtime.getRuntime().availableProcessors() );
           	startTheService();
  			Thread.sleep(2000);
		    ActorBasic serviceActor= sysUtil.getActor("servicemath");  
		    CommUtils.outblue( "initSystemTotest serviceActor=" + serviceActor   );

			conn = TcpConnection.create("localhost", servicePort);
 			CommUtils.outblue( "connected	"   );
		} catch ( Exception e) {
			fail("startTheService " + e.getMessage());
		}
	}
	
	@AfterClass
	public static void endtesting() {		
	    CommUtils.outmagenta( "destroyed - isAlive=" + proc.isAlive()   );
	    try {
// 		conn.forward( exitMsg.toString()  );  //con java -jar non necessario
			if( proc != null ) {
	 		    proc.destroy();
	 		    CommUtils.outmagenta( "AfterClass destroyed - isAlive=" + proc.isAlive()   );
	 		}
			Thread.sleep(2000);  //GIve time to show last wishes 
	    }catch(Exception e) {
	    	CommUtils.outred( "AfterClass error:" + e.getMessage()  );
	    }
	}
	

	private static void startTheService(){
	    new Thread(){
	    public void run(){
	        try{
	            String directoryName = System.getProperty("user.dir");
	            String cmdtorun  = directoryName+"/src/main/java/test/runGradle.bat";
	            //String javatorun = directoryName+"/bin/it/unibo/ctxservice/MainCtxServiceKt";
	            String javatorun = directoryName+"/build/libs/unibo.servicemathsynch-1.0.jar";
//	            CommUtils.outblue(directoryName + " " +directoryName);
	            //Runtime.getRuntime().exec("it.unibo.ctxservice.MainCtxserviceKt.main()");
	            //it.unibo.ctxservice.MainCtxserviceKt.main();
	            //String[] cmd = {"ls", "-l"};
	            //Runtime.getRuntime().exec("notepad.exe");
	            //proc = Runtime.getRuntime().exec("C:\\Didattica2024\\issLab24\\servicemath24Synch\\run.bat");
 	            
//	            proc = Runtime.getRuntime().exec(cmdtorun);
 	            
 	            proc = Runtime.getRuntime().exec("java -jar " + javatorun);
 	            //C:\Didattica2024\issLab24\servicemath24Synch\src\main\java\test\runGradle.bat
 	             
	            sysUtil.showOutput( proc );   
 	            CommUtils.outblue( "startTheService done"   );
	        }catch (Exception e) {
	            fail("startTheService " + e.getMessage());
	        }
	    }
	    }.start();
	    CommUtils.outblue( "startTheService started"   );
	}

//	public static void showOutput(Process proc) {
//	    new Thread(){
//	    public void run(){
//	        try{
//				BufferedReader stdInput = new BufferedReader(new  InputStreamReader(proc.getInputStream()));	
//				BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));		
//				// Read the output from the command
//				System.out.println("Here is the standard output of the command:\n");
//				String s = null;
//				while ((s = stdInput.readLine()) != null) {
//				    System.out.println(s);
//				}
//		
//				// Read any errors from the attempted command
//				System.out.println("Here is the standard error of the command (if any):\n");
//				while ((s = stdError.readLine()) != null) {
//				    System.out.println(s);
//				}
//			        }catch (Exception e) {
//			            fail("startTheService " + e.getMessage());
//			        }
//			    }
//	    }.start();
//	    CommUtils.outblue( "started"   );		
//	}
    
	//@Test
	public void test35(){
		CommUtils.outgreen( "test35 ---------------------- " + conn  );
		try {
 		    String answer35 = conn.request(req35.toString());
 		    CommUtils.outblue( "test35 answer35:" + answer35 );
			int res35 = fromAnswerToValue(answer35);			
		    assertEquals(res35,9227465);
		} catch (Exception e) {
			CommUtils.outred( "test35 ERROR" + e.getMessage() );
			fail("test35 " + e.getMessage());
		}
	}
    
    //@Test
	public void test6(){
		CommUtils.outgreen( "test6 ---------------------- " + conn  );
		try {
 		    String answer6 = conn.request(req6.toString());
 		    CommUtils.outblue( "test35 answer6:" + answer6 );
			int res6 = fromAnswerToValue(answer6);			
		    assertEquals(res6,8);
		} catch (Exception e) {
			CommUtils.outred( "test6 ERROR" + e.getMessage() );
			fail("test6 " + e.getMessage());
		}
	} 

    @Test
	public void testObservable(){
		CommUtils.outgreen( "testObservable ---------------------- "   );
		try {
			String path = "ctxservice/Servicemath";
 			ServicemathObserverCoap obs = new ServicemathObserverCoap("localhost",servicePort,"ctxservice/servicemath");
    		Thread.sleep( 4000 );
 			String s = obs.getobserved();
 			CommUtils.outgreen( "getobserved   " + s  );
 			assertTrue( s.contains("working(servicemath,msgid(dofibo(43)),sender(caller))"));
		} catch (Exception e) {
			CommUtils.outred( "test6 ERROR" + e.getMessage() );
			fail("testObservable " + e.getMessage());
		}
    	
    }

	//Utility
	private int fromAnswerToValue(String msganswer){
		 //msganswer:fibodone( CALLER,N,V,TIME )
		IApplMessage answreq = new ApplMessage(msganswer);
		Struct tanswer = (Struct) Term.createTerm( answreq.msgContent() );
		int n   = Integer.parseInt( tanswer.getArg(1).toString() );
		int res = Integer.parseInt( tanswer.getArg(2).toString() );
		CommUtils.outgreen( "fibo for " + n + "=" + res );
		return res;
	}

}
