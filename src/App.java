import javax.swing.*;
import java.awt.*;


public class App {
    public static void main(String[] args) {
        // board dimensions - dimensions of background image
        int boardWidth = 360;
        int boardHeight = 640;

        // create new frame, title it, and place in center of screen
        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird fb = new FlappyBird();
        Menu menu = new Menu();
        frame.add(menu, BorderLayout.CENTER);
//        frame.add(fb, BorderLayout.CENTER);
        // not including title bar
        frame.pack();
        fb.requestFocus();
        frame.setVisible(true);

    }
}