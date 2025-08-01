package advancedfinalproject.game.PlantsVsZombies;

public class SnowPea extends PeaPlant {
    public SnowPea(int row, int col) {
        super(row, col);
        type = Bullet.BulletType.FROZEN;
    }
}
