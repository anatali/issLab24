package main;
import unibo.basicomm23.utils.CommUtils;
import java.net.URI;
import javax.websocket.*;

//Si veda https://www.baeldung.com/java-websockets

@ClientEndpoint
public class ServiceObserverWSNaive {

private Session session;
private String addr = "localhost:8088/accessgui";

		@OnOpen
		public void onOpen(Session userSession) {
			CommUtils.outyellow("TestMovesUsingWs | opening websocket");
		    //this.userSession = userSession;
		}
		
		@OnClose
		public void onClose(Session userSession, CloseReason reason) {
			CommUtils.outyellow("TestMovesUsingWs | closing websocket");
		    //this.userSession = null;
		}

	    @OnMessage
	    public void onMessage(String message)  {
	    	CommUtils.outmagenta("TestMovesUsingWs | onMessage:" + message );
	    }
	    
	public void doJob() {
		  try {
		    CommUtils.outblue("ServiceCallerWSNaive | STARTS "   );
		    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		    session = container.connectToServer(this, new URI("ws://"+addr));
		    Thread.sleep(30000);
		    session.close();
		    CommUtils.outblue("ServiceCallerWSNaive | BYE "   );
		  }catch(Exception e){
		    CommUtils.outred("ERROR " + e.getMessage() );
		    
		  }    
	}
	

	public static void main( String[] args)   {
		new ServiceObserverWSNaive().doJob(); 
	}		
}
