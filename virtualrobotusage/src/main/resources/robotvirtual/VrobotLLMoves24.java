package main.resources.robotvirtual;

import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.MsgUtil;
import org.json.simple.JSONObject;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.ApplAbstractObserver;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;
import unibo.basicomm23.ws.WsConnection;

/*
 * ------------------------------------------------------------------------
 * Supporto per un Actor (owner) che deve comunicare con il VirtualRobot23
 * Apre una connessione wsconn su WebSocket  con il VirtualRobot23
 * ed opera come observer su questa connessione
 * 
 * Trasforma le informazioni ricevute su wsconn in eventi
 *      sonardata : sonar(D)
 * o messaggi verso owner
 *      vrinfo    : vrinfo(A,B)  //MOVE,ENDMOVE | elapsed,collision | obstacle,unknown
 
 * Risponde alla request step, sulla base della specifica:
 * 
 *   Request step       : step(TIME)   
 *   Reply stepdone     : stepdone(V)                 for step
 *   Reply stepfailed   : stepfailed(DURATION, CAUSE) for step
 * 
 * ------------------------------------------------------------------------
*/
public class VrobotLLMoves24 extends ApplAbstractObserver implements IVrobotLLMoves {
    protected String vitualRobotIp = "localhost";
    protected Interaction conn;
    protected int elapsed             = 0;     //modified by update
    protected String asynchMoveResult = null;  //for observer part
    protected int threadCount = 1;
    protected ActorBasic owner;
    protected String toApplMsg   ;
    protected boolean tracing         = false;
    protected boolean doingStepSynch  = false;
    protected boolean doingStepAsynch = false;

    //Factory method
    public static VrobotLLMoves24 create( String vitualRobotIp, ActorBasic owner ) {
    	return new VrobotLLMoves24( vitualRobotIp, owner );
    }
    
    //Constructor
    public VrobotLLMoves24(String vitualRobotIp, ActorBasic owner) {
    	connect(vitualRobotIp, owner);
    }
    
    protected void connect(String vitualRobotIp, ActorBasic owner) {
    	this.vitualRobotIp = vitualRobotIp;
    	this.owner         = owner;
        this.conn = //WsConnection.create(vitualRobotIp+":8091");
        		ConnectionFactory.createClientSupport(ProtocolType.ws,vitualRobotIp+":8091","");
        ((WsConnection) conn).addObserver(this);
        if( owner != null )
        	toApplMsg = "msg(wenvinfo, dispatch, support, RECEIVER, CONTENT, 0)"
               .replace("RECEIVER",owner.getName());
        else
            toApplMsg = "msg(wenvinfo, dispatch, support, RECEIVER, CONTENT, 0)"
            .replace("RECEIVER","alien");       	
       CommUtils.outyellow("     VRLL24 | CREATED in " + Thread.currentThread().getName());
    }
    
    public void setTrace(boolean v){
        tracing = v;
    }
    public Interaction getConn() {
        return conn;
    }
 
    @Override
    public void turnLeft() throws Exception {
        requestSynch(VrobotMsgs.turnleftcmd);
    }

    @Override
    public void turnRight() throws Exception {
        requestSynch(VrobotMsgs.turnrightcmd);
    }

    @Override
    public void forward(int time) throws Exception {
        startTimer();
        conn.forward(VrobotMsgs.forwardcmd.replace("TIME", "" + time));
    }

    @Override
    public void backward(int time) throws Exception {
        startTimer();
        conn.forward(VrobotMsgs.backwardcmd.replace("TIME", "" + time));
    }

    @Override
    public void halt()   {
    	try {
	        conn.forward(VrobotMsgs.haltcmd);
	        CommUtils.delay(50); //wait for halt completion since halt on ws does not send answer
		}catch(Exception e) {
			CommUtils.outred("halt error (strange....)" + e.getMessage() );
		}
    }
 
/* 
 * ----------------------------------------
 * Observer part   
 * ----------------------------------------
*/
    
    protected int sonarDataNum = 0;
    
    protected void handleSonar(JSONObject jsonObj) {
        if (jsonObj.get("sonarName") != null) { //defensive
        	CommUtils.outred("     VRLL24 | handleSonar " + jsonObj);
            long d = (long) jsonObj.get("distance") ;
            if( d < 0 ) d = -d;
            IApplMessage sonarEvent = CommUtils.buildEvent( "VRLL24","sonardata","sonar(" + d + " )");
            emitInfo(sonarEvent);
         }
    }
    
    protected void handleMoveok(String move) {
    	elapsed = getDuration();
        if( tracing )              
        CommUtils.outgreen("     VRLL24 | handleMoveok:" + move + " elapsed=" + elapsed );               
       if( ( move.equals("turnLeft") || move.equals("turnRight")) ){
            activateWaiting( move,"true" );
            return;
        }
       if( ! doingStepSynch ) {   //DISPATCH
            String wenvInfo = toApplMsg.replace("wenvinfo","vrinfo") 
                    .replace("CONTENT", "vrinfo(" + move + ", elapsed)");
            IApplMessage msg = new ApplMessage(wenvInfo);
            sendInfo(msg);
       }else {  //move is a forwardcmd for step synch
            activateWaiting(move,"true" );
       }        
    }
    
