package advancedfinalproject.game.PlantsVsZombies;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Menu {

    public static final int WIDTH = GameScreen.WIDTH;
    public static final int HEIGHT = GameScreen.HEIGHT;

    private Stage menuStage;
    private Pane pane;
    private Circle dayGameBtn, nightGameBtn;
    private int btnWidth = 340;
    private int btnHeight = 340;
    private int btnCenterX = 400;
    private int btnCenterY = 450;
    private int btnRadius = 170;
    private int horizontalSpace = 630;

    public Menu(Stage stage) {

        menuStage = stage;
        pane = new Pane();

        setScreen();
        drawDayBtn();
        drawNightBtn();


        menuStage.setTitle("Menu");
        menuStage.setResizable(false); //size stage ro fix mikone

        Scene menuScene = new Scene(pane, WIDTH, HEIGHT);
        menuStage.setScene(menuScene);
        menuStage.centerOnScreen(); //stage ro vasate safhe gharar mide
        menuStage.show();
    }

    private void setScreen() {
        Image screen = new Image(getClass().getResource("/Screen Images/menu background.jpg").toExternalForm());
        ImageView screenImage = new ImageView(screen);

        //baraye tanzime aks ba panjere ye asli
        screenImage.fitWidthProperty().bind(pane.widthProperty());
        screenImage.fitHeightProperty().bind(pane.heightProperty());

        pane.getChildren().add(screenImage);
    }

    private void drawDayBtn() {
        dayGameBtn = new Circle(btnCenterX, btnCenterY, btnRadius);
        dayGameBtn.setStroke(Color.BLACK);
        dayGameBtn.setFill(Color.TRANSPARENT);

        Image day = new Image(getClass().getResource("/buttons/dayBtn.png").toExternalForm());
        ImageView dayImage = new ImageView(day);
        dayImage.setFitWidth(btnWidth);
        dayImage.setFitHeight(btnHeight);
        dayImage.setLayoutX(btnCenterX - btnRadius);
        dayImage.setLayoutY(btnCenterY - btnRadius);

        pane.getChildren().add(dayImage);
        pane.getChildren().add(dayGameBtn);

        dayGameBtn.setOnMouseClicked(e -> {
            Load load = new Load(GameState.GameCode.DAY);
            if (load.isEmpty()) {
                ItemSelectionScreen itemSelection = new ItemSelectionScreen(menuStage, GameState.GameCode.DAY);
            }
            else {
                GameScreen gameScreen = new GameScreen(menuStage, load);
                //gameScreen.start();
            }
        });
        dayGameBtn.setOnMouseEntered(e -> {
            dayGameBtn.setStroke(Color.RED);
        });
        dayGameBtn.setOnMouseExited(e -> {
            dayGameBtn.setStroke(Color.BLACK);
        });
    }

    private void drawNightBtn() {

        nightGameBtn = new Circle(btnCenterX + horizontalSpace, btnCenterY, btnRadius);
        nightGameBtn.setStroke(Color.BLACK);
        nightGameBtn.setFill(Color.TRANSPARENT);

        Image night = new Image(getClass().getResource("/buttons/nightBtn.png").toExternalForm());
        ImageView nightImage = new ImageView(night);
        nightImage.setFitWidth(btnWidth);
        nightImage.setFitHeight(btnHeight);
        nightImage.setLayoutX(btnCenterX - btnRadius + horizontalSpace);
        nightImage.setLayoutY(btnCenterY - btnRadius);

        pane.getChildren().add(nightImage);
        pane.getChildren().add(nightGameBtn);

        nightGameBtn.setOnMouseClicked(e -> {
            Load load = new Load(GameState.GameCode.NIGHT);
            if (load.isEmpty()) {
                ItemSelectionScreen itemSelection = new ItemSelectionScreen(menuStage, GameState.GameCode.NIGHT);
            }
            else {
                GameScreen gameScreen = new GameScreen(menuStage, load);
                //gameScreen.start();
            }
        });
        nightGameBtn.setOnMouseEntered(e -> {
            nightGameBtn.setStroke(Color.RED);
        });
        nightGameBtn.setOnMouseExited(e -> {
            nightGameBtn.setStroke(Color.BLACK);
        });
    }
}
