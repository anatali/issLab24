%====================================================================================
% servicemath description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "servicemathouttopic").
request( dofibo, dofibo(N) ). %to service
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
%====================================================================================
context(ctxsmath, "localhost",  "TCP", "8011").
 qactor( smathasynch, ctxsmath, "it.unibo.smathasynch.Smathasynch").
 static(smathasynch).
  qactor( actionexec, ctxsmath, "it.unibo.actionexec.Actionexec").
dynamic(actionexec). %%Oct2023 
