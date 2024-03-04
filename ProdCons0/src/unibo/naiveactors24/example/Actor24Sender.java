package unibo.naiveactors24.example;

 
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;
import unibo.naiveactors24.ActorBasic24;
import unibo.naiveactors24.ActorContext24;
 

public class Actor24Sender extends ActorBasic24 {
     
    protected boolean stopped = false;

    public Actor24Sender(String name, ActorContext24 ctx) {
        super(name, ctx);
    }


    @Override
    protected void elabMsg( IApplMessage msg ) throws Exception{
        CommUtils.outblue(name + " | elab " + msg + " in " + Thread.currentThread().getName());
        if( msg.isReply() ){  
            //CommUtils.outmagenta(name + " | elabMsg ENDS " + Thread.currentThread().getName());
            CommUtils.delay(800); //Simulate some work ...
//            if (!stopped) request(msg.msgSender());
            return;
        }
        if( msg.isDispatch() ) {
            if (msg.msgId().equals("cmd") && msg.msgContent().equals("start")) {
            	IApplMessage m = CommUtils.buildDispatch(name, "info", "hello"  , "a2");
            	IApplMessage r = CommUtils.buildRequest(name, "info", "greetings"  , "a2");
            	forward(m);
                request(r);  //NON blocking: answer come elabMsg
            }  
            return;
        }
    }

//    protected void forward(IApplMessage m){
////        IApplMessage m = CommUtils.buildDispatch(name, "info", "hello" + n, "a2");
////        n++;
//        CommUtils.outblue(name + " | forward " + m + " " + Thread.currentThread().getName());
//        sendMsg(m);
//    }
//
//    protected void request(IApplMessage m){
////        IApplMessage m = CommUtils.buildRequest(name, "info", "greetinds" + n, dest);
////        n++;
//        CommUtils.outblue(name + " | request " + m + " " + Thread.currentThread().getName());
//        sendMsg(m);
//    }

}
