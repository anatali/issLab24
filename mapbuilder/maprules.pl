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

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Calcolo plan
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

commutedir(up,   up,   []).
commutedir(down, down, []).
commutedir(right,right,[]).
commutedir(left, left, []).


commutedir(down,left,[r]).
commutedir(down,right,[l]).
commutedir(down,up,[l,l]).

commutedir(up,left,[l]).
commutedir(up,right,[r]).
commutedir(up,down,[r,r]). %%anche [l,l]

commutedir(right,down,[r]).
commutedir(right,up,[l]).
commutedir(right,left,[l,l]).

commutedir(left,right,[r,r]).  %%anche [l,l]
commutedir(left,up,[r]).
commutedir(left,down,[l]).


goxdown(X1,Y1,XT,YT,DIR,CURP,P):-
  XNEW is X1 + 1,
  commutedir( DIR, down, R1 ),
  append(R1,[w],R2),
  append(CURP,R2,R),
  plan(XNEW,Y1,XT,YT,down,R,P).

goxup(X1,Y1,XT,YT,DIR,CURP,P):-
  XNEW is X1 - 1,
  commutedir( DIR, up, R1 ),
  append(R1,[w],R2),
  append(CURP,R2,R),
  plan(XNEW,Y1,XT,YT,up,R,P).
 
goyright(X1,Y1,XT,YT,DIR, CURP,P):-
  %%stdout <- println( goyright(X1,Y1,XT,YT,DIR,CURP ) ),
  YNEW is Y1 + 1,
  commutedir(DIR,right,R1),
  append(R1,[w],R2),
  append(CURP,R2,R),
  plan(X1,YNEW,XT,YT,right,R,P).

goyleft(X1,Y1,XT,YT,DIR,CURP,P):-
  %%stdout <- println( goyleft(X1,Y1,XT,YT,DIR,CURP ) ),
  YNEW is Y1 - 1,
  commutedir(DIR,left,R1),
  append(R1,[w],R2),
  append(CURP,R2,R),
  plan(X1,YNEW,XT,YT,left,R,P).
  
% ENTRY POINT 
plan(X1,Y1,XT,YT,DIR,P):-
    stdout <- println( planentry(X1,Y1,XT,YT,DIR,P) ),
  	plan(X1,Y1,XT,YT,DIR,[],P),
 	stdout <- println( planfinal(X1,Y1,XT,YT,DIR,P ) ).

plan(X, Y, X, Y, DIR, CURP, CURP ).
plan(X1,Y1,XT,YT,DIR, CURP, P ):-
  stdout <- println( plan(X1,Y1,XT,YT,DIR,CURP ) ),
  ( X1 < XT,  !,           goxdown( X1,Y1,XT,YT,DIR,CURP,P);
    X1 == XT, Y1 < YT, !,  goyright(X1,Y1,XT,YT,DIR,CURP,P);
    X1 == XT, Y1 > YT, !,  goyleft( X1,Y1,XT,YT,DIR,CURP,P);
    X1 > XT,               goxup(   X1,Y1,XT,YT,DIR,CURP,P)
  ).


test([w,w,l,w,w]).
