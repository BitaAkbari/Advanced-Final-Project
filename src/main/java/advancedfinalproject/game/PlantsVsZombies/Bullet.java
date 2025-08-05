package advancedfinalproject.game.PlantsVsZombies;

public class Bullet {

    public enum BulletType {NORMAL, FROZEN, PUFF_SHROOM_BULLET, SCARDY_SHROOM_BULLET};

    private BulletType type;
    private int sourceRow, sourceCol;
    private int row, col;
    private double centerX, centerY;
    private double speed;

    public Bullet(BulletType type, int row, int col) {
        sourceRow = row;
        sourceCol = col;
        this.type = type;
        this.row = row;
        this.col = col;
        int horizontalDiff, verticalDiff;
        if (type == BulletType.PUFF_SHROOM_BULLET) {
            horizontalDiff = 85;
            verticalDiff = 70;
        }
        else {
            horizontalDiff = 90;
            verticalDiff = 30;
        }
        centerX = GameScreen.GRID_START_X + col * GameScreen.CELL_WIDTH + (horizontalDiff);
        centerY = GameScreen.GRID_START_Y + row * GameScreen.CELL_HEIGHT + (verticalDiff);
        speed = 2; // har saniye 2 khoone
    }

    public BulletType getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    //    private void setRow(int row) {
//        this.row = row;
//    }

    public int getCol() {
        return col;
    }

    private void setCol(int col) {
        this.col = col;
    }

    public int getSourceRow() {
        return sourceRow;
    }

    public int getSourceCol() {
        return sourceCol;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double x) {
        centerX = x;
        int col = ((int) centerX - GameScreen.GRID_START_X) / GameScreen.CELL_WIDTH;
        setCol(col);
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double y) {
        centerY = y;
    }

    public double getSpeed() {
        return speed;
    }
}