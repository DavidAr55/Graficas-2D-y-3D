import java.awt.Color;

public class Main {
    public static void main(String[] args) throws Exception {
        
        Poligono poligono = new Poligono();

        poligono.setSize(800, 600);
        poligono.setVisible(true);

        poligono.initPixel();

        for(int i = 100; i <= 300; i++) {
            try {
                
                poligono.clearScreen();
                poligono.renderCube(i, 300);
                Thread.sleep(50);
                poligono.repaint();

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}