import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Poligono extends Pixel {

    private int[] x;
    private int[] y;

    public Poligono() {

        super();
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
        fillPolygonScanLine(xPoints, yPoints, c);
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
            }
        }
    }

    public void renderCube(int x, int y) {

        // El Cubo base mide 100 x 100 Pixeles
        int cubeWidth = (100/2);        // Dividimos el whidth/2 para sacar el centro del cubo
        int cubeHeight = (100/2);       // Dividimos el height/2 para sacar el centro del cubo

        x -= cubeWidth;
        y -= cubeHeight;

        int[] xPointsBorder = {0+x, 100+x, 100+x, 0+x};  // Coordenadas x de los vértices
        int[] yPointsBorder = {0+y, 0+y, 100+y, 100+y};  // Coordenadas y de los vértices

        drawPolygon(xPointsBorder, yPointsBorder, Color.BLACK);

        int[] xPointsFill = {5+x, 95+x, 95+x, 5+x};  // Coordenadas x de los vértices
        int[] yPointsFill = {5+y, 5+y, 95+y, 95+y};  // Coordenadas y de los vértices

        drawPolygon(xPointsFill, yPointsFill, Color.decode("#FFFF00"));

        // Ojo izquierdo
        int[] xPointsBorderLeftEye = {23+x, 43+x, 43+x, 23+x};  // Coordenadas x de los vértices
        int[] yPointsBorderLeftEye = {19+y, 19+y, 39+y, 39+y};  // Coordenadas y de los vértices

        drawPolygon(xPointsBorderLeftEye, yPointsBorderLeftEye, Color.BLACK);

        // Ojo Derecho
        int[] xPointsBorderRightEye = {56+x, 76+x, 76+x, 56+x};  // Coordenadas x de los vértices
        int[] yPointsBorderRightEye = {19+y, 19+y, 39+y, 39+y};  // Coordenadas y de los vértices

        drawPolygon(xPointsBorderRightEye, yPointsBorderRightEye, Color.BLACK);

        // Ojo izquierdo relleno
        int[] xPointsFillLeftEye = {27+x, 39+x, 39+x, 27+x};  // Coordenadas x de los vértices
        int[] yPointsFillLeftEye = {23+y, 23+y, 35+y, 35+y};  // Coordenadas y de los vértices

        drawPolygon(xPointsFillLeftEye, yPointsFillLeftEye, Color.decode("#00C8FF"));

        // Ojo Derecho relleno
        int[] xPointsFillRightEye = {60+x, 72+x, 72+x, 60+x};  // Coordenadas x de los vértices
        int[] yPointsFillRightEye = {23+y, 23+y, 35+y, 35+y};  // Coordenadas y de los vértices

        drawPolygon(xPointsFillRightEye, yPointsFillRightEye, Color.decode("#00C8FF"));

        // Borde de la boca
        int[] xPointsBorderMouth = {17+x, 83+x, 83+x, 17+x};  // Coordenadas x de los vértices
        int[] yPointsBorderMouth = {48+y, 48+y, 67+y, 67+y};  // Coordenadas y de los vértices

        drawPolygon(xPointsBorderMouth, yPointsBorderMouth, Color.BLACK);

        // Relleno de la boca
        int[] xPointsFillMouth = {22+x, 78+x, 78+x, 22+x};  // Coordenadas x de los vértices
        int[] yPointsFillMouth = {52+y, 52+y, 62+y, 62+y};  // Coordenadas y de los vértices

        drawPolygon(xPointsFillMouth, yPointsFillMouth, Color.decode("#00C8FF"));
    }
}