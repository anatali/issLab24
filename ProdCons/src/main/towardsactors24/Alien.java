package main.towardsactors24;

import unibo.basicomm23.examples.ActorNaiveCaller;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

public class Alien extends ActorNaiveCaller{
	 protected String msgId        = "distance";
	 protected String  msgReceiver = "consumer";
	 protected int distance = 10;
	protected IApplMessage msg = CommUtils.buildDispatch( name, msgId, ""+distance++, msgReceiver);
	protected IApplMessage req = CommUtils.buildRequest( name, msgId, ""+distance++, msgReceiver);

	public Alien(String name, ProtocolType protocol, String hostAddr, String entry) {
		super(name, protocol, hostAddr, entry);
		CommUtils.outblack(  name + " CREATED. consumer port=" + entry);
		activate();
	}

	@Override
	protected void body() throws Exception {

		sendRequestBlocking();
	
		sendRequestNonBlocking();
		
	}
	
	protected void sendRequestBlocking() throws Exception{
		CommUtils.outblack( name + " sendRequestBlocking " + req);
		IApplMessage answer = connSupport.request(req);  //blocking
		CommUtils.outblack( name + " sendRequestBlocking answer= " + answer);		
	}

	protected void sendRequestNonBlocking() throws Exception{
		CommUtils.outblack( name + " sendRequestNonBlocking " + req);
		connSupport.forward(req);   
		CommUtils.outblack( name + " WAITING FOR answer ... "  );		
		IApplMessage answer = connSupport.receive();
		CommUtils.outblack( name + " sendRequestNonBlocking answer= " + answer);
	}

	
    public static void main(String[] args ){
        new Alien("alienjava", ProtocolType.tcp, "localhost", MainOneNodeWithActors24.entry) ;
    }	

}
