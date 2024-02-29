package unibo.towardsactors24;
import java.util.Vector;

public interface IContext24 {
	public String getName();
	public Vector<String> getLocalActorNames( );
	public ActorBasic24 getActor(String actorName);
	public void addActor( ActorBasic24 a );
	public void removeActor(ActorBasic24 a);
	public  void showActorNames( );
//	public void setActorAsRemote(String actorName,
//		 String entry, String host, ProtocolType protocol );
//	public Proxy getProxy(String actorName);
//	public  void showProxies( );
}
