import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class Reloj extends JFrame {

    // Atributos de mi JFrame
    private BufferedImage buffer;
    private Graphics2D graPixel;

    // Varibles Globales
    private int RELOJ_Y = 400; 
    private int RELOJ_X = 300; 
    private int RADIO_RELOJ = 250; 

    // Definimos las valiables para la hora actual
    private Timer timer = new Timer();
    private int Hora;
    private int Minuto;
    private int Segundo;
    
    public Reloj() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        Graphics2D graphics2d = (Graphics2D) graPixel;
        graphics2d.setBackground(Color.WHITE); 
        graphics2d.clearRect(0, 0, getWidth(), getHeight());
    }

    public void actualizar() {

        timer.scheduleAtFixedRate(new TimerTask() {
            
            @Override
            public void run() {
                Calendar actual = Calendar.getInstance(TimeZone.getDefault());

                Hora = actual.get(Calendar.HOUR_OF_DAY);
                Minuto = actual.get(Calendar.MINUTE);
                Segundo = actual.get(Calendar.SECOND);

                repaint();
            }
        }, 0, 1000);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        dibujarReloj(graPixel, RELOJ_Y, RELOJ_X, RADIO_RELOJ, Hora, Minuto, Segundo);
        graphics.drawImage(buffer, 0, 0, this);
    }

    public void dibujarLineaDDA(int x1, int y1, int x2, int y2, Color c) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int pasos = Math.max(Math.abs(dx), Math.abs(dy));
    
        float x_Incremento = (float) dx / pasos;
        float y_Incremento = (float) dy / pasos;
        
        float x = x1;
        float y = y1;
    
        for (int i = 0; i <= pasos; i++) {
            putPixel(Math.round(x), Math.round(y), c);
            x += x_Incremento;
            y += y_Incremento;
        }
    }

    public void dibujarReloj(Graphics2D g, int RELOJ_Y, int RELOJ_X, int radius, int hour, int minute, int second) {
        
        g.setColor(Color.BLACK);
        g.fillOval(RELOJ_Y - radius, RELOJ_X - radius, 2 * radius, 2 * radius);
    
        int x2 = (int) (RELOJ_Y + 0.8 * radius * Math.sin(Math.toRadians(6 * second)));
        int y2 = (int) (RELOJ_X - 0.8 * radius * Math.cos(Math.toRadians(6 * second)));
    
        int minuto_X = (int) (RELOJ_Y + 0.7 * radius * Math.sin(Math.toRadians(6 * (minute + hour * 60))));
        int minuto_Y = (int) (RELOJ_X - 0.7 * radius * Math.cos(Math.toRadians(6 * (minute + hour * 60))));
        int hora_X = (int) (RELOJ_Y + 0.6 * radius * Math.sin(Math.toRadians(30 * hour + 0.5 * minute)));
        int hora_Y = (int) (RELOJ_X - 0.6 * radius * Math.cos(Math.toRadians(30 * hour + 0.5 * minute)));
    
        dibujarLineaDDA(RELOJ_Y, RELOJ_X, hora_X, hora_Y, Color.RED);
        dibujarLineaDDA(RELOJ_Y, RELOJ_X, minuto_X, minuto_Y, Color.RED);
        dibujarLineaDDA(RELOJ_Y, RELOJ_X, x2, y2, Color.WHITE);
    
        g.setColor(Color.BLACK);
        g.fillOval(RELOJ_Y - 2, RELOJ_X - 2, 4, 4);
    
        g.setColor(Color.decode("#FFD700"));
        g.setFont(new Font("Algerian", Font.PLAIN, 30));
    
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(30 * i);
            int numX = (int) (RELOJ_Y + 0.8 * radius * Math.sin(angle)) - 6;
            int numY = (int) (RELOJ_X - 0.8 * radius * Math.cos(angle)) + 6;
    
            g.drawString(Integer.toString(i), numX, numY);
        }
    }
}
