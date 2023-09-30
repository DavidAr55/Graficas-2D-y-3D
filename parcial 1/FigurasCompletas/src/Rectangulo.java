import java.awt.Color;

public class Rectangulo {
    
    public static void main(String[] args) {
        Figuras figurasFrame = new Figuras();
        figurasFrame.setVisible(true);

        figurasFrame.drawRectangle(150, 100, 600, 300, Color.RED);
        figurasFrame.drawRectangle(150, 350, 250, 450, Color.WHITE);
    }
}
