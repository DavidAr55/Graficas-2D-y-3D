import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class MeshWithMatrix extends JFrame {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    public MeshWithMatrix() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Inicializa el buffer con las dimensiones de la ventana
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

    public void drawCircleWithMesh(int xc, int yc, int radio, int meshSpacing) {
        // Crea una matriz de puntos
        int[][] pointsMatrix = createPointsMatrix(xc, yc, radio, meshSpacing);

        // Dibuja los triángulos en el mallado
        for (int i = 0; i < pointsMatrix.length; i++) {
            int x = pointsMatrix[i][0];
            int y = pointsMatrix[i][1];

            // Dibuja un píxel para representar cada vértice del triángulo
            putPixel(x, y, Color.GRAY);
        }

        // Dibuja el círculo
        for (int x = -radio; x <= radio; x++) {
            int y = (int) Math.round(Math.sqrt(radio * radio - x * x));

            // Son ocho cuadrantes para que salga completo
            putPixel(xc + x, yc + y, Color.ORANGE);
            putPixel(xc - x, yc + y, Color.ORANGE);
            putPixel(xc + x, yc - y, Color.ORANGE);
            putPixel(xc - x, yc - y, Color.ORANGE);
            putPixel(xc + y, yc + x, Color.ORANGE);
            putPixel(xc - y, yc + x, Color.ORANGE);
            putPixel(xc + y, yc - x, Color.ORANGE);
            putPixel(xc - y, yc - x, Color.ORANGE);
            // Pinta las coordenadas del centro
            putPixel(xc, yc, Color.BLACK);
        }

        // Solicita una actualización de la ventana
        repaint();
    }

    private int[][] createPointsMatrix(int xc, int yc, int radio, int meshSpacing) {
        int numPoints = (2 * radio / meshSpacing + 1) * (2 * radio / meshSpacing + 1);
        int[][] pointsMatrix = new int[numPoints][2];
        int index = 0;
        for (int x = -radio; x <= radio; x += meshSpacing) {
            for (int y = -radio; y <= radio; y += meshSpacing) {
                if (x * x + y * y <= radio * radio) {
                    pointsMatrix[index][0] = xc + x;
                    pointsMatrix[index][1] = yc + y;
                    index++;
                }
            }
        }
        return pointsMatrix;
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

    public boolean findPixel(int x, int y, Color targetColor) {
        if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
            // Verifica si las coordenadas están dentro de los límites de la ventana
            return false;
        }

        int pixelColor = buffer.getRGB(x, y);
        Color pixelColorObj = new Color(pixelColor);

        return pixelColorObj.equals(targetColor);
    }

    public void drawLineTriangle(int x, int y, Color pixel, Color fill, int direccion , int meshSpacing) {

        if (findPixel(x, y, pixel)) {
            System.out.println("Punto encontrado en (" + x + ", " + y + ")");

            // Condiciones para las direcciones
            if (direccion == 1)
                drawLineTriangle(x, y - meshSpacing, pixel, fill, direccion, meshSpacing);

            else if (direccion == 2)
                drawLineTriangle(x + meshSpacing, y, pixel, fill, direccion, meshSpacing);

            else if (direccion == 3)
                drawLineTriangle(x, y + meshSpacing, pixel, fill, direccion, meshSpacing);

            else if (direccion == 4)
                drawLineTriangle(x - meshSpacing, y, pixel, fill, direccion, meshSpacing);

            else if (direccion == 5)
                drawLineTriangle(x + meshSpacing, y + meshSpacing, pixel, fill, direccion, meshSpacing);
        }

        else {
            System.out.println("Pixel no encontrado en (" + x + ", " + y + ")");

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }

            // Condiciones para los limites
            drawLineBresenham(x, y, x, y - meshSpacing, pixel);
            repaint();

            if(!findPixel(x, y - meshSpacing, pixel))
                direccion ++;


            // Condiciones para las direcciones
            if (direccion == 1)
                drawLineTriangle(x, y - meshSpacing, pixel, fill, direccion, meshSpacing);

            else if (direccion == 2)
                drawLineTriangle(x + meshSpacing, y, pixel, fill, direccion, meshSpacing);

            else if (direccion == 3)
                drawLineTriangle(x, y + meshSpacing, pixel, fill, direccion, meshSpacing);

            else if (direccion == 4)
                drawLineTriangle(x - meshSpacing, y, pixel, fill, direccion, meshSpacing);

            else if (direccion == 5)
                drawLineTriangle(x + meshSpacing, y + meshSpacing, pixel, fill, direccion, meshSpacing);
        }
    }

    public int[] findFristPoint(int x, int y, Color border, Color pixel) {

        int[] result = new int[2];

        boolean findLeft = false, findBottom = false;

        while(!(findLeft && findBottom)) {

            if(!findLeft) {

                x--;
                if(findPixel(x, y, border)) {

                    // Regresamos para buscar el punto mas cercano
                    while(!findPixel(x, y, pixel)) {
                        x++;
                    }

                    findLeft = true;
                    result[0] = x;
                }
            }

            else {

                y++;
                if(findPixel(x, y, border)) {

                    // Regresamos para buscar el punto mas cercano
                    while(!findPixel(x, y, pixel)) {
                        y--;
                    }

                    findBottom = true;
                    result[1] = y;
                }
            }
        }

        return result;
    }

    @Override
    public void paint(Graphics g) {
        // Dibuja el buffer en la ventana
        g.drawImage(buffer, 0, 0, this);
    }

    public static void main(String[] args) {
        MeshWithMatrix drawer = new MeshWithMatrix();
        drawer.setVisible(true);

        int centerX = 250;
        int centerY = 250;
        int radius = 200;
        int meshSpacing = 50;

        int direccion = 1;

        drawer.drawCircleWithMesh(centerX, centerY, radius, meshSpacing);
        int[] cordsPoint = drawer.findFristPoint(centerX, centerY, Color.ORANGE, Color.GRAY);

        System.out.println("(" + cordsPoint[0] + ", " + cordsPoint[1] + ")");
        drawer.putPixel(cordsPoint[0], cordsPoint[1], Color.BLUE);

        drawer.drawLineTriangle(cordsPoint[0], cordsPoint[1], Color.GRAY, Color.RED, direccion, meshSpacing);
    }
}
