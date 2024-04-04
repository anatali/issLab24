%====================================================================================
% pingping24 description   
%====================================================================================
dispatch( ball, ball(N) ).
%====================================================================================
context(ctxping, "127.0.0.1",  "TCP", "8014").
context(ctxpong, "localhost",  "TCP", "8015").
 qactor( ping, ctxping, "it.unibo.ping.Ping").
 static(ping).
  qactor( pong, ctxpong, "it.unibo.pong.Pong").
 static(pong).
