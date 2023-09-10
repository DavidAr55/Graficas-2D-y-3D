import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Casita extends JFrame {

    public Casita() {
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Evento para cerrra la ventana al precionar Enter
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Nos vamos a mover en una matriz donde cada bloque medirá [10] x [10]

        // Generamos el relleno del fondo
        g.setColor(Color.decode("#533557"));
        g.fillRect(0, 0, 500, 500);

        g.setColor(Color.decode("#1B172E"));
        g.fillRect(0, 430, 500, 70);

        g.setColor(Color.decode("#D3B5D7"));
        g.fillRect(70, 65, 5, 5);

        g.setColor(Color.decode("#D3B5D7"));
        g.fillRect(50, 150, 4, 4);

        g.setColor(Color.decode("#D3B5D7"));
        g.fillRect(30, 55, 3, 3);

        g.setColor(Color.decode("#D3B5D7"));
        g.fillRect(235, 55, 5, 5);

        g.setColor(Color.decode("#D3B5D7"));
        g.fillRect(350, 50, 4, 4);


        // Generamos la Luna con una funcion basada en la ecuación de la distancia euclidiana
        int SQUARE_SIZE = 5;
        int CIRCLE_RADIUS = 15;
        
        g.setColor(Color.decode("#7D5287"));

        int circleCenterX = 450;
        int circleCenterY = 85;

        for (int x = -CIRCLE_RADIUS; x <= CIRCLE_RADIUS; x++) {
            for (int y = -CIRCLE_RADIUS; y <= CIRCLE_RADIUS; y++) {
                if (x * x + y * y <= CIRCLE_RADIUS * CIRCLE_RADIUS) {
                    int xPos = circleCenterX + x * SQUARE_SIZE;
                    int yPos = circleCenterY + y * SQUARE_SIZE;
                    g.fillRect(xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }

        CIRCLE_RADIUS = 9;
        
        g.setColor(Color.decode("#9664A3"));

        for (int x = -CIRCLE_RADIUS; x <= CIRCLE_RADIUS; x++) {
            for (int y = -CIRCLE_RADIUS; y <= CIRCLE_RADIUS; y++) {
                if (x * x + y * y <= CIRCLE_RADIUS * CIRCLE_RADIUS) {
                    int xPos = circleCenterX + x * SQUARE_SIZE;
                    int yPos = circleCenterY + y * SQUARE_SIZE;
                    g.fillRect(xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }

        CIRCLE_RADIUS = 6;
        
        g.setColor(Color.decode("#C8ADD0"));

        for (int x = -CIRCLE_RADIUS; x <= CIRCLE_RADIUS; x++) {
            for (int y = -CIRCLE_RADIUS; y <= CIRCLE_RADIUS; y++) {
                if (x * x + y * y <= CIRCLE_RADIUS * CIRCLE_RADIUS) {
                    int xPos = circleCenterX + x * SQUARE_SIZE;
                    int yPos = circleCenterY + y * SQUARE_SIZE;
                    g.fillRect(xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }


        // Generando los colores de la casa en general
        g.setColor(Color.decode("#9F7B59"));
        g.fillRect(40, 210, 420, 200);

        g.setColor(Color.decode("#CA8870"));
        g.fillRect(40, 410, 420, 10);

        g.setColor(Color.decode("#8F4A2D"));
        g.fillRect(40, 420, 420, 20);

        int[] xPoints = {30, 140, 250}; // Coordenadas x de los vértices
        int[] yPoints = {210, 100, 210};  // Coordenadas y de los vértices

        g.setColor(Color.decode("#9F7B59"));
        g.fillPolygon(xPoints, yPoints, 3); // Dibujar un triángulo con los puntos dados

        int[] xPoints_2 = {330, 420, 470}; 
        int[] yPoints_2 = {220, 130, 220};  

        g.setColor(Color.decode("#9F7B59"));
        g.fillPolygon(xPoints_2, yPoints_2, 3); 


        // Generando el tejado
        g.setColor(Color.black);
        g.fillRect(140, 60, 190, 10);

        g.setColor(Color.decode("#8F4A2D"));
        g.fillRect(140, 70, 190, 10);

        g.setColor(Color.black);
        g.fillRect(130, 70, 10, 10);

        g.setColor(Color.black);
        g.fillRect(150, 70, 10, 10);
        
        g.setColor(Color.black);
        g.fillRect(330, 70, 10, 10);
        
        g.setColor(Color.black);
        g.fillRect(130, 80, 210, 10);
        
        g.setColor(Color.black);
        g.fillRect(120, 90, 10, 10);

        g.setColor(Color.black);
        g.fillRect(340, 90, 10, 10);

        // Generamos el mismo patron 7 veces
        for(int i = 0; i < 210; i+=30) {

            g.setColor(Color.decode("#CA8870"));
            g.fillRect(130 + i, 90, 10, 10);

            g.setColor(Color.decode("#A1695A"));
            g.fillRect(140 + i, 90, 20, 10);
        }

        // Generamos el mismo patron 4 x 12 veces
        int recorrer = 0;
        for(int j = 0; j < 120; j+=10) {

            for(int i = 0; i < 150; i+=30) {

                g.setColor(Color.decode("#CA8870"));
                g.fillRect(recorrer + 140 + i, 100 + j, 10, 10);

                g.setColor(Color.decode("#A1695A"));
                g.fillRect(recorrer + 150 + i, 100 + j, 20, 10);

                if(recorrer >= 10 && i == 0) {
                    g.setColor(Color.black);
                    g.fillRect(recorrer + 130 + i, 100 + j, 10, 10);
                }
            }

            recorrer += 10;
        }

        // Generamos el mismo patron 1 x 12 veces a la izquierda
        recorrer = 0;
        for(int j = 0; j < 120; j+=10) {

            for(int i = 10; i > 0; i-=10) {

                g.setColor(Color.black);
                g.fillRect(recorrer + 130 - i, 90 + j, 10, 10);

                g.setColor(Color.decode("#CA8870"));
                g.fillRect(recorrer + 140 - i, 90 + j, 10, 10);

                g.setColor(Color.decode("#A1695A"));
                g.fillRect(recorrer + 150 - i, 90 + j, 10, 10);

                if(recorrer <= -20 && i == 10) {
                    g.setColor(Color.black);
                    g.fillRect(recorrer + 140 + i, 90 + j, 10, 10);
                }
            }

            recorrer -= 10;
        }

        g.setColor(Color.black);
        g.fillRect(280, 100, 150, 10);

        g.setColor(Color.decode("#8F4A2D"));
        g.fillRect(290, 110, 140, 10);

        g.setColor(Color.black);
        g.fillRect(280, 110, 10, 10);

        g.setColor(Color.black);
        g.fillRect(410, 110, 10, 10);

        g.setColor(Color.black);
        g.fillRect(430, 110, 10, 10);

        g.setColor(Color.black);
        g.fillRect(280, 120, 160, 10);
        
        recorrer = 0;
        for(int i = 0; i < 30; i += 10) {

            g.setColor(Color.black);
            g.fillRect(290 + i, 130 + recorrer, 10, 30);

            recorrer += 30;
        }

        recorrer = 0;
        for(int i = 0; i < 50; i += 10) {

            if(i != 20) {
                g.setColor(Color.black);
                g.fillRect(420 + i, 130 + recorrer, 10, 20);

                g.setColor(Color.decode("#A1695A"));
                g.fillRect(430 + i, 130 + recorrer, 10, 20);

                g.setColor(Color.black);
                g.fillRect(440 + i, 130 + recorrer, 10, 20);

                recorrer += 20;
            }

            else {
                g.setColor(Color.black);
                g.fillRect(420 + i, 130 + recorrer, 10, 30);

                g.setColor(Color.decode("#A1695A"));
                g.fillRect(430 + i, 130 + recorrer, 10, 30);

                g.setColor(Color.black);
                g.fillRect(440 + i, 130 + recorrer, 10, 30);

                recorrer += 30;
            }
        }

        // Generamos el mismo patron 2 x 5 veces
        recorrer = 0;
        for(int j = 0; j < 110; j+=20) {

            for(int i = 0; i < 60; i+=30) {

                if(j != 40 && j != 50) {
                    if(i == 0) {
                        g.setColor(Color.black);
                        g.fillRect(recorrer + 420, 130 + j, 10, 20);
                    }

                    g.setColor(Color.decode("#CA8870"));
                    g.fillRect(recorrer + 410 - i, 130 + j, 10, 20);

                    g.setColor(Color.decode("#A1695A"));
                    g.fillRect(recorrer + 390 - i, 130 + j, 20, 20);
                }

                else {
                    if(i == 0) {
                        g.setColor(Color.black);
                        g.fillRect(recorrer + 420, 130 + j, 10, 30);
                    }

                    if(j == 50)
                        j -= 10;

                    g.setColor(Color.decode("#CA8870"));
                    g.fillRect(recorrer + 410 - i, 130 + j, 10, 30);

                    g.setColor(Color.decode("#A1695A"));
                    g.fillRect(recorrer + 390 - i, 130 + j, 20, 30);
                    
                    j += 10;
                }
            }

            recorrer -= 10;
        }

        // Contorno del tejado bottom
        g.setColor(Color.decode("#A1695A"));
        g.fillRect(300, 130, 20, 20);

        g.setColor(Color.decode("#CA8870"));
        g.fillRect(320, 130, 10, 20);

        g.setColor(Color.decode("#A1695A"));
        g.fillRect(330, 130, 20, 20);

        g.setColor(Color.decode("#CA8870"));
        g.fillRect(350, 130, 10, 20);

        g.setColor(Color.decode("#CA8870"));
        g.fillRect(310, 150, 10, 20);

        g.setColor(Color.decode("#A1695A"));
        g.fillRect(320, 150, 20, 20);

        g.setColor(Color.decode("#CA8870"));
        g.fillRect(340, 150, 10, 20);

        g.setColor(Color.decode("#A1695A"));
        g.fillRect(310, 170, 20, 20);

        g.setColor(Color.decode("#CA8870"));
        g.fillRect(330, 170, 10, 30);

        g.setColor(Color.decode("#A1695A"));
        g.fillRect(320, 190, 10, 10);

        g.setColor(Color.decode("#CA8870"));
        g.fillRect(320, 200, 10, 20);

        g.setColor(Color.decode("#CA8870"));
        g.fillRect(310, 220, 10, 10);

        g.setColor(Color.black);
        g.fillRect(10, 210, 30, 10);

        g.setColor(Color.black);
        g.fillRect(240, 210, 80, 10);

        g.setColor(Color.black);
        g.fillRect(460, 240, 30, 10);

        g.setColor(Color.black);
        g.fillRect(320, 240, 70, 10);


        // Poste de Luz
        g.setColor(Color.black);
        g.fillRect(280, 230, 50, 60);

        g.setColor(Color.black);
        g.fillRect(300, 220, 10, 10);

        g.setColor(Color.decode("#3E4067"));
        g.fillRect(290, 240, 30, 10);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(290, 260, 30, 20);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(300, 260, 10, 20);

        g.setColor(Color.black);
        g.fillRect(290, 290, 30, 100);

        g.setColor(Color.decode("#3E4067"));
        g.fillRect(300, 290, 10, 100);

        g.setColor(Color.black);
        g.fillRect(280, 390, 50, 60);

        g.setColor(Color.decode("#7681B9"));
        g.fillRect(290, 400, 30, 20);

        g.setColor(Color.decode("#3E4067"));
        g.fillRect(290, 430, 30, 10);


        // Contorno de la casa
        g.setColor(Color.black);
        g.fillRect(30, 440, 440, 10);

        g.setColor(Color.black);
        g.fillRect(30, 220, 10, 220);

        g.setColor(Color.black);
        g.fillRect(460, 250, 10, 190);

        g.setColor(Color.black);
        g.fillRect(260, 220, 10, 220);

        g.setColor(Color.black);
        g.fillRect(360, 250, 10, 190);

        g.setColor(Color.black);
        g.fillRect(100, 250, 100, 190);

        g.setColor(Color.black);
        g.fillRect(400, 250, 40, 190);


        // Decoracion (Ventanas, luces, puerta)
        g.setColor(Color.decode("#AC7648"));
        g.fillRect(110, 260, 80, 180);

        g.setColor(Color.decode("#AC7648"));
        g.fillRect(410, 260, 20, 180);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(50, 270, 40, 40);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(210, 270, 40, 40);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(50, 320, 40, 60);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(210, 320, 40, 60);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(380, 270, 10, 40);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(450, 270, 10, 40);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(380, 320, 10, 60);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(450, 320, 10, 60);

        
        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(60, 280, 20, 20);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(220, 280, 20, 20);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(60, 330, 20, 40);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(220, 330, 20, 40);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(380, 280, 10, 20);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(450, 280, 10, 20);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(380, 330, 10, 40);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(450, 330, 10, 40);


        g.setColor(Color.decode("#FFC976"));
        g.fillRect(120, 270, 20, 40);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(160, 270, 20, 40);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(120, 320, 20, 60);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(160, 320, 20, 60);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(120, 390, 20, 40);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(160, 390, 20, 40);


        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(120, 280, 20, 20);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(160, 280, 20, 20);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(120, 330, 20, 40);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(160, 330, 20, 40);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(120, 400, 20, 20);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(160, 400, 20, 20);

        
        g.setColor(Color.decode("#6A442D"));
        g.fillRect(170, 340, 10, 20);


        g.setColor(Color.decode("#FFC976"));
        g.fillRect(410, 270, 20, 40);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(410, 320, 20, 60);
        
        g.setColor(Color.decode("#FFC976"));
        g.fillRect(410, 390, 20, 40);


        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(410, 280, 20, 20);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(410, 330, 20, 40);
        
        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(410, 400, 20, 20);


        g.setColor(Color.decode("#6A442D"));
        g.fillRect(420, 340, 10, 20);


        // Luces de arriba de la puerta
        g.setColor(Color.decode("#FFC976"));
        g.fillRect(90, 190, 20, 10);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(80, 200, 40, 10);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(70, 210, 70, 10);


        g.setColor(Color.decode("#FFC976"));
        g.fillRect(190, 190, 20, 10);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(180, 200, 40, 10);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(160, 210, 70, 10);


        g.setColor(Color.decode("#FFC976"));
        g.fillRect(130, 150, 10, 50);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(120, 160, 10, 30);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(110, 170, 10, 10);


        g.setColor(Color.decode("#FFC976"));
        g.fillRect(160, 150, 10, 50);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(170, 160, 10, 30);

        g.setColor(Color.decode("#FFC976"));
        g.fillRect(180, 170, 10, 10);


        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(80, 210, 40, 10);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(90, 200, 20, 10);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(180, 210, 40, 10);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(190, 200, 20, 10);


        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(120, 170, 10, 10);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(130, 160, 10, 30);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(170, 170, 10, 10);

        g.setColor(Color.decode("#FFEBBE"));
        g.fillRect(160, 160, 10, 30);

        /*SQUARE_SIZE = 5;
        CIRCLE_RADIUS = 42;

        g.setColor(Color.decode("#FaF5C9"));

        circleCenterX = 250;
        circleCenterY = 250;

        for (int x = -CIRCLE_RADIUS; x <= CIRCLE_RADIUS; x++) {
            for (int y = -CIRCLE_RADIUS; y <= CIRCLE_RADIUS; y++) {
                if (x * x + y * y <= CIRCLE_RADIUS * CIRCLE_RADIUS) {
                    int xPos = circleCenterX + x * SQUARE_SIZE;
                    int yPos = circleCenterY + y * SQUARE_SIZE;
                    g.fillRect(xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }*/
    }
}
