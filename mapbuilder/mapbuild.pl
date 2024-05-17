%====================================================================================
% mapbuild description   
%====================================================================================
dispatch( stepdone, stepdone(X) ). %automsg
dispatch( stepfailed, stepfailed(X) ). %automsg
dispatch( vrinfo, vrinfo(A,B) ). %from VrobotLLMoves24
%====================================================================================
context(ctxmapbuild, "localhost",  "TCP", "8720").
 qactor( mapbuilder, ctxmapbuild, "it.unibo.mapbuilder.Mapbuilder").
 static(mapbuilder).
