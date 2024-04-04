%====================================================================================
% smath24synch description   
%====================================================================================
request( dofibo, dofibo(N) ). %to service
reply( fibodone, fibodone(CALLER,N,RESULT,ELABTIME) ).  %%for dofibo
%====================================================================================
context(ctxsmath, "localhost",  "TCP", "8033").
 qactor( smathasynch, ctxsmath, "it.unibo.smathasynch.Smathasynch").
 static(smathasynch).
  qactor( actionexec, ctxsmath, "it.unibo.actionexec.Actionexec").
dynamic(actionexec). %%Oct2023 
