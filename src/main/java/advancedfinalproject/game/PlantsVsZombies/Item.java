package advancedfinalproject.game.PlantsVsZombies;

public class Item {

    public enum ItemName { SUNFLOWER, PEASHOOTER, REPEATER, SNOW_PEA,
                           WALL_NUT, TALL_NUT, CHERRY_BOMB, JALAPENO,
                           GRAVE_BUSTER, COFFEE_BEAN, HYPNO_SHROOM, PUFF_SHROOM,
                           SCAREDY_SHROOM, DOOM_SHROOM, ICE_SHROOM, LANTERN, FAN };

    private ItemName name;
    private int stdRechargeTime;
    private int remainingTime;
    private int price;
    private boolean isSelected;

    public Item(ItemName name) {
        this.name = name;
        remainingTime = 0;
        isSelected = false;
        switch (name) {
            case SUNFLOWER:
                price = 50;
                stdRechargeTime = 3;
                break;
            case PEASHOOTER:
                price = 100;
                stdRechargeTime = 2;
                break;
            case REPEATER:
                price = 200;
                stdRechargeTime = 4;
                break;
            case SNOW_PEA:
                price = 175;
                stdRechargeTime = 3;
                break;
            case WALL_NUT:
                price = 50;
                stdRechargeTime = 6;
                break;
            case TALL_NUT:
                price = 125;
                stdRechargeTime = 7;
                break;
            case CHERRY_BOMB:
                price = 150;
                stdRechargeTime = 6;
                break;
            case JALAPENO:
                price = 125;
                stdRechargeTime = 5;
                break;
            case GRAVE_BUSTER:
                price = 75;
                stdRechargeTime = 6;
                break;
            case COFFEE_BEAN:
                price = 75;
                stdRechargeTime = 4;
                break;
            case HYPNO_SHROOM:
                price = 75;
                stdRechargeTime = 3;
                break;
            case PUFF_SHROOM:
                price = 0;
                stdRechargeTime = 3;
                break;
            case SCAREDY_SHROOM:
                price = 25;
                stdRechargeTime = 3;
                break;
            case DOOM_SHROOM:
                price = 125;
                stdRechargeTime = 4;
                break;
            case ICE_SHROOM:
                price = 75;
                stdRechargeTime = 3;
                break;
            case LANTERN:
                price = 25;
                stdRechargeTime = 4;
                break;
            case FAN:
                price = 100;
                stdRechargeTime = 4;
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
