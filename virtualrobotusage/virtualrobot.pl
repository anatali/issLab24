%====================================================================================
% virtualrobot description   
%====================================================================================
dispatch( cmd, cmd(MOVE) ). %MOVE = w|s|a|d|p   mosse del virtual robot
event( vrinfo, vrinfo(A,B) ).
dispatch( vrinfo, vrinfo(A,B) ).
event( sonardata, sonar(DISTANCE) ).
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
%====================================================================================
context(ctxvrqak, "localhost",  "TCP", "8120").
 qactor( vrqak, ctxvrqak, "it.unibo.vrqak.Vrqak").
 static(vrqak).
  qactor( vrobserver, ctxvrqak, "it.unibo.vrobserver.Vrobserver").
 static(vrobserver).
  qactor( vrqakusage, ctxvrqak, "it.unibo.vrqakusage.Vrqakusage").
 static(vrqakusage).
