package advancedfinalproject.game.PlantsVsZombies;

public abstract class Plant {

//    public enum PlantName { SUNFLOWER, PEASHOOTER, REPEATER, SNOW_PEA,
//        WALL_NUT, TALL_NUT, CHERRY_BOMB, JALAPENO,
//        GRAVE_BUSTER, COFFEE_BEAN, HYPNO_SHROOM, PUFF_SHROOM,
//        SCAREDY_SHROOM, DOOM_SHROOM, ICE_SHROOM, LANTERN, FAN };

   // private PlantName type;
    private int row, col;
    protected int hp;

    public Plant(/*PlantName name, */int row, int col) {
        //type = name;
        this.row = row;
        this.col = col;
    }

    public abstract void act();

    public void clear() {

    }

//    public PlantName getType() {
//        return type;
//    }

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
