package advancedfinalproject.game.PlantsVsZombies;

import javafx.scene.layout.Pane;

public class PlantHandler {
    public static void placePlant(Pane pane) {

        Plant p1 = new Sunflower(2, 2);
        GraphicalPlant p11 = new GraphicalPlant(p1);
        pane.getChildren().add(p11.getPlantImage());

    }
}
