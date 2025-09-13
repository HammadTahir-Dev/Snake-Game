import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminDashboardFrame extends JFrame {

    public AdminDashboardFrame() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String[] columnNames = {"Email", "High Score", "Timestamp"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        loadUserData(tableModel);
        setVisible(true);
    }

    private void loadUserData(DefaultTableModel tableModel) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/snakegame", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT email, high_score, score_time FROM users");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String email = rs.getString("email");
                int score = rs.getInt("high_score");
                Timestamp timestamp = rs.getTimestamp("score_time");

                tableModel.addRow(new Object[]{email, score, timestamp});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading user data: " + e.getMessage());
        }
    }
}
