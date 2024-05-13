%====================================================================================
% basicrobot description   
%====================================================================================
dispatch( halt, halt(X) ).
dispatch( move, move(M) ).
request( cmd, cmd(MOVE,T) ). %MOVE = w|s|a|d|p   mosse del virtual robot
reply( cmddone, cmddone(R) ).  %%for cmd
reply( cmdfailed, cmdfailed(T,CAUSE) ).  %%for cmd
dispatch( vrinfo, vrinfo(A,B) ).
event( sonardata, sonar(DISTANCE) ).
event( info, info(X) ).
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
 static(basicrobot).
