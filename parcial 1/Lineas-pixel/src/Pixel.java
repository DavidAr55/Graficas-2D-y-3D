import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Pixel extends JFrame {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    public Pixel() {

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        clearBuffer(Color.WHITE);  // Establecer el color de fondo

        graPixel = (Graphics2D) buffer.getGraphics();
    }

    public void clearBuffer(Color bgColor) {
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                buffer.setRGB(x, y, bgColor.getRGB());
            }
        }
        repaint();
    }    

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(buffer, 0, 0, this);
    }

    public void linea_DDA(int x1, int y1, int x2, int y2, Color c) {
        
        float steps, x, y;
        int k;

        float dx = x2 - x1;
        float dy = y2 - y1;

        if(Math.abs(dx) > Math.abs(dy)) 
            steps = Math.abs(dx);

        else
            steps = Math.abs(dy);

        dx /= steps;
        dy /= steps;

        x = x1;
        y = y1;

        for(k = 1; k <= steps; k++) {
            x += dx;
            y += dy;
            
            putPixel((int) x, (int) y, c);
        }
    }

    public void linea_Recta(int x1, int y1, int x2, int y2, Color c) {
        int dx = x2 - x1;
        int dy = y2 - y1;

        float a = (float) dy / dx;
        float b = y1 - a * x1;

        for(int xi = x1; xi <= x2; xi++) {
            int y = (int) (a * xi + b);
            putPixel(xi, y, c);
        }
    }
}
