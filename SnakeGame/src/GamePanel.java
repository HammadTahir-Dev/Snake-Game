import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private final int UNIT_SIZE = 20;

    private final LinkedList<Point> snake = new LinkedList<>();
    private Point food;
    private final javax.swing.Timer timer;
    private char direction = 'R';  // U, D, L, R
    private boolean running = false;
    private int score = 0;

    private final String mode;
    private final String userEmail;
    private final List<Point> hurdles = new LinkedList<>();
    private final int HURDLE_SIZE = UNIT_SIZE;

    public GamePanel(String mode, String userEmail) {
        this.mode = mode.toLowerCase();
        this.userEmail = userEmail;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        initGame();
        timer = new javax.swing.Timer(100, this);
        timer.start();
    }

    private void initGame() {
        snake.clear();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        snake.add(new Point(WIDTH / 2 - UNIT_SIZE, HEIGHT / 2));
        snake.add(new Point(WIDTH / 2 - 2 * UNIT_SIZE, HEIGHT / 2));
        direction = 'R';
        score = 0;
        running = true;
        placeFood();
        hurdles.clear();
        if (mode.equals("hard")) generateHurdles();
    }

    private void generateHurdles() {
        Random random = new Random();
        int hurdleCount = 15;
        while (hurdles.size() < hurdleCount) {
            int x = random.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE;
            int y = random.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            Point p = new Point(x, y);
            if (!snake.contains(p) && !p.equals(food) && !hurdles.contains(p)) {
                hurdles.add(p);
            }
        }
    }

    private void placeFood() {
        Random random = new Random();
        int x, y;
        Point p;
        do {
            x = random.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE;
            y = random.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            p = new Point(x, y);
        } while (snake.contains(p) || hurdles.contains(p));
        food = p;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 25);

        if (running) {
            g.setColor(Color.RED);
            g.fillOval(food.x, food.y, UNIT_SIZE, UNIT_SIZE);

            if (mode.equals("hard")) {
                g.setColor(Color.GRAY);
                for (Point hurdle : hurdles) {
                    g.fillRect(hurdle.x, hurdle.y, HURDLE_SIZE, HURDLE_SIZE);
                }
            }

            for (int i = 0; i < snake.size(); i++) {
                g.setColor(i == 0 ? Color.GREEN.brighter() : Color.GREEN.darker());
                Point p = snake.get(i);
                g.fillRect(p.x, p.y, UNIT_SIZE, UNIT_SIZE);
            }

        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        timer.stop();
        updateHighScore();

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2);

        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, (WIDTH - metrics.stringWidth("Score: " + score)) / 2, HEIGHT / 2 + 50);

        new javax.swing.Timer(1500, e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
            new MainMenu(userEmail);
        }).start();
    }

    private void move() {
        Point head = snake.getFirst();
        int x = head.x;
        int y = head.y;

        switch (direction) {
            case 'U': y -= UNIT_SIZE; break;
            case 'D': y += UNIT_SIZE; break;
            case 'L': x -= UNIT_SIZE; break;
            case 'R': x += UNIT_SIZE; break;
        }

        if (mode.equals("classic")) {
            if (x < 0) x = WIDTH - UNIT_SIZE;
            else if (x >= WIDTH) x = 0;
            if (y < 0) y = HEIGHT - UNIT_SIZE;
            else if (y >= HEIGHT) y = 0;
        } else {
            if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
                running = false;
                return;
            }
        }

        Point newHead = new Point(x, y);
        if (mode.equals("hard") && hurdles.contains(newHead)) {
            running = false;
            return;
        }

        if (snake.contains(newHead)) {
            running = false;
            return;
        }

        snake.addFirst(newHead);

        if (newHead.equals(food)) {
            score++;
            placeFood();
        } else {
            snake.removeLast();
        }
    }

    private void updateHighScore() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/snakegame", "root", "");
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE users SET high_score = ?, score_time = CURRENT_TIMESTAMP WHERE email = ? AND (high_score IS NULL OR ? > high_score)"
             )) {
            ps.setInt(1, score);
            ps.setString(2, userEmail);
            ps.setInt(3, score);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  if (direction != 'R') direction = 'L'; break;
            case KeyEvent.VK_RIGHT: if (direction != 'L') direction = 'R'; break;
            case KeyEvent.VK_UP:    if (direction != 'D') direction = 'U'; break;
            case KeyEvent.VK_DOWN:  if (direction != 'U') direction = 'D'; break;
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
