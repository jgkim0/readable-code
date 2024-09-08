package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.Advenced;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.gamelevel.Middle;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class GameApplication {

    public static void main(String[] args){
        GameLevel gameLevel = new Advenced();
        InputHandler inputHandler = new ConsoleInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();

        Minesweeper minesweeper = new Minesweeper(gameLevel, inputHandler, outputHandler);
        minesweeper.initialze();
        minesweeper.run();

        // 메인 분리
    }

    /**
     * DIP ( Dependency Inversion Principle )
     *
     * DI ( Dependency Injection ) - "3"
     *
     * IoC ( Inversion of Control )
     *
     */

}
