package javacallers;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;

import unibo.basicomm23.utils.CommUtils;

public class ServerObserverCoap implements CoapHandler{
	private CoapClient client;
	private CoapObserveRelation obsRel;

	public ServerObserverCoap(String uri) {
		client = new CoapClient("coap://"+uri);
		obsRel = client.observe(this);
		CommUtils.outblue("ServerObserverCoap | STARTED " );
	}
	@Override
	public void onLoad(CoapResponse response) {
		String content = response.getResponseText();
		CommUtils.outblue("ServerObserverCoap | onLoad receives: "+content);
	}

	@Override
	public void onError() {
		CommUtils.outred("ServerObserverCoap | ERROR");
		
	}
	
	public void shutdown() {
		CommUtils.outblue("CoapObserverForSonar | shutdown  ");
		client.shutdown();
		if( obsRel != null ) obsRel.proactiveCancel();

	}	

    public static void main( String[] args) throws InterruptedException   {
    	ServerObserverCoap appl = 
    			new ServerObserverCoap("localhost:8011/ctxservice/servicemath");
    	Thread.sleep(30000);
    	CommUtils.outyellow("ServerObserverCoap BYE "  );
    }

	
}
