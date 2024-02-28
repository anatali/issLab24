package main.enablers;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

/*
 * ===========================================================================
 * Crea due Producer
 * Crea il Consumer
 * Attiva i Producer
 * ===========================================================================
 */
public class MainOneNodeEnablers {
public static final String host    = "localhost";
public static final int port       = 8888;
public static ProtocolType protocol= ProtocolType.tcp;

public static String consumerName = "consumer";

public void configureTheSystem()  {
    CommUtils.outblack("MainOneNodeEnablers STARTS " );
    //Connection.trace   = true;
    //Create the producers
    ProducerUsingEnablers producer1 = new ProducerUsingEnablers("prod1", host, port,protocol );
    ProducerUsingEnablers producer2 = new ProducerUsingEnablers("prod2", host, port,protocol );
    //Create and activates the consumer
    new ConsumerUsingEnablers(consumerName, port, protocol ).activate();
    
    
    producer1.activate();
    producer2.activate();
    

}

/*
 * main
 */
public static void main(String[] args)  {
	 new MainOneNodeEnablers().configureTheSystem();
}
}
