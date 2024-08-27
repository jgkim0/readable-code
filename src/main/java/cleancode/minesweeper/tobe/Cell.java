package cleancode.minesweeper.tobe;

public class Cell {

    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String UNCHECKED_SIGN = "□";
    private static final String EMPTY_SIGN = "■";

    private final String sign;
    private int nearbyLandMineCount;
    private boolean isLandMine;

    private boolean isFlagged;
    private boolean isOpend;

    private Cell(String sign, int nearbyLandMineCount, boolean isLandMine, boolean isFlagged, boolean isOpend) {
        this.sign = sign;
        this.nearbyLandMineCount = nearbyLandMineCount;
        this.isLandMine = isLandMine;
        this.isFlagged = isFlagged
    }

    // 정적 팩토리 메서드
    public static Cell of(String sign, int nearbyLandMineCount, boolean isLandMine, boolean isFlagged){
        return new Cell(sign, 0 , false, false, false);
    }

    public static Cell create() {
        return of("", 0 , false, false);
    }

    public static Cell ofFlag() {
        return of(FLAG_SIGN, 0, false);
    }

    public static Cell ofLandMine() {
        return of(LAND_MINE_SIGN, 0, false);
    }

    public static Cell ofClosed() {
        return of(UNCHECKED_SIGN, 0, false);
    }

    public static boolean isOpened() {
        return of(EMPTY_SIGN, 0, false);
    }

    public static Cell ofNearbyLandMineCount(int count) {
        return of(String.valueOf(count), 0, false);
    }


    public String getSign() {
        return sign;
    }


    public boolean isClosed() {
        return UNCHECKED_SIGN.equals(this.sign);
    }

    public boolean doesNotClosed() {
        return !isClosed();
    }

    public void turnOnLandMine() {
        this.isLandMine = true;
    }

    public void updateNearbyLandMineCount(int count) {
        this.nearbyLandMineCount = count;
    }

    public void flag() {
        this.isFlagged = true;
    }

    public boolean isChecked() {
        return isFlagged || isOpend;
    }

    public boolean isLandMine() {
        return isFlagged;
    }

    public void open() {
        this.isOpend = true;
    }

    public boolean isOpened() {
        return isOpend;
    }
}
