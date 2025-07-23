package advancedfinalproject.game.PlantsVsZombies;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ItemSelectionScreen {

    private final int WIDTH = 1500;
    private final int HEIGHT = 750;

    public ItemSelectionScreen(Stage stage) {

        Pane pane = new Pane();

        Button playBtn = new Button("play");
        playBtn.setLayoutX(0);
        playBtn.setLayoutY(700);
        pane.getChildren().add(playBtn);

        playBtn.setOnAction(e -> {
            GameScreen game = new GameScreen();
            // if ....
            game.start(stage);
        });


        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle("item selection screen");
        stage.show();
    }
}
