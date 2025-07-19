package advancedfinalproject.game.prototype;

public class ZombieController {
    private final Zombie zombieApp;

    public ZombieController(Zombie zombieApp) {
        this.zombieApp = zombieApp;
    }

    public void toggleMovement() {
        zombieApp.isMoving = !zombieApp.isMoving;
    }
}