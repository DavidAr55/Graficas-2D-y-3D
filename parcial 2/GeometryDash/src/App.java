import java.awt.Color;

public class App {
    public static void main(String[] args) throws Exception {

        Cubo cubito = new Cubo(100, 550, 1);
        
        cubito.drawCube();
        cubito.drawBackground();

        // Crea un nuevo hilo para reproducir el audio
        Thread audioThread = new Thread(() -> {
            cubito.playWav();
        });

        // Inicia el hilo de audio
        audioThread.start();

        int[][] xCords = cubito.getXCube();
        int[][] yCords = cubito.getYCube();

        cubito.movingCubeWithMatrix(xCords, yCords, cubito.getColors(), 5, 0, 130, true, true);

        
        int[] centerCube = {cubito.getX(), cubito.getY()};
        xCords = cubito.getXCube();
        yCords = cubito.getYCube();

        cubito.rotatingPoligonWithMatrix(xCords, yCords, cubito.getColors(), centerCube, 360, 1);


        centerCube = new int[] {cubito.getX(), cubito.getY()};
        xCords = cubito.getXCube();
        yCords = cubito.getYCube();

        cubito.scalingWithMatrix(xCords, yCords, cubito.getColors(), 0.75, 20);

        centerCube = new int[] {cubito.getX(), cubito.getY()};
        xCords = cubito.getXCube();
        yCords = cubito.getYCube();

        cubito.scalingWithMatrix(xCords, yCords, cubito.getColors(), 1.25, 20);

        try {
            cubito.setText(200, 300, "Bienvenidos a mi animacion :D", Color.WHITE);
            Thread.sleep(1500);
        } catch (Exception e) {
            // TODO: handle exception
        }

        centerCube = new int[] {cubito.getX(), cubito.getY()};
        xCords = cubito.getXCube();
        yCords = cubito.getYCube();

        try {
            cubito.setText(200, 300, "Una animacion hecha por David Loera", Color.WHITE);
            Thread.sleep(500);
        } catch (Exception e) {
            // TODO: handle exception
        }

        int[][] xWall = {{1500, 2000, 2000, 1500}};
        int[][] yWall = {{0, 0, 900, 900}};

        Color[] cWall = {Color.BLACK};

        cubito.movingCubeWithMatrix(xWall, yWall, cWall, -40, 0, 100, false, false);
        
        cubito.clearAllScreen();

        int[][] xSpike = {
            {700, 750, 800},
            {710, 750, 790}
        };

        int[][] ySpike = {
            {800, 690, 800},
            {795, 700, 795}
        };

        cubito.fillPolygonScanLine(xSpike[0], ySpike[0], Color.WHITE);
        cubito.fillPolygonScanLine(xSpike[1], ySpike[1], Color.BLACK);

        cubito.resetCube(1400, 750);


        centerCube = new int[] {cubito.getX(), cubito.getY()};
        xCords = cubito.getXCube();
        yCords = cubito.getYCube();

        cubito.movingCubeWithMatrix(xCords, yCords, cubito.getColors(), -5, 0, 80, true, true);

        try {
            cubito.setText(200, 300, "Ohh! Que es eso?", Color.WHITE);
            Thread.sleep(1500);
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            cubito.setText(200, 300, "Eso es un Spike, tienes que saltarlo", Color.decode("#87EE34"));
            Thread.sleep(1500);
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            cubito.setText(200, 300, "Pero cuidado, puedes morir si lo tocas! D:", Color.decode("#87EE34"));
            Thread.sleep(1500);
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            cubito.setText(200, 300, "Okey, intentare saltarlo", Color.WHITE);
            Thread.sleep(1500);
        } catch (Exception e) {
            // TODO: handle exception
        }

        cubito.clearScreen(0, 0, cubito.getWidth(), 400);
        cubito.drawBackground();

        cubito.movingCubeWithMatrix(xCords, yCords, cubito.getColors(), -5, -5, 50, true, true);


        centerCube = new int[] {cubito.getX(), cubito.getY()};
        xCords = cubito.getXCube();
        yCords = cubito.getYCube();

        cubito.rotatingPoligonWithMatrix(xCords, yCords, cubito.getColors(), centerCube, 360, 1);
        cubito.clearAllScreen();
        cubito.resetCube(750, 500);;
        cubito.fillPolygonScanLine(xSpike[0], ySpike[0], Color.WHITE);
        cubito.fillPolygonScanLine(xSpike[1], ySpike[1], Color.BLACK);
        

        centerCube = new int[] {cubito.getX(), cubito.getY()};
        xCords = cubito.getXCube();
        yCords = cubito.getYCube();

        cubito.movingCubeWithMatrix(xCords, yCords, cubito.getColors(), -5, 5, 50, true, true);


        try {
            cubito.setText(200, 300, "Hey, Bien hecho!", Color.decode("#87EE34"));
            Thread.sleep(1500);
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            cubito.setText(200, 300, "Gracias, eso fue facil", Color.WHITE);
            Thread.sleep(1500);
        } catch (Exception e) {
            // TODO: handle exception
        }

        cubito.clearScreen(0, 0, cubito.getWidth(), 400);
        cubito.drawBackground();

        centerCube = new int[] {cubito.getX(), cubito.getY()};
        xCords = cubito.getXCube();
        yCords = cubito.getYCube();

        cubito.movingCubeWithMatrix(xCords, yCords, cubito.getColors(), -5, 0, 110, true, true);


        xWall = new int[][] {{-1500, 0, 0, -1500}};
        yWall = new int[][] {{0, 0, 900, 900}};

        cubito.movingCubeWithMatrix(xWall, yWall, cWall, 40, 0, 90, false, false);

        cubito.clearAllScreen();

        int[][] xSpikes = {
            {700, 750, 800},
            {710, 750, 790},
            {600, 650, 700},
            {610, 650, 690},
            {800, 850, 900},
            {810, 850, 890}
        };

        int[][] ySpikes = {
            {800, 690, 800},
            {795, 700, 795},
            {800, 690, 800},
            {795, 700, 795},
            {800, 690, 800},
            {795, 700, 795}
        };

        cubito.fillPolygonScanLine(xSpikes[0], ySpikes[0], Color.WHITE);
        cubito.fillPolygonScanLine(xSpikes[1], ySpikes[1], Color.BLACK);

        cubito.fillPolygonScanLine(xSpikes[2], ySpikes[2], Color.WHITE);
        cubito.fillPolygonScanLine(xSpikes[3], ySpikes[3], Color.BLACK);

        cubito.fillPolygonScanLine(xSpikes[4], ySpikes[4], Color.WHITE);
        cubito.fillPolygonScanLine(xSpikes[5], ySpikes[5], Color.BLACK);

        cubito.resetCube(100, 750);


        try {
            cubito.setText(200, 300, "Que te parece? subi un poco la dificultad", Color.decode("#87EE34"));
            Thread.sleep(1500);
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            cubito.setText(200, 300, "ya veo...", Color.WHITE);
            Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            cubito.setText(200, 300, "Bueno, ahi vamos!", Color.WHITE);
            Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}