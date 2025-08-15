package advancedfinalproject.game.PlantsVsZombies;

import java.util.ArrayList;

public class HypnoShroom extends Shroom {
    private ArrayList<GraphicalZombie> zombies;

    public HypnoShroom(int row, int col) {
        super(PlantName.HYPNO_SHROOM, row, col);
        zombies = new ArrayList<>();
    }

    public void act() {
        for (GraphicalZombie zombie: zombies)
            zombie.hypnosis();

        zombies.clear();
        zombies = null;
        hp = 0;
    }

    public void addZombie(GraphicalZombie zombie) {
        zombies.add(zombie);
    }
}
