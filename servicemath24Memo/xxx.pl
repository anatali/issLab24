showFiboFacts(FIBONUMS) :-
	findall(fibo(X,Y),fibo(X,Y),FIBONUMS),
	!.

showFiboFacts(sorry).

showFiboFacts :-
	stdout <- println('&&& storage &&&'),
	findall(fibo(X,Y),fibo(X,Y),FIBONUMS),
	!,
	showElements(FIBONUMS),
	stdout <- println('&&& storage &&&').

fibo(0,0).

fibo(1,1).

fibo(2,1).

fibo(3,2).

fibo(4,3).

fibo(5,5).

fibo(6,8).

fibo(7,13).

fibo(8,21).

fibo(9,34).

fibo(10,55).

fibo(11,89).

fibo(12,144).

fibo(13,233).

fibo(14,377).

fibo(15,610).

fibo(16,987).

fibo(17,1597).

fibo(18,2584).

fibo(19,4181).

fibo(20,6765).

fibo(21,10946).

fibo(22,17711).

fibo(23,28657).

fibo(24,46368).

fibo(25,75025).

fibo(26,121393).

fibo(27,196418).

fibo(28,317811).

fibo(29,514229).

fibo(30,832040).

fibo(31,1346269).

fibo(32,2178309).

fibo(33,3524578).

fibo(34,5702887).

fibo(35,9227465).

fibo(36,14930352).

fibo(37,24157817).

fibo(38,39088169).

fibo(39,63245986).

fibo(40,102334155).

fibo(41,165580141).

fibo(42,267914296).

fibo(43,433494437).

showListOfElements('[]').

showListOfElements([C|R]) :-
	stdout <- println(C),
	showElements(R).

showElements(ElementListString) :-
	text_term(ElementListString,ElementList),
	showListOfElements(ElementList).

