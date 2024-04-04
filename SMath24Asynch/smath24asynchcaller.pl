%====================================================================================
% smath24asynchcaller description   
%====================================================================================
request( dofibo, dofibo(N) ). %request to send to the service
reply( fibodone, fibodone(CALLER,N,RESULT,ELABTIME) ).  %%for dofibo
dispatch( callerinfo, callerdone(CALLER,N) ).
dispatch( obsend, obsend(X) ). %automsg of callerobserver
request( info, info(X) ). %sent by the testUnit to the callerobserver
reply( obsinfo, obsinfo(X) ).  %%for info
%====================================================================================
context(ctxcaller, "localhost",  "TCP", "8035").
context(ctxsmath, "127.0.0.1",  "TCP", "8033").
 qactor( smathasynch, ctxsmath, "external").
  qactor( c1, ctxcaller, "it.unibo.c1.C1").
 static(c1).
  qactor( callerobserver, ctxcaller, "it.unibo.callerobserver.Callerobserver").
 static(callerobserver).
  qactor( testmock, ctxcaller, "it.unibo.testmock.Testmock").
 static(testmock).
