import javax.swing.*;
import java.awt.*;

public class RoleSelectionFrame extends JFrame {

    public RoleSelectionFrame() {
        setTitle("Select Login Role");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));
        getContentPane().setBackground(Color.DARK_GRAY);

        JLabel label = new JLabel("Choose Login Type", JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        add(label);

        JButton userBtn = new JButton("User Login");
        JButton adminBtn = new JButton("Admin Login");

        styleButton(userBtn);
        styleButton(adminBtn);

        userBtn.addActionListener(e -> {
            dispose();
            new LoginFrame(); // your existing user login
        });

        adminBtn.addActionListener(e -> {
            dispose();
            new AdminLoginFrame(); // next class we’ll build
        });

        add(userBtn);
        add(adminBtn);

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(30, 30, 30));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RoleSelectionFrame::new);
    }
}
