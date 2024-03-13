package monaco.application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static monaco.application.Track.RAND;
import static monaco.application.Track.WIDTH;

public class Road {

    int posX, posY;
    private int h, w, r, g, b;
    private double opacity;

    public Road() {
        posX = RAND.nextInt(WIDTH);
        posY = 0;
       /* w = RAND.nextInt(5) + 1;
        h =  RAND.nextInt(5) + 1;
        r = RAND.nextInt(100) + 150;
        g = RAND.nextInt(100) + 150;
        b = RAND.nextInt(100) + 150;
        opacity = RAND.nextFloat();*/
        if(opacity < 0) opacity *=-1;
        if(opacity > 0.5) opacity = 0.5;
    }

    public void draw(GraphicsContext gc) {
        if(opacity > 0.8) opacity-=0.01;
        if(opacity < 0.1) opacity+=0.01;
        gc.setFill(Color.rgb(r, g, b, opacity));
        gc.fillOval(posX, posY, w, h);
        posY+=20;
    }
}
