import java.io.IOException;

public class PlaySudoku {
    public static void main(String[] args) throws IOException {
        SudokuBoard s = new SudokuBoard("boards/data1.sdk");
        System.out.println(s.toString());
    }
}
