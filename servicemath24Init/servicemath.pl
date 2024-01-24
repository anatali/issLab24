%====================================================================================
% servicemathsynchbase description   
%====================================================================================
%%mqttBroker("broker.hivemq.com", "1883", "servicemathsynch/events").
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8011").
 qactor( servicemathcoded, ctxservice, "Servicecodedbasic").
 static(servicemathcoded).
 
