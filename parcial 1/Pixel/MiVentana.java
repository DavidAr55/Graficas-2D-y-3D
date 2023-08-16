package Pixel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class MiVentana extends JFrame {
    
    private BufferedImage buffer;
    private Graphics graPixel;

    public MiVentana() {

        setSize(500, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_BGR);
        graPixel = (Graphics2D) buffer.createGraphics();
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(0,0,c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        putPixel(100, 100, Color.red);
    }
}
