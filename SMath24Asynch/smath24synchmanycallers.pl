%====================================================================================
% smath24synchmanycallers description   
%====================================================================================
request( dofibo, dofibo(N) ). %request to send to the service
reply( fibodone, fibodone(CALLER,N,RESULT,TIME) ).  %%for dofibo
event( startcaller, startcaller(X) ).
dispatch( callerinfo, callerdone(X) ).
request( info, info(X) ). %sent by the testUnit to the callerobserver
reply( obsinfo, obsinfo(X) ).  %%for info
%====================================================================================
context(ctxmanycallers, "localhost",  "TCP", "8035").
context(ctxsmath, "127.0.0.1",  "TCP", "8033").
 qactor( smathsynch, ctxsmath, "external").
  qactor( clr1, ctxmanycallers, "it.unibo.clr1.Clr1").
 static(clr1).
  qactor( clr2, ctxmanycallers, "it.unibo.clr2.Clr2").
 static(clr2).
  qactor( callerobserver, ctxmanycallers, "it.unibo.callerobserver.Callerobserver").
 static(callerobserver).
