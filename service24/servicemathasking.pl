%====================================================================================
% servicemathasking description   
%====================================================================================
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
request( confirm, confirm(X) ).
reply( confirmed, confirmed(X) ).  %%for confirm
dispatch( doelab, doelab(X) ).
dispatch( out, out(S) ).
dispatch( show, show(S) ).
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8011").
 qactor( display, ctxservice, "it.unibo.display.Display").
 static(display).
  qactor( caller_test, ctxservice, "it.unibo.caller_test.Caller_test").
 static(caller_test).
  qactor( servicemath, ctxservice, "it.unibo.servicemath.Servicemath").
 static(servicemath).
  qactor( actionexec, ctxservice, "it.unibo.actionexec.Actionexec").
dynamic(actionexec). %%Oct2023 