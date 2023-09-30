import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Poligono extends JFrame {
    private BufferedImage buffer;
    private Graphics2D graPixel;

    public Poligono() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
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

    public void drawPolygon(int[] xPoints, int[] yPoints, Color c) {
        int numVertices = xPoints.length;

        for (int i = 0; i < numVertices; i++) {
            int x1 = xPoints[i];
            int y1 = yPoints[i];
            int x2 = xPoints[(i + 1) % numVertices];
            int y2 = yPoints[(i + 1) % numVertices];

            drawLineBresenham(x1, y1, x2, y2, c);
        }

        repaint();
    }

    public void fillPolygon(int[] xPoints, int[] yPoints, Color c) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
    
        // Encontrar el rango horizontal del polígono
        for (int x : xPoints) {
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
        }
    
        List<Integer> intersections = new ArrayList<>();
    
        // Escanear cada línea vertical dentro del rango horizontal
        for (int x = minX; x <= maxX; x++) {
            intersections.clear();
    
            for (int i = 0; i < xPoints.length; i++) {
                int x1 = xPoints[i];
                int y1 = yPoints[i];
                int x2 = xPoints[(i + 1) % xPoints.length];
                int y2 = yPoints[(i + 1) % xPoints.length];
    
                if ((x1 <= x && x2 > x) || (x2 <= x && x1 > x)) {
                    // Calcula la intersección vertical con la línea
                    double y = y1 + (double) (x - x1) * (y2 - y1) / (x2 - x1);
                    intersections.add((int) y);
                }
            }
    
            // Ordena las intersecciones de arriba a abajo
            intersections.sort(Integer::compareTo);
    
            // Rellena el espacio entre las intersecciones
            for (int i = 0; i < intersections.size(); i += 2) {
                int startY = intersections.get(i);
                int endY = intersections.get(i + 1);
                for (int y = startY; y < endY; y++) {
                    putPixel(x, y, c);
                }

                repaint();

            }
        }
    }   
    
    public void fillPolygonInundation(int x, int y, Color fill_color, Color boundary_color) {
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
            return; // Salir si estamos fuera de los límites de la ventana
        }

        int pixelColor = buffer.getRGB(x, y);

        if (pixelColor != boundary_color.getRGB() && pixelColor != fill_color.getRGB()) {
            putPixel(x, y, fill_color);
            fillPolygonInundation(x + 1, y, fill_color, boundary_color);
            fillPolygonInundation(x - 1, y, fill_color, boundary_color);
            fillPolygonInundation(x, y + 1, fill_color, boundary_color);
            fillPolygonInundation(x, y - 1, fill_color, boundary_color);
        }
    }

    public static void main(String[] args) {
        Poligono poligono = new Poligono();
        poligono.setVisible(true);

        int[] xPoints = {100, 200, 250, 350, 300, 200};
        int[] yPoints = {100, 50, 150, 200, 300, 250};

        poligono.drawPolygon(xPoints, yPoints, Color.BLUE);
        poligono.fillPolygon(xPoints, yPoints, Color.RED);

        /*int[] xPointsNext = {100, 150, 200, 300, 250, 200, 150, 100};
        int[] yPointsNext = {100, 50, 100, 100, 200, 250, 300, 250};
        Color fillColor = Color.BLUE;
        Color borderColor = Color.BLACK;

        poligono.drawPolygon(xPointsNext, yPointsNext, borderColor);
        poligono.fillPolygonInundation(200, 200, fillColor, borderColor);*/
    }
}
