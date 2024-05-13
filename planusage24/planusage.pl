%====================================================================================
% planusage description   
%====================================================================================
dispatch( move, move(MOVE) ).
dispatch( halt, halt(X) ).
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
event( alarm, alarm(X) ).
event( mapinfo, mapinfo(X) ).
request( doplan, doplan(PATH,OWNER) ).
reply( doplandone, doplandone(ARG) ).  %%for doplan
reply( doplanfail, doplanfail(ARG) ).  %%for doplan
%====================================================================================
context(ctxmapbuild, "localhost",  "TCP", "8032").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( mapbuilder, ctxmapbuild, "it.unibo.mapbuilder.Mapbuilder").
 static(mapbuilder).
