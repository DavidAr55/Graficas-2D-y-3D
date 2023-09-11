import java.awt.Color;
import javax.swing.JFrame;

public class Rectangulo extends JFrame {

    public Rectangulo() {
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
    }

    public void putPixel(int x, int y, Color c) {
        getGraphics().setColor(c);
        getGraphics().drawRect(x, y, 1, 1);
    }

    public void drawRectangle(int x1, int y1, int x2, int y2, Color c) {
        /*int minX = Math.min(x1, x2);
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
        }*/

        // Esto va a dibujar la linea de arriba
        for(int i = x1; i <= x2; i++)
            putPixel(i, y1, Color.RED);

        // Esto dibuja la linea de abajo
        for(int i = x1; i <= x2; i++)
            putPixel(i, y2, Color.BLUE);

        // Esto dibuja la linea izquierda
        for(int i = y1; i <= y2; i++)
            putPixel(x1, i, Color.GREEN);

        // Esto dibuja la linea derecha
        for(int i = y1; i <= y2; i++)
            putPixel(x2, i, Color.PINK);
    }

    public void paint(java.awt.Graphics g) {
        super.paint(g);
        drawRectangle(50, 100, 400, 300, Color.green);
    }
}
