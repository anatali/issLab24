%====================================================================================
% servicemathsynchbase description   
%====================================================================================
request( dofibo, dofibo(N) ).
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
dispatch( exit, exit(X) ).
%====================================================================================
context(ctxservice, "localhost",  "TCP", "8710").
 qactor( servicemath, ctxservice, "Servicecodedbasic").
 static(servicemath).
