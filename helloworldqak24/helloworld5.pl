%====================================================================================
% helloworld5 description   
%====================================================================================
dispatch( out, out(TERM) ).
%====================================================================================
context(ctxhello, "localhost",  "TCP", "8005").
 qactor( display, ctxhello, "it.unibo.display.Display").
 static(display).
  qactor( worker, ctxhello, "it.unibo.worker.Worker").
dynamic(worker). %%Oct2023 
  qactor( creator, ctxhello, "it.unibo.creator.Creator").
 static(creator).
