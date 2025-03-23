import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import javax.swing.*;

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
    private final int birdX = boardWidth / 8;
    private final int birdY = boardWidth / 2;

    private int score = 0;

    private JButton retryButton;

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
    private final int pipeX = boardWidth;
    private final int pipeY = 0;
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
    private int velocityY = 0;
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

        // Retry button
        retryButton = new JButton("Retry");
        retryButton.setBackground(Color.RED);
        retryButton.setFont(new Font("Arial", Font.PLAIN, 20));
        retryButton.setForeground(Color.white);
        retryButton.addActionListener(e -> restartGame());
        retryButton.setLocation(boardWidth, boardHeight);

        // Load images
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/flappybirdbg.png"))).getImage();
        birdImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/flappybird.png"))).getImage();
        topPipeImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/toppipe.png"))).getImage();
        bottomPipeImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("Assets/bottompipe.png"))).getImage();

        // Bird
        bird = new Bird(birdImage);
        topPipes = new ArrayList<>();
        bottomPipes = new ArrayList<>();

        // Place pipes timer
        placePipesTimer = new Timer(2000, _ -> placePipes());
        placePipesTimer.start();

        // Game loop timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    // Restart the game
    private void restartGame() {
        remove(retryButton);
        score = 0;
        bird.y = birdY;
        velocityY = 0;
        topPipes.clear();
        bottomPipes.clear();
        gameLoop.start();
        placePipesTimer.start();
        repaint();
    }

    // Place pipes
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
        // Draw background image
        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);

        // Draw bird
        g.drawImage(birdImage, bird.x, bird.y, birdWidth, birdHeight, null);

        // Draw pipes
        for (int i = 0; i < topPipes.size(); i++) {
            Pipe topPipe = topPipes.get(i);
            Pipe bottomPipe = bottomPipes.get(i);
            g.drawImage(topPipeImage, topPipe.x, topPipe.y, topPipe.width, topPipe.height, null);
            g.drawImage(bottomPipeImage, topPipe.x, bottomPipe.y, bottomPipe.width, bottomPipe.height, null);
        }

        // Draw score
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 30);

        // Draw "You Died!" message
        if (checkCollision()) {
            g.setFont(new Font("Arial", Font.BOLD, 30));
            FontMetrics fm = g.getFontMetrics();
            int stringXPos = (boardWidth - fm.stringWidth("You Died!")) / 2;
            g.drawString("You Died!", stringXPos, 200);
        }
    }

    public void move() {
        // Update x and y of objects
        int gravity = 1;
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(0, bird.y);

        // Move pipes
        for (int i = 0; i < topPipes.size(); i++) {
            Pipe topPipe = topPipes.get(i);
            Pipe bottomPipe = bottomPipes.get(i);
            int velocityX = -4;
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
        if (checkCollision()) {
            gameLoop.stop();
            placePipesTimer.stop();
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(10, 10, 10, 10);
            c.gridx = 0;
            c.gridy = 5;
            add(retryButton, c);
            revalidate();
            repaint();
        }
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
        return birdBounds.intersects(getTopPipeBounds()) || birdBounds.intersects(getBottomPipeBounds()) || bird.y > boardHeight;
    }
}