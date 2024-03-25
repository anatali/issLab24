%====================================================================================
% pingpong24_pong description   
%====================================================================================
dispatch( ball, ball(N) ).
event( startgame, startgame(X) ).
event( stopgame, stopgame(X) ).
%====================================================================================
context(ctxpong, "localhost",  "TCP", "8015").
context(ctxping, "127.0.0.1",  "TCP", "8014").
 qactor( ping, ctxping, "external").
  qactor( pong, ctxpong, "it.unibo.pong.Pong").
 static(pong).
