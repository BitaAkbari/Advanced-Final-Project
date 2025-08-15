package advancedfinalproject.game.PlantsVsZombies;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Load {
    private String fileName;
    private FileInputStream inFile;
    private DataInputStream input;
    private GameState.GameCode code;
    private Plant.PlantName[] itemNames;
    private GameHandler gameHandler;
    private GameState gameState;
    private ArrayList<Sun> suns;
    private int time;
    private Pane pane;

    public Load(GameState.GameCode code) {
        this.code = code;
        if (code == GameState.GameCode.DAY)
            fileName = "dayFile.dat";
        else
            fileName = "nightFile.dat";

        try {
            inFile = new FileInputStream(fileName);
            input = new DataInputStream(inFile);
        }
        catch (Exception e) {

        }
    }

    public void uploadData(Stage stage, Pane pane) {
        this.pane = pane;
        suns = new ArrayList<>();

        try {
            itemNames = new Plant.PlantName[GameState.ITEM_COUNT];
            for (int i = 0; i < GameState.ITEM_COUNT; i++) {
                String name = input.readUTF();
                itemNames[i] = Plant.PlantName.valueOf(name);
            }
            gameState = new GameState(code, itemNames);

            for (int i = 0; i < GameState.ITEM_COUNT; i++) {
                int remainingTime = input.readInt();
                gameState.getStore()[i].setRemainingTime(remainingTime);
                if (remainingTime != 0)
                    gameState.getStore()[i].charge();
            }

            int score = input.readInt();
            gameState.setSunPoint(score);
            time = input.readInt();
            gameState.setSeconds(time);

            gameHandler = new GameHandler(pane, gameState, stage);

            Cell[][] field = new Cell[GameState.ROW_COUNT][GameState.COL_COUNT];
            for (int i = 0; i < GameState.ROW_COUNT; i++) {
                for (int j = 0; j < GameState.COL_COUNT; j++) {
                    Cell cell = new Cell(i, j);
                    String content = input.readUTF();
                    cell.setCellContent(Cell.Content.valueOf(content));
                    cell.setHasFog(input.readBoolean());
                    cell.setLanternEffect(input.readBoolean());
                    cell.setFanEffect(input.readBoolean());
                    int remainingSecondsForFog = input.readInt();
                    if (remainingSecondsForFog != 0) {
                        cell.setRemainingSeconds(remainingSecondsForFog);
                        cell.removeFogByFan();
                    }

                    String plantName = input.readUTF();
                    if (!plantName.equals("NULL")) {
                        Plant plant = gameState.itemToPlant(Plant.PlantName.valueOf(plantName), i, j);
                        plant.setHp(input.readInt());
                        if (plant instanceof Shroom)
                            ((Shroom) plant).setSleeping(input.readBoolean());

                        if (plant instanceof BombPlant || plant instanceof Sunflower
                            || plant instanceof Lantern || plant instanceof Fan)
                            plant.act();
                        cell.setPlant(plant);
                    }
                    field[i][j] = cell;
                }
            }
            gameState.setField(field);

            int zombieCount = input.readInt();
            for (int i = 0; i < zombieCount; i++) {
                String type = input.readUTF();
                int row = input.readInt();
                int col = input.readInt();
                Zombie zombie = new Zombie(Zombie.ZombieType.valueOf(type), row, col);
                zombie.setCenterX(input.readDouble());
                zombie.setCurrSpeed(input.readDouble());
                zombie.setHp(input.readInt());
                zombie.setIsSlowed(input.readBoolean());
                zombie.setIsHypnotized(input.readBoolean());
                gameState.getZombieList().add(zombie);
            }

            int cycleCount = input.readInt();
            if (time <= 30) {
                gameHandler.getZombieHandler().setLevel1_CycleCount(cycleCount);
            }
            else if (time <= 60) {
                gameHandler.getZombieHandler().setLevel2_CycleCount(cycleCount);
            }
            else if (time <= 90) {
                gameHandler.getZombieHandler().setLevel3_CycleCount(cycleCount);
            }
            else if (time <= 120) {
                gameHandler.getZombieHandler().setLevel4_CycleCount(cycleCount);
            }
            gameHandler.getZombieHandler().setMidAttckCycleCount(input.readInt());
            gameHandler.getZombieHandler().setFinalAttackCycleCount(input.readInt());

            gameHandler.getZombieHandler().setIndex(input.readInt());
            for (int i = 0; i < GameState.ROW_COUNT; i++)
                gameHandler.getZombieHandler().getRandomOrder().add(i, input.readInt());

            gameHandler.getZombieHandler().sendZombie();

            int sunCount = input.readInt();
            for (int i = 0; i < sunCount; i++) {
                double x = input.readDouble();
                double y = input.readDouble();
                double realY = input.readDouble();
                Sun sun;
                if (y >= realY) {
                    sun = new Sun(x, y);
                }
                else {
                    sun = new Sun();
                    sun.setX(x);
                    sun.setY(y);
                    sun.setRealY(realY);
                }
                suns.add(sun);
            }

            int bulletCount = input.readInt();
            for (int i = 0; i < bulletCount; i++) {
                String type = input.readUTF();
                int sourceRow = input.readInt();
                int sourceCol = input.readInt();
                Bullet bullet = new Bullet(Bullet.BulletType.valueOf(type), sourceRow, sourceCol);
                double x = input.readDouble();
                double y = input.readDouble();
                bullet.setCenterX(x);
                bullet.setCenterY(y);
                gameState.getBulletList().add(bullet);
            }

        } catch (Exception e) {

        }
        clear();
    }

    public void uploadGraphic() {
        for (int i = 0; i < GameState.ROW_COUNT; i++) {
            for (int j = 0; j < GameState.COL_COUNT; j++) {
                System.out.print(i + "," + j + " " + gameState.getField()[i][j].getCellContent() + "plant:");
                if (gameState.getField()[i][j].getPlant() == null)
                    System.out.println("null");
                else
                    System.out.println(gameState.getField()[i][j].getPlant().getType());
                if (gameState.getField()[i][j].getPlant() != null)
                    gameHandler.getPlantHandler().addPlant(i, j);

            }
        }

        for (Zombie zombie: gameState.getZombieList()) {
            GraphicalZombie graphicalZombie = new GraphicalZombie(zombie);
            if (zombie.isHypnotized())
                graphicalZombie.getZombieImage().setScaleX(-1);
            gameHandler.getZombieHandler().getGraphicalZombies().add(graphicalZombie);
            pane.getChildren().add(graphicalZombie.getZombieImage());
            pane.getChildren().add(graphicalZombie.getDot());/////////////////
        }

        for (Sun sun: suns) {
            gameHandler.handleSun(sun);
        }

        for (Bullet bullet: gameState.getBulletList()) {
            GraphicalBullet graphicalBullet = new GraphicalBullet(bullet);
            gameHandler.getGraphicalBullets().add(graphicalBullet);
            pane.getChildren().add(graphicalBullet.getBulletImage());
        }

        gameHandler.updateFogImages();
    }

    public boolean isEmpty() {
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0)
            return true;
        else
            return false;
    }

    private void clear() {
        try {
            FileOutputStream outFile = new FileOutputStream(fileName);
            DataOutputStream output = new DataOutputStream(outFile);
        }
        catch (Exception e) {

        }
    }

    public GameState.GameCode getCode() {
        return code;
    }

    public Plant.PlantName[] getItemNames() {
        return itemNames;
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }
}
