fibo( 0,  0 ).
fibo( 1,  1 ).
fibo( 2,  1 ).
fibo( 3,  2 ).
fibo( 4,  3 ).
fibo( 5,  5 ).

showFiboFacts(FIBONUMS) :-
  	findall( fibo(X,Y), fibo( X,Y ), FIBONUMS), !.
showFiboFacts(sorry).	

showFiboFacts  :-
	stdout <- println("&&& storage &&&"),
  	findall( fibo(X,Y), fibo( X,Y ), FIBONUMS), !,
  	showElements(FIBONUMS),
  	stdout <- println("&&& storage &&&").
 
	
showElements(ElementListString):- 
	text_term( ElementListString, ElementList ),
 	showListOfElements(ElementList).
showListOfElements([]).
showListOfElements([C|R]):-
	stdout <- println( C ),
	showElements(R).	