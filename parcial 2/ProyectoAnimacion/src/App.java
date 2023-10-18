import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class App extends JPanel {
    private double angle = 0.0;
    private Timer timer;

    public App() {
        timer = new Timer(30, e -> {
            angle += Math.PI / 90; // Cambia la velocidad de rotación aquí
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int squareSize = 200;
        int x = getWidth() / 2 - squareSize / 2;
        int y = getHeight() / 2 - squareSize / 2;

        Graphics2D g2d = (Graphics2D) g;

        // Aplica la transformación de rotación
        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, x + squareSize / 2, y + squareSize / 2);
        g2d.setTransform(transform);

        // Dibuja el cuadrado
        g2d.setColor(Color.BLACK);
        g2d.fillRect(x, y, squareSize, squareSize);

        // Dibuja las figuras dentro del cuadrado
        g2d.setColor(Color.RED);
        g2d.fillOval(x + 10, y + 10, squareSize / 2 - 20, squareSize / 2 - 20);

        g2d.setColor(Color.GREEN);
        g2d.fillOval(x + squareSize / 2 + 10, y + 10, squareSize / 2 - 20, squareSize / 2 - 20);

        g2d.setColor(Color.BLUE);
        g2d.fillOval(x + 10, y + squareSize / 2 + 10, squareSize / 2 - 20, squareSize / 2 - 20);

        g2d.setColor(Color.ORANGE);
        g2d.fillOval(x + squareSize / 2 + 10, y + squareSize / 2 + 10, squareSize / 2 - 20, squareSize / 2 - 20);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rotating Square");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new App());
            frame.setSize(400, 400);
            frame.setVisible(true);
        });
    }
}
