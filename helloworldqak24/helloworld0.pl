%====================================================================================
% helloworld0 description   
%====================================================================================
dispatch( info, info(N) ). %Informazione inviata dal worker al display
%====================================================================================
context(ctxhello, "localhost",  "TCP", "8000").
 qactor( worker, ctxhello, "it.unibo.worker.Worker").
 static(worker).
  qactor( display, ctxhello, "it.unibo.display.Display").
 static(display).
