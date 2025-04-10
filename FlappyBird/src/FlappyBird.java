import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    int frameWidth = 360;
    int frameHeight = 640;

    // image attributes
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    // player attributes
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    // pipes attributes
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;

    // game logic
    Timer gameLoop;
    Timer pipeSpawner;
    int gravity = 1;

    // game state and score
    private boolean gameOver = false;
    private int score = 0;
    private JLabel scoreLabel;

    // constructor
    public FlappyBird() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        addKeyListener(this);
        setLayout(null); // Untuk posisi absolut pada JLabel
        setBackground(Color.blue);

        // Score label
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(10, 10, 100, 30);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreLabel);

        // load images
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        // instantiate player
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);

        // instantiate pipes list
        pipes = new ArrayList<>();
        placePipes(); // place initial pipes

        // main game loop
        gameLoop = new Timer(1000 / 60, this); // ~60 FPS
        gameLoop.start();

        // pipe spawner (every 1.5 seconds)
        pipeSpawner = new Timer(1500, e -> placePipes());
        pipeSpawner.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // draw background
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);

        // draw player
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        // draw pipes
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        // Show game over message
        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 150)); // ketika gameover maka si tampilanya jadi hitam semi transparan
            g.fillRect(0, 0, frameWidth, frameHeight);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String gameOverText = "Game Over";
            FontMetrics fm = g.getFontMetrics();
            int textX = (frameWidth - fm.stringWidth(gameOverText)) / 2;
            g.drawString(gameOverText, textX, frameHeight / 2 - 50);

            g.setFont(new Font("Arial", Font.BOLD, 24));
            String finalScore = "Score: " + score;
            fm = g.getFontMetrics();
            textX = (frameWidth - fm.stringWidth(finalScore)) / 2;
            g.drawString(finalScore, textX, frameHeight / 2);

            g.setFont(new Font("Arial", Font.PLAIN, 18));
            String restartText = "Press 'R' to restart";
            fm = g.getFontMetrics();
            textX = (frameWidth - fm.stringWidth(restartText)) / 2;
            g.drawString(restartText, textX, frameHeight / 2 + 50);
        }
    }

    public void move() {
        // move player
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        // pindahkan pipa ke kiri
        for (Pipe pipe : pipes) {
            pipe.setPosX(pipe.getPosX() - 2); // move speed
        }

        // hapus pipa yang keluar dari layar
        pipes.removeIf(pipe -> pipe.getPosX() + pipe.getWidth() < 0);
    }

    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        // pipa atas
        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        // pipa bawah
        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + pipeHeight + openingSpace), pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public boolean checkCollision() {
        // Periksa apakah pemain menyentuh tanah
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            return true;
        }

        // Periksa tabrakan pipa
        for (Pipe pipe : pipes) {
            // Create bounding box for player
            Rectangle playerBox = new Rectangle(
                    player.getPosX(),
                    player.getPosY(),
                    player.getWidth(),
                    player.getHeight()
            );

            // Buat kotak pembatas untuk pipa
            Rectangle pipeBox = new Rectangle(
                    pipe.getPosX(),
                    pipe.getPosY(),
                    pipe.getWidth(),
                    pipe.getHeight()
            );

            // Periksa persimpangan
            if (playerBox.intersects(pipeBox)) {
                return true;
            }
        }

        return false;
    }

    public void updateScore() {
        for (Pipe pipe : pipes) {
            // Periksa apakah pemain telah melewati pipa dan belum dihitung
            if (!pipe.getPassed() && pipe.getPosX() + pipe.getWidth() < player.getPosX()) {
                pipe.setPassed(true);
                // Hitung hanya satu pipa dari setiap pasangan (pipa atas)
                if (pipe.getPosY() <= 0) {
                    score++;
                    scoreLabel.setText("Score: " + score);
                }
            }
        }
    }

    public void restartGame() {
        // Setel ulang permainan
        gameOver = false;
        score = 0;
        scoreLabel.setText("Score: 0");

        // Atur ulang posisi pemain
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);

        // Bersihkan pipa yang ada
        pipes.clear();

        // Tambahkan pipa awal
        placePipes();

        // Mulai ulang waktu
        gameLoop.start();
        pipeSpawner.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            updateScore();

            // Check for collisions
            if (checkCollision()) {
                gameOver = true;
                pipeSpawner.stop();
                gameLoop.stop();
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            player.setVelocityY(-10); // jump
        }

        // Mulai ulang permainan dengan tombol 'R' saat permainan berakhir
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}