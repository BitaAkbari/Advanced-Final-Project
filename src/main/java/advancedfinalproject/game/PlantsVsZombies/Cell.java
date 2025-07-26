package advancedfinalproject.game.PlantsVsZombies;

public class Cell {

    public enum Content { BLANK, GRAVE, PLANT};

    private Content cellContent;
    private Plant plant;

    private Cell() {
        cellContent = Content.BLANK;
        plant = null;
    }

    public boolean placePlant(Plant plant) {
        if (cellContent == Content.BLANK) {
            this.plant = plant;
            cellContent = Content.PLANT;
            return true;
        }
        else return false;
    }

    public boolean removePlant() {
        if (cellContent == Content.PLANT) {
            plant = null;
            cellContent = Content.BLANK;
            return true;
        }
        else return false;
        //if....
    }

    public Content getCellContent() {
        return cellContent;
    }

    public Plant getPlant() {
        return plant;
    }

}
