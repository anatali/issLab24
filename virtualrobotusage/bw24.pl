%====================================================================================
% bw24 description   
%====================================================================================
dispatch( cmd, cmd(MOVE) ). %MOVE = w|s|a|d|p   mosse del virtual robot
event( vrinfo, vrinfo(A,B) ).
dispatch( vrinfo, vrinfo(A,B) ).
event( sonardata, sonar(DISTANCE) ).
event( obstacle, obstacle(X) ).
dispatch( pause, pause(X) ).
%====================================================================================
context(ctxbw24, "localhost",  "TCP", "8120").
 qactor( bw24core, ctxbw24, "it.unibo.bw24core.Bw24core").
 static(bw24core).
  qactor( sonar24mock, ctxbw24, "it.unibo.sonar24mock.Sonar24mock").
 static(sonar24mock).
  qactor( vrobserver, ctxbw24, "it.unibo.vrobserver.Vrobserver").
 static(vrobserver).
