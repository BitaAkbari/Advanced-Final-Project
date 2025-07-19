package advancedfinalproject.game.prototype;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class ZombieController {
    private List<Image> frames;
    private ImageView imageView;
    private int currentFrame = 0;
    private double x = 0;
    private double speed = 2;

    public ZombieController(List<Image> frames) {
        this.frames = frames;
        this.imageView = new ImageView(frames.get(0));
        imageView.setLayoutX(x);
        imageView.setLayoutY(200);
    }

    public void update() {
        // تغییر فریم انیمیشن
        currentFrame = (currentFrame + 1) % frames.size();
        imageView.setImage(frames.get(currentFrame));

        // حرکت به چپ
        x += speed;
        imageView.setLayoutX(x);

        // اگر از صفحه خارج شد، از ابتدا شروع کند
        if (x > 800) {
            x = -frames.get(0).getWidth();
        }
    }

    public ImageView getImageView() {
        return imageView;
    }
}