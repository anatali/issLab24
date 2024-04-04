%====================================================================================
% smath24synch description   
%====================================================================================
request( dofibo, dofibo(N) ). %request to send to the service
reply( fibodone, fibodone(CALLER,N,RESULT,REQTIME,ELABTIME) ).  %%for dofibo
%====================================================================================
context(ctxsmath, "localhost",  "TCP", "8033").
 qactor( smathsynch, ctxsmath, "it.unibo.smathsynch.Smathsynch").
 static(smathsynch).
