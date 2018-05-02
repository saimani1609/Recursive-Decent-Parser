# Recursive-Decent-Parser
Recursive Decent Parser


Input:
A → aBcdE


Output:
A → <a><BcdE>
<a> → a
<BcdE> → B<cdE>
<cdE> → <c><dE>
<c> → c
<dE> > <d>E
<d> → d
