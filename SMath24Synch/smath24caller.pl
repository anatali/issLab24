%====================================================================================
% smath24caller description   
%====================================================================================
request( dofibo, dofibo(N) ). %request to send to the service
reply( fibodone, fibodone(CALLER,N,RESULT,REQTIME,ELABTIME) ).  %%for dofibo
dispatch( callerinfo, callerdone(CALLER,REQTIME,ELABTIME,ELAPSED) ).
dispatch( obsend, obsend(X) ). %automsg of callerobserver
request( info, info(X) ). %sent by the testUnit to the callerobserver
reply( obsinfo, obsinfo(X) ).  %%for info
%====================================================================================
context(ctxcaller, "localhost",  "TCP", "8035").
context(ctxsmath, "127.0.0.1",  "TCP", "8033").
 qactor( smathsynch, ctxsmath, "external").
  qactor( c1, ctxcaller, "it.unibo.c1.C1").
 static(c1).
