import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

// inherit from JPanel
public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    final int boardWidth = 360;
    final int boardHeight = 640;

    // Images
    final Image backgroundImage;
    final Image birdImage;
    final Image topPipeImage;
    final Image bottomPipeImage;

    // Bird dimensions
    final int birdWidth = 34;
    final int birdHeight = 24;

    // Bird starting position
    int birdX = boardWidth / 8;
    int birdY = boardWidth / 2;


    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    final int pipeWidth = 64;
    final int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    // We need the gap between both pipes to be the height of the bird and then some

    // game logic
    Bird bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;
    Random rand = new Random();

    ArrayList<Pipe> topPipes;
    ArrayList<Pipe> bottomPipes;
    final int gap = birdHeight + 95;
    Timer gameLoop;
    Timer placePipesTimer;


    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        //load images
        backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // bird
        bird = new Bird(birdImage);
        topPipes = new ArrayList<>();
        bottomPipes = new ArrayList<>();

        //place pipes timer
        placePipesTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });

        placePipesTimer.start();

        // game timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

    }

    // we want to place the bottom pipe at topPipe.y + topPipe.height + gap
    public void placePipes() {
        Pipe topPipe = new Pipe(topPipeImage);
        Pipe bottomPipe = new Pipe(bottomPipeImage);
        topPipe.y -= rand.nextInt(topPipe.height - 20);
        bottomPipe.y = topPipe.y + topPipe.height + gap;
        topPipes.add(topPipe);
        bottomPipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // System.out.println("Drawing");

        // draw background image
        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);

        // draw background image
        g.drawImage(birdImage, bird.x, bird.y, birdWidth, birdHeight, null);


        // draw pipes
        for (int i = 0; i < topPipes.size(); i++) {
            Pipe topPipe = topPipes.get(i);
            Pipe bottomPipe = bottomPipes.get(i);
            g.drawImage(topPipeImage, topPipe.x, topPipe.y, topPipe.width, topPipe.height, null);
            g.drawImage(bottomPipeImage, topPipe.x, bottomPipe.y, bottomPipe.width, bottomPipe.height, null);
        }
//        for (int i = 0; i < bottomPipes.size(); i++) {
//            Pipe bottomPipe = bottomPipes.get(i);
//            g.drawImage(bottomPipeImage, bottomPipe.x, bottomPipe.y, bottomPipe.width, bottomPipe.height, null);
//        }
    }

    public void move() {
        // update x and y of objects
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(0, bird.y);
        bird.y = Math.min(boardHeight - birdHeight, bird.y);

        // move pipes
        for (int i = 0; i < topPipes.size(); i++) {
            Pipe topPipe = topPipes.get(i);
            Pipe bottomPipe = bottomPipes.get(i);
            topPipe.x += velocityX;
            bottomPipe.x += velocityX;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}