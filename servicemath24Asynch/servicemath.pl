%====================================================================================
% servicemath description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "alarm").
request( dofibo, dofibo(N) ). %to service
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8011").
 qactor( servicemath, ctxservice, "it.unibo.servicemath.Servicemath").
 static(servicemath).
  qactor( actionexec, ctxservice, "it.unibo.actionexec.Actionexec").
dynamic(actionexec). %%Oct2023 
tracing.
