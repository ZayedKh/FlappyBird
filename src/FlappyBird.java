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

        if (touchedPipes()) {
            gameLoop.stop();

        }

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

    public boolean touchedPipes() {

        if (topPipes.size() > 0 && bird.x + bird.width >= topPipes.getLast().x && bird.x <= topPipes.getLast().x + topPipes.getLast().width) {
            if (bird.y <= topPipes.getLast().y + topPipes.getLast().height || bird.y + bird.height >= bottomPipes.getLast().y) {
                return true;
            }
        }

        return false;
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