import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class App extends JFrame {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    public App() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        Graphics2D g2d = (Graphics2D) graPixel;
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    public void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            buffer.setRGB(x, y, c.getRGB());
        }
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

    public void curvaResorte() {
        int numPuntos = 100;
        double[] tz = new double[numPuntos];
        double t = (8 * Math.PI) / numPuntos;

        for (int i = 0; i < numPuntos; i++) {
            tz[i] = t * i;
        }

        double[] x = new double[numPuntos];
        double[] y = new double[numPuntos];

        for (int i = 0; i < numPuntos; i++) {
            x[i] = Math.cos(tz[i]);
            y[i] = Math.sin(tz[i]);
        }

        double[] vp = { 1, 1, 15 };
        double[] u = new double[numPuntos];
        double[] xp = new double[numPuntos];
        double[] yp = new double[numPuntos];

        for (int i = 0; i < numPuntos - 1; i++) {
            u[i] = -(tz[i] / vp[2]);
            xp[i] = (x[i] + (vp[0] * u[i])) * 25 + getWidth() / 2; // Ajuste y escala
            yp[i] = (y[i] + (vp[1] * u[i])) * 25 + getHeight() / 2; // Ajuste y escala

            System.out.println();
            System.out.println("xp[" + i + "]: " + xp[i]);
            System.out.println("yp[" + i + "]: " + yp[i]);

            putPixel((int) xp[i], (int) yp[i], Color.red);
        }

        for (int i = 0; i < numPuntos - 1; i++) {

            if (!(i == numPuntos - 2)) {
                try {
                    Thread.sleep(50);
                    drawLineBresenham((int) xp[i], (int) yp[i], (int) xp[i + 1], (int) yp[i + 1], Color.BLUE);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                repaint();
            }

            else
                break;
        }
    }

    public void printCube() {
        double[][] puntos = {
                { 1, 4, 3 },
                { 3, 4, 5 },
                { 3, 2, 3.5 },
                { 1, 2, 1.5 },
                { 2, 3, 3.5 },
                { 2, 5, 5 },
                { 4, 5, 6.5 },
                { 4, 3, 5 }
        };

        double[] vt = { 1, 1, 1 };
        double[] u = new double[puntos.length];
        double scale = 10; // Escala de ajuste

        int[] cordsX = new int[4];
        int[] cordsY = new int[4];

        int[] cordsProjectionX = new int[4];
        int[] cordsProjectionY = new int[4];

        for (int i = 0; i < puntos.length; i++) {
            u[i] = puntos[i][2] / vt[2];

            int xProjected = (int) (puntos[i][0] * u[i] * scale) + getWidth() / 2;
            int yProjected = (int) (puntos[i][1] * u[i] * scale) + getHeight() / 2;

            Color pointColor = Color.RED;

            if (i < 4) {
                cordsX[i] = xProjected;
                cordsY[i] = yProjected;
            } else {
                cordsProjectionX[i - 4] = xProjected;
                cordsProjectionY[i - 4] = yProjected;
            }

            try {
                Thread.sleep(50);
                fillEllipseScanLine(xProjected, yProjected, 3, 3, pointColor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 4; i++) {
            int nextIndex = (i + 1) % 4;
            drawLineBresenham(cordsX[i], cordsY[i], cordsX[nextIndex], cordsY[nextIndex], Color.BLUE);
            drawLineBresenham(cordsProjectionX[i], cordsProjectionY[i], cordsProjectionX[nextIndex],
                    cordsProjectionY[nextIndex], Color.BLUE);

            drawLineBresenham(cordsX[i], cordsY[i], cordsProjectionX[nextIndex], cordsProjectionY[nextIndex],
                    Color.BLUE);
        }

        repaint();
    }

    public void printOrthogonal() {
        double[][] puntos = {
                {1, 4, 3},
                {3, 4, 5},
                {3, 2, 3.5},
                {1, 2, 1.5},
                {2, 3, 3.5},
                {2, 5, 5},
                {4, 5, 6.5},
                {4, 3, 5}
        };
    
        double scale = 50; // Escala de ajuste
    
        int[] cordsOrthogonalX = new int[4];
        int[] cordsOrthogonalY = new int[4];
    
        double[] vp = {1, 1, 15};  // Ajusta las coordenadas del observador
    
        for (int i = 0; i < 4; i++) {
            int xOrthogonal = (int) (puntos[i][0] * scale + vp[0]) + getWidth() / 2;
            int yOrthogonal = (int) (puntos[i][2] * scale + vp[2]) + getHeight() / 2;
    
            cordsOrthogonalX[i] = xOrthogonal;
            cordsOrthogonalY[i] = yOrthogonal;
        }
    
        for (int i = 0; i < 4; i++) {
            int nextIndex = (i + 1) % 4;
    
            drawLineBresenham(cordsOrthogonalX[i], cordsOrthogonalY[i], cordsOrthogonalX[nextIndex], cordsOrthogonalY[nextIndex], Color.RED);
        }
    
        drawLineBresenham(cordsOrthogonalX[0], cordsOrthogonalY[0], cordsOrthogonalX[1], cordsOrthogonalY[1], Color.RED);
        drawLineBresenham(cordsOrthogonalX[1], cordsOrthogonalY[1], cordsOrthogonalX[2], cordsOrthogonalY[2], Color.RED);
        drawLineBresenham(cordsOrthogonalX[2], cordsOrthogonalY[2], cordsOrthogonalX[3], cordsOrthogonalY[3], Color.RED);
        drawLineBresenham(cordsOrthogonalX[3], cordsOrthogonalY[3], cordsOrthogonalX[0], cordsOrthogonalY[0], Color.RED);
    
        repaint();
    }            

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    public static void main(String[] args) throws Exception {

        App app = new App();
        app.setVisible(true);

        // app.curvaResorte();
        // app.printCube();
        app.printOrthogonal();
    }
}