%====================================================================================
% servicemathsynchbase description   
%====================================================================================
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8041").
 qactor( servicemath, ctxservice, "Servicecodedbasic").
 static(servicemath).