package main.java.test;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.utils.CommUtils;

public class ServicemathObserverCoap implements CoapHandler{
	private CoapClient client;
	private CoapObserveRelation obsRel;

    private String hostAddr    = "localhost";
    private int    port        = 8011;
    private String path        = "";//"ctxservice/servicemath";
    private String observed    = "";
    
	public ServicemathObserverCoap( String host, int port, String path ) {
		CommUtils.outblue("ServicemathObserverCoap | STARTED " );
		this.hostAddr = host;
		this.port     = port;
		this.path     = path;
		crrateObserver();
	}
	
	public String getobserved() {		
		return observed;
	}
	
 	
    protected void crrateObserver() {
    	//"localhost:8011/ctxservice/servicemath"
    	String uri = hostAddr+":"+port+"/"+path;
		client = new CoapClient("coap://"+uri);
		obsRel = client.observe(this);    	
		CommUtils.outblue("ServicemathObserverCoap | observing on " + uri );
    }
	
	@Override
	public void onLoad(CoapResponse response) {
		String content = response.getResponseText();
		observed = observed + "\n" + content;
  		CommUtils.outblue("ServicemathObserverCoap | onLoad receives: "+content);
	}

	@Override
	public void onError() {
		CommUtils.outred("ServicemathObserverCoap | ERROR");		
	}
	
	public void shutdown() {
		client.shutdown();
		CommUtils.outblue("ServicemathObserverCoap | shutdown obsRel.isCanceled()=" + obsRel.isCanceled());
		if( obsRel != null ) obsRel.proactiveCancel();
		CommUtils.outblue("ServicemathObserverCoap | shutdown obsRel.isCanceled()=" + obsRel.isCanceled());
	}

 
}
