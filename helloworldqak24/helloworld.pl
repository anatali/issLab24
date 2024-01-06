%====================================================================================
% helloworld description   
%====================================================================================
dispatch( start, start(X) ).
dispatch( out, out(TERM) ).
%====================================================================================
context(ctxhello, "localhost",  "TCP", "8001").
 qactor( worker, ctxhello, "it.unibo.worker.Worker").
 static(worker).
