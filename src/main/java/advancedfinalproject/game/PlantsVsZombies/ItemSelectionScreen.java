package advancedfinalproject.game.PlantsVsZombies;

import javafx.scene.Scene;
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
    private Rectangle playBtn;

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
        selectionRect.setLayoutY(40);
        selectionRect.setFitWidth(800);
        selectionRect.setFitHeight(600);
        pane.getChildren().add(selectionRect);

        drawPlayBtn(pane);
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
                    removeSelectedItems(pane);
                    loadSelectedItems(pane);
                    drawRect(pane);
                }

            });
        }

        playBtn.setOnMouseClicked(e -> {
            if (selectedItems.size() == 6) {
                Item.ItemName[] items = new Item.ItemName[6];
                for (int i = 0; i < 6; i++) {
                    items[i] = selectedItems.get(i);
                }
                GameScreen game = new GameScreen();
                game.start(stage,items);
            }
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
        int startX = 590;
        int startY = 120;

        for (int i = 0; i < 20; i++) {
            int row = i / 4;
            int col = i % 4;
            int x = startX + col * (rectWidth + horizontalSpace);
            int y = startY + row * (rectHeight + verticalSpace);

            list[i] = new Rectangle(x, y, rectWidth, rectHeight);
            list[i].setStrokeWidth(2);
            list[i].setStroke(Color.LIGHTBLUE);
            //list[i].setStroke(Color.TRANSPARENT);
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

    private void drawRect(Pane pane) {
        for (int i = 0; i <6; i++) {
            if (rect[i] != null)
                pane.getChildren().remove(rect[i]);
            rect[i] = new Rectangle(200, 100 + (i * 100), 130, 90);
            rect[i].setFill(Color.TRANSPARENT);
            rect[i].setStroke(Color.TRANSPARENT);
            rect[i].setStrokeWidth(2);
            pane.getChildren().add(rect[i]);

            final int index = i;
            rect[i].setOnMouseClicked(e -> {
                System.out.println(1);
                if (index < selectedItems.size()) {
                    int num = Item.getOrder(selectedItems.get(index));
                    isSelected[num] = false;
                    selectedItems.remove(index);
                    removeSelectedItems(pane);
                    loadSelectedItems(pane);
                    drawRect(pane);
                }
            });
        }

    }

    private void loadSelectedItems(Pane pane){
        int i = 0;
        for (; i < selectedItems.size(); i++) {
            if (selectedImages[i] != null)
                pane.getChildren().remove(selectedImages[i]);
            int number = Item.getOrder(selectedItems.get(i));
            Image image = new Image(getClass().getResource("/items/" + number + ".png").toExternalForm());
            selectedImages[i] = new ImageView(image);
            selectedImages[i].setFitWidth(130);
            selectedImages[i].setFitHeight(90);
            selectedImages[i].setLayoutX(200);
            selectedImages[i].setLayoutY(100 + (i * 100));
            pane.getChildren().add(selectedImages[i]);
        }
        for (; i < 6; i++) {
            Image blank = new Image(getClass().getResource("/items/blank.png").toExternalForm());
            selectedImages[i] = new ImageView(blank);
            selectedImages[i].setFitWidth(130);
            selectedImages[i].setFitHeight(90);
            selectedImages[i].setLayoutX(200);
            selectedImages[i].setLayoutY(100 + (i * 100));

            pane.getChildren().add(selectedImages[i]);
        }
    }

    private void removeSelectedItems(Pane pane) {
        for (int i = 0; i < 6; i++) {
            if (selectedImages[i] != null)
                pane.getChildren().remove(selectedImages[i]);
        }
    }

    private void drawPlayBtn(Pane pane) {
        int btnWidth = 180;
        int btnHeight = 90;
        double startX = 500 + (800 - btnWidth) / 2;
        double startY = 650;
        Image play = new Image(getClass().getResource("/buttons/playBtn.png").toExternalForm());
        ImageView playImage = new ImageView(play);
        playImage.setFitWidth(btnWidth);
        playImage.setFitHeight(btnHeight);
        playImage.setLayoutX(startX);
        playImage.setLayoutY(startY);
        pane.getChildren().add(playImage);
        playBtn = new Rectangle(startX, startY, btnWidth, btnHeight);
        playBtn.setFill(Color.TRANSPARENT);
        pane.getChildren().add(playBtn);

        playBtn.setOnMouseEntered(e -> {
            playBtn.setFill(Color.rgb(200, 200, 200, 0.1));
        });
        playBtn.setOnMouseExited(e -> {
            playBtn.setFill(Color.TRANSPARENT);
        });
    }
}
