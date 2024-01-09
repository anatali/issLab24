%====================================================================================
% helloworld6 description   
%====================================================================================
dispatch( out, out(TERM) ).
%====================================================================================
context(ctxhello, "localhost",  "TCP", "8006").
 qactor( display, ctxhello, "it.unibo.display.Display").
 static(display).
  qactor( worker, ctxhello, "it.unibo.worker.Worker").
 static(worker).
