package advancedfinalproject.game.PlantsVsZombies;

public class Bullet {
    private double speed;
    private double centerX;
    private double centerY;
    private final double bulletLength = 20;
    private final double bulletWidth = 20;
    private int HP = 1;

    public Bullet(double x, double y) {
        centerX = x + bulletWidth /2;
        centerY = y + bulletLength /2;
    }


    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getSpeed() {
        return speed;
    }

    public int getHp() {
        return HP;
    }

    public void setCenterX(double x) {
        centerX = x + bulletWidth /2;
    }

    public void setCenterY(double y) {
        centerY = y + bulletLength /2;
    }


}