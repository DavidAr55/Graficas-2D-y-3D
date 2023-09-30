import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CircleDrawingApp extends JFrame {
    private BufferedImage canvas;
    private int centerX, centerY, radius;
    private int angle = 0;
    private Timer timer;

    public CircleDrawingApp() {
        setTitle("Dibujando un círculo con senos y cosenos");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        radius = 100;

        timer = new Timer(250, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (angle < 360) {
                    int x = centerX + (int) (radius * Math.cos(Math.toRadians(angle)));
                    int y = centerY + (int) (radius * Math.sin(Math.toRadians(angle)));

                    if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
                        canvas.setRGB(x, y, Color.RED.getRGB());
                        repaint();
                    }

                    angle++;
                } else {
                    timer.stop();
                }
            }
        });

        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CircleDrawingApp().setVisible(true);
            }
        });
    }
}
