import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    private final String userEmail;

    public MainMenu(String userEmail) {
        this.userEmail = userEmail;

        setTitle("Snake Game Menu");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panel.setBackground(new Color(30, 30, 30));

        JLabel title = new JLabel("SNAKE GAME", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JButton newGameBtn = createStyledButton("Start Game");
        newGameBtn.addActionListener(e -> {
            dispose();
            new ModeSelectionFrame(userEmail);  // updated to pass userEmail
        });

        JButton highScoreBtn = createStyledButton("High Scores");
        highScoreBtn.addActionListener(e -> new HighScoreViewer(userEmail));  // updated to pass userEmail

        JButton quitBtn = createStyledButton("Quit");
        quitBtn.addActionListener(e -> System.exit(0));

        panel.add(newGameBtn);
        panel.add(highScoreBtn);
        panel.add(quitBtn);

        add(panel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBackground(new Color(60, 60, 60));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}

