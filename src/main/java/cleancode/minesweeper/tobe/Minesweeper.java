package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

import java.util.Arrays;
import java.util.Random;

public class Minesweeper {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final int LAND_MINE_COUNT = 10;
    private final GameBoard gameBoard = new GameBoard(BOARD_ROW_SIZE, BOARD_COL_SIZE);
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배


    public void run() {
        consoleOutputHandler.showGameStartComments();
        gameBoard.initializeGame();

        while (true) {
            try{
                consoleOutputHandler.showBoard(gameBoard);

                if (doseUserWinTheGame()) {
                    consoleOutputHandler.printGameWinningComment();
                    break;
                }
                if (doseUserLoseTheGame()) {
                    consoleOutputHandler.printGameLosingComment();
                    break;
                }

                String cellInput = getCellInputFromUser();
                String userActionCol = getUserActionInputFromUser();
                actOnCell(cellInput, userActionCol);
                actOnCell(cellInput, userActionCol);

            } catch (GameException e){
                consoleOutputHandler.printExceptionMessage(e);
            } catch (Exception e){
                consoleOutputHandler.printSimpleMessage("프로그램에 문제가 생겼습니다.");
            }
        }
    }



    private void actOnCell(String cellInput, String userActionCol) {
        int selectedColIndex = getSelectedColIndex(cellInput);
        int selectedRowIndex = getSelectedRowIndex(cellInput);

        if (doseUserChooseToPlantFlag(userActionCol)) {
            gameBoard.flag(selectedRowIndex, selectedColIndex);
//            BOARD[selectedRowIndex][selectedColIndex].flag();
            checkIfGameIsOver();
            return;
        }

        if (doseUserChooseToOpenCell(userActionCol)) {
            if (gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
                gameBoard.open(selectedRowIndex, selectedColIndex);
//                BOARD[selectedRowIndex][selectedColIndex].open();
                changeIfGameToLose();
                return;
            }

            gameBoard.openSurroundedCells(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }
        throw new GameException("잘못된 번호를 선택");

    }

    private boolean doseUserChooseToOpenCell(String userActionCol) {
        return userActionCol.equals("1");
    }

    private boolean doseUserChooseToPlantFlag(String userActionCol) {
        return userActionCol.equals("2");
    }

    private int getSelectedRowIndex(String cellInput) {
        char cellInputRow = cellInput.charAt(1);
        int selectedRowIndex = convertRowFrom(cellInputRow);
        return selectedRowIndex;
    }

    private int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        int selectedColIndex = convertColFrom(cellInputCol);
        return selectedColIndex;
    }

    private String getUserActionInputFromUser() {
        consoleOutputHandler.printCommentForUserAction();
        return consoleInputHandler.getCellInput();
    }

    private String getCellInputFromUser() {
        consoleOutputHandler.printCommentForSelectingCell();
        return consoleInputHandler.getCellInput();
    }

    private boolean doseUserLoseTheGame() {
        return gameStatus == -1;
    }

    private boolean doseUserWinTheGame() {
        return gameStatus == 1;
    }

    private void checkIfGameIsOver() {
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private void changeIfGameToLose() {
        gameStatus = -1;
    }

//    private static boolean isAllCellIsOpend() {
//        boolean isAllOpened = true;
//        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
//            for (int col = 0; col < BOARD_COL_SIZE; col++) {
//                if (BOARD[row][col].equals(CLOSED_CELL_SIGN)) {
//                    isAllOpened = false;
//                }
//            }
//        }
//        return isAllOpened;
//    }



    private int convertRowFrom(char cellInputRow) {
        int rowIndex = Character.getNumericValue(cellInputRow) - 1;
        if(rowIndex >= BOARD_ROW_SIZE){
            throw new GameException("잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColFrom(char cellInputCol) {
        switch (cellInputCol) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'j':
                return 9;
            default:
                throw new GameException("잘못된 입력입니다.");
        }
    }

    private void showBoard() {

    }



}
