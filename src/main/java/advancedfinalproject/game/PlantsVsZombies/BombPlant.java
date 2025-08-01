package advancedfinalproject.game.PlantsVsZombies;

public abstract class BombPlant extends Plant {
    protected GameState gameState;

    public BombPlant(GameState gameState, int row, int col) {
        super(row, col);
        hp = 4;
        this.gameState = gameState;
    }
}
