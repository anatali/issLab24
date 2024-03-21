%====================================================================================
% prodcons24 description   
%====================================================================================
dispatch( distance, distance(D) ). %dispatch di nome uguale a request
request( distance, distance(D) ). %request di nome uguale a dispatch
reply( distanceack, ack(D) ).  %%for distance
%====================================================================================
context(ctxprodcons, "localhost",  "TCP", "8014").
 qactor( producer1, ctxprodcons, "it.unibo.producer1.Producer1").
 static(producer1).
  qactor( producer2, ctxprodcons, "it.unibo.producer2.Producer2").
 static(producer2).
  qactor( consumer, ctxprodcons, "it.unibo.consumer.Consumer").
 static(consumer).
