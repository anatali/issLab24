package unibo.towardsactors24;

import unibo.basicomm23.enablers.ServerFactory;
import unibo.basicomm23.interfaces.IApplMsgHandler;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.Connection;
import java.util.HashMap;
import java.util.Vector;

public  class ActorContext24 implements IContext24 {
    public static final String actorReplyPrefix = "arply_";
    protected HashMap<String, ActorBasic24> ctxActorMap = new HashMap<String, ActorBasic24>();
//    protected HashMap<String, Proxy> ctxProxyMap = new HashMap<String, Proxy>();
//    protected HashMap<String, Proxy> proxiesMap  = new HashMap<String, Proxy>();

    protected String name="ctxdummy";
    protected ServerFactory server;
    protected String hostName;
    protected int port;

    public ActorContext24( String name, String hostName, int port ){
        this.name     = name;
        this.hostName = hostName;
        this.port = port;
        IApplMsgHandler ctxMsgHandler = new ContextMsgHandler(name+"CtxMsgHandler", this);
        server = new ServerFactory("appl1Server",port, ProtocolType.tcp, ctxMsgHandler);
        server.start();
    }

    @Override
    public ActorBasic24 getActor(String actorName) {
        return ctxActorMap.get(actorName);
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public   void addActor( ActorBasic24 a ) {
        ctxActorMap.put(a.getName(), a );
        if( a.autostart ) a.activateAndStart();
        else a.activate();
    }
    @Override
    public  void removeActor(ActorBasic24 a) {
        ctxActorMap.remove( a.getName() );
    }

    @Override
    public Vector<String> getLocalActorNames( ) {
        Vector<String> actorList = new Vector<String>();
        ctxActorMap.forEach( (name, actor) -> actorList.add(name) );
        return actorList;
    }

    @Override
    public  void showActorNames( ) {
        CommUtils.outcyan("CURRENT ACTORS in context:" + name );
        ctxActorMap.forEach( (v, x) ->
                CommUtils.outcyan("" + v + " in " + x.getContextName() )
        );
    }



}
