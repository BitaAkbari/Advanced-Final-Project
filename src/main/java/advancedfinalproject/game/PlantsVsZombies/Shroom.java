package advancedfinalproject.game.PlantsVsZombies;

public abstract class Shroom extends Plant {
    protected boolean isSleeping;

    public Shroom(int row, int col) {
        super(row, col);
        hp = 4;
        isSleeping = true;
    }

    public boolean isSleeping() {
        return isSleeping;
    }

    public void setSleeping(boolean sleeping) {
        isSleeping = sleeping;
    }
}
