package advancedfinalproject.game.PlantsVsZombies;

public abstract class BombPlant extends Plant {
    protected GameState gameState;

    public BombPlant(GameState gameState, PlantName name, int row, int col) {
        super(name, row, col);
        hp = 4;
        this.gameState = gameState;
    }
}
