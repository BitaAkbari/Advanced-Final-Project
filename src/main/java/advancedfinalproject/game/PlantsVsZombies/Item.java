package advancedfinalproject.game.PlantsVsZombies;

public class Item {

    public enum ItemName {SUNFLOWER, JALAPENO, CHERRY_BOMB, TALL_NUT, WALL_NUT,
        SNOW_PEA, REPEATER, PEASHOOTER};
    private ItemName name;
    private int stdRechargeTime;
    private int remainingTime;
    private int price;
    private boolean isSelected;

    public Item(ItemName name){
        this.name = name;
        remainingTime = 0;
        isSelected = false;
        switch (name) {
            case SUNFLOWER:
                price = 50;
                stdRechargeTime = 3;
                break;
            case JALAPENO:
                price = 125;
                stdRechargeTime = 5;
                break;
            case CHERRY_BOMB:
                price = 150;
                stdRechargeTime = 6;
                break;
            case TALL_NUT:
                price = 125;
                stdRechargeTime = 7;
                break;
            case WALL_NUT:
                price = 50;
                stdRechargeTime = 6;
                break;
            case SNOW_PEA:
                price = 175;
                stdRechargeTime = 3;
                break;
            case REPEATER:
                price = 200;
                stdRechargeTime = 4;
                break;
            case PEASHOOTER:
                price = 100;
                stdRechargeTime = 2;
                break;

        }

    }

    public ItemName getName() {
        return name;
    }

    public int getStdRechargeTime() {
        return stdRechargeTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getPrice() {
        return price;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
