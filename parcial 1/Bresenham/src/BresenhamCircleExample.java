import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BresenhamCircleExample extends JPanel {

    private BufferedImage canvas;

    public BresenhamCircleExample(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        drawCircle(width / 2, height / 2, 100); // Dibuja un círculo en el centro
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, this);
    }

    // Función para dibujar un píxel en el lienzo
    private void plotPixel(int x, int y) {
        if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight()) {
            canvas.setRGB(x, y, Color.WHITE.getRGB()); // Colorea el píxel de blanco
        }
    }

    // Algoritmo de Bresenham para dibujar un círculo
    private void drawCircle(int centerX, int centerY, int radius) {
        int x = 0;
        int y = radius;
        int d = 3 - 2 * radius;

        while (x <= y) {
            plotPixel(centerX + x, centerY + y);
            plotPixel(centerX - x, centerY + y);
            plotPixel(centerX + x, centerY - y);
            plotPixel(centerX - x, centerY - y);
            plotPixel(centerX + y, centerY + x);
            plotPixel(centerX - y, centerY + x);
            plotPixel(centerX + y, centerY - x);
            plotPixel(centerX - y, centerY - x);

            if (d <= 0) {
                d = d + 4 * x + 6;
            } else {
                d = d + 4 * (x - y) + 10;
                y--;
            }
            x++;
        }
    }

    public static void main(String[] args) {
        int width = 400;
        int height = 400;

        JFrame frame = new JFrame("Bresenham Circle Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.add(new BresenhamCircleExample(width, height));
        frame.setVisible(true);
    }
}