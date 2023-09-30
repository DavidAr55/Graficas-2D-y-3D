import java.awt.Color;

public class Bresenham {
    
    public static void main(String[] args) {
        Figuras figurasFrame = new Figuras();
        figurasFrame.setVisible(true);

        figurasFrame.drawLineBresenham(50, 50, 500, 90, Color.YELLOW);
        figurasFrame.drawCircleBresenham(200, 300, 150, Color.BLUE);
    }
}
