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

changedir(down, l,  right).
changedir(down, r,  left).
changedir(up,   l,  left).
changedir(up,   r,  right).
changedir(left, l,  down).
changedir(left, r,  up).
changedir(right,l,  up).
changedir(right,r,  down).

commutedir(up,up,[]).
commutedir(down,down,[]).
commutedir(right,right,[]).
commutedir(left,left,[]).

commutedir(right,left,[l,l]).
commutedir(left,right,[r,r]).  %%anche [l,l]
commutedir(right,up,[l]).
commutedir(left,up,[r]).
commutedir(down,left,[r]).
commutedir(down,right,[l]).
commutedir(up,left,[l]).
commutedir(up,right,[r]).


goonx(X1,Y1,XT,YT,DIR,CURP,P):-
  XNEW is X1 + 1,
  append(CURP,[w],R),
  path(XNEW,Y1,XT,YT,DIR,R,P).
goony(X1,Y1,XT,YT,DIR,CURP,P):-
  YNEW is Y1 + 1,
  append(CURP,[w],R),
  stdout <- println( goony(path(X1,YNEW,XT,YT,DIR,R,P)) ),
  path(X1,YNEW,XT,YT,DIR,R,P).
backx(X1,Y1,XT,YT,DIR,CURP,P):-
  XNEW is X1 - 1,
  append(CURP,[w],R),
  path(XNEW,Y1,XT,YT,DIR,R,P).
backy(X1,Y1,XT,YT,DIR,CURP,P):-
  YNEW is Y1 - 1,
  append(CURP,[w],R),
  path(X1,Y1,XT,YNEW,DIR,R,P).

turnleft(X1,Y1,XT,YT,DIR, CURP,P):-
  stdout <- println( turnleftxxxxxxxxxxx(X1,Y1,XT,YT,DIR,CURP ) ),
  commutedir(DIR,right,OPS),
  append(CURP,OPS,P1),
  stdout <- println( turnleft22222222222(P1) ),
  goony(X1,Y1,XT,YT,right,P1,P). 
  %%path(X1,Y1,XT,YT,right,P1,P).
turnright(X1,Y1,XT,YT,DIR,CURP,P):-
  stdout <- println( turnright(X1,Y1,XT,YT,DIR,CURP ) ),
  commutedir(DIR,left,OPS),
  append(CURP,OPS,P1),
  path(X1,Y1,XT,YT,right,[OPS|CURP],P).
  
  
path(X1,Y1,XT,YT,P):-
 	path(X1,Y1,XT,YT,down,[],P),
	stdout <- println( path(X1,Y1,XT,YT,down,P ) ).
	%%reverse(P1,P).

path(X,Y,X,Y,DIR,CURP,CURP ):-!.
path(X1,Y1,XT,YT,DIR,CURP,P ):-
  stdout <- println( path11111(X1,Y1,XT,YT,DIR,CURP ) ),
  ( X1 < XT,  !, goonx(X1,Y1,XT,YT,DIR,CURP,P);
    X1 == XT, Y1 < YT, !,  turnleft(X1,Y1,XT,YT,DIR,CURP,P);
    X1 == XT, Y1 > YT, !,  turnright(X1,Y1,XT,YT,DIR,CURP,P);
    X1 > XT, backx(X1,Y1,XT,YT,DIR,CURP,P)
  ).

  
  
 



