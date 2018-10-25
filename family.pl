parent( pam, bob ).
parent( tom, bob ).
parent( tom, liz ).
parent( bob, ann ).
parent( bob, pat ).
parent( pat, jim ).

female( pam ).
female( liz ).
female( ann ).
female( pat ).

male( tom ).
male( bob ).
male( jim ).

offspring( pam, bob ) :- 
	parent( pam, bob ).


mother( pam, bob ) :- 
	parent( pam, bob ),
	female( pam ).


grandparent( pam, Z ) :- 
	parent( pam, bob ),
	parent( bob, Z ).


sister( pam, bob ) :- 
	parent( Z, pam ),
	parent( Z, bob ),
	female( pam ),
	different( pam, bob ).


different( pam, pam ) :- 
	!,
	fail.

different( pam, bob ).

predecessor( pam, Z ) :- 
	parent( pam, Z ).

predecessor( pam, Z ) :- 
	parent( pam, bob ),
	predecessor( bob, Z ).


