package cleancode.minesweeper.tobe;

public class BoardIndexConverter {

    private static final int BOARD_ROW_SIZE = 8;
    private static final int BOARD_COL_SIZE = 10;

    private static final char BASE_CHAR_FOR_COL = 'a';

    public int getSelectedColIndex(String cellInput, int colSize) {
        char cellInputCol = cellInput.charAt(0);
        int selectedColIndex = convertColFrom(cellInputCol, colSize);
        return selectedColIndex;
    }

    public int getSelectedRowIndex(String cellInput, int rowSize) {
        String cellInputRow = cellInput.substring(1);
        int selectedRowIndex = convertRowFrom(cellInputRow, rowSize);
        return selectedRowIndex;
    }

    private int convertRowFrom(String cellInputRow, int rowSize) {
        int rowIndex = Integer.parseInt(cellInputRow) - 1;
        if(rowIndex < 0 || rowIndex >= rowSize){
            throw new GameException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColFrom(char cellInputCol, int colSize) {
        int colIndex = cellInputCol - BASE_CHAR_FOR_COL;
        if ( colIndex < 0 || colIndex >= colSize ){
            throw new GameException("잘못된 입력입니다.");
        }

        return colIndex;
    }
}
