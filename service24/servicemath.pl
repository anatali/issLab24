%====================================================================================
% servicemath description   
%====================================================================================
request( doaction, doaction(REQID,RARG,RCALLER,SERVICE) ).
reply( actiondone, actiondone(RCALLER,REQID,N,R) ).  %%for doaction
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,R) ).  %%for dofibo
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
  qactor( actionexecutor, ctxservice, "it.unibo.actionexecutor.Actionexecutor").
dynamic(actionexecutor). %%Oct2023 
