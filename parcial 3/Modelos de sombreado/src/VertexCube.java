import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class VertexCube extends JFrame implements KeyListener {

    private BufferedImage buffer;
    private Graphics2D graPixel;
    private double anguloX = 0; // Angulo de rotación en el eje X
    private double anguloY = 0; // Angulo de rotación en el eje Y
    private double anguloZ = 0; // Angulo de rotación en el eje Z
    private boolean rotarX = false;
    private boolean rotarY = false;
    private boolean rotarZ = false;

    public VertexCube() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        Graphics2D g2d = (Graphics2D) graPixel;
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());

        addKeyListener(this);  // Agregar el escuchador de teclas

        // Configurar temporizador para la rotación continua
        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rotarX) {
                    anguloX += 0.01; // Ajusta la velocidad de rotación según sea necesario
                }
                if (rotarY) {
                    anguloY += 0.01;
                }
                if (rotarZ) {
                    anguloZ += 0.01;
                }
                dibujarCubo();
            }
        });
        timer.start();
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

    // Función para aplicar una rotación en el eje X a un punto 3D
    private int[] rotarX(int x, int y, int z, double angulo) {
        int[] resultado = new int[3];
        resultado[0] = x;
        resultado[1] = (int) (y * Math.cos(angulo) - z * Math.sin(angulo));
        resultado[2] = (int) (y * Math.sin(angulo) + z * Math.cos(angulo));
        return resultado;
    }

    // Función para aplicar una rotación en el eje Y a un punto 3D
    private int[] rotarY(int x, int y, int z, double angulo) {
        int[] resultado = new int[3];
        resultado[0] = (int) (x * Math.cos(angulo) + z * Math.sin(angulo));
        resultado[1] = y;
        resultado[2] = (int) (-x * Math.sin(angulo) + z * Math.cos(angulo));
        return resultado;
    }

    // Función para aplicar una rotación en el eje Z a un punto 3D
    private int[] rotarZ(int x, int y, int z, double angulo) {
        int[] resultado = new int[3];
        resultado[0] = (int) (x * Math.cos(angulo) - y * Math.sin(angulo));
        resultado[1] = (int) (x * Math.sin(angulo) + y * Math.cos(angulo));
        resultado[2] = z;
        return resultado;
    }

    // Función para dibujar un cubo en 3D y proyectarlo a 2D con rotación
    public void dibujarCubo() {
        // Definir los vértices del cubo en 3D
        int[][] vertices = {
                {100, 100, 100},
                {100, 100, -100},
                {100, -100, 100},
                {100, -100, -100},
                {-100, 100, 100},
                {-100, 100, -100},
                {-100, -100, 100},
                {-100, -100, -100}
        };

        // Cambiar la posición de la cámara (punto de perspectiva)
        int puntoDePerspectiva = 500;

        // Aplicar rotación a los vértices según las teclas presionadas
        for (int i = 0; i < vertices.length; i++) {
            int[] resultadoRotacion = vertices[i];
            resultadoRotacion = rotarX(resultadoRotacion[0], resultadoRotacion[1], resultadoRotacion[2], anguloX);
            resultadoRotacion = rotarY(resultadoRotacion[0], resultadoRotacion[1], resultadoRotacion[2], anguloY);
            resultadoRotacion = rotarZ(resultadoRotacion[0], resultadoRotacion[1], resultadoRotacion[2], anguloZ);
            vertices[i][0] = resultadoRotacion[0];
            vertices[i][1] = resultadoRotacion[1];
            vertices[i][2] = resultadoRotacion[2];
        }

        // Limpiar el buffer antes de volver a dibujar
        graPixel.clearRect(0, 0, getWidth(), getHeight());

        // Proyectar los vértices en 2D y dibujar las líneas del cubo
        for (int i = 0; i < vertices.length; i++) {
            int x = (vertices[i][0] * puntoDePerspectiva) / (vertices[i][2] + puntoDePerspectiva) + getWidth() / 2;
            int y = (vertices[i][1] * puntoDePerspectiva) / (vertices[i][2] + puntoDePerspectiva) + getHeight() / 2;

            // Conectar los vértices para formar el cubo
            for (int j = i + 1; j < vertices.length; j++) {
                int x2 = (vertices[j][0] * puntoDePerspectiva) / (vertices[j][2] + puntoDePerspectiva) + getWidth() / 2;
                int y2 = (vertices[j][1] * puntoDePerspectiva) / (vertices[j][2] + puntoDePerspectiva) + getHeight() / 2;

                drawLineBresenham(x, y, x2, y2, Color.BLACK);
            }
        }

        // Repintar el JFrame para mostrar el cubo
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    // Métodos de la interfaz KeyListener
    @Override
    public void keyTyped(KeyEvent e) {
        // No necesitamos implementar este método en este caso
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Capturar teclas presionadas y ajustar los estados de rotación
        if (e.getKeyChar() == 'x' && !rotarX) {
            rotarX = true;
        } else if (e.getKeyChar() == 'y' && !rotarY) {
            rotarY = true;
        } else if (e.getKeyChar() == 'z' && !rotarZ) {
            rotarZ = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Restaurar el cubo a su posición estática
            anguloX = 0;
            anguloY = 0;
            anguloZ = 0;
            rotarX = false;
            rotarY = false;
            rotarZ = false;
            dibujarCubo();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No necesitamos implementar este método en este caso
    }

    public static void main(String[] args) throws Exception {
        VertexCube sombreador = new VertexCube();
        sombreador.setVisible(true);
    }
}
