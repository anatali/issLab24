package unibo.naiveactors24.example;

 
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;
import unibo.naiveactors24.ActorBasic24;
import unibo.naiveactors24.ActorContext24;
 

public class Actor24Receiver extends ActorBasic24 {

    public Actor24Receiver(String name, ActorContext24 ctx) {
        super(name,ctx);
    }

    @Override
    protected void elabMsg(IApplMessage msg) throws Exception {
        CommUtils.outgreen(name + " | elab " + msg + " in:" + Thread.currentThread().getName());
        if( msg.isRequest() ) {
            IApplMessage reply = CommUtils.buildReply(name,"answer", "ok" + msg.msgContent(), msg.msgSender());
            CommUtils.outgreen(name + " | sendReply " + reply + " " + Thread.currentThread().getName());
            sendReply(msg,reply);  //invio la reply a un destinatario, anche remoto
        }
    }
}
