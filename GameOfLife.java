// "■" "□" "█"

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
// import java.awt.Point;

public class GameOfLife {
  private char[][] golGrid;
  private int delay, numGenerations;
  /*
   ******************************** CONSTRUCTORS **************************************
   */
  /*
   * // Constructor allows for some elements to be already filled in by reading
   * the filledIn[][] point array.
   * public GameOfLife(int x, int y, int wait, int generations, Point[] filledIn)
   * {
   * golGrid = new char[x][y];
   * 
   * // Fills game of life Grid array with blank tiles.
   * for (int r = 0; r < golGrid.length; r++)
   * {
   * for (int c = 0; c < golGrid[0].length; c++)
   * {
   * golGrid[r][c] = ' ';
   * }
   * }
   * 
   * for (int k = 0; k < filledIn.length; k++)
   * {
   * try
   * {
   * setTile((int) filledIn[k].getY(), (int) filledIn[k].getX());
   * }
   * catch (IndexOutOfBoundsException ex) // Skips any points that are out of
   * range.
   * {
   * continue;
   * }
   * }
   * 
   * delay = wait;
   * numGenerations = generations;
   * }
   */

  // Constructor that allows user to enter in a preset String matrix that is
  // entirely filled and only uses the filled/unfilled boxes. preset will be
  // randomly generated.
  public GameOfLife(int wait, int generations, char[][] preset) {
    golGrid = preset;
    delay = wait;
    numGenerations = generations;
  }

