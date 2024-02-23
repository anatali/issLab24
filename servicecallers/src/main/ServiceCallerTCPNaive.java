package main;
import java.io.*;
import java.net.Socket;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.BasicMsgUtil;

public class ServiceCallerTCPNaive {
/*1*/  private final String destination = "servicemath";
/*2*/  private final String sender      = "clientjava";
/*3*/  private final String hostAddr    = "localhost";
/*4*/  private final int    port        = 8011;
/*5*/  private final String msgid       = "dofibo";
/*6*/  private final String msgcontent  = "dofibo(39)";
/*7*/  private Socket socket   ;

  public void doJob() {
    try {
      socket  =  new Socket( hostAddr, port );
/*8*/IApplMessage req =
        BasicMsgUtil.buildRequest(sender,msgid,msgcontent,destination);
/*9*/ sendUsingTcp( req  );
/*10*/receiveAnswer( );
      socket.close();
    }catch(Exception e){
      CommUtils.outred("ERROR " + e.getMessage() );
    }
  }

  protected void receiveAnswer( ) throws Exception {
    InputStream inStream    = socket.getInputStream();
    BufferedReader inputChannel =
        new BufferedReader(new InputStreamReader(inStream));
/*11*/String  answer = inputChannel.readLine() ;
/*12*/IApplMessage msg = new ApplMessage(answer);
/*13*/CommUtils.outblue("ServiceCallerTCPNaive|answer:"+answer);
/*14*/CommUtils.outblue("ServiceCallerTCPNaive|content:"+msg.msgContent());
  }

  protected  void sendUsingTcp(IApplMessage req) throws Exception{
    OutputStream outStream  = socket.getOutputStream();
    DataOutputStream outputChannel = new DataOutputStream(outStream);
    outputChannel.writeBytes(req+"\n" );
    outputChannel.flush();
  }

  public static void main( String[] args)   {
    new ServiceCallerTCPNaive().doJob();
  }
}