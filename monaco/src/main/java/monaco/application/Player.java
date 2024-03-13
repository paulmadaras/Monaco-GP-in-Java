package monaco.application;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;

public class Player {

    int posX, posY, size;
    boolean exploding, destroyed;
    Image img;
    int explosionStep = 0;

    public Player(int posX, int posY, int size, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        img = image;
    }

    public void update() {
        if (exploding) explosionStep++;
        destroyed = explosionStep > Track.EXPLOSION_STEPS;
    }

    public void draw(GraphicsContext gc) {
        if (exploding) {
            gc.drawImage(Track.EXPLOSION_IMG, explosionStep % Track.EXPLOSION_COL * Track.EXPLOSION_W,
                    (explosionStep / Track.EXPLOSION_ROWS) * Track.EXPLOSION_H + 1,
                    Track.EXPLOSION_W, Track.EXPLOSION_H,
                    posX, posY, size, size);
        } else {
            gc.drawImage(img, posX, posY);
        }
    }

    public boolean colide(Player other) {
        int d = distance(this.posX + 45, this.posY + 90,
                other.posX + 45, other.posY + 90);
        return d < 90;
    }

    public void explode() {
        exploding = true;
        explosionStep = -1;
    }

    public void moveLeft() {
        if (this.posX > 0) {
            this.posX -= 2;
        }
    }

    public void moveRight() {
        if (this.posX < Track.WIDTH) {
            this.posX += 2;
        }
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}
