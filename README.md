# MysticSquare
Solving the Mystic Square N-Tile Puzzle or Sliding-Tile Puzzle Using A* Algorithm and Genetic Algorithm
For n = 3 (9 tiles)
the goal square is:<br>
[ ][1][2]<br>
[3][4][5]<br>
[6][7][8]<br>


the initial square is generated from shuffeling the original square to not create an incoherent square (half of them),
it was calculated using a blind search (9!/2) in the closed List without finding a solution. (exactly: 181440) for n =3
or by typing the numbers one by one.

A*: 
---
the Heuristic is calculated using the Sum of Manhattan Distance of all cases + Sum of misplaced tiles
plus G(x)= number of explored nodes for a solution.

if we only use misplaced tiles, we lose the precision after 8 moves G(x) >> h(x), and the generated solution is longer and takes more time.
A good solution to that is to multiply the H(x) value by 2.<br>
Manhattan Distance only gives a far better result, resulting in a faster and shorter solutions.<br>
becomes even better by multiplying h(x) by 1.5<br>

Using both is the best, as it contains more information, and is closer to the real length of a solution.<br>
especially for hard to solve instances.<br>
also the Open and Closed lists contain much less explored solutions.<br>

##### New Square
 [3] [7] [2]<br>
 [5] [1] [ ]<br>
 [6] [8] [4]<br>
distance: 12 misplaced: 7

Using Distance: 
[left, up, right, down, down, left, up, up, right, down, left, left, up, right, right, down, left, up, left] = 19<br>
Open has 355
Closed has 564

Using Misplaced Cases:
[left, up, right, down, down, left, up, up, right, down, left, left, up, right, right, down, left, up, left] = 19<br>
Open has 547
Closed has 877

Using Both:
[left, up, right, down, down, left, up, up, right, down, left, left, up, right, right, down, left, up, left] = 19<br>
Open has 179
Closed has 258

found a solution !<br>
 [ ] [1] [2]<br>
 [3] [4] [5]<br>
 [6] [7] [8]<br>

End.<br> 
took for all three: 224ms



Genetic: 
--------
Here we use floating point numbers to represent the moves [0-1]<br>
using those numbers we chose what move to chose next from an arranged set of moves {Up,Down,Left,Right}<br>
if the tile can't move to the left for example the new list of legal moves = {Up,Down,Right} and so on<br>
how to chose: if the solution for example has (0.3, 0.6, 0.41 , 0.7 , 0.2}<br>
and legal moves are {Down, Left, Right}, then the first move is Down, because  0.3 < 1/3<br>
using the same list of legal moves, the second will give: Left, because  0.6 < 2/3<br>
that means we chose:
0 <=          1/3      2/3        <= 3/3<br>
       Down   <  Left   <  Right<br>
       
so, to recapitulate: <br>
we choose only the legal moves depending on the numbers that we have from the solution.<br>
No Admissiblity problems when Crossing or mutating.<br>
the coherence of the choice is not compromised because the set of legal moves have one arrangement.<br>

work to do: remove the previous move from the legal moves to not get usesless moves {left, right, left, right ...}<br>

#### Fitness:
is calculated the same way as H(x) in A*.<br>
but we don't calculate it for the void tile [ ], <br>
because, by not doing so, the algorithm tends to prefer solutions with that tile the closest possible to it's placement,
preventing it from moving others fairely (dislikes going far to moves the far tiles).<br>

we also, have the choice to add more moves after a long stagnation, 
the length will be increased. this hoever does not appear to add more value to the solution,

the algorithm stops searching and select the best solution if it solves the square (even before reading all the list)
it will then return only the list of moves that leads to the real solution.



### Comparing the Two:
  A* is much, much faster for a 3*3 square, 4 becomes much slower, (couple of seconds),
  will let you discover how much it takes for 5<br>
  genetic is slower than A* for n = 3, sometimes similar to A* or better for 4 (if it does not stagnate),
  is much better for 5 (but did not find a solution) but rather gives one with a fairly good fitness score.<br>
  
  
  
<br>
<br>
 - Note: as I constantly modify this code, you might find errors in the solution.*
<br>
