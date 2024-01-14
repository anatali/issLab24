fibo( 1,  1          ).
fibo( 2,  1          ).
fibo( 4,  3          ).
fibo( 5,  5          ).

fibo( 29,514229 ).
fibo( 30,832040 ).

%% fibo( 39, 63245986   ).

fibo( 40, 102334155  ).
fibo( 43, 433494437  ).
fibo( 44, 701408733  ).


storednums(NUMS) :- findall( N, fibo( N,_ ), NUMS).

%% vedi https://www.progetti.iisleviponti.it/Le_forme_dei_numeri/html/fibo.html