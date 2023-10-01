import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

    public void fillPolygonScanLine(int[] xPoints, int[] yPoints, Color c) {
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

                /* try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    System.out.println(e);
                } */
            }
        }
    }   

    public int[] calculateCentroid(int[] xPoints, int[] yPoints) {
        int n = xPoints.length;
        int cx = 0, cy = 0;
        int area = 0;

        for (int i = 0; i < n; i++) {
            int x1 = xPoints[i];
            int y1 = yPoints[i];
            int x2 = xPoints[(i + 1) % n];
            int y2 = yPoints[(i + 1) % n];

            int partialArea = x1 * y2 - x2 * y1;
            area += partialArea;
            cx += (x1 + x2) * partialArea;
            cy += (y1 + y2) * partialArea;
        }

        area /= 2;
        cx /= (6 * area);
        cy /= (6 * area);

        int[] centroid = {cx, cy};

        putPixel(cx, cy, Color.RED);
        return centroid;
    }

    public void fillPolygonInundation(int[] centroid, int[] xPoints, int[] yPoints, Color fillColor) {
    // Obtiene las coordenadas del centroide
    int x = centroid[0];
    int y = centroid[1];

    // Obtiene el color del píxel en la coordenada (x, y)
    Color pixelColor = new Color(buffer.getRGB(x, y));

    // Verifica si el píxel en la coordenada (x, y) es parte del borde del polígono
    if (!pixelColor.equals(fillColor)) {
        // Inicializa una cola (queue) para el algoritmo de inundación
        Queue<Integer> queue = new LinkedList<>();

        // Agrega la coordenada (x, y) a la cola
        queue.add(x);
        queue.add(y);

        // Mientras la cola no esté vacía
        while (!queue.isEmpty()) {
            // Desencola las coordenadas (x, y)
            x = queue.poll();
            y = queue.poll();

            // Obtiene el color del píxel en la coordenada (x, y)
            pixelColor = new Color(buffer.getRGB(x, y));

            // Verifica si el píxel es parte del borde del polígono
            if (!pixelColor.equals(fillColor)) {
                // Colorea el píxel con el nuevo color
                putPixel(x, y, fillColor);

                // Agrega los vecinos (norte, este, sur, oeste) a la cola
                queue.add(x);
                queue.add(y + 1); // Norte
                queue.add(x + 1);
                queue.add(y);     // Este
                queue.add(x);
                queue.add(y - 1); // Sur
                queue.add(x - 1);
                queue.add(y);     // Oeste
            }
        }

        // Actualiza la ventana con el nuevo relleno
        repaint();
        }
    }

    public static void main(String[] args) {
        Poligono poligono = new Poligono();
        poligono.setVisible(true);
    
        int[] xPoints = {100, 200, 250, 350, 300, 200};
        int[] yPoints = {100, 50, 150, 200, 300, 250};
    
        poligono.drawPolygon(xPoints, yPoints, Color.BLUE);
        poligono.fillPolygonScanLine(xPoints, yPoints, Color.RED);
        
        int[] xPoints2 = {(100 + 300), (200 + 300), (250 + 300), (350 + 300), (400 + 300), (350 + 300), (250 + 300), (200 + 300), (100 + 300)};
        int[] yPoints2 = {100, 50, 150, 200, 300, 400, 350, 250, 200};
        
        
        int[] centroid = poligono.calculateCentroid(xPoints2, yPoints2);
        System.out.println("El centro del polígono está en: (" + centroid[0] + ", " + centroid[1] + ")");

        poligono.drawPolygon(xPoints2, yPoints2, Color.GREEN);
        poligono.fillPolygonInundation(centroid, xPoints2, yPoints2, Color.PINK);
    }    
}
