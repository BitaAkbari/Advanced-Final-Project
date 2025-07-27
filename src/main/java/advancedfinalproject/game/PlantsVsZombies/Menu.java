package advancedfinalproject.game.PlantsVsZombies;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Menu {
    public static final int WIDTH = 1500;
    public static final int HEIGHT = 750;
    private Stage menuStage;
    private Pane pane;
    private Rectangle dayGameBtn, nightGameBtn;
    //private ImageView dayImage, nightImage;
    private int btnWidth = 200;
    private int btnHeight = 100;
    private int btnStartX = 600;
    private int btnStartY = 40;
    private int verticalSpace = 10;

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
        Image screen = new Image(getClass().getResource("/Screen Images/menu background.png").toExternalForm());
        ImageView screenImage = new ImageView(screen);
        screenImage.fitWidthProperty().bind(pane.widthProperty());
        screenImage.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(screenImage);
    }

    private void drawDayBtn() {
        dayGameBtn = new Rectangle(btnStartX, btnStartY, btnWidth, btnHeight);
        dayGameBtn.setStroke(Color.BLACK);
        dayGameBtn.setFill(Color.TRANSPARENT);

        Image day = new Image(getClass().getResource("/buttons/dayBtn.png").toExternalForm());
        ImageView dayImage = new ImageView(day);
        dayImage.setFitWidth(btnWidth);
        dayImage.setFitHeight(btnHeight);
        dayImage.setLayoutX(btnStartX);
        dayImage.setLayoutY(btnStartY);

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
        nightGameBtn = new Rectangle(btnStartX, btnStartY + btnHeight + verticalSpace, btnWidth, btnHeight);
        nightGameBtn.setStroke(Color.BLACK);
        nightGameBtn.setFill(Color.TRANSPARENT);

        Image night = new Image(getClass().getResource("/buttons/nightBtn.png").toExternalForm());
        ImageView nightImage = new ImageView(night);
        nightImage.setFitWidth(btnWidth);
        nightImage.setFitHeight(btnHeight);
        nightImage.setLayoutX(btnStartX);
        nightImage.setLayoutY(btnStartY + btnHeight + verticalSpace);

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
