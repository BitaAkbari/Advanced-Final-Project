package advancedfinalproject.game.PlantsVsZombies;

public abstract class Plant {
    private int row, col;
    protected int hp;

    public Plant(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public abstract void act();

    public void clear() {

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getHp() {
        return hp;
    }

    public void reduceHp() {
        hp --;
    }
}