  // Makes a game of life grid from a .txt file. Look at README.txt file to see
  // how it should be formatted.
  public GameOfLife(String pathname, int wait, int generations) throws IOException {
    delay = wait;
    numGenerations = generations;
    File input = new File(pathname);

    // Reads the first two lines to check the rows and columns.
    Scanner numReader = new Scanner(input);
    int rows = numReader.nextInt();
    int columns = numReader.nextInt();
    golGrid = new char[rows][columns];
    numReader.close();

    BufferedReader reader = new BufferedReader(new FileReader(input));

    // This is a weird solution where the characters in the text file are first put
    // into a String. The String is then traversed character wise to look for '█'.
    int ch = 0;
    String str = "";
    while ((ch = reader.read()) != -1) {
      if (ch == 46 || ch == 9608) // 46 is the ascii character code for '.' , 9607 is the ascii character code for
                                  // '█'
      {
        str += (char) ch;
      }
    }

    int index = 0;
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < columns; c++) {
        if (str.charAt(index) == '█') {
          golGrid[r][c] = '█';
        } else {
          golGrid[r][c] = 32;
        }
        index++;
      }
    }
    reader.close();
  }

  /*
   *************************** METHODS *************************************
   */

  // Makes a string buffer of the Game Of Life grid.
  public String toString() {
    String str = "";

    for (int r = 0; r < golGrid.length; r++) {
      for (int c = 0; c < golGrid[0].length; c++) {
        str += golGrid[r][c];
      }
      str += "\n";
    }

    return str;
  }

  // Returns a String representation of the Game of Life grid with an added
  // border.
  public String toStringWithBorder() {
    String str = "";
    String top = "";

    for (int k = 0; k < golGrid[0].length + 2; k++) {
      top += "░";
    }
    top += "\n";

    str += top;
    for (int r = 0; r < golGrid.length; r++) {
      str += "░";
      for (int c = 0; c < golGrid[0].length; c++) {
        str += golGrid[r][c];
      }
      str += "░\n";
    }
    str += top;

    return str;
  }

  // Returns a String representation of how many neighbours each tile has in the
  // current golGrid.
  public String neighboursToString() {
    String str = "";
    for (int r = 0; r < golGrid.length; r++) {
      for (int c = 0; c < golGrid[0].length; c++) {
        str += findNeighbours(r, c);
      }
      str += "\n";
    }

    return str;
  }

  // Makes a string representation of the Game Of Life grid except each char is
  // its ascii character code.
  public String asciiValuesToString() {
    String str = "";
    for (int r = 0; r < golGrid.length; r++) {
      for (int c = 0; c < golGrid[0].length; c++) {
        str += (int) golGrid[r][c] + " ";
      }
      str += "\n";
    }

    return str;
  }

  // Tests an element in the grid and returns how many neighbours it has, from 1
  // to 8.
  public int findNeighbours(int r, int c) {
    int numNeighbours = 0;

    // 9608 is the ascii character code for '█'. 120 is the ascii character code for
    // 'x'.
    if (r - 1 >= 0 && c - 1 >= 0 && (golGrid[r - 1][c - 1] == 9608 || golGrid[r - 1][c - 1] == 120))
      numNeighbours++;
    if (r - 1 >= 0 && (golGrid[r - 1][c] == 9608 || golGrid[r - 1][c] == 120))
      numNeighbours++;
    if (r - 1 >= 0 && c + 1 < golGrid[0].length && (golGrid[r - 1][c + 1] == 9608 || golGrid[r - 1][c + 1] == 120))
      numNeighbours++;
    if (c - 1 >= 0 && (golGrid[r][c - 1] == 9608 || golGrid[r][c - 1] == 120))
      numNeighbours++;
    if (c + 1 < golGrid[0].length && (golGrid[r][c + 1] == 9608 || golGrid[r][c + 1] == 120))
      numNeighbours++;
    if (r + 1 < golGrid.length && c - 1 >= 0 && (golGrid[r + 1][c - 1] == 9608 || golGrid[r + 1][c - 1] == 120))
      numNeighbours++;
    if (r + 1 < golGrid.length && (golGrid[r + 1][c] == 9608 || golGrid[r + 1][c] == 120))
      numNeighbours++;
    if (r + 1 < golGrid.length && c + 1 < golGrid[0].length
        && (golGrid[r + 1][c + 1] == 9608 || golGrid[r + 1][c + 1] == 120))
      numNeighbours++;

    return numNeighbours;
  }

  // Changes the elements in grid approproiate to the rules of game of Life.
  public void changeTile(int r, int c) {
    int numNeighbours = findNeighbours(r, c);
    boolean isAlive = false;
    if (golGrid[r][c] == '█')
      isAlive = true;

    if (isAlive == true && (numNeighbours > 3 || numNeighbours < 2))
      golGrid[r][c] = 'x'; // Marks the elements so that it will die in the next generation.
    if (isAlive == false && numNeighbours == 3)
      golGrid[r][c] = '*'; // Marks the element so that it will become alive in the next generation.
  }

  // Makes all of the elements that will die in the next generation "dead", and
  // all of the elements that will become alive "alive"
  public void finalizeGrid() {
    for (int r = 0; r < golGrid.length; r++) {
      for (int c = 0; c < golGrid[0].length; c++) {
        if (golGrid[r][c] == ('x'))
          golGrid[r][c] = ' ';
        else if (golGrid[r][c] == ('*'))
          golGrid[r][c] = '█';
      }
    }
  }

  // Fills in a tile of the given coordinates
  public void setTile(int row, int column) {
    golGrid[row][column] = '█';
  }

  // Delays the program with drawing new grids to help with flow.
  // https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
  public static void wait(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  // Clears the console.
  // https://stackoverflow.com/questions/2979383/how-to-clear-the-console
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  // Represents the "game loop" of the program.
  public void play() {
    System.out.print(toStringWithBorder());
    wait(delay);

    for (int k = 0; k < numGenerations; k++) {
      for (int r = 0; r < golGrid.length; r++) {
        for (int c = 0; c < golGrid[0].length; c++) {
          changeTile(r, c);
        }
      }
      finalizeGrid();
      clearScreen();
      System.out.print(toStringWithBorder());
      wait(delay);
    }
  }
}