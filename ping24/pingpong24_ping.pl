%====================================================================================
% pingpong24_ping description   
%====================================================================================
dispatch( ball, ball(N) ). %info scambiata
event( startgame, startgame(X) ).
event( stopgame, stopgame(X) ).
%====================================================================================
context(ctxping, "localhost",  "TCP", "8014").
context(ctxpong, "127.0.0.1",  "TCP", "8015").
 qactor( pong, ctxpong, "external").
  qactor( ping, ctxping, "it.unibo.ping.Ping").
 static(ping).
