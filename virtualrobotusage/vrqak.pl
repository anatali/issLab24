%====================================================================================
% vrqak description   
%====================================================================================
dispatch( cmd, cmd(MOVE) ). %MOVE = w|s|a|d|p   mosse del virtual robot
dispatch( vrinfo, vrinfo(A,B) ).
event( sonardata, sonar(DISTANCE) ).
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
%====================================================================================
context(ctxvrqak, "localhost",  "TCP", "8125").
 qactor( vrqak, ctxvrqak, "it.unibo.vrqak.Vrqak").
 static(vrqak).
  qactor( vrobserver, ctxvrqak, "it.unibo.vrobserver.Vrobserver").
 static(vrobserver).
