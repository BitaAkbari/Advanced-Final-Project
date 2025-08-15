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

    private final int WIDTH = GameScreen.WIDTH;
    private final int HEIGHT = GameScreen.HEIGHT;
    private final int TOTAL_ITEM_COUNT = 17;
    private final int RECT_COUNT = TOTAL_ITEM_COUNT + 3;
    public static final int SELECTED_COUNT = 6;

    private GameState.GameCode code;
    private Plant.PlantName[] itemList; // liste hameye giyahane mojood
    private boolean[] isSelected; // motanazer ba liste hameye giyahan
    private ArrayList<Plant.PlantName> selectedItems; // liste entekhab haye karbar - 6 onsor
    private Rectangle[] list; // mostatil haye liste giyahan
    private Rectangle[] selectedRects; // mostatil haye entekhabe karbar
    private ImageView[] selectedImages; // araye ye aks haye entekhab shode ha
    private Rectangle playBtn;
    private Pane pane;
    private Stage stage;

    // andaze haye marboot be selection background
    private int selectionBgWidth;
    private int selectionBgHeight;
    private double selectionBgStartX;
    private double selectionBgStartY;

    public ItemSelectionScreen(Stage stage, GameState.GameCode code) {

        this.stage = stage;
        this.code = code;

        itemList = Plant.PlantName.values();
        isSelected = new boolean[TOTAL_ITEM_COUNT];
        for (int i = 0; i < TOTAL_ITEM_COUNT; i++)
            isSelected[i] = false;
        selectedItems = new ArrayList<Plant.PlantName>();
        list = new Rectangle[RECT_COUNT];
        selectedRects = new Rectangle[SELECTED_COUNT];
        selectedImages = new ImageView[SELECTED_COUNT];

        pane = new Pane();

        setBackground();
        drawItemList();
        loadSelectedItems();
        drawSelectedRects();
        drawPlayBtn();
        drawMenuBtn();

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        stage.setResizable(false); //size stage ro fix mikone
        stage.setScene(scene);
        stage.setTitle("item selection screen");
        stage.show();
    }

    private void setBackground() {
        String imageName;
        if (code == GameState.GameCode.DAY)
            imageName = "day-background.png";
        else
            imageName = "night-background.png";

        Image screen = new Image(getClass().getResource("/Screen Images/" + imageName).toExternalForm());
        ImageView background = new ImageView(screen);
        background.fitWidthProperty().bind(pane.widthProperty());
        background.fitHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(background);

        ColorAdjust darken = new ColorAdjust();
        darken.setBrightness(-0.5);
        background.setEffect(darken);
    }

    private void drawItemList() {
        int rectWidth = 140;
        int rectHeight = 80;
        int horizontalSpace = 20;
        int verticalSpace = 20;
        int startX = 600;
        int startY = 115;

        selectionBgWidth = 700;
        selectionBgHeight = 600;
        selectionBgStartX = startX - (selectionBgWidth - (4 * rectWidth + 3 * horizontalSpace)) / 2; // = 560
        selectionBgStartY = 40;

        Image selectionBackground = new Image(getClass().getResource("/Screen Images/selection background.png").toExternalForm());
        ImageView selectionRect = new ImageView(selectionBackground);
        selectionRect.setLayoutX(selectionBgStartX);
        selectionRect.setLayoutY(selectionBgStartY);
        selectionRect.setFitWidth(selectionBgWidth);
        selectionRect.setFitHeight(selectionBgHeight);
        pane.getChildren().add(selectionRect);

        for (int i = 0; i < RECT_COUNT; i++) {
            int row = i / 4;
            int col = i % 4;
            int x = startX + col * (rectWidth + horizontalSpace);
            int y = startY + row * (rectHeight + verticalSpace);

            list[i] = new Rectangle(x, y, rectWidth, rectHeight);
            list[i].setStrokeWidth(2);
            list[i].setStroke(Color.LIGHTBLUE);
            //list[i].setStroke(Color.TRANSPARENT);
            list[i].setFill(Color.TRANSPARENT);
            if (i < TOTAL_ITEM_COUNT) {
                Image image = new Image(getClass().getResource("/items/" + i + ".png").toExternalForm());
                ImageView picture = new ImageView(image);
                picture.setFitWidth(rectWidth);
                picture.setFitHeight(rectHeight);
                picture.setLayoutX(x);
                picture.setLayoutY(y);
                pane.getChildren().add(picture);
            }
            pane.getChildren().add(list[i]);

            final int index = i;
            list[i].setOnMouseClicked(e -> {
                if (index < TOTAL_ITEM_COUNT && selectedItems.size() < SELECTED_COUNT && !isSelected[index]) {
                    selectedItems.add(itemList[index]);
                    isSelected[index] = true;
                    loadSelectedItems();
                }
            });
        }
    }

    private void drawSelectedRects() {
        int width = 130;
        int height = 90;
        int x = 230;
        int startY = 100;
        for (int i = 0; i < SELECTED_COUNT; i++) {
            selectedRects[i] = new Rectangle(x, startY + (i * (height + 10)), width, height);
            selectedRects[i].setFill(Color.TRANSPARENT);
            selectedRects[i].setStroke(Color.TRANSPARENT);
            selectedRects[i].setStrokeWidth(2);
            pane.getChildren().add(selectedRects[i]);

            final int index = i;
            selectedRects[i].setOnMouseClicked(e -> {
                if (index < selectedItems.size()) {
                    Item item = new Item(selectedItems.get(index));
                    int num = item.getName().ordinal();
                    isSelected[num] = false;
                    selectedItems.remove(index);
                    loadSelectedItems();
                }
            });
        }

    }

    private void loadSelectedItems() {
        int width = 130;
        int height = 90;
        int x = 230;
        int startY = 100;
        int i = 0;
        for (; i < selectedItems.size(); i++) {
            if (selectedImages[i] != null)
                pane.getChildren().remove(selectedImages[i]);
            Item tempItem = new Item(selectedItems.get(i));
            int number = tempItem.getName().ordinal();
            Image image = new Image(getClass().getResource("/items/" + number + ".png").toExternalForm());
            selectedImages[i] = new ImageView(image);
            selectedImages[i].setFitWidth(width);
            selectedImages[i].setFitHeight(height);
            selectedImages[i].setLayoutX(x);
            selectedImages[i].setLayoutY(startY + (i * (height + 10)));
            selectedImages[i].setMouseTransparent(true);
            pane.getChildren().add(selectedImages[i]);
        }
        for (; i < SELECTED_COUNT; i++) {
            if (selectedImages[i] != null)
                pane.getChildren().remove(selectedImages[i]);
            Image blank = new Image(getClass().getResource("/items/blank.png").toExternalForm());
            selectedImages[i] = new ImageView(blank);
            selectedImages[i].setFitWidth(width);
            selectedImages[i].setFitHeight(height);
            selectedImages[i].setLayoutX(x);
            selectedImages[i].setLayoutY(startY + (i * (height + 10)));
            selectedImages[i].setMouseTransparent(true);
            pane.getChildren().add(selectedImages[i]);
        }
    }

    private void drawPlayBtn() {
        int btnWidth = 180;
        int btnHeight = 90;
        double startX = selectionBgStartX + (selectionBgWidth - btnWidth) / 2; // = 820
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
        playBtn.setOnMouseClicked(e -> {
            if (selectedItems.size() == SELECTED_COUNT) {
                // tabdile arraylist be araye
                Plant.PlantName[] items = new Plant.PlantName[SELECTED_COUNT];
                for (int i = 0; i < SELECTED_COUNT; i++) {
                    items[i] = selectedItems.get(i);
                }
                GameScreen game = new GameScreen(stage, code, items);
            }
        });
    }

    private void drawMenuBtn() {
        int width = 150;
        int height = 50;
        int startX = 1280;
        int startY = 10;

        Image menuImage = new Image(getClass().getResource("/buttons/menu-button.png").toExternalForm());
        ImageView menuView = new ImageView(menuImage);
        menuView.setFitWidth(width);
        menuView.setFitHeight(height);
        menuView.setLayoutX(startX);
        menuView.setLayoutY(startY);

        Rectangle menuGameBtn = new Rectangle(startX , startY , width , height);
        menuGameBtn.setStroke(Color.BLACK);
        menuGameBtn.setFill(Color.TRANSPARENT);

        pane.getChildren().addAll(menuView , menuGameBtn);

        menuGameBtn.setOnMouseEntered(e -> {
            menuGameBtn.setFill(Color.rgb(200, 200, 200, 0.1));
        });
        menuGameBtn.setOnMouseExited(e -> {
            menuGameBtn.setFill(Color.TRANSPARENT);
        });
        menuGameBtn.setOnMouseClicked( e -> {
            new Menu(stage);
        });
    }
}
