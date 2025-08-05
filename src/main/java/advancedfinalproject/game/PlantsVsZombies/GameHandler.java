package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameHandler {
    private static GameState gameState;
    private ZombieHandler zombieHandler;
    private PlantHandler plantHandler;
    private ArrayList<GraphicalBullet> graphicalBullets;
    private static ArrayList<GraphicalSun> graphicalSuns;
    private static Pane pane;
    private Timeline timer, checkLoop, sunGenerator;
    private Timeline eating;



    public GameHandler(Pane pane, GameState state) {

        this.pane = pane;
        gameState = state;
        zombieHandler = new ZombieHandler(pane, gameState);
        plantHandler = new PlantHandler(pane, gameState);
        graphicalBullets = new ArrayList<>();
        graphicalSuns = new ArrayList<>();

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            gameState.setSeconds();
            GameScreen.setTimerLabel(gameState.getSeconds() + " s");
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        zombieHandler.sendZombie();

        // in bayad 0.5s bemoone chon dar har dor ejra yek joon az giyah kam mikone
        eating = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            checkEatingPlant();
        }));
        eating.setCycleCount(Timeline.INDEFINITE);
        eating.play();


        checkLoop = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            shoot();
            checkBulletHit();
            checkDeadPlants();
            checkDeadZombies();
            checkExpiredSuns();
            checkLose();
        }));
        checkLoop.setCycleCount(Timeline.INDEFINITE);
        checkLoop.play();

        sunGenerator = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
            Sun sun = new Sun();
            handleSun(sun);

        }));
        sunGenerator.setCycleCount(Timeline.INDEFINITE);
        sunGenerator.play();

        Timeline checkWin = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            if (gameState.getZombieList().isEmpty()) {
                //System.out.println("you won");
                stopAllTimelines();
            }
        }));
        checkWin.setCycleCount(Timeline.INDEFINITE);
        checkWin.setDelay(Duration.seconds(60));
        checkWin.play();

    }

    public PlantHandler getPlantHandler() {
        return plantHandler;
    }

    public ZombieHandler getZombieHandler() {
        return zombieHandler;
    }

    public static void handleSun(Sun sun) {
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
            if (gameState.getField()[row][col].getPlant() != null) {
                zombie.eating();
                gameState.getField()[row][col].getPlant().reduceHp();
            }
            else
                zombie.walking();
        }
    }

    private boolean isZombieComing(int row, int col) {
        ArrayList<GraphicalZombie> zombies = zombieHandler.getGraphicalZombies();
        int zombieRow, zombieCol;
        for (GraphicalZombie zombie: zombies) {
            zombieRow = zombie.getZombie().getRow();
            zombieCol = zombie.getZombie().getCol();

            if (zombieRow == row && zombieCol >= col)
                return true;
        }
        return false;
    }

    private void shoot() {
        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT; j++)
                if (gameState.getField()[i][j].getPlant() instanceof PeaPlant) {
                    if (isZombieComing(i, j)) {

                        gameState.getField()[i][j].getPlant().act();

                        ArrayList<Bullet> bullets = ((PeaPlant) gameState.getField()[i][j].getPlant()).getBullets();
                        for (Bullet b : bullets) {
                            gameState.getBulletList().add(b);
                            GraphicalBullet gb = new GraphicalBullet(b);
                            graphicalBullets.add(gb);
                            pane.getChildren().add(gb.getBulletImage());
                            //pane.getChildren().add(gb.getDot());///////////
                        }
                        ((PeaPlant) gameState.getField()[i][j].getPlant()).getBullets().clear();
                    }
                    else
                        ((PeaPlant) gameState.getField()[i][j].getPlant()).stopAct();
                }
    }

    private void checkBulletHit() {
        ArrayList<GraphicalBullet> expiredBullets = new ArrayList<>();
        for (GraphicalBullet gb: graphicalBullets) {
            boolean sw = false; //baraye inke do ta zombie ro hadaf nagire
            for (GraphicalZombie gz : zombieHandler.getGraphicalZombies()) {
                if (!sw && gb.getBullet().getRow() == gz.getZombie().getRow()
                        && gb.getBullet().getCol() <= gz.getZombie().getCol()) {
                    if (Math.abs(gb.getBullet().getCenterX() - gz.getZombie().getCenterX()) <= 30) {
                        gz.getZombie().reduceHp();
                        if (gb.getBullet().getType() == Bullet.BulletType.FROZEN) {
                            gz.getZombie().slowDown();
                        }
                        expiredBullets.add(gb);
                        sw = true;
                    }
                }
            }
        }
        for (GraphicalBullet gb: expiredBullets) {
            gameState.getBulletList().remove(gb.getBullet());
            pane.getChildren().remove(gb.getBulletImage());
            //pane.getChildren().remove(gb.getDot());
        }
        graphicalBullets.removeAll(expiredBullets);
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
            for (int j = 0; j < GameState.COL_COUNT; j++)
                if (gameState.getField()[i][j].getPlant() != null && gameState.getField()[i][j].getPlant().getHp() <= 0) {
                    gameState.getField()[i][j].removePlant();
                    plantHandler.deletePlant(i, j);
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
                stopAllTimelines();
                return true;
            }
        }
        return false;
    }

    private void stopAllTimelines() {
        timer.stop();
        sunGenerator.stop();
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
                if (gameState.getField()[i][j].getPlant() != null)
                    gameState.getField()[i][j].getPlant().clear();
            }
        for (GraphicalBullet bullet: graphicalBullets)
            bullet.stop();
        for (GraphicalSun sun: graphicalSuns) {
            sun.getSunImage().setMouseTransparent(true);
            sun.stop();
        }

        for (Node node : pane.getChildren()) {
            if (node.getUserData() instanceof GraphicalSun gs) {
                gs.stop();
            }
        }

    }
}
