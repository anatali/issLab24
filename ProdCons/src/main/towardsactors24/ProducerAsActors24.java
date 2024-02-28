package main.towardsactors24;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;
import unibo.towardsactors24.ActorBasic24;
import unibo.towardsactors24.ActorContext24;

public class ProducerAsActors24 extends ActorBasic24{
	 protected String msgId        = "distance";
	 protected String  msgReceiver = "consumer";
	 protected int distance = 0;
	 
	public ProducerAsActors24(String name, ActorContext24 ctx) {
		super(name, ctx);
		activateAndStart();
	}

	@Override
	protected void elabMsg(IApplMessage msg) throws Exception {
		CommUtils.outblue( name + " | elabMsg " + msg ); 
		if( msg.msgId().equals("cmd")) doJob();
	}
	
	protected void doJob() {

		IApplMessage msg = CommUtils.buildDispatch( name, msgId, ""+distance++, msgReceiver);
		CommUtils.outgreen( name + " | forward " + msg );
		forward( msg );
		IApplMessage req = CommUtils.buildRequest( name, msgId, ""+distance++, msgReceiver);
		CommUtils.outblue( name + " | request " + msg );
		request( req ); //non-blocking
//		CommUtils.outblue( name + " | forward " + req );
//		forward( req );
		
		
	}

}
