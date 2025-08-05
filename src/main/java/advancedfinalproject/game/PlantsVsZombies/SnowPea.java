package advancedfinalproject.game.PlantsVsZombies;

public class SnowPea extends PeaPlant {
    public static final int EFFECT_LENGTH = 4;

    public SnowPea(int row, int col) {
        super(PlantName.SNOW_PEA, row, col);
        type = Bullet.BulletType.FROZEN;
    }
}
