package cleancode.minesweeper.tobe;
import cleancode.minesweeper.tobe.cell.*;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();
        board = new Cell[rowSize][colSize];

        landMineCount = gameLevel.getLandMineCount();
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.flag();
    }

    public void openAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        cell.open();
    }

    public void openSurroundedCells(CellPosition cellPosition) {
        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMineCellAt(cellPosition)) {
            return;
        }

        openAt(cellPosition);

        if (doesCellHaveLandMineCount(cellPosition)) {
            return;
        }

        List<CellPosition> surroundedPositions = calculateSurroundedPositions(cellPosition, getRowSize(), getColSize());
        surroundedPositions.forEach(this::openSurroundedCells);
    }

    private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.hasLandMineCount();
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isOpened();
    }

    public boolean isLandMineCellAt(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.isLandMine();
    }

    public boolean isAllCellChecked() {
        Cells cells = Cells.from(board);
        return cells.isAllChecked();
    }

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        return cellPosition.isRowIndexMoreThanOrEqual(rowSize)
                || cellPosition.isColIndexMoreThanOrEqual(colSize);
    }

    public void initializeGame() {
        CellPositions cellPositions = CellPositions.from(board);

        int rowSize = getRowSize();
        int colSize = getColSize();

        initializeEmptyCells(cellPositions);
//        for (int row = 0; row < rowSize; row++) {
//            for (int col = 0; col < colSize; col++) {
//                board[row][col] = new EmptyCell();
//            }
//        }

        List<CellPosition> landMinePositions = cellPositions.extractRandomPositions(landMineCount);
        updateCellsAt(landMinePositions, new LandMineCell());
//        for(CellPosition landMinePosition : landMinePositions){
//            updateCellAt(landMinePosition, new LandMineCell());
////            board[landMinePosition.getRowIndex()][landMinePosition.getColIndex()] = new LandMineCell();
//        }

//        for (int i = 0; i < landMineCount; i++) {
//            int landMineCol = new Random().nextInt(colSize);
//            int landMineRow = new Random().nextInt(rowSize);
//            board[landMineRow][landMineCol] = new LandMineCell();
//        }

        List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMinePositions);

        for(CellPosition candidatePosition : numberPositionCandidates){
            int count = countNearbyLandMines(candidatePosition);
            if (count != 0){
                updateCellAt(candidatePosition, new NumberCell(count));
            }
        }

//        for (int row = 0; row < rowSize; row++) {
//            for (int col = 0; col < colSize; col++) {
//                CellPosition cellPosition = CellPosition.of(row, col);
//
//                if (isLandMineCellAt(cellPosition)) {
//                    continue;
//                }
//                int count = countNearbyLandMines(cellPosition);
//                if (count == 0) {
//                    continue;
//                }
//                updateCellAt(cellPosition, new NumberCell(count));
////                board[row][col] = new NumberCell(count);
//            }
//        }
    }

    private void initializeEmptyCells(CellPositions cellPositions) {
        List<CellPosition> allPositions = cellPositions.getPositions();
        updateCellsAt(allPositions, new EmptyCell());
    }

    private void updateCellsAt(List<CellPosition> allPositions, Cell cell) {
        for ( CellPosition position : allPositions){
            updateCellAt(position, cell);
        }
    }

    private void updateCellAt(CellPosition position, Cell cell) {
        board[position.getRowIndex()][position.getColIndex()] = cell;
    }


    private Cell findCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    private int countNearbyLandMines(CellPosition cellPosition) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        long count = calculateSurroundedPositions(cellPosition, rowSize, colSize).stream()
                .filter(this::isLandMineCellAt)
                .count();

        return (int) count;
    }

    private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
        return RelativePosition.SURROUNDED_POSITIONS.stream()
                .filter(cellPosition::canCalculatePositionBy)
                .map(cellPosition::calculatePositionBy)
                .filter(position -> position.isRowIndexLessThan(rowSize))
                .filter(position -> position.isColIndexLessThan(colSize))
                .toList();
    }

    public CellSnapshot getSnapshot(CellPosition cellPosition) {
        Cell cell = findCell(cellPosition);
        return cell.getSnapshot();
    }
}
