%====================================================================================
% servicemathsynch description   
%====================================================================================
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,R) ).  %%for dofibo
dispatch( show, show(S) ).
dispatch( out, out(S) ).
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8011").
 qactor( servicemath, ctxservice, "it.unibo.servicemath.Servicemath").
 static(servicemath).
