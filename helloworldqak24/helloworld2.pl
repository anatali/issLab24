%====================================================================================
% helloworld2 description   
%====================================================================================
dispatch( start, start(X) ).
dispatch( out, out(TERM) ).
%====================================================================================
context(ctxhello, "localhost",  "TCP", "8001").
 qactor( ca, ctxhello, "DisplayCodedQak").
 static(ca).
  qactor( worker, ctxhello, "it.unibo.worker.Worker").
 static(worker).
