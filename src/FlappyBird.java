import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

// inherit from JPanel
public class FlappyBird extends JPanel implements ActionListener {
    int boardWidth = 360;
    int boardHeight = 640;

    // Images
    Image backgroundImage;
    Image birdImage;
    Image topPipeImage;
    Image bottomPipeImage;

    // Bird dimensions
    int birdWidth = 34;
    int birdHeight = 24;

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

    // game logic
    Bird bird;

    Timer gameLoop;


    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        //load images
        backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdImage);

        // game timer
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // draw background image
        g.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);

        // draw background image
        g.drawImage(birdImage, birdX, birdY, birdWidth, birdHeight, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}