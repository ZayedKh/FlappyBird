import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

// inherit from JPanel
public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    private final int boardWidth = 360;
    private final int boardHeight = 640;

    // Images
    private final Image backgroundImage;
    private final Image birdImage;
    private final Image topPipeImage;
    private final Image bottomPipeImage;

    // Bird dimensions
    private final int birdWidth = 34;
    private final int birdHeight = 24;

    // Bird starting position
    private int birdX = boardWidth / 8;
    private int birdY = boardWidth / 2;

    private int score = 0;


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
    private int pipeX = boardWidth;
    private int pipeY = 0;
    private final int pipeWidth = 64;
    private final int pipeHeight = 512;

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
    private int velocityX = -4;
    private int velocityY = 0;
    private int gravity = 1;
    Random rand = new Random();

    ArrayList<Pipe> topPipes;
    ArrayList<Pipe> bottomPipes;
    final int gap = birdHeight + 95;
    Timer gameLoop;
    Timer placePipesTimer;
    private boolean collisionDetected = false;


    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        //load images
        backgroundImage = new ImageIcon(getClass().getResource("Assets/flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("Assets/flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("Assets/toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("Assets/bottompipe.png")).getImage();

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

        // draw background image
        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);

        // draw background image
        g.drawImage(birdImage, bird.x, bird.y, birdWidth, birdHeight, null);
        g.setColor(Color.RED);
        g.drawRect(bird.x, bird.y, bird.width, bird.height);


        // draw pipes
        for (int i = 0; i < topPipes.size(); i++) {
            Pipe topPipe = topPipes.get(i);
            Pipe bottomPipe = bottomPipes.get(i);
            g.drawImage(topPipeImage, topPipe.x, topPipe.y, topPipe.width, topPipe.height, null);
            g.drawImage(bottomPipeImage, topPipe.x, bottomPipe.y, bottomPipe.width, bottomPipe.height, null);

            g.setColor(Color.RED);
            g.drawRect(topPipe.x, topPipe.y, topPipe.width, topPipe.height);
            g.drawRect(bottomPipe.x, bottomPipe.y, bottomPipe.width, bottomPipe.height);
        }

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 30);

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
            if (!topPipe.passed && bird.x > topPipe.x) {
                topPipe.passed = true;
                score++;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        collisionDetected = checkCollision();
        if (collisionDetected) {
            gameLoop.stop();
        }
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

    public Rectangle getBirdBounds() {
        return new Rectangle(bird.x, bird.y, bird.width, bird.height);
    }

    public Rectangle getTopPipeBounds() {
        try {
            Pipe pipe = topPipes.get(topPipes.size() - 2);
            return new Rectangle(pipe.x, pipe.y, pipe.width, pipe.height);
        } catch (Exception e) {
            return new Rectangle(0, 0, 0, 0);
        }
    }

    public Rectangle getBottomPipeBounds() {
        try {
            Pipe pipe = bottomPipes.get(bottomPipes.size() - 2);
            return new Rectangle(pipe.x, pipe.y, pipe.width, pipe.height);
        } catch (Exception e) {
            return new Rectangle(0, 0, 0, 0);
        }
    }

    public boolean checkCollision() {
        Rectangle birdBounds = getBirdBounds();

        if (birdBounds.intersects(getTopPipeBounds()) || birdBounds.intersects(getBottomPipeBounds())) {
            System.out.println("Collision detected");
            return true;
        } else {
            return false;
        }
    }
}