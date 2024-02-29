package unibo.basicomm23.coap;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.Connection;


public class CoapConnection extends Connection {
protected CoapClient client;  //protected : Barbieri 2023
protected String url;
private String answer = "unknown";

	public static Interaction create(String host, String path) throws Exception {
	 	return new CoapConnection(host,path);
	}

	public CoapConnection( String address, String path) {
		//"coap://localhost:5683/" + path
 		setCoapClient(address,path);
	}

	public String toString(){
		return url;
	}
	protected void setCoapClient(String addressWithPort, String path) {
		if( trace ) CommUtils.outmagenta(  "    +++ CoapConn | setCoapClient addressWithPort=" +  addressWithPort  );
		//url            = "coap://"+address + ":5683/"+ path;
		url            = "coap://"+addressWithPort + "/"+ path;
		if( trace )  CommUtils.outyellow(  "    +++ CoapConn | setCoapClient url=" +  url  );
		client          = new CoapClient( url );
 		client.useExecutor(); //To be shutdown
		if( trace )  CommUtils.outyellow("    +++ CoapConn | STARTS client url=" +  url ); //+ " client=" + client );
		client.setTimeout( 1000L );		 		
	}
 	
	public void removeObserve(CoapObserveRelation relation) {
		relation.proactiveCancel();
		if( trace )  CommUtils.outyellow("    +++ CoapConn | removeObserve !!!!!!!!!!!!!!!" + relation   );
	}
	public CoapObserveRelation observeResource( CoapHandler handler  ) {
		CoapObserveRelation relation = client.observe( handler ); 
		//if( trace )  CommUtils.outyellow("    +++ CoapConn |  added " + handler + " relation=" + relation + relation );
 		return relation;
	}


	
//From Interaction 
	@Override
	public void forward(String msg)   {
	    if( trace ) CommUtils.outyellow(  "    +++ CoapConn | forward " + url + " msg=" + msg );

		if( client != null ) {
			CoapResponse resp = client.put(msg, MediaTypeRegistry.TEXT_PLAIN); //Blocking!
			if( resp != null ) {
				answer = resp.getResponseText();
				if (trace)
					CommUtils.outyellow("    +++ CoapConn | forward " + msg + " answer=" + answer);
			}else {
		    	CommUtils.outred("    +++ CoapConn | forward - resp null for " + msg  );
		    }  //?????
		} 
	}

	
	@Override
	public String request(String query)   {
		if( trace ) CommUtils.outyellow(  "    +++ CoapConn | request query=" + query + " url="+url  );
		String param = query.isEmpty() ? "" :  "?q="+query;
		if( trace ) CommUtils.outyellow(  "    +++ CoapConn | param=" + (url+param)  );

  		client.setURI(url+param); //JAN2024
		CoapResponse response = client.put(query, MediaTypeRegistry.TEXT_PLAIN); //FEB2023
		//OPPURE ( FEB2023 )
		//client.setURI(url );
		//CoapResponse response = client.put(query, MediaTypeRegistry.TEXT_PLAIN);

		if( response != null ) {
			if( trace )
				CommUtils.outyellow(  "    +++ CoapConn | request=" + query
 	 				+" RESPONSE CODEEEE: " + response.getCode() + " answer=" + response.getResponseText()  );
			answer = response.getResponseText();
 	 		return answer;
		}else {
	 		CommUtils.outred(  "    +++ CoapConn | request=" + query +" RESPONSE NULL " );
			return null;
		}
	}
	
	//https://phoenixnap.com/kb/install-java-raspberry-pi
	
	@Override
	public void reply(String reqid) throws Exception {
		throw new Exception( "   +++ CoapConn | reply not allowed");
	} 

	@Override
	public String receiveMsg() throws Exception {
		if( trace )  CommUtils.outyellow(  "    +++ CoapConn | receiveMsg" );
		while( answer.equals( "unknown" ) ){ //FEB2023
			Thread.sleep(500);
			CommUtils.outyellow(  "    +++ CoapConn | waiting for answer ..." );
		}
		return answer;
	}

	@Override
	public void close()  {
		if( trace ) CommUtils.outyellow(  "    +++ CoapConn | client shutdown=" + client);
		client.shutdown();	
	}

}
/*
Log4j by default looks for a file called log4j.properties or log4j.xml on the classpath
System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");
*/