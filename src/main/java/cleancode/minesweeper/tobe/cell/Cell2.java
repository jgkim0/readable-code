package cleancode.minesweeper.tobe.cell;

public abstract class Cell2 {

    protected static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";
    protected static final String UNCHECKED_SIGN = "□";



    protected boolean isFlagged;
    protected boolean isOpened;

    // 정적 팩토리 메서드

    public abstract void turnOnLandMine();

    public abstract void updateNearbyLandMineCount(int count);

    public abstract boolean isLandMine();

    public abstract boolean hasLandMineCount();

    public abstract String getSign();

    public void flag() {
        this.isFlagged = true;
    }

    public boolean isChecked() {
        return isFlagged || isOpened;
    }

    public void open() {
        this.isOpened = true;
    }

    public boolean isOpened() {
        return isOpened;
    }

}
