%====================================================================================
% pingpong24_pingevents description   
%====================================================================================
event( ball, ball(N) ). %info exchanged by the players
%====================================================================================
context(ctxping, "localhost",  "TCP", "8014").
context(ctxpong, "127.0.0.1",  "TCP", "8015").
 qactor( pong, ctxpong, "external").
  qactor( ping, ctxping, "it.unibo.ping.Ping").
 static(ping).
