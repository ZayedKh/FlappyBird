import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instructions extends JPanel {
    private JButton menuButton;
    private final int instructionsWidth = 360;
    private final int instructionsHeight = 500;
    private final Image backgroundImage = new ImageIcon(getClass().getResource("Assets/flappybirdbg.png")).getImage();

    Instructions() {
        setPreferredSize(new Dimension(instructionsWidth, instructionsHeight));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        menuButton = new JButton("Back to menu");
        menuButton.setBackground(Color.blue);
        menuButton.setFont(new Font("Arial", Font.PLAIN, 20));
        menuButton.setForeground(Color.white);
        menuButton.setLayout(new FlowLayout());

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Instructions.this);
                frame.remove(Instructions.this);
                Menu menu = new Menu();
                frame.add(menu, BorderLayout.CENTER);
                frame.pack();
                menu.requestFocus();
            }
        });


        JLabel instructions = new JLabel("<html>Instructions:<p><br>Press the <strong>space bar</strong> to make the bird fly.</p><p><br>Don't let the bird hit the pipes.</p><p><br>Every pipe you pass gives you a point.</p><br>Good luck!</html>");
        instructions.setFont(new Font("Arial", Font.PLAIN, 12));

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(instructions, gbc);
        gbc.gridy = 1;
        add(menuButton, gbc);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, instructionsWidth, instructionsHeight, null);
    }
}