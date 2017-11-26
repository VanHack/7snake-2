# 7snake
Given a grid (10x10 for instance), this program identifies in this grid two 7-snakes that the sum of their values is equal and both doesn't have conflicted cells.

This solution was developed with Java 8 using Eclipse IDE. The results are printed on console.

In summary:

1) First of all, the program reads a csv (comma separator) file with the grid of possible 7-Snakes;

2) So it loads the cells in memory to process;

3) Then it searches for possible combinations of 7-Snakes. The program will consider all combinations

4) Finally it verifies existing pairs of two 7-Snakes with no conflicted cells and the sum of their values is equal;

5) The program will print everything on console and by default will print the first result.



If you want to execute this code you have two options:

1) Open the project using a IDE like Eclipse and Run the class Main.java.
First you have to specify some parameters to execute it.

  - Arguments: "/my-workspace/7snake/src/grid.csv" or "/my-workspace/7snake/src/grid.csv 2"

2) Or execute the class Main using command line.

  - java br.lucio.snake7.Main grid.csv (print the first 7-Snake found)

  - java br.lucio.snake7.Main grid.csv 3 (in this case, print the first 3 7-Snake found)



By Lucio Ribeiro
