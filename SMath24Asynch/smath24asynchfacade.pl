%====================================================================================
% smath24asynchfacade description   
%====================================================================================
request( dofibo, dofibo(N) ). %to service
reply( fibodone, fibodone(CALLER,N,RESULT,ELABTIME) ).  %%for dofibo
dispatch( smathinfo, smathinfo(INFO) ).
dispatch( usercmd, usercmd(CMD) ).
%====================================================================================
context(ctxsmathfacade, "localhost",  "TCP", "8033").
 qactor( smathasynchfacade, ctxsmathfacade, "it.unibo.smathasynchfacade.Smathasynchfacade").
 static(smathasynchfacade).
  qactor( actionexec, ctxsmathfacade, "it.unibo.actionexec.Actionexec").
dynamic(actionexec). %%Oct2023 
  qactor( facademock, ctxsmathfacade, "it.unibo.facademock.Facademock").
 static(facademock).
