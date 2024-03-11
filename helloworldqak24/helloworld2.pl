%====================================================================================
% helloworld2 description   
%====================================================================================
dispatch( out, out(TERM) ).
%====================================================================================
context(ctxhello, "localhost",  "TCP", "8001").
 qactor( display, ctxhello, "DisplayCodedQak").
 static(display).
  qactor( worker, ctxhello, "it.unibo.worker.Worker").
 static(worker).
