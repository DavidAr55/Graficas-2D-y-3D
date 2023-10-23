import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.sound.sampled.UnsupportedAudioFileException;  // Importa esta excepción
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Poligono extends JFrame implements KeyListener {

    private BufferedImage buffer;  // Doble búfer
    private Graphics2D graPixel;
    private Font customFont;

    public Poligono() {
        super();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 900);
        setVisible(true);
        setLocationRelativeTo(null);

        initPixel();

        // Registra el KeyListener en el JFrame
        addKeyListener(this);

        // Asegúrate de que el JFrame pueda recibir eventos del teclado
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public static void playWav() {
        try {
            File audioFile = new File("media/TheFatRat-Windfall.wav");
    
            if (audioFile.exists()) {
                try {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
    
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
    
                    // Adelanta 1 segundo (1000000 microsegundos)
                    long skipMicroseconds = 4000000;
                    clip.setMicrosecondPosition(clip.getMicrosecondPosition() + skipMicroseconds);
    
                    clip.start();
    
                    // Espera hasta que el audio termine de reproducirse
                    Thread.sleep(clip.getMicrosecondLength() / 1000);
    
                    clip.close();
                    audioStream.close();
                } catch (UnsupportedAudioFileException e) {
                    System.err.println("Formato de archivo de audio no soportado: " + e.getMessage());
                }
            } else {
                System.err.println("El archivo de audio no existe: media/TheFatRat-Windfall.wav");
            }
        } catch (LineUnavailableException | IOException | InterruptedException | ExceptionInInitializerError e) {
            e.printStackTrace();
        }
    }    

    // Implementa los métodos del KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            // Cierra todo el proceso de la aplicación
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No es necesario implementar este método
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No es necesario implementar este método
    }

    public void initPixel() {
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        Graphics2D g2d = (Graphics2D) graPixel;
        g2d.setBackground(Color.decode("#2573EA"));
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    public void setText(int x, int y, String text, Color colorText) {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/PUSAB___.otf"));
            customFont = customFont.deriveFont(45f);

            clearScreen(0, 0, getWidth(), 400);
            drawBackground();
    
            graPixel.setFont(customFont);
            graPixel.setColor(colorText); // Establece el color del texto
    
            char[] charArray = text.toCharArray();
            StringBuilder auxString = new StringBuilder();
    
            for (int i = 0; i < charArray.length; i++) {
                auxString.append(charArray[i]);
                graPixel.drawString(auxString.toString(), x, y);
                repaint();
    
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            buffer.setRGB(x, y, c.getRGB());
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, this);
    }

    public void clearScreen(int x1, int y1, int x2, int y2) {
        graPixel.clearRect(x1, y1, x2 - x1, y2 - y1);
        repaint();
    }
    

    public void clearAllScreen() {
        graPixel.clearRect(0, 0, getWidth(), getHeight());
        drawBackground();
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

    public void drawBackground() {

        Color backgroundColor = Color.decode("#2169D6");

        int[] bg1x = {0, 300, 300, 0};
        int[] bg1y = {0, 0, 200, 200};
        fillPolygonScanLine(bg1x, bg1y, backgroundColor);

        int[] bg2x = {320, 700, 700, 320};
        int[] bg2y = {0, 0, 400, 400};
        fillPolygonScanLine(bg2x, bg2y, backgroundColor);

        int[] bg3x = {720, 950, 950, 720};
        int[] bg3y = {0, 0, 250, 250};
        fillPolygonScanLine(bg3x, bg3y, backgroundColor);

        int[] bg4x = {970, 1400, 1400, 970};
        int[] bg4y = {0, 0, 250, 250};
        fillPolygonScanLine(bg4x, bg4y, backgroundColor);

        int[] bg5x = {1420, 1500, 1500, 1420};
        int[] bg5y = {0, 0, 400, 400};
        fillPolygonScanLine(bg5x, bg5y, backgroundColor);

        int[] bg6x = {0, 300, 300, 0};
        int[] bg6y = {220, 220, 400, 400};
        fillPolygonScanLine(bg6x, bg6y, backgroundColor);

        int[] bg7x = {720, 1400, 1400, 720};
        int[] bg7y = {270, 270, 400, 400};
        fillPolygonScanLine(bg7x, bg7y, backgroundColor);
    }
}