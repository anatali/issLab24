package unibo.basicomm23.ws;

import java.net.URI;
import java.util.HashMap;
import java.util.Vector;
import javax.websocket.*;
import unibo.basicomm23.interfaces.IObservable;
import unibo.basicomm23.interfaces.IObserver;
import unibo.basicomm23.utils.*;

@ClientEndpoint
public class WsConnection  extends Connection implements  IObservable{ //implements Interaction2021, IObservable{
	private static HashMap<String,WsConnection> connMap= new HashMap<String,WsConnection>();
	private Vector<IObserver> observers                = new Vector<IObserver>();
    private boolean opened         = false;
    private Session userSession    = null;
	private String answer = "unknown";  //FEB2024
	private String entry = "";         //FEB2024

	public static WsConnection create(String addr, String entry ){
		CommUtils.outyellow("             WsConnection | wsconnect addr=" + addr + " " + connMap.containsKey(addr)  );
		if( ! connMap.containsKey(addr)){
			connMap.put(addr, new WsConnection( addr,entry ) );
		}else {
			CommUtils.outyellow("             WsConnection | ALREADY connected to addr=" + addr  );
		}
		return connMap.get(addr);
	}
	public static WsConnection create(String addr ){
		CommUtils.outyellow("             WsConnection | wsconnect addr=" + addr + " " + connMap.containsKey(addr)  );
		if( ! connMap.containsKey(addr)){
			connMap.put(addr, new WsConnection( addr ) );
		}else {
		    CommUtils.outred("             WsConnection | ALREADY connected to addr=" + addr  );
		}
		return connMap.get(addr);
	}
	public static WsConnection create(String addr, Vector<IObserver> externalObs ){
		WsConnection conn = create(addr);
		externalObs.forEach( obs -> conn.addObserver(  obs  ));
		return conn;
	}

	private WsConnection( String addr, String entry  ) {
		this.entry = entry;
		wsconnect(addr+"/"+entry );
	}

	private WsConnection( String addr  ) {
		wsconnect(addr);
 	}

    private void wsconnect(String wsAddr){    // localhost:8091
        try {
			CommUtils.outyellow("             WsConnection | wsconnect wsAddr=" + wsAddr);
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI uri = new URI("ws://"+wsAddr);
			CommUtils.outyellow("             WsConnection | wsconnect uri=" + uri);
			if( trace ) CommUtils.outyellow("             WsConnection | wsconnect to uri=" + uri  );
            //CommUtils.outyellow("             WsConnection | wsconnect container=" + container + " uri=" + uri );
            container.connectToServer(this, uri);
			if( trace ) CommUtils.outyellow("             WsConnection | wsconnected to wsAddr=" + wsAddr  );
        } catch ( Exception ex) {
        	CommUtils.outred("             WsConnection | wsconnect ERROR: " + ex.getMessage());
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
		if( trace ) CommUtils.outyellow("             WsConnection | opening websocket" ); //userSession="+userSession.getRequestURI()
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
		if( trace ) CommUtils.outyellow("             WsConnection | closing. userSession="+userSession);
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message)   {
    	if( trace ) CommUtils.outyellow("             WsConnection | onMessage:" + message);
		answer = message;
		updateObservers(message);
    }

    public void sendMessageSynch(String message ) throws Exception {
		if( trace ) CommUtils.outmagenta("             WsConnection | sendMessageSynch " + message + " userSession=" + userSession);
		this.userSession.getBasicRemote().sendText(entry+"/"+ message);//blocks until the message has been transmitted
    }
	public void sendMessageAsynch(String message ) throws Exception {
		if( trace ) CommUtils.outmagenta("             WsConnection | sendMessageSynch " + message + " userSession=" + userSession);
		this.userSession.getAsyncRemote().sendText(entry+"/"+ message);
	}

//Since Interaction2021
	@Override
	public void forward( String msg) throws Exception {
		if( trace ) CommUtils.outyellow("             WsConnection | forward "  + msg );
 	    sendMessageSynch(msg);
 	}

	@Override
	public String request(String msg) throws Exception {
		if( trace ) CommUtils.outyellow("             WsConnection | request "  + msg );
		forward(msg);
		if( trace ) CommUtils.outyellow("             WsConnection | receiveMsg in request "   );
		return receiveMsg();
	}

	@Override
	public void reply(String msgJson) throws Exception {
		forward(msgJson);
	}

	@Override
	public String receiveMsg() throws Exception {
		if( trace ) CommUtils.outyellow("             WsConnection | receiveMsg, perhaps onMessage"   );
		//throw new Exception("             WsConnection: receiveMsg not allowed");
		//FEB2024
		while( answer.equals("unknown") ) {
			Thread.sleep(300);
			if( trace ) CommUtils.outyellow("             WsConnection | waits ... "   );
		}
		return answer;
	}

	@Override
	public void close() throws Exception {
		if( trace ) CommUtils.outyellow("             WsConnection | close gracefulShutdown=" );
		userSession.close();
	}

//Since IObservable
	@Override
	public void addObserver(IObserver obs) {
        CommUtils.outyellow("             WsConnection | addObserver " + obs  );
		observers.add( obs);
	}
	@Override
	public void deleteObserver(IObserver obs) {
		observers.remove( obs);
	}

    protected void updateObservers( String msg ){
		if( trace ) CommUtils.outyellow("             WsConnection | updateObservers " + observers.size()  );
        observers.forEach( v -> v.update(null, msg) );
    }

    
 }
