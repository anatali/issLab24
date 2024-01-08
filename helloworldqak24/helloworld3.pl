%====================================================================================
% helloworld3 description   
%====================================================================================
dispatch( out, out(TERM) ).
%====================================================================================
context(ctxhello, "localhost",  "TCP", "8003").
 qactor( display, ctxhello, "it.unibo.display.Display").
 static(display).
  qactor( worker, ctxhello, "it.unibo.worker.Worker").
dynamic(worker). %%Oct2023 
  qactor( starter, ctxhello, "it.unibo.starter.Starter").
 static(starter).
