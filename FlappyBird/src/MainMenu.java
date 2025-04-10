import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private JButton startButton;
    private JButton exitButton;
    private Image backgroundImage;
    private Image birdImage;

    // Untuk animasi burung
    private Timer animationTimer;
    private int birdY = 250;
    private boolean birdGoingUp = false;
    private final int ANIMATION_SPEED = 2;

    public MainMenu() {
        setTitle("Flappy Bird");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 640);
        setLocationRelativeTo(null);
        setResizable(false);

        // Load gambar
        try {
            backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("assets/background.png"))).getImage();
            birdImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("assets/bird.png"))).getImage();
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }

        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Gambar background
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

                // Shadow untuk judul (efek 3D)
                g.setFont(new Font("Arial", Font.BOLD, 48));
                g.setColor(new Color(0, 0, 0, 100));
                String title = "FLAPPY BIRD";
                FontMetrics fm = g.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(title)) / 2 + 3;
                g.drawString(title, x, 103);

                // Judul utama
                g.setColor(Color.YELLOW);
                g.drawString(title, x - 3, 100);

                // Gambar burung (animated)
                g.drawImage(birdImage, getWidth()/2 - 17, birdY, 34, 24, this);

                // Dekorasi garis
                g.setColor(new Color(255, 255, 255, 150));
                g.drawLine(50, 130, getWidth() - 50, 130);
                g.drawLine(50, 450, getWidth() - 50, 450);
            }
        };
        mainPanel.setLayout(null);

        // Start button dengan efek hover
        startButton = createStyledButton("Start Game", new Color(76, 175, 80), new Color(56, 142, 60));
        startButton.setBounds(90, 320, 180, 50);
        startButton.addActionListener(e -> startGame());

        // Exit button
        exitButton = createStyledButton("Exit", new Color(244, 67, 54), new Color(211, 47, 47));
        exitButton.setBounds(90, 390, 180, 50);
        exitButton.addActionListener(e -> System.exit(0));

        // Label untuk pembuat
        JLabel creatorLabel = new JLabel();
        creatorLabel.setForeground(Color.WHITE);
        creatorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        creatorLabel.setBounds(0, 560, 360, 20);

        // Menambahkan komponen ke panel
        mainPanel.add(startButton);
        mainPanel.add(exitButton);
        mainPanel.add(creatorLabel);

        // Animasi burung sederhana
        animationTimer = new Timer(50, e -> {
            if (birdGoingUp) {
                birdY -= ANIMATION_SPEED;
                if (birdY < 240) {
                    birdGoingUp = false;
                }
            } else {
                birdY += ANIMATION_SPEED;
                if (birdY > 260) {
                    birdGoingUp = true;
                }
            }
            mainPanel.repaint();
        });
        animationTimer.start();

        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color baseColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);

        // Efek hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        return button;
    }

    private void startGame() {
        animationTimer.stop();
        this.dispose();

        JFrame frame = new JFrame("Flappy Bird");
        FlappyBird game = new FlappyBird();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            // Set tampilan UI ke sistem operasi
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainMenu());
    }
}