import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Pixel extends JFrame {
    
    private BufferedImage buffer;
    private Graphics2D graPixel;

    public Pixel() {
        
    }

    public void initPixel() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializa el buffer con las mismas dimensiones que la ventana
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        // Establece el color de fondo del buffer
        Graphics2D g2d = (Graphics2D) graPixel;
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    public void clearScreen() {
        graPixel.clearRect(0, 0, getWidth(), getHeight());
    }
}
