import java.awt.Color;

public class App {
    public static void main(String[] args) throws Exception {
        
        Figuras figuras = new Figuras();
        figuras.setVisible(!false);

        figuras.drawLine(50, 50, 150, 150, Color.RED);
        figuras.drawLine(150, 90, 250, 90, Color.RED);
        figuras.drawLine(250, 150, 350, 50, Color.RED);
        figuras.drawLine(350, 90, 450, 90, Color.RED);

        figuras.drawCircle(75, 235, 60, Color.BLUE);
        figuras.drawCircle(75, 235, 45, Color.BLUE);
        figuras.drawCircle(75, 235, 30, Color.BLUE);
        figuras.drawCircle(75, 235, 15, Color.BLUE);

        figuras.drawRectangle(150, 200, 300, 290, Color.ORANGE);
        figuras.drawRectangle(170, 220, 280, 270, Color.ORANGE);

        figuras.drawEllipse(425, 235, 100, 50, Color.CYAN);
        figuras.drawEllipse(425, 235, 85, 40, Color.CYAN);
        figuras.drawEllipse(425, 235, 70, 30, Color.CYAN);
        figuras.drawEllipse(425, 235, 55, 20, Color.CYAN);

        figuras.drawPolarCircle(450, 400, 60, Color.WHITE);
    }
}
