
import java.util.Scanner;
import java.io.IOException;
//import java.awt.Point;

public class Main {
  public static void main(String[] args) {
    Scanner kb = new Scanner(System.in);
    String fileName = "", choice = "";
    int numGens, delay, rows, columns;
    GameOfLife game;

    System.out.println("Welcome to the worst rendition of Conway's Game Of Life that you will likely ever witness.");

    System.out.print("Now, please enter the number of generations for your game: ");
    numGens = kb.nextInt();
    System.out.print("Enter how long each generation will last (milliseconds): ");
    delay = kb.nextInt();

    System.out.print("Enter \"Random\" for a random game. Otherwise enter anything else for the other option: ");
    choice = kb.next();
    if (choice.equalsIgnoreCase("Random")) {

      System.out.print("Please enter the number of rows you would like: ");
      rows = kb.nextInt();
      System.out.print("Please enter the number of columns you would like: ");
      columns = kb.nextInt();

      char[][] board = randomGame(rows, columns);
      GameOfLife.clearScreen();
      game = new GameOfLife(delay, numGens, board);
      game.play();
    } else {
      System.out.print("Please enter the pathname for your file: ");
      fileName = kb.next();
      GameOfLife.clearScreen();

      try {
        game = new GameOfLife(fileName, delay, numGens);
        game.play();
      } catch (IOException ex) {
        System.out.println("*** File " + fileName + " not found ***");
        System.exit(1);
      }
    }
  }

  // Makes a pseudo-randomly generated starting grid for Game Of Life.
  public static char[][] randomGame(int x, int y) {
    char[][] randomGrid = new char[x][y];

    for (int r = 0; r < randomGrid.length; r++) {
      for (int c = 0; c < randomGrid[0].length; c++) {
        int randomNum = (int) Math.floor(Math.random() * 2);

        if (randomNum == 0) {
          randomGrid[r][c] = ' ';
        } else {
          randomGrid[r][c] = '█';
        }
      }
    }

    return randomGrid;
  }
}

// Garbage

/*
 * Point[] blinker = {new Point(1, 0), new Point(1, 1), new Point(1, 2)};
 * Point[] glider = {new Point(1, 1), new Point(2, 2), new Point(2, 3), new
 * Point(3, 1), new Point(3,2)};
 * Point[] pentadecathlon = {
 * new Point(6, 6),
 * new Point(7, 6),
 * new Point(8, 7),
 * new Point(8, 5),
 * new Point(9, 6),
 * new Point(10, 6),
 * new Point(11, 6),
 * new Point(12, 6),
 * new Point(13, 7),
 * new Point(13, 5),
 * new Point(14, 6),
 * new Point(15, 6)};
 * 
 * GameOfLife game2 = new GameOfLife(rows, columns, 500, numGens,
 * pentadecathlon);
 * game2.play();
 */