import javax.swing.*;


public class App {
    public static void main(String[] args) {
        // board dimensions - dimensions of background image
        int boardWidth = 360;
        int boardHeight = 640;

        // create new frame, title it, and place in center of screen
        JFrame frame = new JFrame("Flappy Bird");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}