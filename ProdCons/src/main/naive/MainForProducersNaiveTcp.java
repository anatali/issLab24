package main.naive;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

public class MainForProducersNaiveTcp {
public static final String host    = "localhost";
public static final int port       = 8888;
public static ProtocolType protocol= ProtocolType.tcp;

public static String consumerName = "consumer";

public void configureTheSystem()  {
    CommUtils.outblack("MainForProducers STARTS " );
    //Connection.trace   = true;
    //Create the producers
    ProducerNaiveTcp producer1 = new ProducerNaiveTcp("prod1", host, port );
    ProducerNaiveTcp producer2 = new ProducerNaiveTcp("prod2", host, port );
    //Create the consumer
//    Consumer consumer  = new Consumer(consumerName, port, protocol);
//    //Activate
//    consumer.activate();
//    CommUtils.delay(500);
    
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
	 new MainForProducersNaiveTcp().configureTheSystem();
}
}
