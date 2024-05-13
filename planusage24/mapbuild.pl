%====================================================================================
% mapbuild description   
%====================================================================================
dispatch( cmd, cmd(MOVE) ).
request( engage, engage(ARG,STEPTIME) ).
dispatch( disengage, disengage(ARG) ).
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
event( alarm, alarm(X) ).
request( doplan, doplan(PATH,OWNER) ).
reply( doplandone, doplandone(ARG) ).  %%for doplan
reply( doplanfail, doplanfail(ARG) ).  %%for doplan
%====================================================================================
context(ctxmapbuild, "localhost",  "TCP", "8032").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( mapbuilder, ctxmapbuild, "it.unibo.mapbuilder.Mapbuilder").
 static(mapbuilder).
