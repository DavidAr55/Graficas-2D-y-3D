import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferMovimiento extends JFrame {
    
    private BufferedImage buffer;
    private Graphics2D graPixel;

    public BufferMovimiento() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Inicializa el buffer con las mismas dimensiones que la ventana
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        graPixel = buffer.createGraphics();

        buffer = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D containerCube = buffer.createGraphics();

        containerCube.setColor(Color.RED);
        containerCube.fillRect(0, 0, 50, 50);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    public static void main(String[] args) throws Exception {
        BufferMovimiento bufferMovimiento = new BufferMovimiento();
        bufferMovimiento.setVisible(true);
    }
}
