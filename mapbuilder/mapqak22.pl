%====================================================================================
% mapqak22 description   
%====================================================================================
dispatch( cmd, cmd(MOVE) ).
request( step, step(TIME) ).
event( alarm, alarm(X) ).
request( dopath, dopath(PATH,OWNER) ).
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxmapqak22, "localhost",  "TCP", "8032").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( mapqak22, ctxmapqak22, "it.unibo.mapqak22.Mapqak22").
 static(mapqak22).
