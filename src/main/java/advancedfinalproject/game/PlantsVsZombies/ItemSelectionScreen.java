package advancedfinalproject.game.PlantsVsZombies;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ItemSelectionScreen {

    private final int WIDTH = 1500;
    private final int HEIGHT = 750;
    private Item.ItemName[] itemList;
    private boolean[] isSelected;
    private ArrayList<Item.ItemName> selectedItems; // bishtar az 6 nabayad ozv begire
    private Rectangle[] list;
    private Rectangle[] rect;
    private ImageView[] selectedImages;

    public ItemSelectionScreen(Stage stage) {

        itemList = Item.ItemName.values();
        isSelected = new boolean[17];
        for (int i = 0; i < 17; i++)
            isSelected[i] = false;
        selectedItems = new ArrayList<>();
        list = new Rectangle[20];
        rect = new Rectangle[6];
        selectedImages = new ImageView[6];

        Pane pane = new Pane();

        stage.setResizable(false); //size stage ro fix mikone

        Image screen = new Image(getClass().getResource("/Screen Images/day-background.png").toExternalForm());
        ImageView background = new ImageView(screen);
        background.fitWidthProperty().bind(pane.widthProperty());
        background.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(background);

        ColorAdjust darken = new ColorAdjust();
        darken.setBrightness(-0.5);
        background.setEffect(darken);

        Image selectionBackground = new Image(getClass().getResource("/Screen Images/selection background.png").toExternalForm());
        ImageView selectionRect = new ImageView(selectionBackground);
        selectionRect.setLayoutX(500);
        selectionRect.setLayoutY(70);
        selectionRect.setFitWidth(800);
        selectionRect.setFitHeight(600);
        pane.getChildren().add(selectionRect);
        drawItemList(pane);
        loadSelectedItems(pane);
        drawRect(pane);

        for (int i = 0; i < 17; i++) {
            final int index = i;
            list[i].setOnMouseClicked(e -> {
                if (selectedItems.size() < 6 && !isSelected[index]) {
                    selectedItems.add(itemList[index]);
                    isSelected[index] = true;
                    System.out.println(index);
                    loadSelectedItems(pane);
                    drawRect(pane);
                }

            });
        }

        for (int i = 0; i < 6; i++){
            final int index = i;
            rect[i].setOnMouseClicked(e -> {
                isSelected[index] = false;
                selectedItems.remove(index);
                removeSelectedItems(pane);
                loadSelectedItems(pane);
                drawRect(pane);
            });
        }


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

    private void drawItemList(Pane pane) {
        int rectWidth = 140;
        int rectHeight = 80;
        int horizontalSpace = 20;
        int verticalSpace = 20;
        int startX = 600;
        int startY = 150;

        for (int i = 0; i < 20; i++) {
            int row = i / 4;
            int col = i % 4;
            int x = startX + col * (rectWidth + horizontalSpace);
            int y = startY + row * (rectHeight + verticalSpace);

            list[i] = new Rectangle(x, y, rectWidth, rectHeight);
            list[i].setStrokeWidth(2);
            list[i].setStroke(Color.LIGHTBLUE);
            list[i].setFill(Color.TRANSPARENT);
            if (i < 17) {
                Image image = new Image(getClass().getResource("/items/" + i + ".png").toExternalForm());
                ImageView picture = new ImageView(image);
                picture.setFitWidth(rectWidth);
                picture.setFitHeight(rectHeight);
                picture.setLayoutX(x);
                picture.setLayoutY(y);
                pane.getChildren().add(picture);
            }
            pane.getChildren().add(list[i]);
        }
    }

    private void drawRect(Pane pane){
        for (int i = 0; i <6; i++) {
            rect[i] = new Rectangle(200, 100 + (i * 100), 130, 90);
            rect[i].setFill(Color.TRANSPARENT);
            rect[i].setStroke(Color.BLACK);
            rect[i].setStrokeWidth(2);
            pane.getChildren().add(rect[i]);
        }

    }

    private void loadSelectedItems(Pane pane){
        for(int i = 0; i < selectedItems.size(); i++){
            int number = Item.getOrder(selectedItems.get(i));
            Image image = new Image(getClass().getResource("/items/" + number + ".png").toExternalForm());
            selectedImages[i] = new ImageView(image);
            selectedImages[i].setFitWidth(130);
            selectedImages[i].setFitHeight(90);
            selectedImages[i].setLayoutX(200);
            selectedImages[i].setLayoutY(100 + (i * 100));
            pane.getChildren().add(selectedImages[i]);
        }
    }

    private void removeSelectedItems(Pane pane){
        for(int i = 0; i < 6; i++){
            pane.getChildren().remove(selectedImages[i]);
        }
    }
}
