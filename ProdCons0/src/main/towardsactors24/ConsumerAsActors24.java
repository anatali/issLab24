package main.towardsactors24;

import java.io.FileWriter;
import java.io.IOException;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;
import unibo.naiveactors24.ActorBasic24;
import unibo.naiveactors24.ActorContext24;


/* 
 * ===========================================================================
 * Eredita da  ActorBasic24 la capacit� di elaborare in modo FIFO i messaggi 
 * posti sulla sua coda di input.
 * Si attiva invocando il metodo activateAndStart che pone sulla coda 
 *       msg(cmd,dispatch,consumer,consumer,start,1)
 * In quanto Consumer, tratta solo messaggi con msgId="distance"
 * Nel caso di request invia un ack come reply.
 * ===========================================================================
 */

public class ConsumerAsActors24 extends ActorBasic24{
private FileWriter myWriter  ; //Per il testing

	public ConsumerAsActors24(String name, ActorContext24 ctx) {
		super(name, ctx);
		activateAndStart();
		//Writer del log file per il testing 
		try {
			myWriter = new FileWriter("TestLog.txt");
		} catch (IOException e) {
 			e.printStackTrace();
		}
	}

	@Override
	protected void elabMsg(IApplMessage msg) throws Exception {
		CommUtils.outyellow( name + " | elabMsg " + msg + " " + Thread.currentThread().getName() ) ; 
		if( msg.msgId().equals("distance")) handleMsg(msg);
		
	}

	public void handleMsg( IApplMessage msg ) {       
        CommUtils.outgreen(name + "  | consumes " + msg.msgContent() + " sent by " + msg.msgSender() + " " + Thread.currentThread().getName());		
        if( msg.isRequest() ) {
	        String outMsg = "ack("+msg.msgContent()+")";
	 		IApplMessage reply = CommUtils.buildReply( name, "ack", outMsg, msg.msgSender());
	        CommUtils.outgreen(name + "  | sends reply= " + reply );		
			reply(msg, reply);
        }else {
            writeLog(msg.toString());        	
        }
	}
	
	/*
	 * Scrittura di informazione sul log file:
	 * un primo modo per rendere il componente OSSERVABILE
	 */
	protected void writeLog(String s) {
		try {
			 myWriter.append(s+"\n");
			 myWriter.flush();
		} catch (IOException e) {
 			e.printStackTrace();
		}
	}
 
}
