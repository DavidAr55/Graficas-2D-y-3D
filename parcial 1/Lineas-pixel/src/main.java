import java.awt.Color;

import javax.swing.JOptionPane;

public class main {

    public static void main(String[] args) throws Exception {
        
        Pixel pixel = new Pixel();
        pixel.setVisible(!false);

        // Lineas con DDA
        pixel.linea_DDA(30, recorrer_y(30), 80, recorrer_y(80), Color.RED);
        pixel.linea_DDA(recorrer_x(80), recorrer_y(80), recorrer_x(30), recorrer_y(30), Color.BLUE);

        // Linea recta
        pixel.linea_Recta(100, 250, 300, 250, Color.ORANGE);
    }

    public static int recorrer_x(int x) {
        return (x + 250);
    }

    public static int recorrer_y(int y) {
        return (y + 50);
    }
}
