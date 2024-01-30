%====================================================================================
% servicemathask description   
%====================================================================================
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
request( confirm, confirm(X) ).
reply( confirmed, confirmed(X) ).  %%for confirm
dispatch( stop, stop(X) ).
dispatch( doelab, doelab(X) ).
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8011").
 qactor( caller, ctxservice, "it.unibo.caller.Caller").
 static(caller).
  qactor( servicemath, ctxservice, "it.unibo.servicemath.Servicemath").
 static(servicemath).
  qactor( actionexec, ctxservice, "it.unibo.actionexec.Actionexec").
dynamic(actionexec). %%Oct2023 
