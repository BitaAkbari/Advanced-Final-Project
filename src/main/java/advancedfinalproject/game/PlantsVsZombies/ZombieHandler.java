package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ZombieHandler {

    private static ArrayList<GraphicalZombie> graphicalZombies;
    private Pane pane;
    private GameState gameState;
    private long seed;
    private Random numGenerator;
    private int time;
    private Timeline midAttack, finalAttack, nightAttack;
    private Timeline preLevel, inner;
    private Timeline level1, level2, level3, level4;
    private int level1_CycleCount, level2_CycleCount, level3_CycleCount, level4_CycleCount;
    private int midAttckCycleCount, finalAttackCycleCount;
    private final int LEVEL1_START_TIME = 0;
    private final int LEVEL2_START_TIME = 28;
    private final int LEVEL3_START_TIME = 58;
    private final int LEVEL4_START_TIME = 88;
    private ArrayList<Integer> randomOrder;
    private int index;


    public ZombieHandler(Pane pane, GameState gameState) {
        this.pane = pane;
        this.gameState = gameState;
        time = gameState.getSeconds();
        graphicalZombies = new ArrayList<GraphicalZombie>();
        randomOrder = new ArrayList<>();
        index = 0;
        seed = System.currentTimeMillis();
        numGenerator = new Random(seed);
        level1_CycleCount = 0;
        level2_CycleCount = 0;
        level3_CycleCount = 0;
        level4_CycleCount = 0;
        midAttckCycleCount = 0;
        finalAttackCycleCount = 0;
    }

    public void sendZombie() {

        if (time <= 30) {
            int diff = 6 - ((time - LEVEL1_START_TIME) % 6);
            if (time > LEVEL1_START_TIME) {
                preLevel = new Timeline(new KeyFrame(Duration.seconds(diff), e -> {
                    Zombie.ZombieType type = randomType(1);
                    int row = randomRow();
                    buildZombie(type, row);
                    level1_CycleCount ++;
                }));
                preLevel.setCycleCount(1);
                preLevel.play();
            }
            level1 = new Timeline(new KeyFrame(Duration.seconds(/*3*/6), event -> {
                int row = randomRow();
                buildZombie(Zombie.ZombieType.ZOMBIE, row);
                level1_CycleCount ++;
            }));
            level1.setCycleCount(5 - level1_CycleCount - 1);
            if (time > LEVEL1_START_TIME)
                level1.setDelay(Duration.seconds(diff));
            level1.play();
        }

        if (time <= 60) {
            int diff = 4 - ((time - LEVEL2_START_TIME) % 4);
            if (time > LEVEL2_START_TIME) {
                preLevel = new Timeline(new KeyFrame(Duration.seconds(diff), e -> {
                    Zombie.ZombieType type = randomType(2);
                    int row = randomRow();
                    buildZombie(type, row);
                    level2_CycleCount ++;
                }));
                preLevel.setCycleCount(1);
                preLevel.play();
            }
            level2 = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
                Zombie.ZombieType type = randomType(2);
                int row = randomRow();
                buildZombie(type, row);
                level2_CycleCount ++;
            }));
            level2.setCycleCount(8 - level2_CycleCount - 1);
            if (time <= LEVEL2_START_TIME)
                level2.setDelay(Duration.seconds(LEVEL2_START_TIME - time));
            else
                level2.setDelay(Duration.seconds(diff));
            level2.play();
        }

        if (time <= 90) {
            int diff = 4 - ((time - LEVEL3_START_TIME) % 4);
            if (time > LEVEL3_START_TIME) {
                preLevel = new Timeline(new KeyFrame(Duration.seconds(diff), e -> {
                    Zombie.ZombieType type = randomType(3);
                    int row = randomRow();
                    buildZombie(type, row);
                    level3_CycleCount ++;
                }));
                preLevel.setCycleCount(1);
                preLevel.play();
            }
            level3 = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
                Zombie.ZombieType type = randomType(3);
                int row = randomRow();
                buildZombie(type, row);
                level3_CycleCount ++;
            }));
            level3.setCycleCount(8 - level3_CycleCount - 1);
            if (time <= LEVEL3_START_TIME)
                level3.setDelay(Duration.seconds(LEVEL3_START_TIME - time));
            else
                level3.setDelay(Duration.seconds(diff));
            level3.play();
        }

        if (time <= 120) {
            int diff = 4 - ((time - LEVEL4_START_TIME) % 4);
            if (time > LEVEL4_START_TIME) {
                preLevel = new Timeline(new KeyFrame(Duration.seconds(diff), e -> {
                    Zombie.ZombieType type = randomType(4);
                    int row = randomRow();
                    buildZombie(type, row);
                    level4_CycleCount ++;

                    inner = new Timeline(new KeyFrame(Duration.millis(200), event -> {
                        Zombie.ZombieType t = randomType(4);
                        int r = randomRow();
                        buildZombie(t, r);
                    }));
                    inner.setCycleCount(1);
                    inner.play();
                }));
                preLevel.setCycleCount(1);
                preLevel.play();
            }
            level4 = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
                Zombie.ZombieType type = randomType(4);
                int row = randomRow();
                buildZombie(type, row);
                level4_CycleCount ++;

                inner = new Timeline(new KeyFrame(Duration.millis(200), event -> {
                    Zombie.ZombieType t = randomType(4);
                    int r = randomRow();
                    buildZombie(t, r);
                }));
                inner.setCycleCount(1);
                inner.play();
            }));
            level4.setCycleCount(8 - level4_CycleCount - 1);
            if (time <= LEVEL4_START_TIME)
                level4.setDelay(Duration.seconds(LEVEL4_START_TIME - time));
            else
                level4.setDelay(Duration.seconds(diff));
            level4.play();
        }

        if (time <= 63) {
            midAttack = new Timeline(new KeyFrame(Duration.seconds(/*1*/2), e -> {
                for (int i = 0; i < GameState.ROW_COUNT; i++) {
                    final int row = i;
                    //Timeline delay = new Timeline(new KeyFrame(Duration.millis(200), event -> {
                    Zombie.ZombieType type = randomType(2);
                    buildZombie(type, row);
                    //}));
                    //delay.setCycleCount(2);
                    //delay.play();

                }
                midAttckCycleCount ++;
            }));
            midAttack.setCycleCount(/*7*/4 - midAttckCycleCount);
            if (time <= 55)
                midAttack.setDelay(Duration.seconds(/*25*/55 - time));
            midAttack.play();
        }

        if (time <= 110) {
            if (gameState.getCode() == GameState.GameCode.NIGHT) {
                nightAttack = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                    nightAttack();
                }));
                nightAttack.setCycleCount(1);
                if (time <= 109)
                    nightAttack.setDelay(Duration.seconds(109 - time));
                nightAttack.play();
            }
        }

        if (time <= 120) {
            finalAttack = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                for (int i = 0; i < GameState.ROW_COUNT; i++) {
                    final int row = i;
                    //Timeline delay = new Timeline(new KeyFrame(Duration.millis(200), event -> {
                    Zombie.ZombieType type = randomType(4);
                    buildZombie(type, row);
                    //}));
                    //delay.setCycleCount(3);
                    //delay.play();
                }
                finalAttackCycleCount ++;
            }));
            finalAttack.setCycleCount(/*13*/10 - finalAttackCycleCount);
            if (time <= 109)
                finalAttack.setDelay(Duration.seconds(/*46*/109 - time));
            finalAttack.play();
        }
    }

    private Zombie.ZombieType randomType(int optionCount) {
        Random indexGenerator = new Random();
        int index = indexGenerator.nextInt(optionCount);
        return Zombie.ZombieType.values()[index];
    }

    private int randomRow() {
        //return numGenerator.nextInt(GameState.ROW_COUNT);
        if (randomOrder.size() == 0 || index > 4)
            shuffle();
        int num = randomOrder.get(index);
        index++;
        return num;
    }

    private void shuffle() {
        randomOrder.clear();
        for (int i = 0; i < GameState.ROW_COUNT; i++)
            randomOrder.add(i);
        Collections.shuffle(randomOrder);
        index = 0;
    }

    private void nightAttack() {
        for (int i = 0; i < GameState.ROW_COUNT; i++)
            for (int j = 0; j < GameState.COL_COUNT; j++)
                if (gameState.getField()[i][j].getCellContent() == Cell.Content.GRAVE) {
                    Zombie zombie = new Zombie(Zombie.ZombieType.ZOMBIE, i, j);
                    gameState.getZombieList().add(zombie);
                    GraphicalZombie graphicalZombie = new GraphicalZombie(zombie);
                    graphicalZombies.add(graphicalZombie);
                    pane.getChildren().add(graphicalZombie.getZombieImage());
//                    pane.getChildren().add(graphicalZombie.getDot());//////////////////////////
                }
    }



    private void buildZombie(Zombie.ZombieType type, int row) {
        Zombie zombie = new Zombie(type, row);
        gameState.getZombieList().add(zombie);
        GraphicalZombie graphicalZombie = new GraphicalZombie(zombie);
        graphicalZombies.add(graphicalZombie);
        pane.getChildren().add(graphicalZombie.getZombieImage());
//        pane.getChildren().add(graphicalZombie.getDot());//////////////////////
        GameHandler.updateFogImages();
    }

    public static ArrayList<GraphicalZombie> getGraphicalZombies() {
        return graphicalZombies;
    }

    public int getLevel1_CycleCount() {
        return level1_CycleCount;
    }

    public void setLevel1_CycleCount(int level1_CycleCount) {
        this.level1_CycleCount = level1_CycleCount;
    }

    public int getLevel2_CycleCount() {
        return level2_CycleCount;
    }

    public void setLevel2_CycleCount(int level2_CycleCount) {
        this.level2_CycleCount = level2_CycleCount;
    }

    public int getLevel3_CycleCount() {
        return level3_CycleCount;
    }

    public void setLevel3_CycleCount(int level3_CycleCount) {
        this.level3_CycleCount = level3_CycleCount;
    }

    public int getLevel4_CycleCount() {
        return level4_CycleCount;
    }

    public void setLevel4_CycleCount(int level4_CycleCount) {
        this.level4_CycleCount = level4_CycleCount;
    }

    public int getMidAttckCycleCount() {
        return midAttckCycleCount;
    }

    public void setMidAttckCycleCount(int midAttckCycleCount) {
        this.midAttckCycleCount = midAttckCycleCount;
    }

    public int getFinalAttackCycleCount() {
        return finalAttackCycleCount;
    }

    public void setFinalAttackCycleCount(int finalAttackCycleCount) {
        this.finalAttackCycleCount = finalAttackCycleCount;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<Integer> getRandomOrder() {
        return randomOrder;
    }

    public void updateImages() { //baraye inke akse zombie ha hamishe rooye akse giyah biyofte
        for (GraphicalZombie zombie: graphicalZombies) {
            pane.getChildren().remove(zombie.getZombieImage());
//            pane.getChildren().remove(zombie.getDot());//////////////
            pane.getChildren().add(zombie.getZombieImage());
//            pane.getChildren().add(zombie.getDot());/////////////
            GameHandler.updateFogImages();
        }
    }

    public void clear() {
        stopTimeline(level1);
        stopTimeline(level2);
        stopTimeline(level3);
        stopTimeline(level4);
        stopTimeline(preLevel);
        stopTimeline(inner);
        stopTimeline(midAttack);
        stopTimeline(nightAttack);
        stopTimeline(finalAttack);
    }

    private void stopTimeline(Timeline timeline) {
        if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING)
            timeline.stop();
    }
}
