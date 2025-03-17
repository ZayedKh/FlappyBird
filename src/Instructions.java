import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Instructions extends JPanel {

    Instructions() {
        setPreferredSize(new Dimension(360, 640));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel instructions = new JLabel("<html>Instructions:<br>Press the space bar to make the bird fly.<br>Don't let the bird hit the pipes.<br>Every pipe you pass gives you a point.<br>Good luck!</html>");
    }

}