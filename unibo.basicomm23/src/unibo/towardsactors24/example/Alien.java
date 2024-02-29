package unibo.towardsactors24.example;

import unibo.basicomm23.examples.ActorNaiveCaller;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

public class Alien extends ActorNaiveCaller{
	protected IApplMessage m = CommUtils.buildDispatch(name, "cmd", "hello_from_alien"  , "a2");
	protected IApplMessage r = CommUtils.buildRequest(name, "info", "request_from_alien"  , "a2");

	public Alien(String name, ProtocolType protocol, String hostAddr, String entry) {
		super(name, protocol, hostAddr, entry);
		CommUtils.outblack(  name + " CREATED. consumer port=" + entry);
	}

	@Override
	protected void body() throws Exception {

		sendRequestBlocking();
		
		sendRequestNonBlocking();
		
		CommUtils.outblack( name + " sends dispatch " + r);
		connSupport.request(m);  //connSupport inherited


	}
	
	protected void sendRequestBlocking() throws Exception{
		CommUtils.outblack( name + " sendRequestBlocking " + r);
		IApplMessage answer = connSupport.request(r);  //blocking
		CommUtils.outblack( name + " sendRequestBlocking answer= " + answer);		
	}

	protected void sendRequestNonBlocking() throws Exception{
		CommUtils.outblack( name + " sendRequestNonBlocking " + r);
		connSupport.forward(r);   
		CommUtils.outblack( name + " WAITING FOR answer ... "  );		
		IApplMessage answer = connSupport.receive();
		CommUtils.outblack( name + " sendRequestNonBlocking answer= " + answer);
	}

	
    public static void main(String[] args ){
        new Alien("javacaller", ProtocolType.tcp, "localhost", "8123") ;
    }	

}
