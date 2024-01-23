package javacallers;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.utils.CommUtils;

public class ServerObserverCoap implements CoapHandler{
	private CoapClient client;
	private CoapObserveRelation obsRel;

    private final String hostAddr    = "localhost";
    private final int    port        = 8011;
    private String path = "ctxservice/servicemathcoded";
    
	public ServerObserverCoap( ) {
		CommUtils.outblue("ServerObserverCoap | STARTED " );
	}
	
    public void doJob() {
        try {            
        	crrateObserver();
        }catch(Exception e){
            CommUtils.outred("ERROR " + e.getMessage() );
        }
    }
	
    protected void crrateObserver() {
    	//"localhost:8011/ctxservice/servicemath"
    	String uri = hostAddr+":"+port+"/"+path;
		client = new CoapClient("coap://"+uri);
		obsRel = client.observe(this);    	
		CommUtils.outblue("ServerObserverCoap | observing on " + uri );
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
		ServerObserverCoap appl = new ServerObserverCoap();
		appl.doJob();
		Thread.sleep(60000);
		CommUtils.outyellow("ServerObserverCoap BYE "  );
		appl.shutdown();
    }
}
