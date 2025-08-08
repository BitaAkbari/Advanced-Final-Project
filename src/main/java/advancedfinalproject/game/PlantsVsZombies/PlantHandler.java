package advancedfinalproject.game.PlantsVsZombies;

import javafx.scene.layout.Pane;

public class PlantHandler {
    private Pane pane;
    private GameState gameState;
    private GraphicalPlant[][] graphicPlants;
    private GraphicalPlant[][] coffeeBeans;

    public PlantHandler(Pane pane, GameState gameState) {
        this.pane = pane;
        this.gameState = gameState;
        graphicPlants = new GraphicalPlant[GameState.ROW_COUNT][GameState.COL_COUNT];
        coffeeBeans = new GraphicalPlant[GameState.ROW_COUNT][GameState.COL_COUNT];

        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT; j++) {
                graphicPlants[i][j] = null;
                coffeeBeans[i][j] = null;
            }

    }

    public void addPlant(int row, int col) {
        Plant plant = gameState.getField()[row][col].getPlant();
        if (gameState.getCoffeeBeanList()[row][col] != null && coffeeBeans[row][col] == null) {
            coffeeBeans[row][col] = new GraphicalPlant(gameState.getCoffeeBeanList()[row][col]);
            pane.getChildren().add(coffeeBeans[row][col].getPlantImage());
        }
        else {
            graphicPlants[row][col] = new GraphicalPlant(plant);
            pane.getChildren().add(graphicPlants[row][col].getPlantImage());
        }
    }

    public void deletePlant(int row, int col) {
        pane.getChildren().remove(graphicPlants[row][col].getPlantImage());
        graphicPlants[row][col].stop();
        graphicPlants[row][col] = null;
    }

    public void clearCoffeeBean(int row, int col) {
        pane.getChildren().remove(coffeeBeans[row][col].getPlantImage());
        coffeeBeans[row][col].stop();
        coffeeBeans[row][col] = null;
    }

    public GraphicalPlant[][] getGraphicPlants() {
        return graphicPlants;
    }

//    public void clearGraphicalPlants() {
//        graphicPlants = null;
//    }
}
