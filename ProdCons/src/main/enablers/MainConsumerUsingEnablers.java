package main.enablers;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

public class MainConsumerUsingEnablers {
public static final String host    = "localhost";
public static final int port       = 8888;
public static ProtocolType protocol= ProtocolType.tcp;

public static String consumerName = "consumer";

public void configureTheSystem()  {
    CommUtils.outblack("MainConsumerBasicTcp STARTS " );
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
	 new MainConsumerUsingEnablers().configureTheSystem();
}
}
