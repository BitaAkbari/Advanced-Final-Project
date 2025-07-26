package advancedfinalproject.game.PlantsVsZombies;

public class Zombie {
    private double speed;
    private double centerX, centerY;

    public Zombie(double x, double y) {
        speed = 4.425;
        centerX = x;
        centerY = y;
    }

    public void setX(double x) {
        centerX = x;
    }

    public double getX() {
        return centerX;
    }

    public double getY() {
        return centerY;
    }

    public double getSpeed() {
        return speed;
    }
}
