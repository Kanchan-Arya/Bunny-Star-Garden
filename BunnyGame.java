import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class BunnyGame extends JPanel implements ActionListener, KeyListener {

    final int W = 700, H = 500;
    Timer timer = new Timer(20, this);
    Random random = new Random();

    int bunnyX = 320, bunnyY = 370;
    int starX, starY;
    int cloudX, cloudY = 90;

    int score = 0;
    int lives = 3;
    int tick = 0;

    boolean left, right, up, down;
    boolean gameOver = false;

    Color pink = new Color(255, 190, 218);
    Color lavender = new Color(220, 200, 255);
    Color cream = new Color(255, 248, 224);

    public BunnyGame() {
        setPreferredSize(new Dimension(W, H));
        setFocusable(true);
        addKeyListener(this);
        resetStar();
        timer.start();
    }

    void resetStar() {
        starX = 30 + random.nextInt(W - 60);
        starY = 75 + random.nextInt(H - 130);
    }

    void drawHeart(Graphics2D g, int x, int y, int size) {
        g.setColor(new Color(255, 100, 160));
        g.fillOval(x, y, size / 2, size / 2);
        g.fillOval(x + size / 2, y, size / 2, size / 2);
        int[] xs = {x, x + size, x + size / 2};
        int[] ys = {y + size / 4, y + size / 4, y + size};
        g.fillPolygon(xs, ys, 3);
    }

    void drawBunny(Graphics2D g, int x, int y) {
        // Shadow
        g.setColor(new Color(180, 145, 180, 80));
        g.fillOval(x + 4, y + 44, 48, 12);

        // Ears
        g.setColor(Color.WHITE);
        g.fillRoundRect(x + 7, y - 20, 15, 38, 12, 12);
        g.fillRoundRect(x + 29, y - 20, 15, 38, 12, 12);

        g.setColor(new Color(255, 160, 195));
        g.fillRoundRect(x + 11, y - 15, 7, 27, 8, 8);
        g.fillRoundRect(x + 33, y - 15, 7, 27, 8, 8);

        // Body and head
        g.setColor(Color.WHITE);
        g.fillOval(x + 5, y + 20, 48, 35);
        g.fillOval(x, y, 58, 48);

        // Cheeks
        g.setColor(new Color(255, 175, 195));
        g.fillOval(x + 5, y + 25, 13, 8);
        g.fillOval(x + 40, y + 25, 13, 8);

        // Eyes
        g.setColor(new Color(75, 55, 75));
        g.fillOval(x + 15, y + 17, 5, 7);
        g.fillOval(x + 38, y + 17, 5, 7);

        // Nose
        g.setColor(new Color(255, 130, 170));
        g.fillOval(x + 26, y + 26, 7, 5);

        // Tiny smile
        g.setColor(new Color(100, 70, 90));
        g.drawArc(x + 23, y + 29, 7, 6, 200, 140);

        // Bow
        g.setColor(new Color(255, 100, 160));
        g.fillOval(x + 34, y - 5, 12, 10);
        g.fillOval(x + 43, y - 5, 12, 10);
        g.fillOval(x + 41, y - 3, 7, 7);
    }

    void drawStar(Graphics2D g, int x, int y) {
        int[] xs = new int[10];
        int[] ys = new int[10];

        for (int i = 0; i < 10; i++) {
            double angle = Math.PI * i / 5 - Math.PI / 2;
            int r = (i % 2 == 0) ? 18 : 8;
            xs[i] = x + (int)(Math.cos(angle) * r);
            ys[i] = y + (int)(Math.sin(angle) * r);
        }

        g.setColor(new Color(255, 190, 50, 70));
        g.fillOval(x - 23, y - 23, 46, 46);
        g.setColor(new Color(255, 202, 65));
        g.fillPolygon(xs, ys, 10);
        g.setColor(Color.WHITE);
        g.fillOval(x - 4, y - 10, 4, 4);
    }

    void drawCloud(Graphics2D g, int x, int y) {
        g.setColor(new Color(155, 145, 190));
        g.fillOval(x, y + 12, 65, 25);
        g.fillOval(x + 12, y, 28, 32);
        g.fillOval(x + 32, y + 4, 30, 30);

        g.setColor(new Color(125, 105, 150));
        g.fillOval(x + 20, y + 20, 4, 5);
        g.fillOval(x + 39, y + 20, 4, 5);
        g.drawArc(x + 25, y + 24, 14, 8, 200, 140);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();

        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Dreamy sky
        g.setPaint(new GradientPaint(
            0, 0, new Color(255, 220, 238),
            0, H, new Color(220, 210, 255)
        ));
        g.fillRect(0, 0, W, H);

        // Floating sparkles
        for (int i = 0; i < 22; i++) {
            int x = (i * 83 + 21) % W;
            int y = (i * 57 + 45) % 310;
            g.setColor(new Color(255, 255, 255, 180));
            g.fillOval(x, y, 4, 4);
        }

        // Garden hills
        g.setColor(new Color(185, 225, 185));
        g.fillOval(-100, 300, 500, 260);
        g.setColor(new Color(165, 215, 175));
        g.fillOval(270, 320, 540, 230);

        // Flowers
        for (int i = 0; i < 13; i++) {
            int x = (i * 59 + 10) % W;
            int y = 425 + (i % 3) * 16;

            g.setColor(new Color(90, 165, 100));
            g.drawLine(x + 5, y, x + 5, y + 17);

            g.setColor(i % 2 == 0 ? pink : lavender);
            for (int p = 0; p < 5; p++) {
                double a = p * Math.PI * 2 / 5;
                int px = x + 5 + (int)(Math.cos(a) * 6);
                int py = y + (int)(Math.sin(a) * 6);
                g.fillOval(px - 4, py - 4, 8, 8);
            }

            g.setColor(new Color(255, 220, 90));
            g.fillOval(x + 1, y - 4, 8, 8);
        }

        // HUD
        g.setColor(new Color(255, 255, 255, 210));
        g.fillRoundRect(18, 15, 255, 42, 20, 20);

        g.setFont(new Font("SansSerif", Font.BOLD, 19));
        g.setColor(new Color(125, 80, 125));
        g.drawString("BUNNY STAR GARDEN", 30, 42);

        g.setFont(new Font("SansSerif", Font.BOLD, 17));
        g.drawString("⭐ " + score + "    ♥ " + lives, 555, 37);

        // Collectible star and grumpy cloud
        if (!gameOver) {
            drawStar(g, starX, starY);
            drawCloud(g, cloudX, cloudY);
        }

        // Bunny
        drawBunny(g, bunnyX, bunnyY);

        // Instructions
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g.setColor(new Color(110, 80, 120));
        g.drawString("Move with ARROW KEYS  •  Collect stars  •  Avoid the grumpy cloud!", 55, 485);

        if (gameOver) {
            g.setColor(new Color(80, 50, 90, 130));
            g.fillRect(0, 0, W, H);

            g.setColor(cream);
            g.fillRoundRect(150, 145, 400, 205, 35, 35);

            g.setColor(new Color(170, 105, 150));
            g.setFont(new Font("SansSerif", Font.BOLD, 34));
            g.drawString("Oh no, little bunny!", 185, 205);

            g.setFont(new Font("SansSerif", Font.PLAIN, 22));
            g.drawString("Stars collected: " + score, 260, 250);

            g.setFont(new Font("SansSerif", Font.BOLD, 19));
            g.drawString("Press R to play again ♡", 225, 300);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            tick++;

            if (left) bunnyX -= 5;
            if (right) bunnyX += 5;
            if (up) bunnyY -= 5;
            if (down) bunnyY += 5;

            bunnyX = Math.max(0, Math.min(W - 58, bunnyX));
            bunnyY = Math.max(65, Math.min(H - 90, bunnyY));

            // Cloud floats back and forth
            cloudX += (tick % 300 < 150) ? 3 : -3;
            cloudX = Math.max(0, Math.min(W - 65, cloudX));

            // Collect stars
            Rectangle bunny = new Rectangle(
                bunnyX, bunnyY, 58, 55
            );
            Rectangle star = new Rectangle(
                starX - 14, starY - 14, 28, 28
            );

            if (bunny.intersects(star)) {
                score++;
                resetStar();
            }

            // Cloud collision
            Rectangle cloud = new Rectangle(
                cloudX, cloudY + 5, 65, 32
            );

            if (bunny.intersects(cloud)) {
                lives--;
                bunnyX = 320;
                bunnyY = 370;
                cloudX = random.nextInt(W - 65);

                if (lives <= 0) {
                    gameOver = true;
                }
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();

        if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A) left = true;
        if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D) right = true;
        if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W) up = true;
        if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) down = true;

        if (gameOver && k == KeyEvent.VK_R) {
            score = 0;
            lives = 3;
            bunnyX = 320;
            bunnyY = 370;
            cloudX = 0;
            tick = 0;
            gameOver = false;
            resetStar();
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();

        if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A) left = false;
        if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D) right = false;
        if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W) up = false;
        if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) down = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bunny Star Garden ♡");
            BunnyGame game = new BunnyGame();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(game);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            game.requestFocusInWindow();
        });
    }
}