package main.towardsactors24;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.naiveactors24.ActorContext24;

public class MainAsActorsConsumerOnly {
public static final String host    = "localhost";
public static final int port       = 8223;
public static ProtocolType protocol= ProtocolType.tcp;

public static String consumerName = "consumer";

public void configureTheSystem()  {
    
    //Connection.trace   = true;
    ActorContext24 ctx1 = new ActorContext24("ctxconsumer", "localhost", port);
    //Create the consumer
    ConsumerAsActors24 consumer  = new ConsumerAsActors24(consumerName, ctx1);
    //Activate
    //consumer.activate();
}

/*
 * main
 */
public static void main(String[] args)  {
	 new MainAsActorsConsumerOnly().configureTheSystem();
}
}
