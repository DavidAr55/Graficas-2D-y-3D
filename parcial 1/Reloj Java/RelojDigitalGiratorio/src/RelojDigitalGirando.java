import javax.swing.JFrame;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RelojDigitalGirando extends JPanel implements Runnable {
    private Thread relojThread;
    private double angulo = 0;

    public RelojDigitalGirando() {
        relojThread = new Thread(this);
        relojThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String horaActual = sdf.format(new Date());

        // Centra el texto en el panel
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(horaActual);
        int textHeight = fm.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - textWidth / 2;
        int y = centerY + textHeight / 2;

        // Rota el gráfico
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(angulo), centerX, centerY);

        // Dibuja la hora actual en el centro del panel
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 48));
        g2d.drawString(horaActual, x, y);
    }

    @Override
    public void run() {
        while (true) {
            angulo += 1; // Incrementa el ángulo de rotación
            if (angulo >= 360) {
                angulo = 0; // Reinicia el ángulo cuando llega a 360 grados
            }
            repaint(); // Redibuja el reloj con el nuevo ángulo
            try {
                Thread.sleep(1000); // Espera 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Reloj Digital Giratorio");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.add(new RelojDigitalGirando());
            frame.setVisible(true);
        });
    }
}
