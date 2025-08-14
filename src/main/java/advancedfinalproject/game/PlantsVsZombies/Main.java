package advancedfinalproject.game.PlantsVsZombies;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
       Menu menu = new Menu(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

