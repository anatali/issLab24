%====================================================================================
% smath24asynchfacade description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "servicemathouttopic").
request( dofibo, dofibo(N) ). %to service
reply( fibodone, fibodone(CALLER,N,RESULT,ELABTIME) ).  %%for dofibo
%====================================================================================
context(ctxsmathfacade, "localhost",  "TCP", "8033").
 qactor( smathasynchfacade, ctxsmathfacade, "it.unibo.smathasynchfacade.Smathasynchfacade").
 static(smathasynchfacade).
  qactor( actionexec, ctxsmathfacade, "it.unibo.actionexec.Actionexec").
dynamic(actionexec). %%Oct2023 
