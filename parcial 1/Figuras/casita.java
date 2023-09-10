import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;

public class casita extends JFrame {

    public casita() {
        setSize(500, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        casita casita = new casita();
        casita.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.red);
        g.fillOval(0, 50, 50, 50);  // Circulo

        g.setColor(Color.green);
        g.fillRect(200,50,50,100);  // Rectanguki

        g.setColor(Color.blue);
        g.fillRect(450,50,50,50);   // Cuadrado
    }
}