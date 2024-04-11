package main;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.utils.CommUtils;

public class ServiceObserverCoap implements CoapHandler{
  private CoapClient client;
  private CoapObserveRelation obsRel;

/*1*/ private final String hostAddr    = "localhost";
/*2*/  private final int    port        = 8033;
/*3*/  private String path = "ctxsmathfacade/smathasynchfacade";

public void doJob() {
    try {
      createObserver();
    }catch(Exception e){
      CommUtils.outred("ERROR " + e.getMessage() );
    }
}

protected void createObserver() {
    String uri = hostAddr+":"+port+"/"+path;
    //uri=="localhost:8011/ctxservice/servicemathcoded"
  /*4*/client = new CoapClient("coap://"+uri);
  /*5*/obsRel = client.observe(this);
    CommUtils.outblue(
        "ServerObserverCoap|observing on " + uri );
}

@Override
public void onLoad(CoapResponse response) {
/*6*/String content = response.getResponseText();
    CommUtils.outblue(
        "ServerObserverCoap|onLoad receives: "+content);
  }

@Override
/*7*/public void onError() {
    CommUtils.outred("ServerObserverCoap|ERROR");
}

public void shutdown() {
    CommUtils.outblue("ServerObserverCoap|shutdown  ");
    client.shutdown();
    if( obsRel != null ) obsRel.proactiveCancel();
}

public static void main( String[] args) throws Exception{
      ServiceObserverCoap appl = new ServiceObserverCoap();
      appl.doJob();
/*8*/ Thread.sleep(60000);
      CommUtils.outyellow("ServerObserverCoap BYE "  );
/*9*/ appl.shutdown();
}
}
