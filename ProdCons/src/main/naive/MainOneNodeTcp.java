package main.naive;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

/*
 * ===========================================================================
 * Crea due Producer
 * Crea il Consumer
 * Attiva i Producer
 * ===========================================================================
 */

public class MainOneNodeTcp {
public static final String host    = "localhost";
public static final int port       = 8888;
public static ProtocolType protocol= ProtocolType.tcp;

public static String consumerName = "consumer";

public void configureTheSystem()  {
    CommUtils.outblack("MainOneNode STARTS " );
    //Connection.trace   = true;
    //Create the producers
    ProducerNaiveTcp producer1 = new ProducerNaiveTcp("prod1", host, port );
    ProducerNaiveTcp producer2 = new ProducerNaiveTcp("prod2", host, port );
    //Create the consumer
     new ConsumerNaiveTcp(consumerName, port );
    
    
    producer1.activate();
    producer2.activate();
    
    CommUtils.delay(2000);
    CommUtils.outblack("MainOneNode ENDS " );
    System.exit(0);
}

/*
 * main
 */
public static void main(String[] args)  {
	 new MainOneNodeTcp().configureTheSystem();
}
}
