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
    private Rectangle dayGameBtn, nightGameBtn;
    private ImageView dayImage, nightImage;
    private int buttonWidth = 200;
    private int buttonHeight = 100;
    private int startX = 600;
    private int startY = 40;

    public Menu(Stage stage) {
       // ItemSelectionScreen itemSelection = new ItemSelectionScreen(stage);
        Pane pane = new Pane();

        Image screen = new Image(getClass().getResource("/Screen Images/menu background.png").toExternalForm());
        ImageView screenImage = new ImageView(screen);
        screenImage.fitWidthProperty().bind(pane.widthProperty());
        screenImage.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(screenImage);

        dayGameBtn = new Rectangle(startX,startY,buttonWidth,buttonHeight);
        dayGameBtn.setStroke(Color.BLACK);
        dayGameBtn.setFill(Color.TRANSPARENT);
        Image day = new Image(getClass().getResource("/buttons/dayBtn.png").toExternalForm());
        dayImage = new ImageView(day);
        dayImage.setFitHeight(buttonHeight);
        dayImage.setFitWidth(buttonWidth);
        dayImage.setLayoutX(startX);
        dayImage.setLayoutY(startY);


        nightGameBtn = new Rectangle(startX,startY + buttonHeight + 10,buttonWidth,buttonHeight);
        nightGameBtn.setStroke(Color.BLACK);
        nightGameBtn.setFill(Color.TRANSPARENT);
        Image night = new Image(getClass().getResource("/buttons/nightBtn.png").toExternalForm());
        nightImage = new ImageView(night);
        nightImage.setFitHeight(buttonHeight);
        nightImage.setFitWidth(buttonWidth);
        nightImage.setLayoutX(startX);
        nightImage.setLayoutY(startY + buttonHeight + 10);


        pane.getChildren().addAll(dayImage , nightImage);
        pane.getChildren().addAll(nightGameBtn , dayGameBtn);



        stage.setTitle("Menu");
        stage.setResizable(false); //size stage ro fix mikone

        Scene screenScene = new Scene(pane, WIDTH, HEIGHT);
        stage.setScene(screenScene);
        stage.centerOnScreen();
        stage.show();


    }
}
