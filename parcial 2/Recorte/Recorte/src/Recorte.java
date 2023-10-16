import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Recorte extends JFrame {

    private BufferedImage buffer;
    private Graphics2D graPixel;
    private Rectangle drawingArea;

    public Recorte() {
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

        // Crea un rectángulo en medio de la pantalla
        int rectWidth = 500;
        int rectHeight = 350;
        int rectX = (getWidth() - rectWidth) / 2;
        int rectY = (getHeight() - rectHeight) / 2;
        drawingArea = new Rectangle(rectX, rectY, rectWidth, rectHeight);

        // Agrega un MouseListener para manejar los clics y el movimiento del mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && drawingArea.contains(e.getPoint())) {
                    int x = e.getX();
                    int y = e.getY();
                    putPixel(x, y, Color.BLACK); // Cambia el color del lápiz según tus preferencias
                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && drawingArea.contains(e.getPoint())) {
                    int x = e.getX();
                    int y = e.getY();
                    putPixel(x, y, Color.BLACK); // Cambia el color del lápiz según tus preferencias
                    repaint();
                }
            }
        });

        // Agrega un KeyListener para manejar la combinación de teclas Ctrl + Z
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown()) {
                    clearDrawingArea();
                }
            }
        });

        // Habilita el enfoque del componente para que el KeyListener funcione
        setFocusable(true);
        requestFocus();
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    public void clearDrawingArea() {
        // Borra el contenido del rectángulo de dibujo
        Graphics2D g2d = (Graphics2D) buffer.getGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(drawingArea.x, drawingArea.y, drawingArea.width, drawingArea.height);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
        // Dibuja el rectángulo en el área de dibujo
        g.setColor(Color.GRAY); // Cambia el color del rectángulo según tus preferencias
        g.drawRect(drawingArea.x, drawingArea.y, drawingArea.width, drawingArea.height);
    }

    public static void main(String[] args) {
        Recorte recorte = new Recorte();
        recorte.setVisible(true);
    }
}
