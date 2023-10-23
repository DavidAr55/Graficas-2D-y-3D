import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Infinito extends JFrame {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    public Infinito() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
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

    public void drawCurve(double r, Color c) {
        int width = getWidth();
        int height = getHeight();
        double t, x, y;
    
        // Muestrea la curva en un rango de valores de t
        for (t = 0; t <= 2 * Math.PI; t += 0.01) {
            // Calcula las coordenadas x e y para el valor actual de t
            x = r * Math.sin(t) / (1 + Math.pow(Math.cos(t), 2));
            y = r * Math.sin(t) * Math.cos(t) / (1 + Math.pow(Math.cos(t), 2));
    
            // Convierte las coordenadas a píxeles
            int pixelX = (int) ((x + r) * width / (2 * r));
            int pixelY = (int) ((r - y) * height / (2 * r));
    
            // Dibuja el punto en el buffer

            try {
                Thread.sleep(1);
            } catch (Exception e) {
                // TODO: handle exception
            }

            putPixel(pixelX, pixelY, c);
            repaint();
        }
    }

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

    public void drawFlowerCurve(Color c) {
        int width = getWidth();
        int height = getHeight();
        double t, x, y;
    
        // Lista para almacenar los puntos de la curva
        List<Integer> xPoints = new ArrayList<>();
        List<Integer> yPoints = new ArrayList<>();
    
        // Muestrea la curva en un rango de valores de t
        for (t = 0; t <= 2 * Math.PI; t += 0.01) {
            // Calcula las coordenadas x e y para el valor actual de t
            x = Math.cos(t) + 0.5 * Math.cos(7 * t) + (1.0 / 3.0) * Math.sin(17 * t);
            y = Math.sin(t) + 0.5 * Math.sin(7 * t) + (1.0 / 3.0) * Math.cos(17 * t);
    
            // Convierte las coordenadas a píxeles
            int pixelX = (int) ((x + 2) * width / 4);
            int pixelY = (int) ((2 - y) * height / 4);
    
            // Agrega los puntos a las listas
            xPoints.add(pixelX);
            yPoints.add(pixelY);
        }
    
        // Une los puntos con líneas
        for (int i = 0; i < xPoints.size() - 1; i++) {
            int x1 = xPoints.get(i);
            int y1 = yPoints.get(i);
            int x2 = xPoints.get(i + 1);
            int y2 = yPoints.get(i + 1);

            try {
                Thread.sleep(5);
            } catch (Exception e) {
                // TODO: handle exception
            }
    
            drawLineBresenham(x1, y1, x2, y2, c);
            repaint();
        }
    }

    public void drawSunCurve(Color c) {
        int width = getWidth();
        int height = getHeight();
    
        for (double t = 0; t <= 20 * Math.PI; t += 0.01) {
            double x = 17 * Math.cos(t) + 7 * Math.cos((17.0 / 7) * t);
            double y = 17 * Math.sin(t) - 7 * Math.sin((17.0 / 7) * t);
    
            // Aplica una escala y traslación para que las coordenadas se ajusten a tu ventana
            int pixelX = (int) ((x + 24) * width / 48); // Escala y desplaza para que se ajuste en la ventana
            int pixelY = (int) ((24 - y) * height / 48);
    
            // Verifica si las coordenadas están dentro de los límites del área de dibujo
            if (pixelX >= 0 && pixelX < width && pixelY >= 0 && pixelY < height) {

                // Dibuja el punto (pixelX, pixelY)

                try {
                    Thread.sleep(5);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                putPixel(pixelX, pixelY, c);
                repaint();
            }
        }
    }

    public void drawCustomCurve(Color c) {
        int width = getWidth();
        int height = getHeight();
    
        for (double t = 0; t <= 50; t += 0.01) {
            // Calcula las coordenadas (x, y) según las ecuaciones paramétricas
            double x = t - 3 * Math.sin(t);
            double y = 4 - 3 * Math.cos(t);
    
            // Aplica una escala y traslación para que las coordenadas se ajusten a tu ventana
            int pixelX = (int) (x * width / 50); // Escala en x
            int pixelY = (int) (y * height / 8);  // Escala en y
    
            // Verifica si las coordenadas están dentro de los límites del área de dibujo
            if (pixelX >= 0 && pixelX < width && pixelY >= 0 && pixelY < height) {

                // Dibuja el punto (pixelX, pixelY)

                try {
                    Thread.sleep(5);
                    putPixel(pixelX, pixelY, c);
                    repaint();
                    
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        
        Infinito infinito = new Infinito();
        infinito.setVisible(true);

        infinito.drawCustomCurve(Color.GREEN);

        double r = 2.0;
        infinito.drawCurve(r, Color.RED);

        infinito.drawFlowerCurve(Color.BLACK);

        infinito.drawSunCurve(Color.BLUE);
    }
}
