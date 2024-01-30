%====================================================================================
% servicemathmemo description   
%====================================================================================
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
dispatch( out, out(S) ).
dispatch( show, show(S) ).
request( getfibo, getfibo(N) ).
reply( getfiboanswer, getfiboanswer(N,V) ).  %%for getfibo
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8011").
 qactor( storage, ctxservice, "it.unibo.storage.Storage").
 static(storage).
  qactor( dummy, ctxservice, "it.unibo.dummy.Dummy").
 static(dummy).
  qactor( servicemath, ctxservice, "it.unibo.servicemath.Servicemath").
 static(servicemath).
  qactor( actionexec, ctxservice, "it.unibo.actionexec.Actionexec").
dynamic(actionexec). %%Oct2023 
