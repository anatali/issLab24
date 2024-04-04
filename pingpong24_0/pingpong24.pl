%====================================================================================
% pingpong24 description   
%====================================================================================
dispatch( ball, ball(N) ). %info exchanged by the players
dispatch( ballview, ball(N) ). %observed info | payload not mandatory
request( info, info(X) ). %sent by the testUnit to the referee
reply( obsinfo, obsinfo(X) ).  %%for info
event( startgame, startgame(X) ).
event( stopgame, stopgame(X) ).
%====================================================================================
context(ctxtest, "localhost",  "TCP", "8014").
 qactor( ping, ctxtest, "it.unibo.ping.Ping").
 static(ping).
  qactor( pong, ctxtest, "it.unibo.pong.Pong").
 static(pong).
  qactor( referee, ctxtest, "it.unibo.referee.Referee").
 static(referee).
