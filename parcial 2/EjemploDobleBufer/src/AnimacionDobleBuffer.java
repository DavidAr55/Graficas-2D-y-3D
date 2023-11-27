import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class AnimacionDobleBuffer extends Canvas implements Runnable {
    private static final int ANCHO_VENTANA = 800;
    private static final int ALTO_VENTANA = 600;
    private static final int VELOCIDAD_ANIMACION = 5;

    private Thread thread;
    private boolean enEjecucion;
    private int xRectangulo;

    public AnimacionDobleBuffer() {
        this.setPreferredSize(new Dimension(ANCHO_VENTANA, ALTO_VENTANA));
    }

    public void iniciar() {
        if (thread == null) {
            thread = new Thread(this);
            enEjecucion = true;
            thread.start();
        }
    }

    public void detener() {
        enEjecucion = false;
    }

    @Override
    public void run() {
        createBufferStrategy(3); // Crear un doble búfer con 3 buffers
        BufferStrategy bufferStrategy = getBufferStrategy();

        while (enEjecucion) {
            actualizar();
            dibujar(bufferStrategy);
            bufferStrategy.show();

            try {
                Thread.sleep(VELOCIDAD_ANIMACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void actualizar() {
        xRectangulo += 1;
        if (xRectangulo > ANCHO_VENTANA) {
            xRectangulo = 0;
        }
    }

    private void dibujar(BufferStrategy bufferStrategy) {
        Graphics g = bufferStrategy.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, ANCHO_VENTANA, ALTO_VENTANA);
        g.setColor(Color.RED);
        g.fillRect(xRectangulo, ALTO_VENTANA / 2 - 25, 50, 50);
        g.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Animación con Doble Búfer");
        AnimacionDobleBuffer animacion = new AnimacionDobleBuffer();
        frame.add(animacion);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animacion.iniciar();
    }
}
