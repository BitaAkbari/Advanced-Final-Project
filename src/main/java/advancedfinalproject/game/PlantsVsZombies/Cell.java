package advancedfinalproject.game.PlantsVsZombies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Cell {

    public enum Content { BLANK, GRAVE, PLANT, BLOCKED};

    private Content cellContent;
    private Plant plant;
    private int row, col;
    private boolean hasFog, lanternEffect, fanEffect;
    private Timeline removeFog, counter;
    private int remainingSeconds;

    public Cell(int row, int col) {
        cellContent = Content.BLANK;
        plant = null;
        this.row = row;
        this.col = col;
        hasFog = false;
        lanternEffect = false;
        fanEffect = false;
        remainingSeconds = 0;
    }

    public boolean placePlant(Plant plant) {
        if (plant.getClass() == GraveBuster.class) {
            if (cellContent == Content.GRAVE) {
                this.plant = plant;
                cellContent = Content.PLANT;
                plant.act();
                return true;
            }
        }
        else if (plant.getClass() == CoffeeBean.class) {
            if (cellContent == Content.PLANT && this.plant instanceof Shroom && ((Shroom) this.plant).isSleeping()) {
                return true;
            }
        }
        else if (cellContent == Content.BLANK) {
            this.plant = plant;
            cellContent = Content.PLANT;
            if (plant instanceof BombPlant || plant instanceof Sunflower
                || plant instanceof Lantern || plant instanceof Fan)
                plant.act();
            return true;
        }
        return false;
    }

    public boolean removePlant() {
        if (cellContent == Content.PLANT || cellContent == Content.BLANK) {
            if (plant instanceof Lantern)
                ((Lantern) plant).stopAct();
            plant.clear();
            plant = null;
            cellContent = Content.BLANK;
            return true;
        }
        if (cellContent == Content.BLOCKED) {
            plant.clear();
            plant = null;
        }
        return false;
    }

    public Content getCellContent() {
        return cellContent;
    }

    public void setCellContent(Content content) {
        cellContent = content;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public boolean getHasFog() {
        return hasFog;
    }

    public void setHasFog(boolean hasFog) {
        this.hasFog = hasFog;
    }

    public boolean isFanEffect() {
        return fanEffect;
    }

    public void setFanEffect(boolean fanEffect) {
        this.fanEffect = fanEffect;
    }

    public boolean isLanternEffect() {
        return lanternEffect;
    }

    public void setLanternEffect(boolean lanternEffect) {
        this.lanternEffect = lanternEffect;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public void setRemainingSeconds(int remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    public void unblock() {
        Timeline unblock = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
            cellContent = Content.BLANK;
        }));
        unblock.setCycleCount(1);
        unblock.play();
    }

    public void removeFogByFan() {
        if (removeFog != null && removeFog.getStatus() == Animation.Status.RUNNING) {
            removeFog.stop();
            removeFog = null;
            remainingSeconds = 20;
        }
        else if (remainingSeconds == 0)
            remainingSeconds = 20;

        removeFog = new Timeline(new KeyFrame(Duration.seconds(remainingSeconds), e -> {
            setFanEffect(false);
        }));
        removeFog.setCycleCount(1);
        removeFog.play();

        if (counter != null && counter.getStatus() == Animation.Status.RUNNING) {
            counter.stop();
            counter = null;
            remainingSeconds = 20;
        }
        else if (remainingSeconds == 0)
            remainingSeconds = 20;
        counter = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            remainingSeconds --;
        }));
        counter.setCycleCount(remainingSeconds);
        counter.play();
    }

    public void clearTimeline() {
        if (removeFog != null)
            removeFog.stop();
        if (counter != null)
            counter.stop();
    }

}
