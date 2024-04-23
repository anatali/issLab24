%====================================================================================
% vrobotusage description   
%====================================================================================
dispatch( cmd, cmd(MOVE) ). %MOVE = w|s|a|d   mosse del virtual robot
event( sonardata, sonar(DISTANCE) ).
event( obstacle, obstacle(X) ).
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
dispatch( stepdone, stepdone(TIME) ).
%====================================================================================
context(ctxvrobotusage, "localhost",  "TCP", "8120").
 qactor( vrclienthl, ctxvrobotusage, "it.unibo.vrclienthl.Vrclienthl").
 static(vrclienthl).
  qactor( basicrobot0, ctxvrobotusage, "it.unibo.basicrobot0.Basicrobot0").
 static(basicrobot0).
  qactor( vrobserver, ctxvrobotusage, "it.unibo.vrobserver.Vrobserver").
 static(vrobserver).
