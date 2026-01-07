import sympy as sp

A = sp.Point(1,2,0)
B = sp.Point(2,1,0)
C = sp.Point(0,2,1)
D = sp.Point(1,0,3)
P = sp.Point(1, sp.Rational(5,4), 1)

faces = [
    (A, B, C, D, "ABC"),
    (A, B, D, C, "ABD"),
    (A, C, D, B, "ACD"),
    (B, C, D, A, "BCD")
]

for X, Y, Z, opp, name in faces:
    u = sp.Matrix(Y - X)
    v = sp.Matrix(Z - X)
    wP = sp.Matrix(P - X)
    wO = sp.Matrix(opp - X)
    cross = u.cross(v)
    print(name, cross.dot(wP), cross.dot(wO))