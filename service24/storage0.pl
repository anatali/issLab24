fibo( 1,  1          ).
fibo( 2,  1          ).
fibo( 4,  3          ).
fibo( 5,  5          ).

fibo( 29,514229 ).
fibo( 30,832040 ).
fibo( 40, 102334155  ).
fibo( 43, 433494437  ).
fibo( 44, 701408733  ).
fibo( 45, 1134903170 ).   %% 1.134.903.170  > 3 sec
fibo( 46, 1836311903 ).   %% 1.836.311.903  > 5 sec


fibo( 47, 2971215073 ).   %% 2.971.215.073           ECCEDE MAXINT
fibo( 48, 4807526976  ).  %% 4.807.526.976 > 13 sec  ECCEDE MAXINT
fibo( 48, 7778742049  ).  %% 7.778.742.049
fibo( 50, 12586269025        ).
fibo( 55, 139583862445       ).
fibo( 65, 17167680177565     ).
fibo( 85, 259695496911122585 ).
fibo( 90, 2880067194370816120).
fibo( 92, 7540113804746346429).  %% 93 ECCEDE MAXINT L
 
maxintjava( 2147483647 ).            %% 2.147.483.647
maxlongjava( 9223372036854775807 ).  %% 9.223.372.036.854.775.807

storednums(NUMS) :- findall( N, fibo( N,_ ), NUMS).

max_iter(L,X) :-
    max_iter_cont(L,0,X).
max_iter_cont([X],MaxAttuale,MaxFinale) :-
    maggiore2(X,MaxAttuale,MaxFinale).
max_iter_cont([X|R],MaxAttuale,MaxFinale) :-
    maggiore2(X,MaxAttuale,MaxNuovo),
    max_iter_cont(R,MaxNuovo,MaxFinale).
maggiore2(X,Y,X) :-
    X >= Y. 
maggiore2(X,Y,Y) :-
    X < Y.
			   		 
maxnumstored(N) :- storednums(NUMS), max_iter(NUMS,N).

%% vedi https://www.progetti.iisleviponti.it/Le_forme_dei_numeri/html/fibo.html