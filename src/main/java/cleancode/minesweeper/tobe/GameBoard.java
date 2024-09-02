package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell2;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    private static final int LAND_MINE_COUNT = 10;
    private final Cell2[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize= gameLevel.getColSize();
        board = new Cell2[rowSize][colSize];

        landMineCount = gameLevel.getLandMineCount();
    }

    public String getSign(int rowSize, int colSize) {
        Cell2 cell = findCell(rowSize, colSize);
        return cell.getSign();
    }

    public void flag(int rowIndex, int colIndex) {
        Cell2 cell = findCell(rowIndex, colIndex);
        cell.flag();
    }

    public void open(int rowIndex, int colIndex) {
        Cell2 cell = findCell(rowIndex, colIndex);
        cell.open();
    }

    public boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        Cell2 cell = findCell(selectedRowIndex, selectedColIndex);
        return cell.isLandMine();
    }

    private Cell2 findCell(int rowSize, int colSize) {
        return board[rowSize][colSize];
    }

    boolean isAllCellChecked() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .allMatch(Cell2::isChecked);
    }

    public void initializeGame() {
        int rowSize = getRowSize();
        int colSize = getColSize();
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = new EmptyCell();
            }
        }

        for (int i = 0; i < landMineCount; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);
            LandMineCell landMineCell = new LandMineCell();
            landMineCell.turnOnLandMine();
//            Cell2 landMineCell = findCell(landMineRow, landMineCol);
            landMineCell.turnOnLandMine();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearByLandMines(row, col);
                if (count == 0 ){
                    continue;
                }
                NumberCell numberCell = new NumberCell();
                numberCell.updateNearbyLandMineCount(count);
                board[row][col] = numberCell;
//                findCell(row, col).updateNearbyLandMineCount(count);
            }
        }
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize(){
        return board[0].length;
    }

    private int countNearByLandMines(int rowIndex, int colIndex) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        int count = 0;
        if (rowIndex - 1 >= 0 && colIndex - 1 >= 0 && isLandMineCell(rowIndex - 1, colIndex - 1)) {
            count++;
        }
        if (rowIndex - 1 >= 0 && isLandMineCell(rowIndex - 1, colIndex)) {
            count++;
        }
        if (rowIndex - 1 >= 0 && colIndex + 1 < colSize && isLandMineCell(rowIndex - 1, colIndex + 1)) {
            count++;
        }
        if (colIndex - 1 >= 0 && isLandMineCell(rowIndex, colIndex - 1)) {
            count++;
        }
        if (colIndex + 1 < colSize && isLandMineCell(rowIndex, colIndex + 1)) {
            count++;
        }
        if (rowIndex + 1 < rowSize && colIndex - 1 >= 0 && isLandMineCell(rowIndex + 1, colIndex - 1)) {
            count++;
        }
        if (rowIndex + 1 < rowSize && isLandMineCell(rowIndex + 1, colIndex)) {
            count++;
        }
        if (rowIndex + 1 < rowSize && colIndex + 1 < colSize && isLandMineCell(rowIndex + 1, colIndex + 1)) {
            count++;
        }
        return count;
    }




    public void openSurroundedCells(int row, int col) {
        if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
            return;
        }
        if (isOpenedCell(row, col)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }

        open(row, col);

        if (doesCellHaveLandMineCount(row, col)) {
            return;
        }

        openSurroundedCells(row - 1, col);
        openSurroundedCells(row - 1, col - 1);
        openSurroundedCells(row - 1, col + 1);
        openSurroundedCells(row, col - 1);
        openSurroundedCells(row, col + 1);
        openSurroundedCells(row + 1, col - 1);
        openSurroundedCells(row + 1, col);
        openSurroundedCells(row + 1, col + 1);
    }

    private boolean doesCellHaveLandMineCount(int row, int col) {
        return findCell(row, col).hasLandMineCount();
    }

    private boolean isOpenedCell(int row, int col) {
        return findCell(row, col).isOpened();
    }
}
