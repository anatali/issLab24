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
 *      obstacle  : obstacle(D)
 *      vrinfo    : vrinfo(A,B)  //MOVE,ENDMOVE | elapsed,collision | obstacle,unknown
 * 
 * e in reply (per stepAsynch), sulla base della specifica:
 * 
 *   Request step       : step(TIME)  //concettualmente per la chiamata stepAsynch
 *   Reply stepdone     : stepdone(V)                 for step
 *   Reply stepfailed   : stepfailed(DURATION, CAUSE) for step
 * 
 *  observer over wsconn
 *  connect
 *  move
 *  step
 *  stepAsynch
 *  update
 *  requestSynch
 * ------------------------------------------------------------------------
*/
public class VrobotHLMoves24 extends ApplAbstractObserver implements IVrobotMoves {
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
    public static VrobotHLMoves24 create( String vitualRobotIp, ActorBasic owner ) {
    	return new VrobotHLMoves24( vitualRobotIp, owner );
    }
    
    //Constructor
    public VrobotHLMoves24(String vitualRobotIp, ActorBasic owner) {
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
       CommUtils.aboutThreads("     	VRHL24 | CREATED");
       //CommUtils.outyellow("     VRHL24 | CREATED in " + Thread.currentThread().getName());
    }
    
    public void setTrace(boolean v){
        tracing = v;
    }
    public Interaction getConn() {
        return conn;
    }


    @Override
    public void move( String cmd ) throws Exception{
        //CommUtils.outyellow("     VrobotHLMovesActors24 move " + cmd);
        if( cmd.equals("w") ) forward( 2500 );
        else if( cmd.equals("s") ) backward( 2500 );
        else if( cmd.equals("a") || cmd.equals("l")) turnLeft(  );
        else if( cmd.equals("d") || cmd.equals("r")) turnRight(  );
        else if( cmd.equals("h")  ) halt(  );
        else if( cmd.equals("p") ) stepAsynch( 350 ); //TODO from file
        //else if( cmd.equals("p") ) step( 350 );
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
	        //CommUtils.outgreen("     VRHL24 | halt");
	        conn.forward(VrobotMsgs.haltcmd);
	        CommUtils.delay(50); //wait for halt completion since halt on ws does not send answer
	        //CommUtils.outgreen("     VRHL24 | halt done " + moveResult );
		}catch(Exception e) {
			CommUtils.outred("halt error (strange....)" + e.getMessage() );
		}
    }

    protected void showMsg( String msg) {
    	
    }

