%====================================================================================
% pong24events description   
%====================================================================================
event( ball, ball(N) ). %info exchanged by the players
%====================================================================================
context(ctxpongev, "localhost",  "TCP", "8015").
context(ctxpingev, "127.0.0.1",  "TCP", "8014").
 qactor( pingev, ctxpingev, "external").
  qactor( pongev, ctxpongev, "it.unibo.pongev.Pongev").
 static(pongev).
