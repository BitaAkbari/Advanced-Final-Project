package advancedfinalproject.game.PlantsVsZombies;

import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class Save {
    private String fileName;
    private FileOutputStream outFile;
    private DataOutputStream output;
    private GameHandler gameHandler;
    private GameState gameState;

    public Save(GameHandler gameHandler) {

        this.gameHandler = gameHandler;
        gameState = gameHandler.getGameState();

        if (gameState.getCode() == GameState.GameCode.DAY)
            fileName = "dayFile.dat";
        else
            fileName = "nightFile.dat";

        try {
            outFile = new FileOutputStream(fileName);
            output = new DataOutputStream(outFile);

            for (int i = 0; i < GameState.ITEM_COUNT; i++) {
                Item item = gameState.getStore()[i];
                output.writeUTF(item.getName().name());
            }
            for (int i = 0; i < GameState.ITEM_COUNT; i++) {
                output.writeInt(gameState.getStore()[i].getRemainingTime());
            }
            output.writeInt(gameState.getSunPoint());
            output.writeInt(gameState.getSeconds());

            for (int i = 0; i < GameState.ROW_COUNT; i++) {
                for (int j = 0; j < GameState.COL_COUNT; j++) {

                    Cell cell = gameState.getField()[i][j];
                    output.writeUTF(cell.getCellContent().name());
                    output.writeBoolean(cell.getHasFog());
                    output.writeBoolean(cell.isLanternEffect());
                    output.writeBoolean(cell.isFanEffect());

                    Plant plant = cell.getPlant();
                    if (plant != null) {
                        output.writeUTF(plant.getType().name());
                        output.writeInt(plant.getHp());
                    }
                    else {
                        output.writeUTF("NULL");
                    }
                }
            }

            output.writeInt(gameState.getZombieList().size());
            for (Zombie zombie: gameState.getZombieList()) {
                output.writeUTF(zombie.getType().name());
                output.writeInt(zombie.getRow());
                output.writeInt(zombie.getCol());
                output.writeDouble(zombie.getCenterX());
                output.writeDouble(zombie.getCurrSpeed());
                output.writeInt(zombie.getHp());
                output.writeBoolean(zombie.isSlowed());
                output.writeBoolean(zombie.isHypnotized());

            }

            int seconds = gameState.getSeconds();
            if (seconds <= 30)
                output.writeInt(gameHandler.getZombieHandler().getLevel1_CycleCount());
            else if (seconds <= 60)
                output.writeInt(gameHandler.getZombieHandler().getLevel2_CycleCount());
            else if (seconds <= 90)
                output.writeInt(gameHandler.getZombieHandler().getLevel3_CycleCount());
            else if (seconds <= 120)
                output.writeInt(gameHandler.getZombieHandler().getLevel4_CycleCount());
            output.writeInt(gameHandler.getZombieHandler().getMidAttckCycleCount());
            output.writeInt(gameHandler.getZombieHandler().getFinalAttackCycleCount());

            output.writeInt(gameHandler.getZombieHandler().getIndex());
            for (int i = 0; i < GameState.ROW_COUNT; i++)
                output.writeInt(gameHandler.getZombieHandler().getRandomOrder().get(i));

            int sunCount = gameState.getSunList().size();
            output.writeInt(sunCount);
            for (int i = 0; i < sunCount; i++) {
                Sun sun = gameState.getSunList().get(i);
                output.writeDouble(sun.getX());
                output.writeDouble(sun.getY());
                output.writeDouble(sun.getRealY());
            }
        }
        catch (Exception e) {

        }
    }
}
