package advancedfinalproject.game.PlantsVsZombies;

public class Lantern extends Plant {

    private GameState gameState;

    public Lantern(GameState gameState, int row, int col) {
        super(PlantName.LANTERN, row, col);
        hp = 4;
        this.gameState = gameState;
    }

    public void act() {
        for (int i = row - 1; i <= row + 1; i++)
            for (int j = col - 1; j <= col + 1; j++)
                if (i >= 0 && i < GameState.ROW_COUNT && j >= 0 && j < GameState.COL_COUNT)
                    gameState.getField()[i][j].setLanternEffect(true);
    }

    public void stopAct() {
        for (int i = row - 1; i <= row + 1; i++)
            for (int j = col - 1; j <= col + 1; j++)
                if (i >= 0 && i < GameState.ROW_COUNT && j >= 0 && j < GameState.COL_COUNT)
                    gameState.getField()[i][j].setLanternEffect(false);
    }
}
