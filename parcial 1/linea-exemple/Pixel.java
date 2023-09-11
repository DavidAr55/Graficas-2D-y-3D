import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Pixel extends JFrame {

    private BufferedImage buffer;
    private Graphics graPixel;

    public Pixel() {
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_BGR);
        graPixel = (Graphics2D) buffer.createGraphics();
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
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

            int err2 = 2 * err;

            if (err2 > -dy) {
                err -= dy;
                x1 += sx;
            }

            if (err2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        putPixel(100, 100, Color.red); // Primer punto en (100, 100) de color rojo
        putPixel(200, 500, Color.blue); // Segundo punto en (200, 200) de color azul
        drawLine(100, 100, 200, 500, Color.green); // Dibuja una línea entre los dos puntos en verde usando Bresenham
    }

    public static void main(String[] args) {
        Pixel pixelFrame = new Pixel();
        pixelFrame.setVisible(true);
    }
}
