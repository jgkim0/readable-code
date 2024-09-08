package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class Minesweeper implements GameInitializable, GameRunnable{

    private final GameBoard gameBoard;
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public Minesweeper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler){
        gameBoard = new GameBoard(gameLevel);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }


    public void run() {
        outputHandler.showGameStartComments();

        while (true) {
            try{
                outputHandler.showBoard(gameBoard);

                if (doseUserWinTheGame()) {
                    outputHandler.showGameWinningComment();
                    break;
                }
                if (doseUserLoseTheGame()) {
                    outputHandler.showGameLosingComment();
                    break;
                }

                String cellInput = getCellInputFromUser();
                String userActionCol = getUserActionInputFromUser();
                actOnCell(cellInput, userActionCol);
                actOnCell(cellInput, userActionCol);

            } catch (GameException e){
                outputHandler.showExceptionMessage(e);
            } catch (Exception e){
                outputHandler.showSimpleMessage("프로그램에 문제가 생겼습니다.");
            }
        }
    }



    private void actOnCell(String cellInput, String userActionCol) {
        int selectedColIndex = boardIndexConverter.getSelectedColIndex(cellInput, gameBoard.getColSize());
        int selectedRowIndex = boardIndexConverter.getSelectedRowIndex(cellInput, gameBoard.getRowSize());

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

    private String getUserActionInputFromUser() {
        outputHandler.showCommentForUserAction();
        return inputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        outputHandler.showCommentForSelectingCell();
        return inputHandler.getUserInput();
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
    @Override
    public void initialze() {
        gameBoard.initializeGame();
    }
}
