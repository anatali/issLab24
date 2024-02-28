package main.enablers;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

public class MainForProducersUsingEnablers {
public static final String host    = "localhost";
public static final int port       = 8888;
public static ProtocolType protocol= ProtocolType.tcp;

public static String consumerName = "consumer";

public void configureTheSystem()  {
    CommUtils.outblack("MainForProducers STARTS " );
    //Connection.trace   = true;
    //Create the producers
    ProducerUsingEnablers producer1 = new ProducerUsingEnablers("prod1", host, port,protocol );
    ProducerUsingEnablers producer2 = new ProducerUsingEnablers("prod2", host, port,protocol );
 
    
    producer1.activate();
    producer2.activate();
    
    CommUtils.delay(2000);
    CommUtils.outblack("MainForProducers ENDS " );
    System.exit(0);
}

/*
 * main
 */
public static void main(String[] args)  {
	 new MainForProducersUsingEnablers().configureTheSystem();
}
}
