import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
//import org.junit.Test;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import it.unibo.kactor.MsgUtil;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.tcp.TcpConnection;
import unibo.basicomm23.utils.CommUtils;

public class TestIntellijervicemath24SynchJava {
	private static Interaction conn;
	IApplMessage req35 = 
			MsgUtil.buildRequest("tester", "dofibo", "dofibo(35)", "servicemath");
	IApplMessage req6  = 
			MsgUtil.buildRequest("tester", "dofibo", "dofibo(6)", "servicemath");

	//See https://www.baeldung.com/junit-before-beforeclass-beforeeach-beforeall
    @BeforeClass
	public static void initSystemTotest(){
		CommUtils.outmagenta( "initSystemTotest"   );
		startTheService();
	}

	private static void startTheService(){
	    new Thread(){
	    public void run(){
	        try{
	            CommUtils.outblue( "memory:" + Runtime.getRuntime().totalMemory() );
	            CommUtils.outblue( "processors:" + Runtime.getRuntime().availableProcessors() );
				it.unibo.ctxservice.MainCtxserviceKt.main();
	        }catch (Exception e) {
	            fail("startTheService " + e.getMessage());
	        }
	    }
	    }.start();
		try {
			conn = TcpConnection.create("localhost", 8041);
		} catch (Exception e) {
			fail("connection " + e.getMessage());
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

    @Test
	public void test35(){
		CommUtils.outgreen( "test35 ---------------------- "  );
		try {
 		    String answer35 = conn.request(req35.toString());
			int res35 = fromAnswerToValue(answer35);
		    assertEquals(res35,9227465);
		} catch (Exception e) {
			fail("test35 " + e.getMessage());
		}
	}

	@Test
	public void test6(){
		CommUtils.outgreen( "test6 ---------------------- "  );
		try {
			String answer6 = conn.request(req6.toString());
			int res6 = fromAnswerToValue(answer6);
			assertEquals(res6,8);
		} catch (Exception e) {
			fail("test6 " + e.getMessage());
		}
	}


	@AfterClass
	public static void endOfTest(){
		CommUtils.outmagenta( "endOfTest"   );
	}


	/*
	public static void main(String[] args) {

		new TesttttttServicemath24Synch().initSystemTotest();
	} */
}