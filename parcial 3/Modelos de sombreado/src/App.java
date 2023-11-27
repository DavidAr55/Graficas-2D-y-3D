import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class App extends JFrame {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    // Coordenadas del plano 3D
    private int[] xPoints = {50, 150, 150, 50};
    private int[] yPoints = {0, 0, 150, 150};
    private int[] zPoints = {0, 0, 0, 0};

    // Angulos de la camara
    private double thetaX = Math.toRadians(45); // Angulo de rotación en el eje X
    private double thetaY = Math.toRadians(30); // Angulo de rotación en el eje Y

    public App() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        // Inicializa el plano 3D con el color verde
        graPixel.setColor(Color.GREEN);
        graPixel.fillPolygon(xPoints, yPoints, 4);

        // Aplica las transformaciones de proyección
        applyProjection();

        // Dibuja la escena
        drawScene();
    }

    private void applyProjection() {
        // Realiza transformaciones de proyección en el plano 3D
        for (int i = 0; i < xPoints.length; i++) {
            // Rotación en el eje X
            int y = (int) (yPoints[i] * Math.cos(thetaX) - zPoints[i] * Math.sin(thetaX));
            int z = (int) (yPoints[i] * Math.sin(thetaX) + zPoints[i] * Math.cos(thetaX));

            yPoints[i] = y;
            zPoints[i] = z;

            // Rotación en el eje Y
            int x = (int) (xPoints[i] * Math.cos(thetaY) + zPoints[i] * Math.sin(thetaY));
            z = (int) (-xPoints[i] * Math.sin(thetaY) + zPoints[i] * Math.cos(thetaY));

            xPoints[i] = x;
            zPoints[i] = z;
        }
    }

    private void drawScene() {
        // Dibuja el plano 3D transformado en el buffer
        graPixel.setColor(Color.GREEN);
        graPixel.fillPolygon(xPoints, yPoints, 4);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.setVisible(true);
    }
}