/* 
 * ----------------------------------------
 * Observer part   
 * ----------------------------------------
*/
     
    protected void handleSonar(JSONObject jsonObj) {
        if (jsonObj.get("sonarName") != null) { //defensive
            long d = (long) jsonObj.get("distance") ;
            IApplMessage sonarEvent = CommUtils.buildEvent( "vrhl24","sonardata","'"+"sonar(" +d + " )"+"'");
            //Imviare un msg ad owner perchè generi un evento a favore di sonarobs/engager
            //CommUtils.outmagenta("     VRHL24 | EMITS:" + sonarEvent);
            if(owner!=null) MsgUtil.emitLocalEvent(sonarEvent,owner,null);  
            if(owner!=null) MsgUtil.emitLocalStreamEvent(sonarEvent,owner,null);
        }
    }
    
    protected void handleMoveok(String move) {
    	elapsed = getDuration();
        if( ( move.equals("turnLeft") || move.equals("turnRight")) ){
            activateWaiting( "true" );
            return;
        }
        if( doingStepAsynch ) {
        	if(owner!=null) {
        		IApplMessage msg = MsgUtil.buildReply("vrhl24","stepdone","stepdone(ok)",owner.getName());
        		//Invia reply all'owner
        		MsgUtil.sendMsg(msg,owner,null); //null is for continuation
        		doingStepAsynch = false;
        	}
        	return;
        }  	
        if( ! doingStepSynch ) {   //DISPATCH
            String wenvInfo = toApplMsg.replace("wenvinfo","vrinfo") 
                    .replace("CONTENT", "vrinfo(" + move + ", elapsed)");
            IApplMessage msg = new ApplMessage(wenvInfo);
       	    if(owner!=null)  MsgUtil.sendMsg(msg,owner,null); //continuation
        }else {  //move is a forwardcmd for step
            activateWaiting("true" );
        }        
    }
    
    protected void handleMoveko(String move) {
    	elapsed = getDuration();
    	if (move.contains("collision")) {
    		if( doingStepAsynch ) {
            	if(owner!=null) {
            		IApplMessage msg = MsgUtil.buildReply(
            				"vrhl24","stepfailed","stepfailed(D, C)".replace("D",""+elapsed).replace("C", "obstacle"),owner.getName());
            		//Invia reply all'owner con la durata effettiva D prima della collisione
            		MsgUtil.sendMsg(msg,owner,null); //null is for continuation
            		doingStepAsynch = false;
            	}
            	return;
    		}
            if(  ! doingStepSynch ) {  
                 String wenvInfo = toApplMsg
                         .replace("wenvinfo", "vrinfo")  
                         .replace("CONTENT","vrinfo(" + elapsed + ", collision )");
                 IApplMessage msg = new ApplMessage(wenvInfo);  //DISPATCH
                 if(owner!=null)  MsgUtil.sendMsg(msg, owner, null);  
            } else {
                IApplMessage collisionEvent = CommUtils.buildEvent(
                        "vrhl24","obstacle","obstacle(unknown)" );
                if(owner!=null) MsgUtil.emitLocalEvent(collisionEvent,owner,null);         
                if(owner!=null) MsgUtil.emitLocalStreamEvent(collisionEvent,owner,null);  
            }
            activateWaiting("false"  );
        }    	
    }
    
    protected void handleCollision( ) {
    	halt(); //interrompe la move che provocato la collision
        IApplMessage collisionEvent = CommUtils.buildEvent(
                "vrhlsprt","vrinfo","vrinfo(obstacle,unknown)" );
        //CommUtils.outred("     VrobotHLMovesActors24 | emit " + collisionEvent);
        if(owner!=null) MsgUtil.emitLocalEvent(collisionEvent,owner,null);   
        if(owner!=null) MsgUtil.emitLocalStreamEvent(collisionEvent,owner,null);  
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
            CommUtils.outblue(
                "     VRHL24 | update:" + info
                        + " jsonObj=" + jsonObj + " doingStep=" + doingStepSynch
                        + " " + Thread.currentThread().getName());    //Grizzly            
            if (jsonObj == null) {
                CommUtils.outred("     VRHL24 | update ERROR Json:" + info);
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
                CommUtils.outred("     VRHL24 | update WARNING!!! _notallowed unexpected in " + info);
                halt();
                return;
            }
            if (jsonObj.get("sonarName") != null) {
            	handleSonar(jsonObj);
                return;
            } 
        } catch (Exception e) {
            CommUtils.outred("     VRHL24 | update ERROR:" + e.getMessage());
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
 * The Step moves
 * --------------------------------------------
 */

    @Override
    public boolean step(long time) throws Exception {
        doingStepSynch = true;
        //if( tracing )
            //CommUtils.outgreen("     VRHL24 | step time=" + time);
        String cmd    = VrobotMsgs.forwardcmd.replace("TIME", "" + time);
        String result = requestSynch(cmd);
        //if( tracing )
            //CommUtils.outgreen("     VRHL24 | step result="+result);
        //result=true elapsed=... OPPURE collision elapsed=...
        doingStepSynch = false;
        return result.contains("true");
    }

    @Override
    public void stepAsynch(int time) {
        try {
        	//CommUtils.outred("     VRHL24 | stepAsynch");
        	doingStepAsynch = true;
            startTimer(); //per getDuration()
            if( tracing ) CommUtils.outyellow("     VRHL24 | stepAsynch" );
            conn.forward(VrobotMsgs.forwardcmd.replace("TIME", "" + time));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String requestSynch(String msg) throws Exception {
        asynchMoveResult = null;
        //Invio fire-and.forget e attendo modifica di  moveResult da update
        startTimer();
        if( tracing ) CommUtils.outyellow("     VRHL24 | requestSynch " + msg);
        conn.forward(msg);
        return waitForResult();  //lo dovrebbe sbloccare il modello qak
    }
    

    protected String waitForResult() throws Exception {
        synchronized (this) {
            while (asynchMoveResult == null) {
                wait();
            }
            if( tracing ) CommUtils.outyellow("     VRHL24 | requestSynch RESUMES moveResult=" + asynchMoveResult);
            return asynchMoveResult;
        }
    }
    protected void activateWaiting(String endmove){
        synchronized (this) {  //sblocca request sincrona per checkRobotAtHome
            //CommUtils.outmagenta("activateWaiting ... ");
            asynchMoveResult = endmove;
            notifyAll();
        }
    }

    
    /*
     * A main just to test ...
     */
    public static void main(String[] args) throws Exception {
        CommUtils.aboutThreads("Before start - ");
        VrobotHLMoves24 appl = VrobotHLMoves24.create("localhost",null); //new VrobotHLMovesActors24("localhost",null);
        appl.move("a");
        appl.move("d");
        CommUtils.delay(500);
        appl.move("w");
        CommUtils.delay(1000);
        appl.move("h");
        CommUtils.delay(1000);
        appl.move("s");
        CommUtils.delay(1000);
        appl.move("h");
        CommUtils.aboutThreads("At end - ");
    }
}