    protected void handleMoveko(String move) {
    	elapsed = getDuration();
    	if (move.contains("collision")) {
            if(  ! doingStepSynch ) {  
                 String wenvInfo = toApplMsg
                         .replace("wenvinfo", "vrinfo")  
                         .replace("CONTENT","vrinfo(" + elapsed + ", collision )");
                 IApplMessage msg = new ApplMessage(wenvInfo);  //DISPATCH
                 sendInfo(msg);
            } else {
                IApplMessage collisionEvent = CommUtils.buildEvent(
                        "VRLL24","obstacle","obstacle(unknown)" );
                emitInfo(collisionEvent);
            }
            activateWaiting(move,"false"  );
        }    	
    }
    
    protected void handleCollision( ) {
    	halt(); //interrompe la move che provocato la collision
        //CommUtils.outred( "     VRLL24 | handleCollision:"   );               
        IApplMessage collisionEvent = CommUtils.buildEvent(
                "vrhlsprt","vrinfo","vrinfo(obstacle,unknown)" );
        //if( tracing ) CommUtils.outred("     VrobotLLMoves24 | emit " + collisionEvent);
        emitInfo(collisionEvent);
    }
    
    protected boolean checkMoveResult(JSONObject jsonObj) {
        boolean moveresult= jsonObj.get("endmove").toString().contains("true");
        return moveresult;   	
    }
    
    @Override
    public void update(String info) {
         try {            
            JSONObject jsonObj = CommUtils.parseForJson(info);
            
            if( tracing )              
            CommUtils.outgreen(
                "     VRLL24 | update:" + info
                        + " jsonObj=" + jsonObj + " doingStep=" + doingStepSynch
                        + " " + Thread.currentThread().getName());    //Grizzly            
            if (jsonObj == null) {
            	CommUtils.outred("     VRLL24 | update ERROR Json:" + info);
                return;
            }            
            if (jsonObj.get("endmove") != null) {
            	
            	String move        = jsonObj.get("move").toString();
                boolean moveresult = checkMoveResult(jsonObj);
                if (moveresult) {
                	handleMoveok(  move );
                    return;
                } 
                 else {
                	handleMoveko(  move );
                	 return;
                }
              
            }//endmove!=null
            if (jsonObj.get("collision") != null) {
            	handleCollision();
               return;
            }          	 
            if (info.contains("_notallowed")) {
                CommUtils.outred("     VRLL24 | update WARNING!!! _notallowed unexpected in " + info);
                halt();
                return;
            }
            if (jsonObj.get("sonarName") != null) {
            	//if( sonarDataNum++ == 0 )
            	handleSonar(jsonObj);  //potrebbe entrare in loop 
                return;
            } 
        } catch (Exception e) {
            CommUtils.outred("     VRLL24 | update ERROR:" + e.getMessage());
        }
    }

    /*
     * --------------------------------------------
     * Timer part
     * --------------------------------------------
     */
    private Long timeStart = 0L;

    public void startTimer() {
        elapsed = 0;
        timeStart = System.currentTimeMillis();
    }

    public int getDuration() {
        long duration = (System.currentTimeMillis() - timeStart);
        return (int) duration;
    }

/*
 * --------------------------------------------
 * The synch Step moves
 * --------------------------------------------
 */

    @Override
    public boolean step(long time) throws Exception {
        doingStepSynch = true;
        String cmd    = VrobotMsgs.forwardcmd.replace("TIME", "" + time);
        String result = requestSynch(cmd);
        doingStepSynch = false;
        return result.contains("true");
    }

//    @Override
//    public void stepAsynch(int time) {
//    }

    protected String requestSynch(String msg) throws Exception {
        asynchMoveResult = null;
        //Invio fire-and.forget e attendo modifica di  moveResult da update
        startTimer();
        if( tracing ) CommUtils.outyellow("     VRLL24 | requestSynch " + msg);
        conn.forward(msg);
        return waitForResult();  //lo dovrebbe sbloccare il modello qak
    }
    

    protected String waitForResult() throws Exception {
        synchronized (this) {
            while (asynchMoveResult == null) {
                wait();
            }
            return asynchMoveResult;
        }
    }
    protected void activateWaiting(String move, String endmove){
        synchronized (this) {  //sblocca request sincrona per checkRobotAtHome
            asynchMoveResult = endmove;
            notifyAll();
        }
    }

    protected void emitInfo(IApplMessage info) {
    	CommUtils.outmagenta("     VRLL24  | emitInfoooo " + info );
        if(owner!=null) MsgUtil.emitLocalStreamEvent(info,owner,null);  
        //if(owner!=null) MsgUtil.emitLocalEvent(info,owner,null);   
    	//if(owner!=null) owner.emit( owner.getContext(), info, null );
    }
    
    protected void sendInfo(IApplMessage msg) {
    	if(owner!=null)  MsgUtil.sendMsg(msg,owner,null); //null:continuation
    }
    
    /*
     * A main just to test ...
     */
    public static void main(String[] args) throws Exception {
        CommUtils.aboutThreads("Before start - ");
//        VrobotLLMoves24 appl = VrobotLLMoves24.create("localhost",null); //new VrobotLLMoves24("localhost",null);
//        appl.move("a");
//        appl.move("d");
//        CommUtils.delay(500);
//        appl.move("w");
//        CommUtils.delay(1000);
//        appl.move("h");
//        CommUtils.delay(1000);
//        appl.move("s");
//        CommUtils.delay(1000);
//        appl.move("h");
        CommUtils.aboutThreads("At end - ");
    }
}

