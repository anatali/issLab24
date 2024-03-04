package main.towardsactors24;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.naiveactors24.ActorNaiveCaller;

/*
 * ===========================================================================
 * Usa l'utility class ActorNaiveCaller
 * in quanto opera come client verso il Consumer
 * 
 * La casse ActorNaiveCaller fornisce il metodo (bloccante) receive
 * in quanto opera con rifeimento diretto alla connessione
 * ===========================================================================
 */

public class ProduceAsActor24External extends ActorNaiveCaller{
	protected String msgId        = "distance";
	protected String  msgReceiver = "consumer";
	protected String pfx          = "        ";	  
	  

	public ProduceAsActor24External(String name, ProtocolType protocol, String hostAddr, String entry) {
		super(name, protocol, hostAddr, entry);
		CommUtils.outblack(  pfx+name + " CREATED. consumer port=" + entry);
		activate();
	}

	@Override
	protected void body() throws Exception {
		CommUtils.outblack( pfx+name + " works using  ActorNaiveCaller "  );
//INVIO RICHIESTA ASINCRONA	
		/*1*/ sendRequestNonBlocking(50);

		CommUtils.outblack( pfx+name + " doing some other action ... "  );		  

 //INVIO DISPATCH		
		/*2*/ sendDispatch();

//ELABORAZIONE DELLA RISPOSTA ALLA RICHIESTA ASINCRONA 		
 		/*3*/ getRequestAnswer();
		
//TERMINAZIONE DEL PRODUCER  			
	    CommUtils.outgreen(pfx+ name + " TERMINATES  " + Thread.currentThread().getName());				  
		System.exit(0);
	}
	
	protected void getRequestAnswer() throws Exception{
		//CommUtils.outmagenta( name + " exploits the lower layer connSupport "  );
		CommUtils.outmagenta( pfx+name + " WAITING FOR answer ... "  );		
		IApplMessage answer =  receive();
		CommUtils.outmagenta( pfx+name + "  answer= " + answer);		
	}
	
	protected void sendRequestBlocking() throws Exception{
		IApplMessage req = CommUtils.buildRequest( name, msgId, "20", msgReceiver);
		CommUtils.outblue( pfx+name + " sendRequestBlocking" + req + "  using LOWER connSupport=" + conn );
		IApplMessage answer = conn.request(req);  //blocking
		CommUtils.outblue( pfx+name + " sendRequestBlocking answer= " + answer  );		
	}

	protected void sendRequestNonBlocking(int v) throws Exception{
		IApplMessage req = CommUtils.buildRequest( name, msgId, ""+v , msgReceiver);
		CommUtils.outmagenta( pfx+name + " sendRequestNonBlocking " + req);
		request(req);   
	}
	protected void sendDispatch() throws Exception{
		IApplMessage msg = CommUtils.buildDispatch( name, msgId, "10", msgReceiver);
		CommUtils.outgreen( pfx+name + " sendDispatch " + msg);
		forward(msg);   
	}
	
	
    public static void main(String[] args ){
        new ProduceAsActor24External("prodexternal", ProtocolType.tcp, "localhost", MainOneNodeWithActors24.entry) ;
    }	

}
