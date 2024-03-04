package main.interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

/*
 * ===========================================================================
 * Main per attivazione dei produttori
 * ===========================================================================
 */
public class MainEnablersProducersOnly {
public static final String host    = "localhost";
public static final int port       = 8888;
public static ProtocolType protocol= ProtocolType.tcp;

public static String consumerName = "consumer";

public void configureTheSystem()  {
    CommUtils.outblack("MainForProducers STARTS " );
    //Connection.trace   = true;
    //Create the producers
    ProducerUsingConnection producer1 = new ProducerUsingConnection("prod1", host, port,protocol );
    ProducerUsingConnection producer2 = new ProducerUsingConnection("prod2", host, port,protocol );
    
    producer1.activate();
    producer2.activate();
    
    CommUtils.delay(2000);
    CommUtils.outblack("MainEnablersProducersOnly ENDS " );
    System.exit(0);
}

/*
 * main
 */
public static void main(String[] args)  {
	 new MainEnablersProducersOnly().configureTheSystem();
}
}
