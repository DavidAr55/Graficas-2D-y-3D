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

    public void drawEllipse(int centerX, int centerY, int rx, int ry, Color c) {
        double angleStep = 0.01; // Suavidad de la curva
        for (double angle = 0; angle < 2 * Math.PI; angle += angleStep) {
            int x = (int) (centerX + rx * Math.cos(angle));
            int y = (int) (centerY + ry * Math.sin(angle));
            putPixel(x, y, c);
        }
    }

    // Algoritmo de Bresenham para dibujar un círculo
    public void drawCircleBresenham(int centerX, int centerY, int radius, Color c) {
        int x = 0;
        int y = radius;
        int d = 3 - 2 * radius;

        while (x <= y) {
            putPixel(centerX + x, centerY + y, c);
            putPixel(centerX - x, centerY + y, c);
            putPixel(centerX + x, centerY - y, c);
            putPixel(centerX - x, centerY - y, c);
            putPixel(centerX + y, centerY + x, c);
            putPixel(centerX - y, centerY + x, c);
            putPixel(centerX + y, centerY - x, c);
            putPixel(centerX - y, centerY - x, c);

            if (d < 0) {
                d = d + 4 * x + 6;
            } else {
                d = d + 4 * (x - y) + 10;
                y--;
            }
            x++;
        }
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
   

    public static void main(String[] args) {
        Figuras figurasFrame = new Figuras();
        figurasFrame.setVisible(true);
    }
}
