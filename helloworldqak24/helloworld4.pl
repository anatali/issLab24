%====================================================================================
% helloworld4 description   
%====================================================================================
dispatch( info, info(SOURCE,TERM) ).
%====================================================================================
context(ctxhello, "localhost",  "TCP", "8004").
 qactor( display, ctxhello, "it.unibo.display.Display").
 static(display).
  qactor( worker, ctxhello, "it.unibo.worker.Worker").
 static(worker).
