%====================================================================================
% mapbuild description   
%====================================================================================
dispatch( move, move(M) ).
request( step, step(T) ).
reply( stepdone, stepdone(X) ).  %%for step
reply( stepfailed, stepfailed(X) ).  %%for step
%====================================================================================
context(ctxmapbuild, "localhost",  "TCP", "8720").
context(ctxvrqak, "127.0.0.1",  "TCP", "8125").
 qactor( vrqak, ctxvrqak, "external").
  qactor( mapbuilder, ctxmapbuild, "it.unibo.mapbuilder.Mapbuilder").
 static(mapbuilder).
