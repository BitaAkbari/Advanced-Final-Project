package advancedfinalproject.game.PlantsVsZombies;

public class Zombie {

    public enum ZombieType {ZOMBIE, CONEHEAD_ZOMBIE, SCREEN_DOOR_ZOMBIE, IMP_ZOMBIE};

    private int row, col;
    private double centerX, centerY;
    private double speed;
    private int hp;

    public Zombie(int row, int col) {
        this.row = row;
        this.col = col;

        if (col == 10) // vaghti az akhare safhe vared shode
            centerX = 1500;
        else // vaghti az ghabr biroon oomade
            centerX = GameScreen.GRID_START_X + col * GameScreen.CELL_WIDTH;

        centerY = GameScreen.GRID_START_Y + row * GameScreen.CELL_HEIGHT + GraphicalZombie.ZOMBIE_HEIGHT / 2;

        speed = 0.25; // har saniye 0.25 az khoone
        hp = 5;

    }

    public Zombie(int row) {
        this(row, 10);
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

    public double getSpeed() {
        return speed;
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

    public int getHp() {
        return hp;
    }
    public void reduceHp() {
        hp --;
    }

    public void burst() {
        hp = 0;
    }
}
