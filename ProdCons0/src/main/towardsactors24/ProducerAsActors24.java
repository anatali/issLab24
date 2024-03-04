package main.towardsactors24;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;
import unibo.naiveactors24.ActorBasic24;
import unibo.naiveactors24.ActorContext24;
 

/*
 * ===========================================================================
 * Eredita da  ActorBasic24 la capacita' di inviare messaggi 
 * ad altri attori locali al suo Contesto.
 * Si attiva invocando il metodo ereditato activateAndStart che pone sulla coda 
 *       msg(cmd,dispatch,consumer,consumer,start,1)
 
 * Invia al Consumer di nome 'consumer' prima una richiesta (sempre asincrona)
 * e poi un dispatch.
 * La reply della richiesta viene posa nella coda dei messaggi.
 * 
 *  Se l'actor entra in un loop (ad es. non cede il controllo in doJob)
 *  nessun altro messaggio in input viene elaborato
 * ===========================================================================
 */
public class ProducerAsActors24 extends ActorBasic24{
	 protected String msgId        = "distance";
	 protected String  msgReceiver = "consumer";
 	 protected String pfx          = "        ";
	
 	 public ProducerAsActors24(String name, ActorContext24 ctx) {
		super(name, ctx);
		activateAndStart();
	}

	@Override
	protected void elabMsg(IApplMessage msg) throws Exception {
		CommUtils.outyellow( pfx  + name + " | elabMsg " + msg + " " + Thread.currentThread().getName() ); 
		if( msg.isReply() ) elabReply(msg);
		if( msg.msgId().equals("cmd")) doJob();
	}
	
	protected void elabReply(IApplMessage msg) throws Exception {
		CommUtils.outblue( pfx  + name + " | elabReply " + msg + " " + Thread.currentThread().getName() );
	}
	
	protected void doJob() throws Exception {
 		IApplMessage msg = CommUtils.buildDispatch( name, msgId, "10" , msgReceiver);
		IApplMessage req = CommUtils.buildRequest(  name, msgId, "50" , msgReceiver);

//INVIO RICHIESTA BLOCCANTE: 
 		//non ammessa
//INVIO RICHIESTA ASINCRONA		 
		CommUtils.outblue( pfx  + name + " sendRequest asynch " + req);
		request(req);   
//INVIO DISPATCH	
		CommUtils.outblue( pfx  + name + " | forward " + msg );
		forward( msg );
//ELABORAZIONE DELLA RISPOSTA ALLA RICHIESTA ASINCRONA 
		//non ammessa: la risposta è elaborata da  elabMsg
//TERMINAZIONE 			
		//non ammessa: il producer dovrebbe terminare ricevendo un msg di top		

//		for( int i=0; i<5; i++) {
//			CommUtils.outblue( pfx  + name + " DOING SOME LONG JOB ... "  + Thread.currentThread().getName()  );
// 			CommUtils.delay(400);   
//		}
			
 	}
 
 
 }
