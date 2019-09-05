# MysticSquare
Solving the Mystic Square N-Tile Puzzle or Sliding-Tile Puzzle Using A* Algorithm and Genetic Algorithm
For n = 3 (9 tiles)
the goal square is:
[ ][1][2]
[3][4][5]
[6][7][8]


the initial square is generated from shuffeling the original square to not create an incoherent square (half of them),
it was calculated using a blind search (9!/2) in the closed List without finding a solution. (exactly: 181440) for n =3
or by typing the numbers one by one.

A*: the Heuristic is calculated using the Sum of Manhattan Distance of all cases + Sum of misplaced tiles
plus G(x)= number of explored nodes for a solution.

if we only use misplaced tiles, we lose the precision after 8 moves G(x) >> h(x), and the generated solution is longer and takes more time
a good solution to that is to multiply the H(x) value by 2.
Manhattan Distance only gives a far better result, resulting in a faster and shorter solutions.
becomes even better by multiplying h(x) by 1.5

Using both the the best, as it contains more information, and is closer to the real length of a solution.
especially for hard to solve instances.
also the Open and Closed lists contain much less explored solutions.

New Square
 [3] [7] [2]
 [5] [1] [ ]
 [6] [8] [4]
distance: 12 misplaced: 7

Using Distance: 
[left, up, right, down, down, left, up, up, right, down, left, left, up, right, right, down, left, up, left] = 19

Open has 355
Closed has 564

Using Misplaced Cases:
[left, up, right, down, down, left, up, up, right, down, left, left, up, right, right, down, left, up, left] = 19
Open has 547
Closed has 877

Using Both:
[left, up, right, down, down, left, up, up, right, down, left, left, up, right, right, down, left, up, left] = 19

Open has 179
Closed has 258

found a solution !
 [ ] [1] [2]
 [3] [4] [5]
 [6] [7] [8]

End. 
took for all three: 224ms
---------------------



Genetic: 
Here we use floating point numbers to represent the moves [0-1]
using those numbers we chose what move to chose next from an arranged set of moves {Up,Down,Left,Right}
if the tile can't move to the left for example the new list of legal moves = {Up,Down,Right} and so on
how to chose: if the solution for example has (0.3, 0.6, 0.41 , 0.7 , 0.2}
and legal moves are {Down, Left, Right}, then the first move is Down, because  0.3 < 1/3
using the same list of legal moves, the second will give: Left, because  0.6 < 2/3
that means we chose:
0 <=          1/3      2/3        <= 3/3
       Down   <  Left   <  Right
so, to recapitulate: 
we choose only the legal moves depending on the numbers that we have from the solution.
No Admissiblity problems when Crossing or mutating.
the coherence of the choice is not compromised because the set of legal moves have one arrangement.

work to do: remove the previous move from the legal moves to not get usesless moves {left, right, left, right ...}

Fitness:
is calculated the same way as H(x) in A*.
but we don't calculate it for the void tile [ ], 
because, by not doing so, the algorithm tends to prefer solutions with that tile the closest possible to it's placement,
preventing it from moving others fairely (dislikes going far to moves the far tiles).

we also, have the choice to add more moves after a long stagnation, 
the length will be increased. this hoever does not appear to add more value to the solution,

the algorithm stops searching and select the best solution if it solves the square (even before reading all the list)
it will then return only the list of moves that leads to the real solution.



Comparing the Two:
  A* is much, much faster for a 3*3 square, 4 becomes much slower, (couple of seconds),
  will let you discover how much it takes for 5
  genetic is slower than A* for n = 3, sometimes similar to A* or better for 4 (if it does not stagnate),
  is much better for 5 (but did not find a solution) but rather gives one with a fairly good fitness score.
  
  
  
  
  
-- note: as I constantly modify this code, you might find errors in the solution.
