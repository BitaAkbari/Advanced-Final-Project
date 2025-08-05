package advancedfinalproject.game.PlantsVsZombies;

public class Bullet {

    public enum BulletType {NORMAL, FROZEN, SHROOM_BULLET};

    private BulletType type;
    private int row, col;
    private double centerX, centerY;
    private double speed;

    public Bullet(BulletType type, int row, int col) {
        this.type = type;
        this.row = row;
        this.col = col;
        centerX = GameScreen.GRID_START_X + col * GameScreen.CELL_WIDTH + (90);
        centerY = GameScreen.GRID_START_Y + row * GameScreen.CELL_HEIGHT + (30);
        speed = 2; // har saniye 2 khoone
    }

    public BulletType getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double x) {
        centerX = x;
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