%====================================================================================
% bw24 description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "sonarbw24data").
dispatch( stepdone, stepdone(X) ).
dispatch( stepfailed, stepfailed(X) ).
event( sonardata, sonar(DISTANCE) ).
event( vrinfo, vrinfo(A,B) ).
dispatch( vrinfo, vrinfo(A,B) ).
event( obstacle, obstacle(D) ). %emesso da WEnv
event( wolf, wolf(D) ). %emesso da sonarmock
%====================================================================================
context(ctxbw24, "localhost",  "TCP", "8120").
 qactor( bw24core, ctxbw24, "it.unibo.bw24core.Bw24core").
 static(bw24core).
