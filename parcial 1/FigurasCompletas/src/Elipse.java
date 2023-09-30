import java.awt.Color;

public class Elipse {
    
    public static void main(String[] args) {
        Figuras figurasFrame = new Figuras();
        figurasFrame.setVisible(true);

        figurasFrame.drawEllipse(425, 235, 100, 50, Color.GREEN);
        figurasFrame.drawEllipse(425, 235, 85, 40, Color.GREEN);
        figurasFrame.drawEllipse(425, 435, 70, 30, Color.GREEN);
    }
}
