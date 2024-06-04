%====================================================================================
% bwbscrbt description   
%====================================================================================
request( engage, engage(OWNER,STEPTIME) ).
reply( engagedone, engagedone(ARG) ).  %%for engage
reply( engagerefused, engagerefused(ARG) ).  %%for engage
dispatch( disengage, disengage(ARG) ).
dispatch( cmd, cmd(MOVE) ). %MOVE = a|d|l|r|h   
request( cmd, cmd(MOVE,T) ). %MOVE = w|s|p (stepSynch)
reply( cmddone, cmddone(R) ).  %%for cmd
reply( cmdfailed, cmdfailed(T,CAUSE) ).  %%for cmd
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
event( sonardata, sonar(DISTANCE) ).
event( obstacle, obstacle(X) ).
dispatch( brdata, brdata(S,INFO) ).
dispatch( pause, pause(X) ).
dispatch( goon, goon(N) ).
%====================================================================================
context(ctxbwbscrbt, "localhost",  "TCP", "8720").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( bwbrcore, ctxbwbscrbt, "it.unibo.bwbrcore.Bwbrcore").
 static(bwbrcore).
  qactor( bwobserver, ctxbwbscrbt, "it.unibo.bwobserver.Bwobserver").
 static(bwobserver).
