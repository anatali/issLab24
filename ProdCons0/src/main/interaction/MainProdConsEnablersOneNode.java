package main.interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

/*
 * ===========================================================================
 * Crea due Producer
 * Crea il Consumer
 * Attiva i Producer
 * ===========================================================================
 */
public class MainProdConsEnablersOneNode {
public static final String host    = "localhost";
public static final int port       = 8888;
public static ProtocolType protocol= ProtocolType.tcp;

public static String consumerName = "consumer";

public void configureTheSystem()  {
    CommUtils.outblack("MainOneNodeEnablers STARTS thread=" + Thread.currentThread().getName() );
    //Connection.trace   = true;
    //Create the producers
//PRODUTTORI CHE USANO CONNECTION    
     ProducerUsingConnection producer1 = new ProducerUsingConnection("prod1", host, port,protocol );
     ProducerUsingConnection producer2 = new ProducerUsingConnection("prod2", host, port,protocol );

  //PRODUTTORI COME ActorNaiveCaller   
//    ProducerAsActiveCaller producer1 = new ProducerAsActiveCaller("prod1", host, port,protocol );
//    ProducerAsActiveCaller producer2 = new ProducerAsActiveCaller("prod2", host, port,protocol );

    //Create and activates the consumer
    new ConsumerUsingEnablers(consumerName, port, protocol).activate();
    
//ATTIVAZIONE DEI PRODUTTORI    
    producer1.activate();
    producer2.activate();
    
//TERMINAZIONE NAIVE DEL SISTEMA
    CommUtils.delay(1500);
    CommUtils.outblack("MainOneNodeEnablers TERMINATES thread=" + Thread.currentThread().getName());
    System.exit(0);
}

/*
 * main
 */
public static void main(String[] args)  {
	 new MainProdConsEnablersOneNode().configureTheSystem();
}
}
