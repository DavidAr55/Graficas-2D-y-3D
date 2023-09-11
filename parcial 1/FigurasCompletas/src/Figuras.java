import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Figuras extends JFrame {

    private BufferedImage buffer;

    public Figuras() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Crear un BufferedImage con el mismo tamaño que el JFrame
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(buffer, 0, 0, this);
    }

    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;
        int err2;

        while (true) {
            putPixel(x1, y1, c);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            err2 = 2 * err;

            if (err2 > -dy) {
                err -= dy;
                x1 += sx;
            }

            if (err2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
        repaint(); // Vuelve a pintar el JFrame para mostrar la línea
    }

    public void drawCircle(int centerX, int centerY, int radius, Color c) {
        int x = radius;
        int y = 0;
        int err = 0;

        while (x >= y) {
            putPixel(centerX + x, centerY + y, c);
            putPixel(centerX + y, centerY + x, c);
            putPixel(centerX - y, centerY + x, c);
            putPixel(centerX - x, centerY + y, c);
            putPixel(centerX - x, centerY - y, c);
            putPixel(centerX - y, centerY - x, c);
            putPixel(centerX + y, centerY - x, c);
            putPixel(centerX + x, centerY - y, c);

            if (err <= 0) {
                y++;
                err += 2 * y + 1;
            } else {
                x--;
                err -= 2 * x + 1;
            }
        }
    }

    public void drawPolarCircle(int centerX, int centerY, int radius, Color color) {
        double increment = 0.01; // Incremento pequeño para varíar el ángulo
        for (double theta = 0; theta < 2 * Math.PI; theta += increment) {
            int x = centerX + (int) (radius * Math.cos(theta));
            int y = centerY + (int) (radius * Math.sin(theta));
            putPixel(x, y, color);
        }
    }

    public void drawRectangle(int x1, int y1, int x2, int y2, Color c) {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int maxX = Math.max(x1, x2);
        int maxY = Math.max(y1, y2);

        for (int x = minX; x <= maxX; x++) {
            putPixel(x, minY, c);
            putPixel(x, maxY, c);
        }

        for (int y = minY; y <= maxY; y++) {
            putPixel(minX, y, c);
            putPixel(maxX, y, c);
        }
    }

    public void drawEllipse(int centerX, int centerY, int a, int b, Color c) {
        int x = 0;
        int y = b;
        double a2 = a * a;
        double b2 = b * b;
        double d = b2 - a2 * (b - 0.25);
        
        while (b2 * (x + 1) < a2 * (y - 0.5)) {
            putPixel(centerX + x, centerY - y, c);
            putPixel(centerX - x, centerY - y, c);
            putPixel(centerX + x, centerY + y, c);
            putPixel(centerX - x, centerY + y, c);
            
            if (d < 0) {
                d += b2 * (2 * x + 3);
            } else {
                d += (b2 * (2 * x + 3) + a2 * (-2 * y + 2));
                y--;
            }
            x++;
        }
        
        d = b2 * (x + 0.5) * (x + 0.5) + a2 * (y - 1) * (y - 1) - a2 * b2;
        
        while (y >= 0) {
            putPixel(centerX + x, centerY - y, c);
            putPixel(centerX - x, centerY - y, c);
            putPixel(centerX + x, centerY + y, c);
            putPixel(centerX - x, centerY + y, c);
            
            if (d < 0) {
                d += b2 * (2 * x + 2) + a2 * (-2 * y + 3);
                x++;
            } else {
                d += a2 * (-2 * y + 3);
            }
            y--;
        }
    }    

    public static void main(String[] args) {
        Figuras figurasFrame = new Figuras();
        figurasFrame.setVisible(true);
    }
}
