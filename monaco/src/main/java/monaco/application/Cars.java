package monaco.application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Cars extends Player {

    int SPEED;

    public Cars(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
        SPEED = Track.score + 50;
    }

    @Override
    public void update() {
        super.update();
        if (!exploding && !destroyed) posY += SPEED;
        if (posY > Track.HEIGHT) destroyed = true;
    }

    @Override
    public void draw(GraphicsContext gc) {
        super.draw(gc);
    }
}
