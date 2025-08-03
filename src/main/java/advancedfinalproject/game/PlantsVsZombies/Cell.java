package advancedfinalproject.game.PlantsVsZombies;

public class Cell {

    public enum Content { BLANK, GRAVE, PLANT};

    private Content cellContent;
    private Plant plant;
    private int row, col;

    public Cell(int row, int col) {
        cellContent = Content.BLANK;
        plant = null;
        this.row = row;
        this.col = col;
    }

    public boolean placePlant(Plant plant) {
        if (cellContent == Content.BLANK) {
            this.plant = plant;
            cellContent = Content.PLANT;
            if (plant instanceof BombPlant || plant instanceof Sunflower)
                plant.act();
            return true;
        }
        return false;
    }

    public boolean removePlant() {
        if (cellContent == Content.PLANT) {
            plant.clear();
            plant = null;
            cellContent = Content.BLANK;
            return true;
        }
        return false;
    }

    public Content getCellContent() {
        return cellContent;
    }

    public Plant getPlant() {
        return plant;
    }

}
