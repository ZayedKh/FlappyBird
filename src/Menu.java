import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JPanel {
    private JButton startButton;

    public Menu() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        startButton = new JButton("Start game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 40));
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

        gbc.gridx = 0;
        gbc.gridy = 0;

        add(startButton, gbc);
    }
}