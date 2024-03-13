package monaco.application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.*;

public class Track extends Application {

    public static final Random RAND = new Random();
    static final int WIDTH = 600;
    static final int HEIGHT = 1000;
    private static final int PLAYER_SIZE = 200;
    static final Image PLAYER_IMG = new Image("file:src/main/resources/imagini/car1.png");
    static final Image EXPLOSION_IMG = new Image("file:src/main/resources/imagini/explosion.png");
    static final int EXPLOSION_W = 128;
    static final int EXPLOSION_ROWS = 3;
    static final int EXPLOSION_COL = 3;
    static final int EXPLOSION_H = 128;
    public static final int EXPLOSION_STEPS = 15;

    static final Image CARS_IMG[] = {
            new Image("file:src/main/resources/imagini/car2.png"),
            new Image("file:src/main/resources/imagini/car3.png"),
            new Image("file:src/main/resources/imagini/car4.png"),
            new Image("file:src/main/resources/imagini/car5.png"),
            new Image("file:src/main/resources/imagini/car6.png"),
            new Image("file:src/main/resources/imagini/car7.png")
    };

    final int MAX_CARS = 2;
    boolean gameOver = false;
    GraphicsContext gc;

    Player player;
    List<Road> univ;
    List<Cars> carsList;

    private double mouseX = WIDTH/2 - 45;
    private double mouseY = HEIGHT/2;

    private boolean moveRight = false, moveLeft = false, moveUp = false, moveDown = false;
    public static int score;

    private static int cases = 0;
    Canvas canvas = new Canvas(WIDTH, HEIGHT);

    @Override
    public void start(Stage stage) throws Exception {
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::keyPressed);
        canvas.setOnKeyReleased(this::keyReleased);

        setup();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Monaco GP Arcade");
        stage.show();
    }


    private void setup() {
        univ = new ArrayList<>();
        carsList = new ArrayList<>();
        player = new Player(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
        score = 0;
        IntStream.range(0, MAX_CARS).mapToObj(i -> newCars()).forEach(carsList::add);
    }

    public void keyPressed(KeyEvent evt){

        KeyCode key = evt.getCode();
        System.out.println("KeyPressed: " + key);

        if((key == KeyCode.A || key == KeyCode.LEFT) && mouseX - 30 > 0) {
            moveLeft = true;
        }
        if((key == KeyCode.D || key == KeyCode.RIGHT) && mouseX + 120 < WIDTH)
            moveRight = true;
        if((key == KeyCode.W || key == KeyCode.UP)&& mouseY - 30 > 0)
            moveUp = true;
        if((key == KeyCode.S || key == KeyCode.DOWN || key == KeyCode.SPACE) && mouseY + 210 < HEIGHT)
            moveDown = true;
        if(key == KeyCode.ENTER) {

            if(gameOver){
            gameOver = false;
            setup();
            }
            if(cases == 0)
                cases = 1;
        }
        if(key == KeyCode.ESCAPE)
            cases = 0;
    }

    public void keyReleased(KeyEvent evt){

        KeyCode key = evt.getCode();
        System.out.println("Key Released: " + key);

        if((key == KeyCode.A || key == KeyCode.LEFT)) {
            moveLeft = false;
        }
        if((key == KeyCode.D || key == KeyCode.RIGHT)){

            moveRight = false;
        }
        if((key == KeyCode.W || key == KeyCode.UP)){

            moveUp = false;
        }
        if(key == KeyCode.S || key == KeyCode.DOWN || key == KeyCode.SPACE){

            moveDown = false;
        }
    }
    private void run(GraphicsContext gc) {

        if (cases == 0) {

            gc.setFill(Color.DARKGREEN);
            gc.fillRect(0, 0, 50, HEIGHT);
            gc.fillRect(WIDTH - 50, 0, 50, HEIGHT);

            gc.setFill(Color.DARKRED);
            gc.fillRect(62, 0, 13, HEIGHT);
            gc.fillRect(WIDTH - 75, 0, 13, HEIGHT);

            gc.setFill(Color.WHITE);
            gc.fillRect(50, 0, 13, HEIGHT);
            gc.fillRect(WIDTH - 62, 0, 12, HEIGHT);

            gc.setFill(Color.grayRgb(24));
            gc.fillRect(75, 0, WIDTH - 150, HEIGHT);


            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font("Elephant", FontWeight.BOLD, 50));
            gc.setFill(Color.RED);
            gc.fillText("Monaco GP", WIDTH / 2, HEIGHT / 2);
            gc.setFont(Font.font("Times New Roman", 25));
            gc.fillText("\n\n Press ENTER to Continue", WIDTH / 2, HEIGHT / 2);



        }
        if (cases == 1) {
            gc.setFill(Color.DARKGREEN);
            gc.fillRect(0, 0, 50, HEIGHT);
            gc.fillRect(WIDTH - 50, 0, 50, HEIGHT);

            gc.setFill(Color.DARKRED);
            gc.fillRect(62, 0, 13, HEIGHT);
            gc.fillRect(WIDTH - 75, 0, 13, HEIGHT);

            gc.setFill(Color.WHITE);
            gc.fillRect(50, 0, 13, HEIGHT);
            gc.fillRect(WIDTH - 62, 0, 12, HEIGHT);

            gc.setFill(Color.grayRgb(24));
            gc.fillRect(75, 0, WIDTH - 150, HEIGHT);

            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFill(Color.RED);
            gc.setFont(Font.font("Times New Roman",FontWeight.BOLD, 20));
            gc.fillText("Score: " + score, 40, 20);

            if (gameOver) {
                gc.setFont(Font.font("Times New Roman", 35));
                gc.setFill(Color.RED);
                gc.fillText("Game Over \n Your Score is: " + score + " \n Press ENTER to play again", WIDTH / 2, HEIGHT / 2.5);
            }

            univ.forEach(road -> road.draw(gc));

            canvas.setOnKeyPressed(this::keyPressed);
            canvas.setOnKeyReleased(this::keyReleased);


            if (moveLeft == true && mouseX + 10 > 0) {
                mouseX -= 40;
            } else moveLeft = false;
            if (moveRight == true && mouseX + 80 < WIDTH) {

                mouseX += 45;
            } else moveRight = false;
            if (moveUp == true && mouseY + 10 > 0) {

                mouseY -= 45;
            } else moveUp = false;
            if (moveDown == true && mouseY + 180 < HEIGHT) {

                mouseY += 70;
            } else moveDown = false;

            player.update();
            player.draw(gc);
            player.posX = (int) mouseX;
            player.posY = (int) mouseY;

            carsList.stream().peek(Cars::update).peek(cars -> cars.draw(gc)).forEach(e -> {
                if (player.colide(e) && !player.exploding) {
                    player.explode();
                }
            });

            for (int i = carsList.size() - 1; i >= 0; i--) {
                if (carsList.get(i).destroyed) {

                    if (!gameOver && !player.exploding)
                        score++;
                    carsList.set(i, newCars());
                }
            }

            gameOver = player.destroyed;
            if (RAND.nextInt(10) > 2) {
                univ.add(new Road());
            }
            univ.removeIf(road -> road.posY > HEIGHT);
        }
    }
        public static void main (String[]args){
            launch();
        }

        Cars newCars () {
            return new Cars(50 + RAND.nextInt(WIDTH - 180), -500 - RAND.nextInt(200), 10, CARS_IMG[RAND.nextInt(CARS_IMG.length)]);
        }
}
