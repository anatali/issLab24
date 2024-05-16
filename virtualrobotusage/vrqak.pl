%====================================================================================
% vrqak description   
%====================================================================================
dispatch( halt, halt(X) ).
dispatch( move, move(M) ).
request( cmd, cmd(MOVE,T) ). %MOVE = w|s|a|d|p   mosse del virtual robot
reply( cmddone, cmddone(R) ).  %%for cmd
reply( cmdfailed, cmdfailed(T,CAUSE) ).  %%for cmd
dispatch( vrinfo, vrinfo(A,B) ).
event( sonardata, sonar(DISTANCE) ).
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
%====================================================================================
context(ctxvrqak, "localhost",  "TCP", "8125").
 qactor( vrqak, ctxvrqak, "it.unibo.vrqak.Vrqak").
 static(vrqak).
