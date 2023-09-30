import java.awt.Color;

public class CirculoPolar {
    
    public static void main(String[] args) {
        Figuras figurasFrame = new Figuras();
        figurasFrame.setVisible(true);

        figurasFrame.drawPolarCircle(450, 400, 60, Color.WHITE);
        figurasFrame.drawPolarCircle(300, 300, 150, Color.RED);
    }
}
