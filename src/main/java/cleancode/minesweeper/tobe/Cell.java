package cleancode.minesweeper.tobe;

public class Cell {

    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    private static final String CLOSED_CELL_SIGN = "□";
    private static final String OPENED_CELL_SIGH = "■";

    private final String sign;
    private int nearbyLandMineCount;
    private boolean isLandMine;

    private Cell(String sign, int nearbyLandMineCount, boolean isLandMine) {
        this.sign = sign;
        this.nearbyLandMineCount = nearbyLandMineCount;
        this.isLandMine = isLandMine;
    }

    // 정적 팩토리 메서드
    public static Cell of(String sign, int nearbyLandMineCount, boolean isLandMine){
        return new Cell(sign, 0 , false);
    }

    public static Cell ofFlag() {
        return of(FLAG_SIGN, 0, false);
    }

    public static Cell ofLandMine() {
        return of(LAND_MINE_SIGN, 0, false);
    }

    public static Cell ofClosed() {
        return of(CLOSED_CELL_SIGN, 0, false);
    }

    public static Cell isOpened() {
        return of(OPENED_CELL_SIGH, 0, false);
    }

    public static Cell ofNearbyLandMineCount(int count) {
        return of(String.valueOf(count), 0, false);
    }


    public String getSign() {
        return sign;
    }


    public boolean isClosed() {
        return CLOSED_CELL_SIGN.equals(this.sign);
    }

    public boolean doesNotClosed() {
        return !isClosed();
    }
}
