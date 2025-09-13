import javax.swing.*;
import java.awt.*;

public class AdminLoginFrame extends JFrame {
    private JTextField adminField;
    private JPasswordField passwordField;

    public AdminLoginFrame() {
        setTitle("Admin Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(4, 1, 10, 10));
        getContentPane().setBackground(Color.DARK_GRAY);

        JLabel titleLabel = new JLabel("Admin Panel Login", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel);

        adminField = new JTextField();
        passwordField = new JPasswordField();

        JPanel adminPanel = new JPanel(new BorderLayout());
        adminPanel.setBackground(Color.DARK_GRAY);
        JLabel adminLabel = new JLabel("Username: ");
        adminLabel.setForeground(Color.WHITE);
        adminPanel.add(adminLabel, BorderLayout.WEST);
        adminPanel.add(adminField, BorderLayout.CENTER);

        JPanel passPanel = new JPanel(new BorderLayout());
        passPanel.setBackground(Color.DARK_GRAY);
        JLabel passLabel = new JLabel("Password: ");
        passLabel.setForeground(Color.WHITE);
        passPanel.add(passLabel, BorderLayout.WEST);
        passPanel.add(passwordField, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(e -> validateAdmin());

        add(adminPanel);
        add(passPanel);
        add(loginButton);

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(30, 30, 30));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
    }

    private void validateAdmin() {
        String username = adminField.getText();
        String password = new String(passwordField.getPassword());

        // Hardcoded admin credentials — update as needed
        if (username.equals("admin") && password.equals("admin123")) {
            dispose();
            new AdminDashboardFrame(); // Step 3 coming next
        } else {
            JOptionPane.showMessageDialog(this, "Invalid admin credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
