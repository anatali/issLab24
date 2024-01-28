%====================================================================================
% servicemathsynch description   
%====================================================================================
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8710").
 qactor( servicemath, ctxservice, "it.unibo.servicemath.Servicemath").
 static(servicemath).
