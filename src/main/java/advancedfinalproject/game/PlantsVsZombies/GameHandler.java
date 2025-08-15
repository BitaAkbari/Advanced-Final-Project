package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameHandler {
    private static GameState gameState;
    private ZombieHandler zombieHandler;
    private PlantHandler plantHandler;
    private ArrayList<GraphicalBullet> graphicalBullets;
    private static ArrayList<GraphicalSun> graphicalSuns;
    private ImageView[][] blockedImages;
    private static ImageView[][] fogImages;
    private static Pane pane;
    private Timeline timer, checkLoop, sunGenerator, checkWin;
    private Timeline eating;
    private Stage stage;
    private static boolean isGameStopped;



    public GameHandler(Pane pane, GameState state, Stage stage) {
        this.stage = stage;
        this.pane = pane;
        gameState = state;
        isGameStopped = false;
        zombieHandler = new ZombieHandler(pane, gameState);
        plantHandler = new PlantHandler(pane, gameState);
        graphicalBullets = new ArrayList<>();
        graphicalSuns = new ArrayList<>();

        blockedImages = new ImageView[GameState.ROW_COUNT][GameState.COL_COUNT];

        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT; j++) {
                blockedImages[i][j] = null;
            }

        loadFogImages();

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            gameState.increaseSeconds();
            GameScreen.setTimerLabel(gameState.getSeconds() + " s");
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        drawFog();

        //zombieHandler.sendZombie();

        // in bayad 0.5s bemoone chon dar har dor ejra yek joon az giyah kam mikone
        eating = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            checkEatingPlant();
            checkEatingEachOther();
        }));
        eating.setCycleCount(Timeline.INDEFINITE);
        eating.play();


        checkLoop = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            shoot();
            checkBulletHit();
            checkDeadPlants();
            checkDeadZombies();
            checkPlantAct();
            checkExpiredSuns();
            if (checkLose()) {
                showLoseTab();
                stopAllTimelines();
            }
            checkBlockedCells();
            if (gameState.getCode() == GameState.GameCode.NIGHT)
                updateFogImages();
        }));
        checkLoop.setCycleCount(Timeline.INDEFINITE);
        checkLoop.play();

        if (gameState.getCode() == GameState.GameCode.DAY) {
            sunGenerator = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
                Sun sun = new Sun();
                handleSun(sun);

            }));
            sunGenerator.setCycleCount(Timeline.INDEFINITE);
            sunGenerator.play();
        }

        checkWin = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            if (gameState.getZombieList().isEmpty()) {
                //System.out.println("you won");
                stopAllTimelines();
            }
        }));
        checkWin.setCycleCount(Timeline.INDEFINITE);
        checkWin.setDelay(Duration.seconds(120));
        checkWin.play();

    }

    public GameState getGameState() {
        return gameState;
    }

    public PlantHandler getPlantHandler() {
        return plantHandler;
    }

    public ZombieHandler getZombieHandler() {
        return zombieHandler;
    }

    public static void handleSun(Sun sun) {
        if (isGameStopped) return;
        gameState.getSunList().add(sun);
        GraphicalSun graphicalSun = new GraphicalSun(sun);
        graphicalSuns.add(graphicalSun);
        pane.getChildren().add(graphicalSun.getSunImage());
        graphicalSun.getSunImage().setOnMouseClicked(event -> {
            pane.getChildren().remove(graphicalSun.getSunImage());
            graphicalSuns.remove(graphicalSun);
            gameState.getSunList().remove(graphicalSun.getSun());
            gameState.addPoint();
            GameScreen.setPointLabel(gameState.getSunPoint() + "");
        });
    }

    private void checkEatingPlant() {
        ArrayList<Zombie> zombies = gameState.getZombieList();

        for (Zombie zombie: zombies) {
            int row = zombie.getRow();
            int col = zombie.getCol();
            if (row < 0 || row >= GameState.ROW_COUNT || col < 0 || col >= GameState.COL_COUNT)
                continue;
            if (gameState.getField()[row][col].getPlant() != null && !zombie.isHypnotized()) {
                zombie.eating();
                gameState.getField()[row][col].getPlant().reduceHp();
            }
            else
                zombie.walking();
        }
    }

    private void checkEatingEachOther() {
        for (GraphicalZombie graphicalZombie: zombieHandler.getGraphicalZombies()) {
            if (graphicalZombie.getZombie().isHypnotized()) {
                GraphicalZombie z = graphicalZombie;

                for (GraphicalZombie zombie: zombieHandler.getGraphicalZombies()) {
                    if (!zombie.getZombie().isHypnotized() &&
                        zombie.getZombie().getRow() == z.getZombie().getRow()
                        && Math.abs(zombie.getZombie().getCenterX() - z.getZombie().getCenterX()) < 50) {
                        z.getZombie().eating();
                        zombie.getZombie().eating();
                        z.getZombie().reduceHp();
                        zombie.getZombie().reduceHp();
                    }
                    else {
                        z.getZombie().walking();
                        zombie.getZombie().walking();
                    }
                }
            }
        }
    }

    private boolean isZombieComing(int row, int col) {
        ArrayList<GraphicalZombie> zombies = zombieHandler.getGraphicalZombies();
        int zombieRow, zombieCol;
        for (GraphicalZombie zombie: zombies) {
            zombieRow = zombie.getZombie().getRow();
            zombieCol = zombie.getZombie().getCol();

            if (zombieRow == row && zombieCol >= col && !zombie.getZombie().isHypnotized())
                return true;
        }
        return false;
    }

    private boolean isZombieInRangeFour(int row, int col) {
        ArrayList<GraphicalZombie> zombies = zombieHandler.getGraphicalZombies();
        int zombieRow, zombieCol;
        for (GraphicalZombie zombie: zombies) {
            zombieRow = zombie.getZombie().getRow();
            zombieCol = zombie.getZombie().getCol();

            if (zombieRow == row && zombieCol >= col && zombieCol - col <= 4 && !zombie.getZombie().isHypnotized())
                return true;
        }
        return false;
    }

    private boolean isZombieAround(int row, int col) {
        ArrayList<GraphicalZombie> zombies = zombieHandler.getGraphicalZombies();
        int zombieRow, zombieCol;
        for (GraphicalZombie zombie: zombies) {
            zombieRow = zombie.getZombie().getRow();
            zombieCol = zombie.getZombie().getCol();

            if (Math.abs(zombieRow - row) <= 1 &&
                    Math.abs(zombieCol - col) <= 1
                    && !zombie.getZombie().isHypnotized())
                return true;
        }
        return false;
    }

    private void shoot() {
        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT; j++) {
                Plant plant = gameState.getField()[i][j].getPlant();
                if (plant != null) {
                    if (plant instanceof PeaPlant) {
                        if (isZombieComing(i, j)) {

                            plant.act();

                            ArrayList<Bullet> bullets = ((PeaPlant) plant).getBullets();
                            for (Bullet b : bullets) {
                                gameState.getBulletList().add(b);
                                GraphicalBullet gb = new GraphicalBullet(b);
                                graphicalBullets.add(gb);
                                pane.getChildren().add(gb.getBulletImage());
                                //pane.getChildren().add(gb.getDot());///////////
                            }
                            ((PeaPlant) plant).getBullets().clear();
                        } else
                            ((PeaPlant) plant).stopAct();
                    }
                    else if (plant instanceof Shroom &&
                            (gameState.getCode() == GameState.GameCode.NIGHT || !((Shroom) plant).isSleeping())) {
                        if (plant.getClass() == PuffShroom.class) {
                            if (isZombieInRangeFour(i, j)) {

                                plant.act();

                                ArrayList<Bullet> bullets = ((PuffShroom) plant).getBullets();
                                for (Bullet b : bullets) {
                                    gameState.getBulletList().add(b);
                                    GraphicalBullet gb = new GraphicalBullet(b);
                                    graphicalBullets.add(gb);
                                    pane.getChildren().add(gb.getBulletImage());
                                    //pane.getChildren().add(gb.getDot());///////////
                                }
                                ((PuffShroom) plant).getBullets().clear();
                            }
                            else
                                ((PuffShroom) plant).stopAct();
                        }
                        else if (plant.getClass() == ScaredyShroom.class) {
                            if (isZombieComing(i, j) && !isZombieAround(i, j)) {

                                plant.act();

                                ArrayList<Bullet> bullets = ((ScaredyShroom) plant).getBullets();
                                for (Bullet b : bullets) {
                                    gameState.getBulletList().add(b);
                                    GraphicalBullet gb = new GraphicalBullet(b);
                                    graphicalBullets.add(gb);
                                    pane.getChildren().add(gb.getBulletImage());
                                    //pane.getChildren().add(gb.getDot());///////////
                                }
                                ((ScaredyShroom) plant).getBullets().clear();
                            }
                            else
                                ((ScaredyShroom) plant).stopAct();
                        }


                    }
                }
            }
    }

    private void checkBulletHit() {
        ArrayList<GraphicalBullet> expiredBullets = new ArrayList<>();
        for (GraphicalBullet gb: graphicalBullets) {
            boolean sw = false; //baraye inke do ta zombie ro hadaf nagire
            for (GraphicalZombie gz : zombieHandler.getGraphicalZombies()) {
                if (!sw && gb.getBullet().getRow() == gz.getZombie().getRow()
                        && gb.getBullet().getCol() <= gz.getZombie().getCol()) {
                    if (Math.abs(gb.getBullet().getCenterX() - gz.getZombie().getCenterX()) <= 40 &&
                        !gz.getZombie().isHypnotized()) {
                        gz.getZombie().reduceHp();
                        if (gb.getBullet().getType() == Bullet.BulletType.FROZEN) {
                            gz.getZombie().slowDown();
                        }
                        expiredBullets.add(gb);
                        sw = true;
                    }
                }
            }
            if (!sw) {
                if (gb.getBullet().getCenterX() >= GameScreen.WIDTH)
                    expiredBullets.add(gb);
                else if (gb.getBullet().getType() == Bullet.BulletType.PUFF_SHROOM_BULLET
                        && (gb.getBullet().getCol() - gb.getBullet().getSourceCol()) > 4)
                    expiredBullets.add(gb);
            }
        }
        for (GraphicalBullet gb: expiredBullets) {
            gameState.getBulletList().remove(gb.getBullet());
            pane.getChildren().remove(gb.getBulletImage());
            //pane.getChildren().remove(gb.getDot());
        }
        graphicalBullets.removeAll(expiredBullets);
    }

    private void checkPlantAct() {
        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT; j++) {
                Plant plant = gameState.getField()[i][j].getPlant();
                if (plant instanceof Shroom &&
                (gameState.getCode() == GameState.GameCode.NIGHT || !((Shroom) plant).isSleeping())) {
                    if (plant instanceof IceShroom || plant instanceof DoomShroom)
                        plant.act();
                    else if (plant instanceof HypnoShroom) {
                        boolean sw = false;
                        for (GraphicalZombie z: zombieHandler.getGraphicalZombies()) {
                            if (z.getZombie().getRow() == plant.getRow() &&
                            z.getZombie().getCol() == plant.getCol()) {
                                ((HypnoShroom) plant).addZombie(z);
                                sw = true;
                            }
                        }
                        if (sw)
                            plant.act();
                    }
                }
                else if (plant instanceof Lantern)
                    plant.act();
            }
    }

    private void checkExpiredSuns() {
        ArrayList<GraphicalSun> expiredSuns = new ArrayList<>();
        for (GraphicalSun graphicalSun: graphicalSuns) {
            if (!graphicalSun.getSun().getIsAlive())
                expiredSuns.add(graphicalSun);
        }
        for (GraphicalSun graphicalSun: expiredSuns) {
            gameState.getSunList().remove(graphicalSun.getSun());
            pane.getChildren().remove(graphicalSun.getSunImage());
        }
        graphicalSuns.removeAll(expiredSuns);
    }

    private void checkDeadPlants() {
        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT; j++) {

                Plant plant = gameState.getField()[i][j].getPlant();
                if (plant != null && plant.getHp() <= 0) {
                    gameState.getField()[i][j].removePlant();
                    plantHandler.deletePlant(i, j);
                }

                plant = gameState.getCoffeeBeanList()[i][j];
                if (plant != null && plant.getHp() <= 0) {
                    gameState.getCoffeeBeanList()[i][j] = null;
                    plantHandler.clearCoffeeBean(i, j);
                }
            }
    }

    private void checkDeadZombies() {
        ArrayList<GraphicalZombie> deadZombies = new ArrayList<>();

        for (GraphicalZombie gz: zombieHandler.getGraphicalZombies())
            if (gz.getZombie().getHp() <= 0) {
                deadZombies.add(gz);
                gameState.getZombieList().remove(gz.getZombie());
                pane.getChildren().removeAll(gz.getZombieImage(), gz.getDot());
            }
        zombieHandler.getGraphicalZombies().removeAll(deadZombies);
    }


    private boolean checkLose() {
        for (GraphicalZombie graphicalZombie: zombieHandler.getGraphicalZombies()) {
            if (graphicalZombie.getZombie().getCenterX() < GameScreen.GRID_START_X) {
                //stopAllTimelines();
                return true;
            }
        }
        return false;
    }

    public void stopAllTimelines() {
        isGameStopped = true;
        timer.stop();
        if (sunGenerator != null) {
            sunGenerator.stop();
            sunGenerator.getKeyFrames().clear();
        }
        checkLoop.stop();
        eating.stop();
        zombieHandler.clear();

        for (GraphicalZombie graphicalZombie : zombieHandler.getGraphicalZombies()) {
            graphicalZombie.stop();
        }
        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT; j++) {
                if (plantHandler.getGraphicPlants()[i][j] != null)
                    plantHandler.getGraphicPlants()[i][j].stop();
                if (gameState.getField() != null) {
                    if (gameState.getField()[i][j].getPlant() != null)
                        gameState.getField()[i][j].getPlant().clear();
                    gameState.getField()[i][j].clearTimeline();
                }
            }

        for (GraphicalBullet bullet: graphicalBullets)
            bullet.stop();
        for (GraphicalSun sun: graphicalSuns) {
            sun.getSunImage().setMouseTransparent(true);
            sun.stop();
        }
        checkWin.stop();

        for (Item item: gameState.getStore())
            item.stopCharge();

        for (Node node : pane.getChildren()) {
            if (node.getUserData() instanceof GraphicalSun gs) {
                gs.getSunImage().setMouseTransparent(true);
                gs.stop();
            }
        }

        for (Node node : pane.getChildren()) {
            if (node.getUserData() instanceof GraphicalZombie gz) {
                gz.stop();
            }
        }
    }

    private void showLoseTab() {
        Rectangle darkOverlay = new Rectangle(0, 0, pane.getWidth(), pane.getHeight());
        darkOverlay.setFill(Color.BLACK);
        darkOverlay.setOpacity(0.6);

        Image loseMessageImage = new Image(getClass().getResource("/buttons/lose message.png").toExternalForm());
        ImageView loseMessageView = new ImageView(loseMessageImage);
        loseMessageView.setFitWidth(1400);
        loseMessageView.setFitHeight(650);
        loseMessageView.setLayoutX(50);
        loseMessageView.setLayoutY(50);

        Timeline gameOver = new Timeline(new KeyFrame(Duration.millis(1), ev -> {

            pane.getChildren().add(darkOverlay);
            pane.getChildren().add(loseMessageView);
        }),
//                new KeyFrame(Duration.seconds(1.5), ev -> {
//                    pane.getChildren().remove(loseMessageView);
//                }),
                new KeyFrame(Duration.seconds(1), ev -> {
                    pane.getChildren().remove(loseMessageView);
                    Image gameOverImage = new Image(getClass().getResource("/buttons/Game Over.png").toExternalForm());
                    ImageView gameOverView= new ImageView(gameOverImage);
                    gameOverView.setFitWidth(600);
                    gameOverView.setFitHeight(400);
                    gameOverView.setLayoutX(450);
                    gameOverView.setLayoutY(175);

                    Rectangle tryAgainBtn = new Rectangle(510 , 485 , 225 , 60);
                    tryAgainBtn.setStroke(Color.BLACK);
                    tryAgainBtn.setFill(Color.TRANSPARENT);
                    tryAgainBtn.setOnMouseClicked(event -> {
                        //clear();
                        ItemSelectionScreen itemSelection = new ItemSelectionScreen(stage, gameState.getCode());
                    });
                    tryAgainBtn.setOnMouseEntered(event -> {
                        tryAgainBtn.setFill(Color.rgb(200, 200, 200, 0.1));
                    });
                    tryAgainBtn.setOnMouseExited(event -> {
                        tryAgainBtn.setFill(Color.TRANSPARENT);
                    });

                    Rectangle mainMenuBtn = new Rectangle(760 ,485  , 225 , 60);
                    mainMenuBtn.setStroke(Color.BLACK);
                    mainMenuBtn.setFill(Color.TRANSPARENT);
                    mainMenuBtn.setOnMouseClicked(event -> {
                        Menu menu = new Menu(stage);
                    });
                    mainMenuBtn.setOnMouseEntered(event -> {
                        mainMenuBtn.setFill(Color.rgb(200, 200, 200, 0.1));
                    });
                    mainMenuBtn.setOnMouseExited(event -> {
                        mainMenuBtn.setFill(Color.TRANSPARENT);
                    });

                    pane.getChildren().addAll(gameOverView, tryAgainBtn, mainMenuBtn);



                }));


        gameOver.setCycleCount(1);
        gameOver.play();
    }


    private void checkBlockedCells() {
        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT; j++) {
                if (gameState.getField()[i][j].getCellContent() == Cell.Content.BLOCKED &&
                blockedImages[i][j] == null) {
                    Image image = new Image(getClass().getResource("/plants/doom shroom effect.png").toExternalForm());
                    blockedImages[i][j] = new ImageView(image);
                    blockedImages[i][j].setFitWidth(GameScreen.CELL_WIDTH - 20);
                    blockedImages[i][j].setFitHeight(GameScreen.CELL_HEIGHT - 20);
                    double x = GameScreen.GRID_START_X + j * GameScreen.CELL_WIDTH + 10;
                    double y = GameScreen.GRID_START_Y + i * GameScreen.CELL_HEIGHT + 10;
                    blockedImages[i][j].setLayoutX(x);
                    blockedImages[i][j].setLayoutY(y);

                    pane.getChildren().add(blockedImages[i][j]);
                }
                if (gameState.getField()[i][j].getCellContent() != Cell.Content.BLOCKED &&
                blockedImages[i][j] != null) {
                    pane.getChildren().remove(blockedImages[i][j]);
                    blockedImages[i][j] = null;
                }
            }
    }

    private void loadFogImages() {
        fogImages = new ImageView[GameState.ROW_COUNT][GameState.COL_COUNT + 1];
        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT + 1; j++)
                fogImages[i][j] = null;

        String address = "/Screen Images/fog/";
        for (int i = 0; i < GameState.ROW_COUNT; i++) {
            for (int j = 0; j < GameState.COL_COUNT; j++) {
                Cell cell = gameState.getField()[i][j];
                if (cell.getHasFog()) {
                    Image image = new Image(getClass().getResource(address + (j - 6) + ".png").toExternalForm());
                    double x = GameScreen.GRID_START_X + j * GameScreen.CELL_WIDTH;
                    double y = GameScreen.GRID_START_Y + i * GameScreen.CELL_HEIGHT;
                    ImageView fogImage = new ImageView(image);
                    fogImage.setFitWidth(GameScreen.CELL_WIDTH);
                    fogImage.setFitHeight(GameScreen.CELL_HEIGHT);
                    fogImage.setLayoutX(x);
                    fogImage.setLayoutY(y);
                    fogImage.setMouseTransparent(true);
                    fogImages[i][j] = fogImage;

                }
            }
            if (gameState.getField()[i][GameState.COL_COUNT - 1].getHasFog()) {
                int j = GameState.COL_COUNT;
                double x = GameScreen.GRID_START_X + j * GameScreen.CELL_WIDTH;
                double y = GameScreen.GRID_START_Y + i * GameScreen.CELL_HEIGHT;
                Image image = new Image(getClass().getResource(address + (j - 6) + ".png").toExternalForm());
                ImageView fogImage = new ImageView(image);
                fogImage.setFitWidth(GameScreen.CELL_WIDTH);
                fogImage.setFitHeight(GameScreen.CELL_HEIGHT);
                fogImage.setLayoutX(x);
                fogImage.setLayoutY(y);
                fogImage.setMouseTransparent(true);
                fogImages[i][j] = fogImage;
            }
        }
    }

    private void drawFog() {
        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT; j++) {
                Cell cell = gameState.getField()[i][j];
                if (cell.getHasFog() && !cell.isLanternEffect() && !cell.isFanEffect()) {
                    pane.getChildren().add(fogImages[i][j]);
                    if (j == GameState.COL_COUNT - 1)
                        pane.getChildren().add(fogImages[i][j + 1]);
                }
            }
    }

    public static void updateFogImages() {
        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT; j++) {
                Cell cell = gameState.getField()[i][j];
                if (cell.getHasFog()) {
                    if (!cell.isLanternEffect() && !cell.isFanEffect()) {
                        pane.getChildren().remove(GameHandler.fogImages[i][j]);
                        pane.getChildren().add(GameHandler.fogImages[i][j]);
                        if (j == GameState.COL_COUNT - 1) {
                            pane.getChildren().remove(GameHandler.fogImages[i][j + 1]);
                            pane.getChildren().add(GameHandler.fogImages[i][j + 1]);
                        }
                    }
                    else {
                        pane.getChildren().remove(GameHandler.fogImages[i][j]);
                        if (j == GameState.COL_COUNT - 1)
                            pane.getChildren().remove(GameHandler.fogImages[i][j + 1]);
                    }
                }
            }
    }

    public static boolean isGameStopped() {
        return isGameStopped;
    }

    public ArrayList<GraphicalBullet> getGraphicalBullets() {
        return graphicalBullets;
    }
}
