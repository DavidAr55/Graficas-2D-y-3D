import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RotatingSquare extends JFrame {

    private BufferedImage buffer;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private double angle = 0.0;

    public RotatingSquare() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Llamar a un temporizador para animar la rotación
        Timer timer = new Timer(10, e -> {
            angle += 0.01; // Incrementa el ángulo de rotación
            drawRotatedSquare();
        });
        timer.start();
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    public void drawRotatedSquare() {
        int size = 100;
        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;
        Color color = Color.RED;
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        // Limpia el búfer
        Graphics2D g2d = buffer.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        // Calcula las coordenadas del cuadrado rotado
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];

        for (int i = 0; i < 4; i++) {
            int x = i % 2 == 0 ? -size / 2 : size / 2;
            int y = i < 2 ? -size / 2 : size / 2;

            // Aplica la rotación usando matrices de rotación
            xPoints[i] = (int) (centerX + x * cos - y * sin);
            yPoints[i] = (int) (centerY + x * sin + y * cos);
        }

        // Dibuja el cuadrado rotado en el búfer
        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, 4);
        g2d.dispose();

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(buffer, 0, 0, this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RotatingSquare());
    }
}
