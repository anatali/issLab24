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
 * --------------------------------------------------------
 *  observer over wsconn
 *  connect
 *  move
 *  step
 *  stepAsynch
 *  update
 *  sendSynchToWenv
 * --------------------------------------------------------
*/
public class VrobotHLMovesActors24 extends ApplAbstractObserver implements IVrobotMovesAsynch {
    protected String vitualRobotIp = "localhost";
    protected Interaction conn;
    protected int elapsed       = 0;     //modified by update
    protected String moveResult = null;  //for observer part
    protected int threadCount = 1;
    protected ActorBasic owner;
    protected String toApplMsg   ;
    protected boolean tracing   = false;
    protected boolean doingStep = false;
    
    public VrobotHLMovesActors24(String vitualRobotIp, ActorBasic owner) {
    	connect(vitualRobotIp, owner);
    }
    public VrobotHLMovesActors24(  ) {
    	 
    }
    public static VrobotHLMovesActors24 create(  ) {
    	return new VrobotHLMovesActors24( );
    }
    
    public void connect(String vitualRobotIp, ActorBasic owner) {
    	this.vitualRobotIp = vitualRobotIp;
    	this.owner         = owner;
        this.conn = WsConnection.create(vitualRobotIp+":8091");
        ((WsConnection) conn).addObserver(this);
        if( owner != null )
        	toApplMsg = "msg(wenvinfo, dispatch, support, RECEIVER, CONTENT, 0)"
               .replace("RECEIVER",owner.getName());
        else
            toApplMsg = "msg(wenvinfo, dispatch, support, RECEIVER, CONTENT, 0)"
            .replace("RECEIVER","cmdgui");
        	
       CommUtils.aboutThreads("     VrobotHLMovesActors24 |");
       CommUtils.outyellow(
               "     VrobotHLMovesActors24 | CREATED in " + Thread.currentThread().getName());
    }
    
    public void setTrace(boolean v){
        tracing = v;
    }
    public Interaction getConn() {
        return conn;
    }


    @Override
    public void move( String cmd ) throws Exception{
        CommUtils.outyellow("     VrobotHLMovesActors24 move " + cmd);
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
        //CommUtils.outyellow(VrobotMsgs.turnleftcmd);
        sendSynchToWenv(VrobotMsgs.turnleftcmd);
    }

