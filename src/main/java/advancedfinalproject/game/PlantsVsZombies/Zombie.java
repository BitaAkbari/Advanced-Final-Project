package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Zombie {

    public enum ZombieType {ZOMBIE, CONEHEAD_ZOMBIE, SCREEN_DOOR_ZOMBIE, IMP_ZOMBIE};

    private ZombieType type;
    private int row, col;
    private double centerX, centerY;
    private double speed, currSpeed;
    private int hp;
    private boolean isSlowed;
    private Timeline slowTimer;

    public Zombie(ZombieType type, int row, int col) {
        this.type = type;
        this.row = row;
        this.col = col;

        isSlowed = false;

        if (col == 10) // vaghti az akhare safhe vared shode
            centerX = 1500;
        else // vaghti az ghabr biroon oomade
            centerX = GameScreen.GRID_START_X + col * GameScreen.CELL_WIDTH;

        centerY = GameScreen.GRID_START_Y + row * GameScreen.CELL_HEIGHT + GraphicalZombie.ZOMBIE_HEIGHT / 2;

        switch (type) {
            case ZOMBIE:
                speed = 0.25; // har saniye 0.25 az khoone
                hp = 5;
                break;
            case CONEHEAD_ZOMBIE:
                speed = 0.25;
                hp = 7;
                break;
            case SCREEN_DOOR_ZOMBIE:
                speed = 0.25;
                hp = 10;
                break;
            case IMP_ZOMBIE:
                speed = 0.5;
                hp = 3;
                break;
        }

        currSpeed = speed;
    }

    public Zombie(ZombieType type, int row) {
        this(type, row, 10);
    }

    public void eating() {
        currSpeed = 0;
    }

    public void walking() {
        if (isSlowed)
            currSpeed = speed * 0.5;
        else
            currSpeed = speed;
    }

    public void slowDown() {
        if (slowTimer != null)
            slowTimer.stop();

        isSlowed = true;
        currSpeed = speed * 0.5;

        slowTimer = new Timeline(new KeyFrame(Duration.seconds(SnowPea.EFFECT_LENGTH), e -> {
            currSpeed = speed;
            isSlowed = false;
        }));
        slowTimer.setCycleCount(1);
        slowTimer.play();
    }

    public boolean getIsSlowed() {
        return isSlowed;
    }

    public ZombieType getType() {
        return type;
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
        return currSpeed;
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
