
// Jun Ong
// CS 143
// HW #2: Sudoku #2
//
// This program will define the basic functionality for a Sudoku board.
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class SudokuBoard {
    // constants
    final private int BOARD_SIZE = 9;
    final private char[] validBoardData = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '.' };
    // fields
    private char[][] board;
    private HashSet<Character> validData;

    // constructors
    /** Pre: File exists, file contains 9 lines of 9 characters */
    public SudokuBoard(String path) throws IOException {
        this.board = new char[BOARD_SIZE][BOARD_SIZE];
        Scanner input = new Scanner(new File(path));

        int row = 0;
        int col = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            for (char c : line.toCharArray()) {
                this.board[row][col] = c;
                col++;
                if (col >= BOARD_SIZE)
                    break;
            }
            col = 0;
            row++;
            if (row >= BOARD_SIZE)
                break;
        }
        input.close();

        this.validData = new HashSet<>();
        for (char c : validBoardData) {
            this.validData.add(c);
        }
    }

    // methods
    // pre: board is properly initialized
    // post: true if puzzle can be solved; false otherwise
    public boolean solve() {
        if (!isValid()) {
            return false;
        }
        if (isSolved()) {
            return true;
        }
        // recursive backtracking

        return false;
    }
    
    // pre: board is properly initialized
    public boolean isSolved() {
        if (board == null) return false;
        if (!isValid()) return false;

        HashMap<Character, Integer> count = new HashMap<>();
        for (char[] row : this.board) {
            for (char c : row) {
                if (!count.containsKey(c))
                    count.put(c, 1);
                else
                    count.put(c, count.get(c) + 1);
            }
        }
        for (int n : count.values()) {
            if (n != 9) return false;
        }        

        return true;
    }

    // pre: board is properly initialized
    public boolean isValid() {
        if (board == null) return false;
        HashMap<Integer, HashSet<Character>> uniqueColumns = new HashMap<>(); // col 0 : {data}
        for (int i = 0; i < BOARD_SIZE; i++) {
            uniqueColumns.put(i, new HashSet<Character>());
        }
        for (char[] row : this.board) {
            HashSet<Character> uniqueRow = new HashSet<>();
            for (int i = 0; i < row.length; i++) {
                char c = row[i];
                // check that c is valid data
                if (!validData.contains(c))
                    return false;

                if (c == '.') continue;
                // If c is not a space,
                // check that c is unique in row
                if (uniqueRow.contains(c))
                    return false;
                uniqueRow.add(c);
                // check that c is unique in column
                HashSet<Character> uniqueCol = uniqueColumns.get(i);
                if (uniqueCol.contains(c))
                    return false;
                uniqueCol.add(c);
            }
        }
        // Check that minisquares are unique
        for (int i=1; i<=BOARD_SIZE; i++) { //9*9 board / 3*3 miniSq = 9 miniSqs
            char[][] miniSq = miniSquare(i);
            HashSet<Character> uniqueInMini = new HashSet<>();
            for (char[] row : miniSq) {
                for (char c : row) {
                    if (c == '.') continue;
                    // If c is not a space, check that c is unique in miniSquare
                    if (uniqueInMini.contains(c)) return false;
                    uniqueInMini.add(c);
                }
            }
        }
        return true;
    }

    // pre: board is properly initialized
    private char[][] miniSquare(int spot) {
        char[][] mini = new char[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                // whoa - wild! This took me a solid hour to figure out (at least)
                // This translates between the "spot" in the 9x9 Sudoku board
                // and a new mini square of 3x3
                mini[r][c] = this.board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
            }
        }
        return mini;
    }

    // toString
    @Override
    public String toString() {
        String out = "   ---   ---   ---\n";
        for (int row = 0; row < this.board.length; row++) {
            out += " | ";
            for (int i = 0; i < this.board[row].length; i++) {
                out += (this.board[row][i] != '.') ? this.board[row][i] : '.';
                if ((i + 1) % 3 == 0) {
                    out += " | ";
                }
            }
            out += "\n";
        }
        out += "   ---   ---   ---\n";
        return out;
    }
}
/*
Checking empty board...passed.
Checking incomplete, valid board...passed.
Checking complete, valid board...passed.
Checking dirty data board...passed.
Checking row violating board...passed.
Checking col violating board...passed.
Checking row&col violating board...passed.
Checking mini-square violating board...passed.
**** HORRAY: ALL TESTS PASSED ****
 */