%====================================================================================
% servicemathsynchbase description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "servicemathsynch/events").
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8011").
 qactor( servicemathcoded, ctxservice, "Servicecodedbasic").
 static(servicemathcoded).
%%  qactor( caller_1, ctxservice, "it.unibo.caller_1.Caller_1").
%% static(caller_1).