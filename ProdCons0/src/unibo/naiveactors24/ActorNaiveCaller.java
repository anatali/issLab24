package unibo.naiveactors24;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

/*
 * ===========================================================================
 * Viene attivato invocando il metodo activate che
 * crea la connessione con il protocollo specificato e 
 * invca il metodo abstract body. 
 * 
 * Fornisce i metodi forward e request per inviare dispatch e request
 * sulla connessione, in modo ASINCRONO.
 * ===========================================================================
 */
public abstract class ActorNaiveCaller {
    protected String name;
    protected Interaction conn;
    protected ProtocolType protocol;
    protected String hostAddr;
    protected String entry;
    protected boolean connected = false;

    public ActorNaiveCaller(String name, ProtocolType protocol, String hostAddr, String entry){
        this.name     = name;
        this.protocol = protocol;
        this.hostAddr = hostAddr;
        this. entry   = entry;
    }


    protected void connect(){
        if( connected ) return;
        connected   = true;
        conn = ConnectionFactory.createClientSupport23(protocol, hostAddr, entry);
        //CommUtils.outblue(name + " | connected client=" + connSupport);
    }

    public void activate(){
        new Thread(){
            public void run(){
                try {
                    connect();
                    body();
                  } catch (Exception e) {
                    CommUtils.outred("");
                }
            }
        }.start();
    }

    protected void forward( IApplMessage msg  )   {
    	try {
			conn.forward(msg);
	        if( ! msg.isDispatch())  
	            CommUtils.outyellow("		ActorNaiveCaller | warning: forward for non-dispatch");
		} catch (Exception e) {
			CommUtils.outred("		ActorNaiveCaller | warning: forward for non-dispatch");
 		} 
    }
    
    protected void request( IApplMessage msg  )   {
    	try {
			conn.forward(msg);
		       if( ! msg.isRequest() )  
		           CommUtils.outyellow("		ActorNaiveCaller | warning: request for non-request");
		} catch (Exception e) {
			CommUtils.outred("		ActorNaiveCaller | warning: forward for non-dispatch");
 		} 
   }
    
    protected IApplMessage receive(    )   {
    	try {
    		IApplMessage answer = conn.receive();
    		return answer;
		} catch (Exception e) {
			CommUtils.outred("		ActorNaiveCaller | receive ERROR:" + e.getMessage());
			return null;
 		} 
   }
    

    protected abstract void body() throws Exception;

}
