%====================================================================================
% servicemath description   
%====================================================================================
request( doaction, doaction(REQID,RARG,RCALLER,SERVICE) ).
reply( actiondone, actiondone(RCALLER,REQID,N,R) ).  %%for doaction
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,R) ).  %%for dofibo
dispatch( show, show(S) ).
dispatch( out, out(S) ).
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8011").
 qactor( caller1, ctxservice, "it.unibo.caller1.Caller1").
 static(caller1).
  qactor( caller2, ctxservice, "it.unibo.caller2.Caller2").
 static(caller2).
  qactor( display, ctxservice, "it.unibo.display.Display").
 static(display).
  qactor( servicemath, ctxservice, "it.unibo.servicemath.Servicemath").
 static(servicemath).
  qactor( actionexecutor, ctxservice, "it.unibo.actionexecutor.Actionexecutor").
dynamic(actionexecutor). %%Oct2023 
