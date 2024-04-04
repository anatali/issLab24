package main.java;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.utils.CommUtils;

public class SMathObserverCoap implements CoapHandler{
	private CoapClient client;
	private CoapObserveRelation obsRel;

    private String hostAddr    = "localhost";
    private int    port        = 8033;
    private String path        = "";//"ctxservice/servicemath";
    private String observed    = "";
    
	public SMathObserverCoap( String host, int port, String path ) {
		CommUtils.outmagenta("ServiceObserverCoap | STARTED " );
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
		CommUtils.outmagenta("ServiceObserverCoap | observing on " + uri );
    }
	
	@Override
	public void onLoad(CoapResponse response) {
		String content = response.getResponseText();
		observed = observed + "\n" + content;
  		CommUtils.outmagenta("ServiceObserverCoap | onLoad observed: "+observed);
	}

	@Override
	public void onError() {
		CommUtils.outred("ServiceObserverCoap | ERROR");		
	}
	
	public void shutdown() {
		client.shutdown();
		CommUtils.outmagenta("ServiceObserverCoap | shutdown obsRel.isCanceled()=" + obsRel.isCanceled());
		if( obsRel != null ) obsRel.proactiveCancel();
		CommUtils.outmagenta("ServiceObserverCoap | shutdown obsRel.isCanceled()=" + obsRel.isCanceled());
	}

	public static void main(String[] args) throws InterruptedException {
		new SMathObserverCoap("localhost",8033,"ctxsmath/smathsynch");
		Thread.sleep(30000);
		CommUtils.outmagenta("ServiceObserverCoap | BYE"  );
		System.exit(0);
	}
}
