import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class CatClock extends JFrame {

    private BufferedImage buffer;
    private Graphics2D graPixel;

    private int centerX = 322; // Coordenada x del centro del reloj
    private int centerY = 470; // Coordenada y del centro del reloj
    private int clockRadius = 104; // Radio del reloj en píxeles
    private int currentHour;
    private int currentMinute;
    private int currentSecond;
    
    public CatClock() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(666, 1000);
        setLocationRelativeTo(null);

        // Inicializa el buffer con las mismas dimensiones que la ventana
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.createGraphics();

        // Establece el color de fondo del buffer
        Graphics2D g2d = (Graphics2D) graPixel;
        g2d.setBackground(Color.WHITE); // Cambia Color.WHITE al color que desees
        g2d.clearRect(0, 0, getWidth(), getHeight());
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(x, y, c.getRGB());
    }

    @Override
    public void paint(Graphics g) {

        // Dibuja el fondo degradado
        drawGradientBackground(graPixel);

        drawCatClock(graPixel);
        drawClock(graPixel, centerX, centerY, clockRadius, currentHour, currentMinute, currentSecond);

        g.drawImage(buffer, 0, 0, this);
    }

    private void drawGradientBackground(Graphics2D g) {
        // Tamaño de la ventana
        int width = getWidth();
        int height = getHeight();
    
        // Colores para el fondo degradado
        Color color1 = new Color(255, 192, 203); // Rosa claro
        Color color2 = new Color(255, 255, 255); // Rosa oscuro
    
        // Dibuja el fondo degradado
        GradientPaint gradient = new GradientPaint(0, 0, color1, width, height, color2);
        g.setPaint(gradient);
        g.fillRect(0, 0, width, height);
    }

    // Algoritmo de Bresenham para dibujar una línea
    public void drawLineBresenham(int x1, int y1, int x2, int y2, Color c) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x1, y1, c);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }
            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
    }

    public void drawCatClock(Graphics2D g) {

        // Declaramos una instancia para nuestra clase ConvertTxtToCords
        ConvertTxtToCords convertTxtToCords = new ConvertTxtToCords();

        // Script para obtener las coordenadas del cuerpo del Gatito uwu
        String filePath = "./cords/cordsBody.txt";

        int[] xPointsFile = convertTxtToCords.convertTxtToXPoints(filePath);
        int[] yPointsFile = convertTxtToCords.convertTxtToYPoints(filePath);
        int numVerticesFile = convertTxtToCords.countVertices(filePath);

        // Multiplica por 2 el valor de las coordenadas en xPointsFile y yPointsFile
        for (int i = 0; i < numVerticesFile; i++) {
            xPointsFile[i] *= 2;
            yPointsFile[i] *= 2;
        }
        // Final del script para dibujar el cuerpo del gatito

        // Script para obtener las coordenadas de la oreja izquierda Gatito uwu
        String filePathLeftEar = "./cords/cordsLeftEar.txt";

        int[] xPointsFileLeftEar = convertTxtToCords.convertTxtToXPoints(filePathLeftEar);
        int[] yPointsFileLeftEar = convertTxtToCords.convertTxtToYPoints(filePathLeftEar);
        int numVerticesFileLeftEar = convertTxtToCords.countVertices(filePathLeftEar);

        for (int i = 0; i < numVerticesFileLeftEar; i++) {
            xPointsFileLeftEar[i] *= 2;
            yPointsFileLeftEar[i] *= 2;
        }
        // Final del script para dibujar la oreja izquierda del gatito

        // Script para obtener las coordenadas de la oreja derecha Gatito uwu
        String filePathRightEar = "./cords/cordsRightEar.txt";

        int[] xPointsFileRightEar = convertTxtToCords.convertTxtToXPoints(filePathRightEar);
        int[] yPointsFileRightEar = convertTxtToCords.convertTxtToYPoints(filePathRightEar);
        int numVerticesFileRightEar = convertTxtToCords.countVertices(filePathRightEar);

        for (int i = 0; i < numVerticesFileRightEar; i++) {
            xPointsFileRightEar[i] *= 2;
            yPointsFileRightEar[i] *= 2;
        }
        // Final del script para dibujar la oreja derecha del gatito

        // Script para obtener las coordenadas de la cola del Gatito uwu
        String filePathTail = "./cords/cordsTail.txt";

        int[] xPointsFileTail = convertTxtToCords.convertTxtToXPoints(filePathTail);
        int[] yPointsFileTail = convertTxtToCords.convertTxtToYPoints(filePathTail);
        int numVerticesFileTail = convertTxtToCords.countVertices(filePathTail);

        for (int i = 0; i < numVerticesFileTail; i++) {
            xPointsFileTail[i] *= 2;
            yPointsFileTail[i] *= 2;
        }
        // Final del script para dibujar la cola del gatito

        // Script para obtener las coordenadas de el collar del Gatito uwu
        String filePathNecklace = "./cords/cordsNecklace.txt";

        int[] xPointsFileNecklace = convertTxtToCords.convertTxtToXPoints(filePathNecklace);
        int[] yPointsFileNecklace = convertTxtToCords.convertTxtToYPoints(filePathNecklace);
        int numVerticesFileNecklace = convertTxtToCords.countVertices(filePathNecklace);

        for (int i = 0; i < numVerticesFileNecklace; i++) {
            xPointsFileNecklace[i] *= 2;
            yPointsFileNecklace[i] *= 2;
        }
        // Final del script para dibujar el collar del gatito

        // Script para obtener las coordenadas de la pata derecha del Gatito uwu
        String filePathRightPaw = "./cords/cordsRightPaw.txt";

        int[] xPointsFileRightPaw = convertTxtToCords.convertTxtToXPoints(filePathRightPaw);
        int[] yPointsFileRightPaw = convertTxtToCords.convertTxtToYPoints(filePathRightPaw);
        int numVerticesFileRightPaw = convertTxtToCords.countVertices(filePathRightPaw);

        for (int i = 0; i < numVerticesFileRightPaw; i++) {
            xPointsFileRightPaw[i] *= 2;
            yPointsFileRightPaw[i] *= 2;
        }
        // Final del script para dibujar la pata derecha del gatito

        // Script para obtener las coordenadas de la Nariz y boca del Gatito uwu
        String filePathNose = "./cords/cordsNose.txt";

        int[] xPointsFileNose = convertTxtToCords.convertTxtToXPoints(filePathNose);
        int[] yPointsFileNose = convertTxtToCords.convertTxtToYPoints(filePathNose);
        int numVerticesFileNose = convertTxtToCords.countVertices(filePathNose);

        for (int i = 0; i < numVerticesFileNose; i++) {
            xPointsFileNose[i] *= 2;
            yPointsFileNose[i] *= 2;
        }
        // Final del script para dibujar la Nariz y boca del gatito

        
        // Dibuja el polígono cargado desde el archivo cordsBody.txt
        Polygon polygonBody = new Polygon(xPointsFile, yPointsFile, numVerticesFile);
        g.setColor(Color.decode("#D6D0BA")); // Color de relleno
        g.fillPolygon(polygonBody); // Rellenar el polígono

        g.setColor(Color.decode("#78674B")); // Color del borde
        g.drawPolygon(polygonBody); // Dibujar el borde del polígono

        // Dibuja relleno de la oreja izquierda
        Polygon polygonLeftEar = new Polygon(xPointsFileLeftEar, yPointsFileLeftEar, numVerticesFileLeftEar);
        g.setColor(Color.decode("#CE938F")); 
        g.fillPolygon(polygonLeftEar); 

        g.setColor(Color.decode("#BB585B")); 
        g.drawPolygon(polygonLeftEar); 

        // Dibuja relleno de la oreja derecha
        Polygon polygonRightEar = new Polygon(xPointsFileRightEar, yPointsFileRightEar, numVerticesFileRightEar);
        g.setColor(Color.decode("#CE938F")); 
        g.fillPolygon(polygonRightEar); 

        g.setColor(Color.decode("#BB585B")); 
        g.drawPolygon(polygonRightEar); 

        // Dibuja una elipse con color de borde y relleno

        // Mabchas del gato
        g.setColor(Color.decode("#A15229")); // Color del borde
        g.drawOval((152 * 2), (57 * 2), 60, 36);
        g.setColor(Color.decode("#C17553")); // Color de relleno
        g.fillOval((152 * 2), (57 * 2), 60, 36);

        g.setColor(Color.decode("#28160C")); // Color del borde
        g.drawOval((158 * 2), (60 * 2), 36, 20);
        g.setColor(Color.decode("#573625")); // Color de relleno
        g.fillOval((158 * 2), (60 * 2), 36, 20);


        g.setColor(Color.decode("#A15229")); // Color del borde
        g.drawOval((87 * 2), (106 * 2), 34, 56);
        g.setColor(Color.decode("#C17553")); // Color de relleno
        g.fillOval((87 * 2), (106 * 2), 34, 56);

        g.setColor(Color.decode("#28160C")); // Color del borde
        g.drawOval((90 * 2), (109 * 2), 24, 38);
        g.setColor(Color.decode("#573625")); // Color de relleno
        g.fillOval((90 * 2), (109 * 2), 24, 38);


        g.setColor(Color.decode("#A15229")); // Color del borde
        g.drawOval((203 * 2), (275 * 2), 56, 78);
        g.setColor(Color.decode("#C17553")); // Color de relleno
        g.fillOval((203 * 2), (275 * 2), 56, 78);

        g.setColor(Color.decode("#28160C")); // Color del borde
        g.drawOval((209 * 2), (282 * 2), 38, 46);
        g.setColor(Color.decode("#573625")); // Color de relleno
        g.fillOval((209 * 2), (282 * 2), 38, 46);


        g.setColor(Color.decode("#A15229")); // Color del borde
        g.drawOval((93 * 2), (284 * 2), 64, 76);
        g.setColor(Color.decode("#C17553")); // Color de relleno
        g.fillOval((93 * 2), (284 * 2), 64, 76);

        g.setColor(Color.decode("#28160C")); // Color del borde
        g.drawOval((97 * 2), (291 * 2), 44, 52);
        g.setColor(Color.decode("#573625")); // Color de relleno
        g.fillOval((97 * 2), (291 * 2), 44, 52);
        

        // Dibuja el polígono cargado desde el archivo cordsBody.txt
        Polygon polygonTail = new Polygon(xPointsFileTail, yPointsFileTail, numVerticesFileTail);
        g.setColor(Color.decode("#D6D0BA")); // Color de relleno
        g.fillPolygon(polygonTail); // Rellenar el polígono

        g.setColor(Color.decode("#78674B")); // Color del borde
        g.drawPolygon(polygonTail); // Dibujar el borde del polígono
    
        
        g.setColor(Color.decode("#A15229")); // Color del borde
        g.drawOval((141 * 2), (392 * 2), 40, 56);
        g.setColor(Color.decode("#C17553")); // Color de relleno
        g.fillOval((141 * 2), (392 * 2), 40, 56);

        g.setColor(Color.decode("#28160C")); // Color del borde
        g.drawOval((143 * 2), (396 * 2), 34, 36);
        g.setColor(Color.decode("#573625")); // Color de relleno
        g.fillOval((143 * 2), (396 * 2), 34, 36);


        // Dibuja relleno de la oreja derecha
        Polygon polygonNecklace = new Polygon(xPointsFileNecklace, yPointsFileNecklace, numVerticesFileNecklace);
        g.setColor(Color.decode("#B52D21")); 
        g.fillPolygon(polygonNecklace); 

        g.setColor(Color.decode("#90462D")); 
        g.drawPolygon(polygonNecklace); 


        // Define las coordenadas de las líneas
        int[] xPoints = {110, 113, 116, 118, 118, 115, 112, 111, 112, 114, 116, 116};
        int[] yPoints = {60, 61, 65, 70, 76, 84, 96, 107, 117, 125, 132, 141};

        for (int i = 0; i < xPoints.length; i++) {
            xPoints[i] *= 2;
            yPoints[i] *= 2;
        }
        // Dibuja las líneas en el componente
        drawLines(g, xPoints, yPoints, Color.decode("#78674B"));


        // Define las coordenadas de las líneas
        int[] xPointsLeftPaw = {92, 90, 89, 89, 94, 99, 98, 98, 99, 98, 98, 99, 106, 110, 111, 109, 105, 109, 111};
        int[] yPointsLeftPaw = {75, 71, 67, 62, 59, 60, 65, 69, 72, 69, 65, 60, 59, 60, 63, 68, 73, 68, 63};

        for (int i = 0; i < xPointsLeftPaw.length; i++) {
            xPointsLeftPaw[i] *= 2;
            yPointsLeftPaw[i] *= 2;
        }
        // Dibuja las líneas en el componente
        drawLines(g, xPointsLeftPaw, yPointsLeftPaw, Color.decode("#78674B"));


        // Dibuja relleno de la oreja derecha
        Polygon polygonRightPaw = new Polygon(xPointsFileRightPaw, yPointsFileRightPaw, numVerticesFileRightPaw);
        g.setColor(Color.decode("#D6D0BA")); 
        g.fillPolygon(polygonRightPaw); 

        g.setColor(Color.decode("#78674B")); 
        g.drawPolygon(polygonRightPaw); 

        // Dibuja relleno de la oreja derecha
        Polygon polygonNose = new Polygon(xPointsFileNose, yPointsFileNose, numVerticesFileNose);
        g.setColor(Color.decode("#CE938F")); 
        g.fillPolygon(polygonNose); 

        g.setColor(Color.decode("#BB585B")); 
        g.drawPolygon(polygonNose); 


        // Array para el ojo izquiero del gato:
        int[] xPointsLeftEye = {131, 135, 139, 146, 149, 152, 153, 150, 147, 144, 140, 134};
        int[] yPointsLeftEye = {93, 90, 88, 88, 89, 92, 95, 94, 93, 92, 92, 93};

        for (int i = 0; i < xPointsLeftEye.length; i++) {
            xPointsLeftEye[i] *= 2;
            yPointsLeftEye[i] *= 2;
        }

        Polygon polygonLeftEye = new Polygon(xPointsLeftEye, yPointsLeftEye, xPointsLeftEye.length);
        g.setColor(Color.decode("#3D2219")); 
        g.fillPolygon(polygonLeftEye); 

        g.setColor(Color.BLACK); 
        g.drawPolygon(polygonLeftEye); 


        // Array para el ojo derecho del gato:
        int[] xPointsRightEye = {178, 178, 181, 184, 188, 194, 198, 201, 197, 192, 189, 184, 180};
        int[] yPointsRightEye = {97, 95, 93, 92, 92, 94, 97, 99, 99, 97, 96, 96, 97};

        for (int i = 0; i < xPointsRightEye.length; i++) {
            xPointsRightEye[i] *= 2;
            yPointsRightEye[i] *= 2;
        }

        Polygon polygonRightEye = new Polygon(xPointsRightEye, yPointsRightEye, xPointsRightEye.length);
        g.setColor(Color.decode("#3D2219")); 
        g.fillPolygon(polygonRightEye); 

        g.setColor(Color.BLACK); 
        g.drawPolygon(polygonRightEye); 


        // Array para el Bigotes de la izquierda del gato:
        int[] arrayX1 = {242, 248, 258, 266, 280, 294, 300};
        int[] arrayY1 = {196, 192, 192, 190, 192, 196, 198};

        int[] arrayX2 = {236, 244, 252, 298};
        int[] arrayY2 = {208, 204, 202, 204};

        int[] arrayX3 = {250, 260, 284, 298};
        int[] arrayY3 = {218, 214, 212, 210};

        drawLines(g, arrayX1, arrayY1, Color.decode("#3D2219"));
        drawLines(g, arrayX2, arrayY2, Color.decode("#3D2219"));
        drawLines(g, arrayX3, arrayY3, Color.decode("#3D2219"));


        // Array para el Bigotes de la izquierda del gato:
        int[] arrayX4 = {360, 372, 388, 402, 410, 418};
        int[] arrayY4 = {204, 202, 200, 202, 204, 208};

        int[] arrayX5 = {362, 392, 404, 414, 422};
        int[] arrayY5 = {210, 208, 212, 214, 218};

        int[] arrayX6 = {364, 374, 388, 396, 404, 412, 414};
        int[] arrayY6 = {216, 216, 218, 220, 222, 226, 230};

        
        drawLines(g, arrayX4, arrayY4, Color.decode("#3D2219"));
        drawLines(g, arrayX5, arrayY5, Color.decode("#3D2219"));
        drawLines(g, arrayX6, arrayY6, Color.decode("#3D2219"));

        g.setColor(Color.decode("#BB585B")); 
        g.drawOval(182, 146, 36, 24);
        g.setColor(Color.decode("#CE938F"));
        g.fillOval(182, 146, 36, 24);

        g.setColor(Color.decode("#977643")); 
        g.drawOval(206, 356, 230, 230);
        g.setColor(Color.decode("#CB975D"));
        g.fillOval(206, 356, 230, 230);

        // Al final del método, muestra el buffer en el componente
        g.drawImage(buffer, 0, 0, this);
    }

    public void DrawLine(int width, int height) {
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(width, height));
    }

    public void drawLines(Graphics2D g, int[] xPoints, int[] yPoints, Color c) {
        if (xPoints.length != yPoints.length) {
            throw new IllegalArgumentException("Arrays de coordenadas de diferente longitud.");
        }

        for (int i = 0; i < xPoints.length - 1; i++) {
            g.setColor(c);
            g.drawLine(xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
        }
    }   

    public void drawClock(Graphics2D g, int centerX, int centerY, int radius, int hour, int minute, int second) {
        // Dibujar el círculo del reloj
        g.setColor(Color.WHITE);
        g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    
        // Dibujar las manecillas del reloj
        int secondX = (int) (centerX + 0.8 * radius * Math.sin(Math.toRadians(6 * second)));
        int secondY = (int) (centerY - 0.8 * radius * Math.cos(Math.toRadians(6 * second)));
    
        int minuteX = (int) (centerX + 0.7 * radius * Math.sin(Math.toRadians(6 * (minute + hour * 60))));
        int minuteY = (int) (centerY - 0.7 * radius * Math.cos(Math.toRadians(6 * (minute + hour * 60))));
    
        int hourX = (int) (centerX + 0.6 * radius * Math.sin(Math.toRadians(30 * hour + 0.5 * minute)));
        int hourY = (int) (centerY - 0.6 * radius * Math.cos(Math.toRadians(30 * hour + 0.5 * minute)));
    
        drawLineBresenham(centerX, centerY, hourX, hourY, Color.BLACK);
        drawLineBresenham(centerX, centerY, minuteX, minuteY, Color.BLACK);
        drawLineBresenham(centerX, centerY, secondX, secondY, Color.RED);
    
        // Dibujar el centro del reloj
        g.setColor(Color.BLACK);
        g.fillOval(centerX - 2, centerY - 2, 4, 4);
    
        // Dibujar números de las horas
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
    
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(30 * i);
            int numX = (int) (centerX + 0.8 * radius * Math.sin(angle)) - 6;
            int numY = (int) (centerY - 0.8 * radius * Math.cos(angle)) + 6;
    
            g.drawString(Integer.toString(i), numX, numY);
        }
    }

    public void update() {

        // Crea un temporizador para actualizar el reloj cada segundo
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Obtén la hora actual en la zona horaria predeterminada
                Calendar now = Calendar.getInstance(TimeZone.getDefault());

                // Ajusta la hora actual restando una hora
                now.add(Calendar.HOUR_OF_DAY, -1);

                // Actualiza las variables de hora, minuto y segundo
                currentHour = now.get(Calendar.HOUR_OF_DAY);
                currentMinute = now.get(Calendar.MINUTE);
                currentSecond = now.get(Calendar.SECOND);

                // Vuelve a pintar el panel para actualizar el reloj
                repaint();
            }
        }, 0, 1000); // Actualizar cada segundo (1000 milisegundos)
    }
}