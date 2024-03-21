%====================================================================================
% helloworld4_2ctx description   
%====================================================================================
dispatch( info, info(SOURCE,TERM) ).
dispatch( news, news(SOURCE,TERM) ).
%====================================================================================
context(ctxdisplay, "localhost",  "TCP", "8005").
context(ctxhello, "localhost",  "TCP", "8004").
 qactor( display, ctxdisplay, "it.unibo.display.Display").
 static(display).
  qactor( worker, ctxhello, "it.unibo.worker.Worker").
 static(worker).
  qactor( workerobs, ctxhello, "it.unibo.workerobs.Workerobs").
 static(workerobs).
