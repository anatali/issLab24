%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% maprules.pl
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
showCells :-
  stdout <- println("&&& map &&&"),
  findall( cell(X,Y,V), cell(X,Y,V), CELLS),
  %%stdout <- println(CELLS),
  showElements(CELLS),
  stdout <- println("&&& map &&&").

showElements(ElementListString):-
  text_term( ElementListString, ElementList ),
  showListOfElements(ElementList).

showListOfElements([]).
  showListOfElements([C|R]):-
  stdout <- println( C ),
  showElements(R).

path(X1,Y1,X2,Y2,P):-
 	path(X1,Y1,X2,Y2,down,[],P1),
	stdout <- println( path(X1,Y1,X2,Y2,down,P1 ) ),
	reverse(P1,P).

path(X,Y,X,Y,DIR,CURP,CURP ):-!.
path(X1,Y1,X2,Y2,down,CURP,P ):-
  stdout <- println( path(X1,Y1,X2,Y2,down,CURP ) ),
  X1 < X2,!,
  XNEW is X1 + 1,
  path(XNEW,Y1,X2,Y2,down,[w|CURP],P).
path(X,Y1,X,Y2,down,CURP,P ):-
  stdout <- println( path(X,Y1,X,Y2,down,CURP ) ),
  Y1 < Y2,!,
  path(X,Y1,X,Y2,right,[l|CURP],P).
path(X,Y1,X,Y2,right,CURP,P ):-
  stdout <- println( path(X,Y1,X,Y2,right,CURP ) ),
  Y1 < Y2,!,
  YNEW is Y1 + 1,
  path(X,YNEW,X,Y2,right,[w|CURP],P).




