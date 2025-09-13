import javax.swing.*;
import java.awt.*;

public class ModeSelectionFrame extends JFrame {
    private final String userEmail;

    public ModeSelectionFrame(String userEmail) {
        this.userEmail = userEmail;

        setTitle("Select Game Mode");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 1, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panel.setBackground(new Color(20, 20, 20));

        JLabel title = new JLabel("Choose Game Mode", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JButton classicBtn = createStyledButton("Classic Mode");
        classicBtn.addActionListener(e -> launchGame("classic"));

        JButton intermediateBtn = createStyledButton("Intermediate Mode");
        intermediateBtn.addActionListener(e -> launchGame("intermediate"));

        JButton hardBtn = createStyledButton("Hard Mode");
        hardBtn.addActionListener(e -> launchGame("hard"));

        panel.add(classicBtn);
        panel.add(intermediateBtn);
        panel.add(hardBtn);

        add(panel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(40, 40, 40));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void launchGame(String mode) {
        dispose();
        JFrame gameFrame = new JFrame("Snake Game - " + mode.toUpperCase());
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.add(new GamePanel(mode, userEmail)); // user email is passed here
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }
}
