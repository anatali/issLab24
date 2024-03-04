package main.interaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

/*
 * ===========================================================================
 * Test che 
 *    - attiva il consumatore (una volta sola)
 *    - esegue il test di invio di una richiesta (testRequest)
 *      osservabilità data dalla risposta
 *    - esegue il test di invio di due dispatch (testDispatch) con 
 *      osservabilità data da file di log
 * ===========================================================================
 */
public class TestProdConsInteraction {
private static Interaction connSupport;

	@BeforeClass
	public static void activateConsumer() {
		CommUtils.outmagenta("activateConsumer");
		new MainEmablersConsumerOnly().configureTheSystem();
		connSupport = ConnectionFactory.createClientSupport(
				          ProtocolType.tcp, "localhost", "8888");
	}
	@After
	public void down() {
		CommUtils.outmagenta("end of  a test "); 
	}
	
	@Test
	public void testRequest() {
		 CommUtils.outmagenta("testRequest ======================================= ");
		//Funge da Producer come ProducerUsingConnection
		IApplMessage req  = CommUtils.buildRequest( "tester", "distance", "distance(20)", "consumer");
		IApplMessage req1 = CommUtils.buildRequest( "tester", "distance", "distance(30)", "consumer");
 		try {
			IApplMessage reply = connSupport.request(req);
			CommUtils.outblue("reply="+reply);
			String answer = reply.msgContent();
			assertEquals(answer, "ack(distance(20))");

			IApplMessage reply1 = connSupport.request(req1);
			CommUtils.outblue("reply1="+reply1);
			String answer1 = reply1.msgContent();
			assertEquals(answer1, "ack(distance(30))");
			 
		} catch (Exception e) {
			fail("testRequest " + e.getMessage());
 		}
	}

	@Test
	public void testDispatch() {
		 CommUtils.outmagenta("testDispatch ======================================= ");
		//Funge da Producer come ProducerUsingConnection
		IApplMessage msg  = CommUtils.buildDispatch( "tester", "distance", "distance(20)", "consumer");
		IApplMessage msg1 = CommUtils.buildDispatch( "tester", "distance", "distance(30)", "consumer");
		try {
			connSupport.forward(msg);
			connSupport.forward(msg1);
			//CommUtils.delay(500);
 			readLogFile();
		} catch (Exception e) {
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
