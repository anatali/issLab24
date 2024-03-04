package main.interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
/*
 * ===========================================================================
 * Main per attivazione del solo consumatore
 * ===========================================================================
 */
public class MainEmablersConsumerOnly {
public static final String host    = "localhost";
public static final int port       = 8888;
public static ProtocolType protocol= ProtocolType.tcp;

public static String consumerName = "consumer";

public void configureTheSystem()  {
    CommUtils.outblack("MainEmablersConsumerOnly STARTS " );
    //Connection.trace   = true;
    //Create the consumer
    ConsumerUsingEnablers consumer  = new ConsumerUsingEnablers(consumerName, port, protocol);
    //Activate
    consumer.activate();
}

/*
 * main
 */
public static void main(String[] args)  {
	 new MainEmablersConsumerOnly().configureTheSystem();
}
}
