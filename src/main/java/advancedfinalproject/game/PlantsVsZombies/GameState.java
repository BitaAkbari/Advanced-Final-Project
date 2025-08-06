package advancedfinalproject.game.PlantsVsZombies;

import java.util.ArrayList;
import java.util.Random;

public class GameState {
    public static final int ROW_COUNT = 5;
    public static final int COL_COUNT = 9;
    public static final int ITEM_COUNT = ItemSelectionScreen.SELECTED_COUNT;

    public enum GameCode {DAY, NIGHT};

    private GameCode code;
    private int sunPoint;
    private final int[] seconds = {0};
    private Item.ItemName[] names;
    private final Item[] store = new Item[ITEM_COUNT];
    private final Cell[][] field = new Cell[ROW_COUNT][COL_COUNT];
    private ArrayList<Zombie> zombieList;
    private ArrayList<Sun> sunList;
    private ArrayList<Bullet> bulletList;
    private Item selectedItem;
    //private Cell selectedCell;
    private int lastChoice;

    public GameState(GameCode gameCode, Item.ItemName[] itemNames) {
        code = gameCode;
        sunPoint = 100; // in ro bayad be 0 taghir bedim ba'dan

        names = itemNames;
        for (int i = 0; i < ITEM_COUNT; i++)
            store[i] = new Item(names[i]);

        for (int i = 0; i < ROW_COUNT; i++)
            for (int j = 0; j < COL_COUNT; j++)
                field[i][j] = new Cell(i, j);

        zombieList = new ArrayList<Zombie>();
        sunList = new ArrayList<Sun>();
        bulletList = new ArrayList<Bullet>();

        if (code == GameCode.NIGHT) buildGrave();

        selectedItem = null;
        //selectedCell = null;
        lastChoice = -1;
    }

    public GameCode getCode() {
        return code;
    }

    public int getSunPoint() {
        return sunPoint;
    }

    public void addPoint() {
        sunPoint += Sun.POINT;
    }

    public int getSeconds() {
        return seconds[0];
    }

    public void setSeconds() {
        seconds[0]++;
    }

    public Item[] getStore() {
        return store;
    }

    public Cell[][] getField() {
        return field;
    }

    public ArrayList<Zombie> getZombieList() {
        return zombieList;
    }

    public ArrayList<Sun> getSunList() {
        return sunList;
    }

    public ArrayList<Bullet> getBulletList() {
        return bulletList;
    }

    public int getLastChoice() {
        return lastChoice;
    }

    public void setLastChoice(int index) {
        lastChoice = index;
    }

    private void buildGrave() {
        Random random = new Random();
        int graveCount = random.nextInt(3) + 3;
        for(int i = 0 ; i < graveCount ; i++) {
            int row = random.nextInt(5);
            int col = random.nextInt(4) + 4;
            if(field[row][col].getCellContent() == Cell.Content.BLANK) {
                field[row][col].setCellContent(Cell.Content.GRAVE);
            }
            else i--;
        }
    }

    public boolean selectItem(int index) {
        if (selectedItem == store[index]) {
            selectedItem = null;
            return false;
        }
        else if (!store[index].getIsSelected() && sunPoint >= store[index].getPrice()) {
            selectedItem = store[index];
            return true;
        }
        else return false;
    }

    public boolean buyAndPlant(int cellRow, int cellCol) {
        if (selectedItem != null) {
            if (field[cellRow][cellCol].placePlant(itemToPlant(selectedItem, cellRow, cellCol))) {
                selectedItem.charge();
                sunPoint -= selectedItem.getPrice();
                selectedItem = null;
                return true;
            }
        }
        return false;
    }

    private Plant itemToPlant(Item item, int row, int col) {
        switch (item.getName()) {
            case SUNFLOWER:
                return new Sunflower(row, col);
            case PEASHOOTER:
                return new Peashooter(row, col);
            case REPEATER:
                return new Repeater(row, col);
            case SNOW_PEA:
                return new SnowPea(row, col);
            case WALL_NUT:
                return new WallNut(row, col);
            case TALL_NUT:
                return new TallNut(row, col);
            case CHERRY_BOMB:
                return new CherryBomb(this, row, col);
            case JALAPENO:
                return new Jalapeno(this, row, col);
            case GRAVE_BUSTER:
                return new GraveBuster(this, row, col);
            case COFFEE_BEAN:
                return new CoffeeBean(row, col);
            case HYPNO_SHROOM:
                return new HypnoShroom(row, col);
            case PUFF_SHROOM:
                return new PuffShroom(row, col);
            case SCAREDY_SHROOM:
                return new ScaredyShroom(row, col);
            case DOOM_SHROOM:
                return new DoomShroom(row, col);
            case ICE_SHROOM:
                return new IceShroom(row, col);
            case LANTERN:
                return new Lantern(row, col);
            case FAN:
                return new Fan(row, col);
            default:
                return null;
        }
    }
}
