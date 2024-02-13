%====================================================================================
% mindbody description   
%====================================================================================
dispatch( sensed, sensed(X) ).
dispatch( sound, sound(X) ).
dispatch( work, work(X) ).
dispatch( mindcmd, mindcmd(X) ).
%====================================================================================
context(ctxmb, "localhost",  "TCP", "8045").
 qactor( mind, ctxmb, "it.unibo.mind.Mind").
 static(mind).
  qactor( body, ctxmb, "it.unibo.body.Body").
 static(body).
  qactor( worldmock, ctxmb, "it.unibo.worldmock.Worldmock").
 static(worldmock).
