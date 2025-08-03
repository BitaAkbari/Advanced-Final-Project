package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class ZombieHandler {
    private ArrayList<GraphicalZombie> graphicalZombies;
    private Pane pane;
    private GameState gameState;
    private long seed;
    private Random numGenerator;
    private Timeline level1, otherLevels, midAttack, finalAttack;
    private Timeline first, second, inner;


    public ZombieHandler(Pane pane, GameState gameState) {
        this.pane = pane;
        this.gameState = gameState;
        graphicalZombies = new ArrayList<GraphicalZombie>();

        seed = System.currentTimeMillis();
        numGenerator = new Random(seed);
    }


    public void sendZombie() {


        level1 = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            int row = randomRow();
            buildZombie(Zombie.ZombieType.ZOMBIE, row);
        }));
        level1.setCycleCount(5);
        level1.play();

        int[] level = {2};
        otherLevels = new Timeline(new KeyFrame(Duration.seconds(15), e -> { // 15,30,45
            int optionCount = level[0];

            first = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                Zombie.ZombieType type = randomType(optionCount);
                int row = randomRow();
                buildZombie(type, row);

                if (optionCount == 4) {
                    inner = new Timeline(new KeyFrame(Duration.millis(200), event -> {
                        Zombie.ZombieType t = randomType(optionCount);
                        int r = randomRow();
                        buildZombie(t, r);
                    }));
                    inner.setCycleCount(1);
                    inner.play();
                }
            }));
            first.setCycleCount(1);
            first.play();

            second = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
                Zombie.ZombieType type = randomType(optionCount);
                int row = randomRow();
                buildZombie(type, row);

                if (optionCount == 4) {
                    Timeline delay = new Timeline(new KeyFrame(Duration.millis(200), event -> {
                        Zombie.ZombieType t = randomType(optionCount);
                        int r = randomRow();
                        buildZombie(t, r);
                    }));
                    delay.setCycleCount(1);
                    delay.play();
                }

            }));
            second.setCycleCount(7);
            second.setDelay(Duration.seconds(1));
            second.play();

            level[0] ++;
        }));
        otherLevels.setCycleCount(3);
        otherLevels.play();


        midAttack = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            for (int i = 0; i < GameState.ROW_COUNT; i++) {
                final int row = i;
                //Timeline delay = new Timeline(new KeyFrame(Duration.millis(200), event -> {
                    Zombie.ZombieType type = randomType(2);
                    buildZombie(type, row);
                //}));
                //delay.setCycleCount(2);
                //delay.play();

            }
        }));
        midAttack.setCycleCount(/*7*/3);
        midAttack.setDelay(Duration.seconds(25));
        midAttack.play();

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
        }));
        finalAttack.setCycleCount(/*13*/5);
        finalAttack.setDelay(Duration.seconds(46));
        finalAttack.play();
    }

    private Zombie.ZombieType randomType(int optionCount) {
        Random indexGenerator = new Random();
        int index = indexGenerator.nextInt(optionCount);
        //System.out.println("optionCount = " + optionCount + ", index = " + index);
        return Zombie.ZombieType.values()[index];
    }

    private int randomRow() {
        return numGenerator.nextInt(GameState.ROW_COUNT);
    }



    private void buildZombie(Zombie.ZombieType type, int row) {
        Zombie zombie = new Zombie(type, row);
        gameState.getZombieList().add(zombie);
        GraphicalZombie graphicalZombie = new GraphicalZombie(zombie);
        graphicalZombies.add(graphicalZombie);
        pane.getChildren().add(graphicalZombie.getZombieImage());
        pane.getChildren().add(graphicalZombie.getDot());
    }

    public ArrayList<GraphicalZombie> getGraphicalZombies() {
        return graphicalZombies;
    }

    public void updateImages() { //baraye inke akse zombie ha hamishe rooye akse giyah biyofte
        for (GraphicalZombie zombie: graphicalZombies) {
            pane.getChildren().remove(zombie.getZombieImage());
            pane.getChildren().remove(zombie.getDot());
            pane.getChildren().add(zombie.getZombieImage());
            pane.getChildren().add(zombie.getDot());
        }
    }

    public void clear() {
        stopTimeline(level1);
        stopTimeline(otherLevels);
        stopTimeline(midAttack);
        stopTimeline(finalAttack);
        stopTimeline(first);
        stopTimeline(second);
        stopTimeline(inner);
    }

    private void stopTimeline(Timeline timeline) {
        if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING)
            timeline.stop();
    }
}
