package unibo.towardsactors24; 
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/*
 * ===========================================================================
 * Realizza il concetto di actor in Java, come componentoi attivo dotato di  
 * una coda di messaggi di tipo  IApplMessage.
 * Gestisce i messaggi in modo FIFO, invocando il metodo abstract  
 * elabMsg( IApplMessage msg ).
 * Fornisce anche metodi per l'invio di messaggi  (forward, request, reply)  
 * ad altri attori locali al contesto.
 * ===========================================================================
 */

public abstract class ActorBasic24 implements IActor24 {
    protected BlockingQueue<IApplMessage> msgQueue = new LinkedBlockingDeque<>();
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
  //          CommUtils.outmagenta(name + "| mainLoop STARTS:" + " thname=" + Thread.currentThread().getName() );
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

    public void activate(){
        new Thread(){
            public void run(){
                mainLoop();
            }
        }.start();
    }
    public void activateAndStart(){
        new Thread(){
            public void run(){
                sendMsg( autoStartMsg );
                mainLoop();
            }
        }.start();
    }
    
    protected void forward( IApplMessage msg  )   {
    	ActorSupport24.sendMsg(msg,ctx);
        if( ! msg.isDispatch())  
          CommUtils.outyellow("		ActorBasic24 | warning: forward for non-dispatch");
    }
    protected void request( IApplMessage msg  )   {
    	ActorSupport24.sendMsg(msg,ctx);
        if( ! msg.isRequest() )  
          CommUtils.outyellow("		ActorBasic24 | warning: request for non-request");
    }
    protected void reply( IApplMessage request, IApplMessage msg  )   {
    	sendReply(request,msg);
         if( ! msg.isReply() )  
            CommUtils.outyellow("		ActorBasic24 | warning: reply for non-reply");
    }
    
    
    protected void sendMsg( IApplMessage msg  )   {
        ActorSupport24.sendMsg(msg,ctx);
    }

    protected void sendReply( IApplMessage request, IApplMessage reply  )   {
    	CommUtils.outmagenta( name + " | sendReply with Alien conn= " + request.getConn() );
    	reply.setConn(request.getConn());
        ActorSupport24.sendMsg(reply,ctx);
    }

    protected void elabMsg( String msg ) throws Exception{
        IApplMessage m = new ApplMessage(msg);
        elabMsg(m);
    }
    protected abstract void elabMsg( IApplMessage msg ) throws Exception;
}
