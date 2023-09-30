import java.awt.Color;

public class PuntoMedio {

    public static void main(String[] args) {
        Figuras figurasFrame = new Figuras();
        figurasFrame.setVisible(true);

        figurasFrame.drawLine(50, 175, 520, 130, Color.WHITE);
        figurasFrame.drawLine(50, 275, 520, 275, Color.WHITE);
        figurasFrame.drawLine(50, 375, 50, 575, Color.WHITE);
    }
}