    @Override
    public void turnRight() throws Exception {
        sendSynchToWenv(VrobotMsgs.turnrightcmd);
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
    public void halt() throws Exception {
        //CommUtils.outgreen("     VrobotHLMovesActors24 | halt");
        conn.forward(VrobotMsgs.haltcmd);
        CommUtils.delay(50); //wait for halt completion since halt on ws does not send answer
        //CommUtils.outgreen("     VrobotHLMovesActors24 | halt done " + moveResult );
    }


/* 
 * Observer part   
 */
    @Override
    public void update(String info) {
         try {
            elapsed = getDuration();
            //if( tracing )
                
                CommUtils.outblack(
                    "     VrobotHLMovesActors24 | update:" + info
                            + " elapsed=" + elapsed + " doingStep=" + doingStep
                            + " " + Thread.currentThread().getName());
               
             JSONObject jsonObj = CommUtils.parseForJson(info);
            if (jsonObj == null) {
                CommUtils.outred("     VrobotHLMovesActors24 | update ERROR Json:" + info);
                return;
            }
            if (info.contains("_notallowed")) {
                CommUtils.outred("     VrobotHLMovesActors24 | update WARNING!!! _notallowed unexpected in " + info);
                halt();
                return;
            }
            if (jsonObj.get("sonarName") != null) {
                long d = (long) jsonObj.get("distance") ;
                IApplMessage sonarEvent = CommUtils.buildEvent(
                        "vrhlsprt","sonardata","'"+"sonar(" +d + " )"+"'");
                //Imviare un msg ad owner perchè generi un evento a favore di sonarobs/engager
                if(owner!=null) MsgUtil.emitLocalEvent(sonarEvent,owner,null);  //percepito da sonarobs/engager
                return;
            }
            if (jsonObj.get("collision") != null) {
            	CommUtils.outmagenta("collision");
             /*
                IApplMessage collisionEvent = CommUtils.buildEvent(
                        "vrhlsprt","obstacle","obstacle(unknown)" );
                MsgUtil.emitLocalEvent(collisionEvent,owner,null);
             */
                return;
            }
            if (jsonObj.get("endmove") != null) {
                //{"endmove":"true/false ","move":"..."}
                boolean endmovee = jsonObj.get("endmove").toString().contains("true");
                String move      = jsonObj.get("move").toString();
                //CommUtils.outred("     VrobotHLMovesActors24 | update move=" + move);
                //move moveForward-collision or moveBackward-collision
                
                IApplMessage vrinfo = CommUtils.buildEvent(
                        "vrhlsprt","vrinfo","'"+"vrinfo(" + move + ","+ endmovee +" )"+"'");
                if(owner!=null)  MsgUtil.emitLocalEvent(vrinfo,owner,null);   
                
                if (endmovee) {
                    if( ( move.equals("turnLeft") || move.equals("turnRight")) ){
                        activateWaiting("" + endmovee);
                        return;
                    }
                    String wenvInfo = toApplMsg.replace("wenvinfo","stepdone") //"stepdone"
                            .replace("CONTENT", "stepdone(" + elapsed + ")");
                    IApplMessage msg = new ApplMessage(wenvInfo);
                    if( ! doingStep ) { 
                    	if(owner!=null)  MsgUtil.sendMsg(msg,owner,null); //continuation
                    }else{
                        activateWaiting("" + endmovee);
                    }
                    return;
                } else if (move.contains("interrupted")) {/*
                    String wenvInfo = toApplMsg.replace("wenvinfo","stepfailed") //stepfailed
                            .replace("CONTENT", "stepfailed(" + elapsed + ", interrupt )");
                    IApplMessage msg = new ApplMessage(wenvInfo);
                    //TODO Actor23Utils.sendMsg(msg, owner);
                    MsgUtil.sendMsg(msg,owner,null);
                    */
                    return;
                 }else if (move.contains("collision")) {
                    String wenvInfo = toApplMsg
                            .replace("wenvinfo", "stepfailed") //stepfailed
                            .replace("CONTENT","stepfailed(" + elapsed + ", collision )");
                    IApplMessage msg = new ApplMessage(wenvInfo);
                    if(  ! doingStep ) { //! removed ARPIL 2024
                        //TODO Actor23Utils.sendMsg(msg, owner);
                    	if(owner!=null)  MsgUtil.sendMsg(msg, owner, null);  
                    }else {
                        IApplMessage collisionEvent = CommUtils.buildEvent(
                                "vrhlsprt","obstacle","obstacle(unknown)" );
                        MsgUtil.emitLocalEvent(collisionEvent,owner,null);                    	
                    }
                    activateWaiting("false"  );
                    //CommUtils.outred("     VrobotHLMovesActors24 | update END move=" + move);
                }
                return;
            }
        } catch (Exception e) {
            CommUtils.outred("     VrobotHLMovesActors24 | update ERROR:" + e.getMessage());
        }
    }


    //  Timer part
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
 * Step
 */

    @Override
    public boolean step(long time) throws Exception {
        doingStep = true;
        //if( tracing )
            //CommUtils.outgreen("     VrobotHLMovesActors24 | step time=" + time);
        String cmd    = VrobotMsgs.forwardcmd.replace("TIME", "" + time);
        String result = sendSynchToWenv(cmd);
        //if( tracing )
            //CommUtils.outgreen("     VrobotHLMovesActors24 | step result="+result);
        //result=true elapsed=... OPPURE collision elapsed=...
        doingStep = false;
        return result.contains("true");
    }

    @Override
    public void stepAsynch(int time) {
        try {
            startTimer(); //per getDuration()
            if( tracing ) CommUtils.outblack("     VrobotHLMovesActors24 | stepAsynch" );
            conn.forward(VrobotMsgs.forwardcmd.replace("TIME", "" + time));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String sendSynchToWenv(String msg) throws Exception {
        moveResult = null;
        //Invio fire-and.forget e attendo modifica di  moveResult da update
        startTimer();
        CommUtils.outyellow("     VrobotHLMovesActors24 | sendSynchToWenv " + msg);
        conn.forward(msg);
        return waitForResult();  //lo dovrebbe sbloccare il modello qak
    }
    

    protected String waitForResult() throws Exception {
        synchronized (this) {
            while (moveResult == null) {
                wait();
            }
            if( tracing ) CommUtils.outyellow("     VrobotHLMovesActors24 | sendSynchToWenv RESUMES moveResult=" + moveResult);
            return moveResult;
        }
    }
    protected void activateWaiting(String endmove){
        synchronized (this) {  //sblocca request sincrona per checkRobotAtHome
            //CommUtils.outmagenta("activateWaiting ... ");
            moveResult = endmove;
            notifyAll();
        }
    }

    
    /*
     * Just to test ...
     */
    public static void main(String[] args) throws Exception {
        CommUtils.aboutThreads("Before start - ");
        VrobotHLMovesActors24 appl = VrobotHLMovesActors24.create(); //new VrobotHLMovesActors24("localhost",null);
        appl.connect("localhost",null);
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

