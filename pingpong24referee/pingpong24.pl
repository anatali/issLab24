%====================================================================================
% pingpong24 description   
%====================================================================================
dispatch( ball, ball(N) ). %info exchanged by the players
dispatch( ballview, ball(N) ). %observed info | payload not mandatory
request( info, info(X) ). %sent by the testUnit to the observer
reply( obsinfo, obsinfo(X) ).  %%for info
dispatch( endgame, endgame(X) ). %sent as an automsg by the observer
%====================================================================================
context(ctxtest, "localhost",  "TCP", "8014").
 qactor( ping, ctxtest, "it.unibo.ping.Ping").
 static(ping).
  qactor( pong, ctxtest, "it.unibo.pong.Pong").
 static(pong).
  qactor( pingobserver, ctxtest, "it.unibo.pingobserver.Pingobserver").
 static(pingobserver).
