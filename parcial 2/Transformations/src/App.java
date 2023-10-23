import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class App extends JFrame {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    public App() {
        super();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 900);
        setVisible(true);
        setLocationRelativeTo(null);

        initPixel();
    }

    public void initPixel() {

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

    public void fillEllipseScanLine(int centerX, int centerY, int a, int b, Color fillColor) {
        
        double a2 = a * a;
        double b2 = b * b;
    
        for (int y = -b; y <= b; y++) {
            int xLimit = (int) (a * Math.sqrt(1 - (y * y) / b2));
            for (int x = -xLimit; x <= xLimit; x++) {
                int pixelX = centerX + x;
                int pixelY = centerY + y;
                putPixel(pixelX, pixelY, fillColor);
            }
        }

        repaint();
    }

    public void rotatingPoligonWithMatrix(double[][] xCords, double[][] yCords, Color[] fillColors, int[] e, double angle, double giros) {

        int z = 0;
        for (int i = 0; i < xCords.length; i++) {
            z = Math.max(z, xCords[i].length);
        }
    
        double[][][] p = new double[2][xCords.length][z];
    
        for (double a = 0; a < (angle * giros); a++) {
            fillEllipseScanLine(e[0], e[1], 3, 3, Color.black);
    
            for (int i = 0; i < xCords.length; i++) {
                for (int j = 0; j < xCords[i].length; j++) {
                    double tempX = xCords[i][j] - e[0];  // Centra la figura en el punto central
                    double tempY = yCords[i][j] - e[1];  // Centra la figura en el punto central
    
                    // Aplicar transformación de rotación
                    double cosA = Math.cos(Math.toRadians(a));
                    double sinA = Math.sin(Math.toRadians(a));
    
                    p[0][i][j] = tempX * cosA - tempY * sinA + e[0];
                    p[1][i][j] = tempX * sinA + tempY * cosA + e[1];
    
                    fillEllipseScanLine((int) p[0][i][j], (int) p[1][i][j], 4, 4, fillColors[i]);
                }
            }
    
            try {
                Thread.sleep(100);
                clearScreen();
                repaint();
            } catch (Exception exception) {
                // Manejo de excepciones
            }
        }
    }
    
    public void scaleAndAnimate(int[][] xPolygons, int[][] yPolygons, double scale, int numSteps, Color[] c) {
        double stepSize = (scale - 1.0) / numSteps;
    
        for (int step = 0; step <= numSteps; step++) {
            int[][] scaledX = new int[xPolygons.length][];
            int[][] scaledY = new int[yPolygons.length][];
    
            for (int i = 0; i < xPolygons.length; i++) {
                int[] xPoints = xPolygons[i];
                int[] yPoints = yPolygons[i];
    
                double centerX = 0.0;
                double centerY = 0.0;
    
                for (int j = 0; j < xPoints.length; j++) {
                    centerX += xPoints[j];
                    centerY += yPoints[j];
                }
    
                centerX /= xPoints.length;
                centerY /= yPoints.length;
    
                scaledX[i] = new int[xPoints.length];
                scaledY[i] = new int[yPoints.length];
    
                for (int j = 0; j < xPoints.length; j++) {
                    double deltaX = xPoints[j] - centerX;
                    double deltaY = yPoints[j] - centerY;
    
                    scaledX[i][j] = (int) (centerX + deltaX * (1 + stepSize * step));
                    scaledY[i][j] = (int) (centerY + deltaY * (1 + stepSize * step));
                }
            }
    
            clearScreen();
            for (int i = 0; i < scaledX.length; i++) {
                fillPolygonScanLine(scaledX[i], scaledY[i], c[i]);
            }
            repaint();
    
            try {
                Thread.sleep(100); // Pausa para ver la animación
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        
        double[][] xCords = {
            {600, 700, 700, 600, 500},
            {750, 850, 850, 750},
            {600, 700, 700, 675, 625, 600},
            {750, 800, 800, 750}
        };
        
        double[][] yCords = {
            {300, 300, 400, 400, 300},
            {300, 300, 400, 400},
            {500, 500, 600, 650, 650, 600},
            {450, 450, 500, 500}
        };

        int[] e = {750, 450};
        Color[] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE};
             
        
        App app = new App();

        // app.rotatingPoligonWithMatrix(xCords, yCords, colores, e, 360, 2);

        int[][] xCube = new int[][] {{0, 100, 100, 0}, 
                                     {5, 95, 95, 5}, 
                                     {23, 43, 43, 23}, 
                                     {56, 76, 76, 56}, 
                                     {27, 39, 39, 27}, 
                                     {60, 72, 72, 60}, 
                                     {17, 83, 83, 17}, 
                                     {22, 78, 78, 22}};

        int[][] yCube = new int[][] {{0, 0, 100, 100}, 
                                     {5, 5, 95, 95}, 
                                     {19, 19, 39, 39}, 
                                     {19, 19, 39, 39}, 
                                     {23, 23, 35, 35}, 
                                     {23, 23, 35, 35}, 
                                     {48, 48, 67, 67}, 
                                     {52, 52, 62, 62}};

        Color[] colors = new Color[] {Color.BLACK, 
                                     Color.decode("#FFFF00"), 
                                     Color.BLACK, 
                                     Color.BLACK, 
                                     Color.decode("#00C8FF"), 
                                     Color.decode("#00C8FF"), 
                                     Color.BLACK, 
                                     Color.decode("#00C8FF")};

        // Centramos nuestro Cubo en las coordenacas xCenter & yCenter
        for(int i = 0; i < xCube.length; i++) {
            for(int j = 0; j < xCube[i].length; j++) {
                xCube[i][j] += (450);
                yCube[i][j] += (750);
            }
        }

        app.scaleAndAnimate(yCube, xCube, 0.5, 20, colors);
    }
}
