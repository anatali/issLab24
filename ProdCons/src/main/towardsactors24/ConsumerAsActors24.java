package main.towardsactors24;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;
import unibo.towardsactors24.ActorBasic24;
import unibo.towardsactors24.ActorContext24;

public class ConsumerAsActors24 extends ActorBasic24{

	public ConsumerAsActors24(String name, ActorContext24 ctx) {
		super(name, ctx);
		activateAndStart();
	}

	@Override
	protected void elabMsg(IApplMessage msg) throws Exception {
		CommUtils.outgreen( name + " | elabMsg " + msg ); 
		if( msg.msgId().equals("distance")) handleMsg(msg);
		
	}

	public void handleMsg( IApplMessage message ) {
         
        CommUtils.outblue(name + "  | consumes " + 
	       message.msgContent() + " sent by " + message.msgSender() );		

        //OPTIONAL no more ...
        if( message.isRequest() ) {
	        String outMsg = "ack("+message.msgContent()+")";
	 		IApplMessage reply = CommUtils.buildReply( name, "ack", outMsg, message.msgSender());
			reply(message, reply);
        }
	}
	
 
}
