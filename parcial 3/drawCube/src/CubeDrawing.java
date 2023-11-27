import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CubeDrawing extends JFrame {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    public CubeDrawing() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        Graphics2D g2d = (Graphics2D) graPixel;
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());

        drawCenteredCube();
    }

    public void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            buffer.setRGB(x, y, c.getRGB());
        }
    }

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

    public void drawCube(int x, int y, int size) {
        int x1 = x, y1 = y;
        int x2 = x + size, y2 = y;
        int x3 = x + size, y3 = y + size;
        int x4 = x, y4 = y + size;

        int x5 = x + size / 3, y5 = y - size / 3;
        int x6 = x2 + size / 3, y6 = y - size / 3;
        int x7 = x2 + size / 3, y7 = y2 - size / 3;
        int x8 = x + size / 3, y8 = y2 - size / 3;

        drawLineBresenham(x1, y1, x2, y2, Color.BLACK);
        drawLineBresenham(x2, y2, x3, y3, Color.BLACK);
        drawLineBresenham(x3, y3, x4, y4, Color.BLACK);
        drawLineBresenham(x4, y4, x1, y1, Color.BLACK);

        drawLineBresenham(x5, y5, x6, y6, Color.BLACK);
        drawLineBresenham(x6, y6, x7, y7, Color.BLACK);
        drawLineBresenham(x7, y7, x8, y8, Color.BLACK);
        drawLineBresenham(x8, y8, x5, y5, Color.BLACK);

        drawLineBresenham(x1, y1, x5, y5, Color.BLACK);
        drawLineBresenham(x2, y2, x6, y6, Color.BLACK);
        drawLineBresenham(x3, y3, x7, y7, Color.BLACK);
        drawLineBresenham(x4, y4, x8, y8, Color.BLACK);
    }

    public void drawCenteredCube() {
        int cubeSize = 200;
        int centerX = (getWidth() - cubeSize) / 2;
        int centerY = (getHeight() - cubeSize) / 2;

        drawCube(centerX, centerY, cubeSize);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(buffer, 0, 0, this);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CubeDrawing cube = new CubeDrawing();
            cube.setVisible(true);
        });
    }
}
