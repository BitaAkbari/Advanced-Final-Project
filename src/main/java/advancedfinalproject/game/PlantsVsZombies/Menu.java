package advancedfinalproject.game.PlantsVsZombies;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Menu {
    public static final int WIDTH = 1500;
    public static final int HEIGHT = 750;
    private Stage menuStage;
    private Pane pane;
    private Circle dayGameBtn, nightGameBtn;
    //private ImageView dayImage, nightImage;
    private int btnWidth = 280;
    private int btnHeight = 280;
    private int btnStartX = 400;
    private int btnStartY = 590;
    private int btnRadius = 140;
    //private int verticalSpace = 10;

    public Menu(Stage stage) {

        menuStage = stage;
        pane = new Pane();

        setScreen();
        drawDayBtn();
        drawNightBtn();


        menuStage.setTitle("Menu");
        menuStage.setResizable(false); //size stage ro fix mikone

        Scene screenScene = new Scene(pane, WIDTH, HEIGHT);
        menuStage.setScene(screenScene);
        menuStage.centerOnScreen();
        menuStage.show();
    }

    private void setScreen() {
        Image screen = new Image(getClass().getResource("/Screen Images/menu background4.jpg").toExternalForm());
        ImageView screenImage = new ImageView(screen);
        screenImage.fitWidthProperty().bind(pane.widthProperty());
        screenImage.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(screenImage);
    }

    private void drawDayBtn() {
        dayGameBtn = new Circle(btnStartX, btnStartY, btnRadius);
        dayGameBtn.setStroke(Color.BLACK);
        dayGameBtn.setFill(Color.TRANSPARENT);

        Image day = new Image(getClass().getResource("/buttons/day2.png").toExternalForm());
        ImageView dayImage = new ImageView(day);
        dayImage.setFitWidth(btnWidth);
        dayImage.setFitHeight(btnHeight);
        dayImage.setLayoutX(btnStartX - btnRadius);
        dayImage.setLayoutY(btnStartY - btnRadius);

        pane.getChildren().add(dayImage);
        pane.getChildren().add(dayGameBtn);

        dayGameBtn.setOnMouseClicked(e -> {
            ItemSelectionScreen itemSelection = new ItemSelectionScreen(menuStage);
        });
        dayGameBtn.setOnMouseEntered(e -> {
            dayGameBtn.setStroke(Color.RED);
        });
        dayGameBtn.setOnMouseExited(e -> {
            dayGameBtn.setStroke(Color.BLACK);
        });
    }

    private void drawNightBtn() {
        nightGameBtn = new Circle(btnStartX + 630, btnStartY, btnRadius);
        nightGameBtn.setStroke(Color.BLACK);
        nightGameBtn.setFill(Color.TRANSPARENT);

        Image night = new Image(getClass().getResource("/buttons/night2.png").toExternalForm());
        ImageView nightImage = new ImageView(night);
        nightImage.setFitWidth(btnWidth);
        nightImage.setFitHeight(btnHeight);
        nightImage.setLayoutX(btnStartX - btnRadius + 630);
        nightImage.setLayoutY(btnStartY - btnRadius);

        pane.getChildren().add(nightImage);
        pane.getChildren().add(nightGameBtn);

        nightGameBtn.setOnMouseClicked(e -> {
            ItemSelectionScreen itemSelection = new ItemSelectionScreen(menuStage);
        });
        nightGameBtn.setOnMouseEntered(e -> {
            nightGameBtn.setStroke(Color.RED);
        });
        nightGameBtn.setOnMouseExited(e -> {
            nightGameBtn.setStroke(Color.BLACK);
        });
    }
}
