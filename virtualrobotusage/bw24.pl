%====================================================================================
% bw24 description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "sonarbw24data").
dispatch( move, move(M) ).
request( step, step(T) ).
reply( stepdone, stepdone(X) ).  %%for step
reply( stepfailed, stepfailed(X) ).  %%for step
event( sonardata, sonar(DISTANCE) ).
event( obstacle, obstacle(D) ).
event( vrinfo, vrinfo(S,INFO) ).
dispatch( vrinfo, vrinfo(A,B) ).
dispatch( stop, stop(X) ).
event( sonardata, sonar(DISTANCE) ).
%====================================================================================
context(ctxbw24, "localhost",  "TCP", "8120").
context(ctxvrqak, "127.0.0.1",  "TCP", "8125").
 qactor( vrqak, ctxvrqak, "external").
  qactor( bw24core, ctxbw24, "it.unibo.bw24core.Bw24core").
 static(bw24core).
  qactor( bwobserver, ctxbw24, "it.unibo.bwobserver.Bwobserver").
 static(bwobserver).
