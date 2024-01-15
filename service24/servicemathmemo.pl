%====================================================================================
% servicemathmemo description   
%====================================================================================
mqttBroker("broker.hivemq.com", "1883", "servicetopic").
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
dispatch( out, out(S) ).
dispatch( show, show(S) ).
request( getfibo, getfibo(N) ).
reply( getfiboanswer, getfiboanswer(N,V) ).  %%for getfibo
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8011").
 qactor( display, ctxservice, "it.unibo.display.Display").
 static(display).
  qactor( caller_test, ctxservice, "it.unibo.caller_test.Caller_test").
 static(caller_test).
  qactor( storage, ctxservice, "it.unibo.storage.Storage").
 static(storage).
  qactor( servicemath, ctxservice, "it.unibo.servicemath.Servicemath").
 static(servicemath).
  qactor( actionexec, ctxservice, "it.unibo.actionexec.Actionexec").
dynamic(actionexec). %%Oct2023 
