%====================================================================================
% servicemathsynch description   
%====================================================================================
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
dispatch( serverinfo, serverinfo(SOURCE,INFO) ).
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8011").
 qactor( servicemath, ctxservice, "it.unibo.servicemath.Servicemath").
 static(servicemath).
  qactor( caller, ctxservice, "it.unibo.caller.Caller").
 static(caller).
  qactor( serviceobserver, ctxservice, "it.unibo.serviceobserver.Serviceobserver").
 static(serviceobserver).
