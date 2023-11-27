import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class VertexCube extends JFrame implements KeyListener {

    private BufferedImage buffer;
    private Graphics2D Poligono3D;
    private double anguloX = 0; // Ángulo de rotación en el eje X
    private double anguloY = 0; // Ángulo de rotación en el eje Y
    private double anguloZ = 0; // Ángulo de rotación en el eje Z
    private double traslacionX = 0; // Traslación en el eje X
    private double traslacionY = 0; // Traslación en el eje Y
    private double escala = 1.0; // Factor de escala
    private boolean rotarX = false;
    private boolean rotarY = false;
    private boolean rotarZ = false;

    public VertexCube() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLocationRelativeTo(null);

        // Configurar el enfoque del componente para recibir eventos de teclado
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);  // Agregar el escuchador de teclas

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Poligono3D = buffer.createGraphics();

        Graphics2D g2d = (Graphics2D) Poligono3D;
        g2d.setBackground(Color.WHITE);
        g2d.clearRect(0, 0, getWidth(), getHeight());

        addKeyListener(this); // Agregar el escuchador de teclas

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

        // Crear botones y agregar escuchadores de eventos
        JButton btnTraslacion = new JButton("Mover");
        btnTraslacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                traslacionX += 10; // Ajusta el valor de traslación según sea necesario
                dibujarCubo();
            }
        });

        JButton btnEscala = new JButton("Hacer más pequeño");
        btnEscala.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                escala *= 0.9; // Ajusta el factor de escala según sea necesario
                dibujarCubo();
            }
        });

        // Crear panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnTraslacion);
        panelBotones.add(btnEscala);

        // Agregar el panel de botones al JFrame
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            buffer.setRGB(x, y, c.getRGB());
        }
    }

    public void clearScreen() {
        // Limpiar el buffer antes de volver a dibujar
        Poligono3D.clearRect(0, 0, getWidth(), getHeight());
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

    // Función para aplicar una traslación a un punto 3D
    private int[] trasladar(int x, int y, int z, double traslacionX, double traslacionY) {
        int[] resultado = new int[3];
        resultado[0] = (int) (x + traslacionX);
        resultado[1] = (int) (y + traslacionY);
        resultado[2] = z;
        return resultado;
    }

    // Función para aplicar una escala a un punto 3D
    private int[] escalar(int x, int y, int z, double escala) {
        int[] resultado = new int[3];
        resultado[0] = (int) (x * escala);
        resultado[1] = (int) (y * escala);
        resultado[2] = (int) (z * escala);
        return resultado;
    }

    // Función para dibujar un cubo en 3D y proyectarlo a 2D con transformaciones
    public void dibujarCubo() {
        // Definir los vértices del cubo en 3D
        int[][] vertices = {
                { 100, 100, 100 },
                { 100, 100, -100 },
                { 100, -100, 100 },
                { 100, -100, -100 },
                { -100, 100, 100 },
                { -100, 100, -100 },
                { -100, -100, 100 },
                { -100, -100, -100 }
        };

        // Definir las caras del cubo
        int[][] caras = {
                { 0, 1, 3, 2 }, // Cara frontal
                { 4, 5, 7, 6 }, // Cara trasera
                { 0, 1, 5, 4 }, // Cara superior
                { 2, 3, 7, 6 }, // Cara inferior
                { 0, 2, 6, 4 }, // Cara izquierda
                { 1, 3, 7, 5 } // Cara derecha
        };

        int puntoDePerspectiva = 500;

        // Aplicar transformaciones a los vértices según las teclas presionadas y
        // botones
        for (int i = 0; i < vertices.length; i++) {
            int[] resultadoTransformacion = vertices[i];
            resultadoTransformacion = rotarX(resultadoTransformacion[0], resultadoTransformacion[1],
                    resultadoTransformacion[2], anguloX);
            resultadoTransformacion = rotarY(resultadoTransformacion[0], resultadoTransformacion[1],
                    resultadoTransformacion[2], anguloY);
            resultadoTransformacion = rotarZ(resultadoTransformacion[0], resultadoTransformacion[1],
                    resultadoTransformacion[2], anguloZ);
            resultadoTransformacion = trasladar(resultadoTransformacion[0], resultadoTransformacion[1],
                    resultadoTransformacion[2], traslacionX, traslacionY);
            resultadoTransformacion = escalar(resultadoTransformacion[0], resultadoTransformacion[1],
                    resultadoTransformacion[2], escala);
            vertices[i][0] = resultadoTransformacion[0];
            vertices[i][1] = resultadoTransformacion[1];
            vertices[i][2] = resultadoTransformacion[2];
        }

        clearScreen();

        // Proyectar los vértices en 2D y dibujar las caras del cubo
        for (int i = 0; i < caras.length; i++) {
            int[] cara = caras[i];
            int[] puntosX = new int[cara.length];
            int[] puntosY = new int[cara.length];

            for (int j = 0; j < cara.length; j++) {
                int x = (vertices[cara[j]][0] * puntoDePerspectiva) / (vertices[cara[j]][2] + puntoDePerspectiva)
                        + getWidth() / 2;
                int y = (vertices[cara[j]][1] * puntoDePerspectiva) / (vertices[cara[j]][2] + puntoDePerspectiva)
                        + getHeight() / 2;
                puntosX[j] = x;
                puntosY[j] = y;
            }

            // Rellenar la cara con un color
            Color colorCara = obtenerColorCara(i);
            Poligono3D.setColor(colorCara);
            Poligono3D.fillPolygon(puntosX, puntosY, cara.length);

            // Dibujar los bordes de la cara
            Poligono3D.setColor(Color.BLACK);
            for (int j = 0; j < cara.length; j++) {
                int x1 = puntosX[j];
                int y1 = puntosY[j];
                int x2 = puntosX[(j + 1) % cara.length];
                int y2 = puntosY[(j + 1) % cara.length];
                drawLineBresenham(x1, y1, x2, y2, Color.BLACK);
            }
        }

        // Repintar el JFrame para mostrar el cubo
        repaint();
    }

    // Función para obtener el color de una cara según su índice
    private Color obtenerColorCara(int indiceCara) {
        switch (indiceCara) {
            case 0:
                return Color.RED; // Cara frontal
            case 1:
                return Color.RED; // Cara trasera
            case 2:
                return Color.RED; // Cara superior
            case 3:
                return Color.RED; // Cara inferior
            case 4:
                return Color.RED; // Cara izquierda
            case 5:
                return Color.RED; // Cara derecha
            default:
                return Color.RED;
        }
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
        // Capturar teclas presionadas y ajustar los estados de transformación
        if (e.getKeyChar() == 'x' && !rotarX) {
            rotarX = true;
        } else if (e.getKeyChar() == 'y' && !rotarY) {
            rotarY = true;
        } else if (e.getKeyChar() == 'z' && !rotarZ) {
            rotarZ = true;
        } else if (e.getKeyChar() == 't') {
            // Tecla "T" para activar traslación
            traslacionX += 10; // Ajusta el valor de traslación según sea necesario
            dibujarCubo();
        } else if (e.getKeyChar() == 's') {
            // Tecla "S" para activar escalación
            escala *= 0.9; // Ajusta el factor de escala según sea necesario
            dibujarCubo();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Restaurar el cubo a su posición estática
            anguloX = 0;
            anguloY = 0;
            anguloZ = 0;
            traslacionX = 0;
            traslacionY = 0;
            escala = 1.0;
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
        VertexCube VertexCube = new VertexCube();
        VertexCube.setVisible(true);
    }
}
