package main.naive;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

public class MainConsumerNaiveTcp {
public static final String host    = "localhost";
public static final int port       = 8888;
public static ProtocolType protocol= ProtocolType.tcp;

public static String consumerName = "consumer";

public void configureTheSystem()  {
    CommUtils.outblack("MainConsumerBasicTcp STARTS " );
    //Connection.trace   = true;
    //Create the consumer
    ConsumerNaiveTcp consumer  = new ConsumerNaiveTcp(consumerName, port);
    //Activate already done
     
}

/*
 * main
 */
public static void main(String[] args)  {
	 new MainConsumerNaiveTcp().configureTheSystem();
}
}
