%====================================================================================
% mindbody description   
%====================================================================================
dispatch( sensed, sensed(X) ).
dispatch( sound, sound(X) ).
dispatch( work, work(X) ).
dispatch( mindcmd, mindcmd(X) ).
dispatch( out, out(S) ).
dispatch( show, show(S) ).
%====================================================================================
context(ctxmb, "localhost",  "TCP", "8045").
 qactor( mind, ctxmb, "it.unibo.mind.Mind").
 static(mind).
  qactor( body, ctxmb, "it.unibo.body.Body").
 static(body).
  qactor( worldmock, ctxmb, "it.unibo.worldmock.Worldmock").
 static(worldmock).
  qactor( display, ctxmb, "it.unibo.display.Display").
 static(display).
