%====================================================================================
% servicemath24synch description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "servicemathouttopic").
request( dofibo, dofibo(N) ). %richiesta al serivzio
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ). %%for dofibo | risposta
dispatch( serverinfo, serverinfo(SOURCE,INFO) ). %per observr
event( out, out(S) ). %info emessa dal servizio
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8011").
 qactor( servicemath, ctxservice, "it.unibo.servicemath.Servicemath").
 static(servicemath).
