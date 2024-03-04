package unibo.naiveactors24; 
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/*
 * ===========================================================================
 * Realizza il concetto di actor in Java, come componente attivo dotato di  
 * una coda di messaggi di tipo  IApplMessage.
 * Gestisce i messaggi in modo FIFO, invocando il metodo abstract  
 * elabMsg( IApplMessage msg ).
 * Fornisce anche metodi per l'invio di messaggi  (forward, request, reply)  
 * ad altri attori locali al contesto.
 * ===========================================================================
 */

public abstract class ActorBasic24 implements IActor24 {
    protected BlockingQueue<IApplMessage> msgQueue     = new LinkedBlockingDeque<>();
    protected HashMap<String, IApplMessage> requestMap = new HashMap<String, IApplMessage>();
     
    protected String name="dummy";
    protected boolean autostart = false;
    protected ActorContext24 ctx;
    protected IApplMessage autoStartMsg ;
    

    public ActorBasic24(String name, ActorContext24 ctx ){ 
        this.name    = name;
        this.ctx     = ctx;
        ctx.addActor(this);
        autoStartMsg = CommUtils.buildDispatch(name, "cmd", "start", name);
    }

    public String getName(){
        return name;
    }

    public String getContextName(){
        return ctx.name;
    }

    protected void mainLoop(){
        try {
            CommUtils.outmagenta(name + "| mainLoop STARTS:" + " thname=" + Thread.currentThread().getName() );
            while( true ) {
            	IApplMessage msg = msgQueue.take();
                CommUtils.outyellow("		" + name + "| mainLoop takes:" + msg + " thname=" + Thread.currentThread().getName() );
                //IApplMessage inputMsg = new ApplMessage(msg);
                elabMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void activate(){
//        new Thread(){
//            public void run(){
//                mainLoop();
//            }
//        }.start();
//    }
    public void activateAndStart(){
        new Thread(){
            public void run(){
                sendMsg( autoStartMsg, ctx );
                mainLoop();
            }
        }.start();
    }
    
    protected void forward( IApplMessage msg  )   {
    	sendMsg(msg,ctx);
        if( ! msg.isDispatch())  
          CommUtils.outyellow("		ActorBasic24 | warning: forward for non-dispatch");
    }
    
    protected void request( IApplMessage msg  )   {
       if( ! msg.isRequest() )  
          CommUtils.outyellow("		ActorBasic24 | warning: request for non-request");
       requestMap.put( msg.msgSender(), msg);
       sendMsg(msg,ctx);
   }

    protected void answer(   IApplMessage msg  )   {
        if( ! msg.isReply() )  
            CommUtils.outyellow("		ActorBasic24 | warning: reply for non-reply");
        IApplMessage request = requestMap.remove( msg.msgReceiver() );
        if( request != null ) sendReply(request,msg);
        else CommUtils.outred("		ActorBasic24 | ERROR: answer without request");
    }
    protected synchronized void reply( IApplMessage request, IApplMessage msg  )   {
    	sendReply(request,msg);
         if( ! msg.isReply() )  
            CommUtils.outyellow("		ActorBasic24 | warning: reply for non-reply");
    }
    protected IApplMessage receive() {
    	return null;
    }
    
    /*
     * Invio di messaggio ad (altro) attore nel contesto
     * Nel caso di attore non locale, invia sulla connessione associata al messaggio
     */
    protected void sendMsg( IApplMessage msg, ActorContext24 ctx  )   {
        try { 
            String destActorName = msg.msgReceiver();
            //CommUtils.outcyan(  "		ActorBasic24 | sendMsg " + msg + " to  " + destActorName + " msg.getConn()=" + msg.getConn());
            ActorBasic24 dest    = ctx.getActor(destActorName);
            if (dest != null) {
                CommUtils.outcyan(  "		ActorBasic24 | sendMsg " + msg + " to " + dest.name  );
                dest.msgQueue.put( msg ); //attore locale
            } 
            else {                
            	Interaction callerConn =  msg.getConn();
            	CommUtils.outyellow(  "		ActorBasic24 | sendMsg " + msg + " callerConn= " + callerConn  );
            	if( callerConn != null) {
            		callerConn.forward(msg);
            	}
            	else CommUtils.outred(  "		ActorBasic24 | sorry, sendMsg to remote actor not implemented  "   );
            }
        }catch(Exception e){
            CommUtils.outred(  "		ActorBasic24 | sendMsg ERROR  " + e.getMessage() );
        }
    }

    protected void sendReply( IApplMessage request, IApplMessage reply  )   {
    	//CommUtils.outmagenta( name + " | sendReply with Alien conn= " + request.getConn() );
    	reply.setConn(request.getConn());
        sendMsg(reply,ctx);
    }

    protected void elabMsg( String msg ) throws Exception{
        IApplMessage m = new ApplMessage(msg);
        if( m.isRequest() ) {
        	elabMsg(m);
        }
        else elabMsg(m);
    }
    protected abstract void elabMsg( IApplMessage msg ) throws Exception;
}
