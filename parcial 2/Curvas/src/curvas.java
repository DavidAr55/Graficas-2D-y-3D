import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class curvas extends JFrame {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    public curvas() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        Graphics2D g2d = (Graphics2D) graPixel;
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());

        // Dibuja la curva y = sin(x)
        curvaUno();

        // Dibuja la curva x = y * cos(4 * y)
        curvaDos();
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    // Algoritmo de Bresenham para dibujar una línea
    public void drawLineBresenham(int x1, int y1, int x2, int y2, Color c) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x1, y1, c);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }
            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
    }

    public void curvaUno() {
        int numPuntos = 100; // Numero de puntos en la grafica
        double intervalo = Math.PI / (numPuntos - 1);
    
        int[] xPoints = new int[numPuntos];
        int[] yPoints = new int[numPuntos];
    
        for (int i = 0; i < numPuntos; i++) {
            double x = i * intervalo;
            double y = Math.sin(x);

            xPoints[i] = (int) (x * (getWidth() - 1) / Math.PI);
            yPoints[i] = (int) ((1 - y) * (getHeight() - 1) / 2);
        }
    
        graPixel.setColor(Color.BLACK);
        for (int i = 0; i < numPuntos - 1; i++) {
            drawLineBresenham(xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1], Color.BLUE);
            putPixel(xPoints[i], yPoints[i], Color.RED);
        }
    
        repaint();
    }

    public void curvaDos() {
        int numPuntos = 100; // Numero de puntos en la grafica
        double intervalo = 2.0 * Math.PI / (numPuntos - 1);
    
        int[] xPoints = new int[numPuntos];
        int[] yPoints = new int[numPuntos];
    
        for (int i = 0; i < numPuntos; i++) {
            double y = i * intervalo;
            double x = y * Math.cos(4 * y);
    
            xPoints[i] = (int) ((x + 2 * Math.PI) * (getWidth() - 1) / (4 * Math.PI));
            yPoints[i] = (int) ((1 - y / (2 * Math.PI)) * (getHeight() - 1));
        }
    
        graPixel.setColor(Color.BLACK);
        for (int i = 0; i < numPuntos - 1; i++) {
            drawLineBresenham(xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1], Color.BLUE);
            putPixel(xPoints[i], yPoints[i], Color.RED);
        }
    
        repaint();
    }     

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    public static void main(String[] args) throws Exception {
        curvas curvas = new curvas();
        curvas.setVisible(true);
    }
}
