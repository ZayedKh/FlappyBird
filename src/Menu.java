import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JPanel {
    private JButton startButton;
    private JButton instructionButton;
    private final int menuHeight = 500;
    private final int menuWidth = 360;
    private final Image backgroundImage = new ImageIcon(getClass().getResource("Assets/flappybirdbg.png")).getImage();
    ;

    Menu() {
        setPreferredSize(new Dimension(menuWidth, menuHeight));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


        startButton = new JButton("Start game");
        startButton.setBackground(Color.BLUE);
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setForeground(Color.white);
        startButton.setLayout(new FlowLayout());


        instructionButton = new JButton("Instructions");
        instructionButton.setBackground(Color.BLUE);
        instructionButton.setFont(new Font("Arial", Font.PLAIN, 20));
        instructionButton.setForeground(Color.white);
        instructionButton.setLayout(new FlowLayout());

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // remove menu and start game
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Menu.this);
                frame.remove(Menu.this);
                FlappyBird fb = new FlappyBird();
                frame.add(fb, BorderLayout.CENTER);
                frame.pack();
                fb.requestFocus();
            }
        });

        instructionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // open instructions
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Menu.this);
                frame.remove(Menu.this);
                Instructions instructions = new Instructions();
                frame.add(instructions, BorderLayout.CENTER);
                frame.pack();
                instructions.requestFocus();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;

        add(startButton, gbc);

        gbc.gridy = 1;
        add(instructionButton, gbc);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, menuWidth, menuHeight, null);
    }
}