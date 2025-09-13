import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class HighScoreViewer extends JFrame {
    public HighScoreViewer(String userEmail) {
        setTitle("Top 3 High Scores");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JTextArea scoreArea = new JTextArea();
        scoreArea.setEditable(false);
        scoreArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        scoreArea.setBackground(new Color(30, 30, 30));
        scoreArea.setForeground(Color.WHITE);

        fetchHighScores(scoreArea);

        JScrollPane scrollPane = new JScrollPane(scoreArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        scrollPane.setBackground(new Color(20, 20, 20));

        add(scrollPane);
        getContentPane().setBackground(new Color(20, 20, 20));
        setVisible(true);
    }

    private void fetchHighScores(JTextArea area) {
        StringBuilder sb = new StringBuilder("Top 3 High Scores:\n\n");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/snakegame", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT email, high_score, score_time FROM users ORDER BY high_score DESC, score_time DESC LIMIT 3")) {

            int rank = 1;
            while (rs.next()) {
                String email = rs.getString("email");
                int score = rs.getInt("high_score");
                Timestamp timestamp = rs.getTimestamp("score_time");

                sb.append(String.format("%d. %s - %d points (%s)\n", rank++, email, score,
                        (timestamp != null ? timestamp.toString() : "No date")));
            }

        } catch (Exception e) {
            e.printStackTrace();
            sb.append("Error fetching scores.");
        }

        area.setText(sb.toString());
    }
}
