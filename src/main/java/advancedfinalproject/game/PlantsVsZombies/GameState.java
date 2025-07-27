package advancedfinalproject.game.PlantsVsZombies;

import java.util.ArrayList;

public class GameState {
    public static final int ROW_COUNT = 5;
    public static final int COL_COUNT = 9;
    public static final int ITEM_COUNT = ItemSelectionScreen.SELECTED_COUNT;

    public enum GameCode {DAY, NIGHT};

    private GameCode code;
    private int sunPoint;
    private final int seconds[] = {0};
    private Item.ItemName[] names;
    private final Item[] store = new Item[ITEM_COUNT];
    private final Cell[][] field = new Cell[ROW_COUNT][COL_COUNT];
    private ArrayList<Zombie> zombieList;
    private ArrayList<Sun> sunList;
    private ArrayList<Bullet> bulletList;
    private Item selectedItem;
    private Cell selectedCell;
    private int lastChoice = -1;

    public GameState(Item.ItemName[] itemNames) {
        code = GameCode.DAY;
        sunPoint = 1000; // in ro bayad be 0 taghir bedim ba'dan

        names = itemNames;
        for (int i = 0; i < ITEM_COUNT; i++)
            store[i] = new Item(names[i]);

        for (int i = 0; i < ROW_COUNT; i++)
            for (int j = 0; j < COL_COUNT; j++)
                field[i][j] = new Cell();

        zombieList = new ArrayList<Zombie>();
        sunList = new ArrayList<Sun>();
        bulletList = new ArrayList<Bullet>();

        selectedItem = null;
        selectedCell = null;
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

    public boolean setSelectItem(int index) {
        if (selectedItem == store[index]) {
            selectedItem = null;
            return false;
        }
        else if (sunPoint >= store[index].getPrice()) {
            selectedItem = store[index];
            return true;
        }
        else return false;
    }

    public boolean buyAndPlant(int cellRow, int cellCol) {
        if (selectedItem != null) {
            if (field[cellRow][cellCol].placePlant(itemToPlant(selectedItem))) {
                sunPoint -= selectedItem.getPrice();
                selectedItem = null;
                return true;
            }
        }
        return false;
    }

    private Plant itemToPlant(Item item) {// bayad takmil beshe.................
        return new Plant();
    }
}
