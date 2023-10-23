import java.awt.Color;

public class Cubo extends Poligono {
    
    private int xCenter;
    private int yCenter;

    private int[][] xCube;
    private int[][] yCube;

    private Color[] colors;
    private int skin;

    public Cubo(int x, int y, int skin) {
        
        this.xCenter = x;
        this.yCenter = y;

        this.skin = skin;

        switch(this.skin) {

            case 1:
                this.colors = new Color[] {Color.BLACK, 
                                           Color.decode("#FFFF00"), 
                                           Color.BLACK, 
                                           Color.BLACK, 
                                           Color.decode("#00C8FF"), 
                                           Color.decode("#00C8FF"), 
                                           Color.BLACK, 
                                           Color.decode("#00C8FF")};

                this.xCube = new int[][] {{0, 100, 100, 0}, 
                                          {5, 95, 95, 5}, 
                                          {23, 43, 43, 23}, 
                                          {56, 76, 76, 56}, 
                                          {27, 39, 39, 27}, 
                                          {60, 72, 72, 60}, 
                                          {17, 83, 83, 17}, 
                                          {22, 78, 78, 22}};

                this.yCube = new int[][] {{0, 0, 100, 100}, 
                                          {5, 5, 95, 95}, 
                                          {19, 19, 39, 39}, 
                                          {19, 19, 39, 39}, 
                                          {23, 23, 35, 35}, 
                                          {23, 23, 35, 35}, 
                                          {48, 48, 67, 67}, 
                                          {52, 52, 62, 62}};
                break;

            default: 
                System.out.println("Erro: la skin seleccionada no existe");
                break;
        }
    }

    // Geters del objeto

    public int getX() {
        return this.xCenter;
    }

    public int getY() {
        return this.yCenter;
    }

    public int[][] getXCube() {
        return this.xCube;
    }

    public int[][] getYCube() {
        return this.yCube;
    }

    public Color[] getColors() {
        return this.colors;
    }

    public int getSkin() {
        return this.skin;
    }

    // Seters del obejto

    public void setX(int x) {
        this.xCenter = x;
    }

    public void setY(int y) {
        this.yCenter = y;
    }

    public void setColors(Color[] c) {
        this.colors = c;
    }

    public void setSkin(int s) {
        this.skin = s;
    }

    // Metodos del objeto
    public void drawCube() {

        switch(this.skin) {

            case 1:
                cube1();
                break;

            default: 
                System.out.println("Erro: la skin seleccionada no se puede dibujar porque no existe");
                break;
        }
    }

    public void cube1() {

        // Centramos nuestro Cubo en las coordenacas xCenter & yCenter
        for(int i = 0; i < xCube.length; i++) {
            for(int j = 0; j < xCube[i].length; j++) {
                this.xCube[i][j] += (xCenter - 50);
                this.yCube[i][j] += (yCenter - 50);
            }
        }

        for(int i = 0; i < xCube.length; i++) {
            int tempX[] = this.xCube[i];
            int tempY[] = this.yCube[i];

            fillPolygonScanLine(tempX, tempY, colors[i]);
        }
    }

    public void resetCube(int x, int y) {

        this.colors = new Color[] {Color.BLACK, 
                                           Color.decode("#FFFF00"), 
                                           Color.BLACK, 
                                           Color.BLACK, 
                                           Color.decode("#00C8FF"), 
                                           Color.decode("#00C8FF"), 
                                           Color.BLACK, 
                                           Color.decode("#00C8FF")};

        this.xCube = new int[][] {{0, 100, 100, 0}, 
                                    {5, 95, 95, 5}, 
                                    {23, 43, 43, 23}, 
                                    {56, 76, 76, 56}, 
                                    {27, 39, 39, 27}, 
                                    {60, 72, 72, 60}, 
                                    {17, 83, 83, 17}, 
                                    {22, 78, 78, 22}};

        this.yCube = new int[][] {{0, 0, 100, 100}, 
                                    {5, 5, 95, 95}, 
                                    {19, 19, 39, 39}, 
                                    {19, 19, 39, 39}, 
                                    {23, 23, 35, 35}, 
                                    {23, 23, 35, 35}, 
                                    {48, 48, 67, 67}, 
                                    {52, 52, 62, 62}};

        // Centramos nuestro Cubo en las coordenacas xCenter & yCenter
        for(int i = 0; i < xCube.length; i++) {
            for(int j = 0; j < xCube[i].length; j++) {
                this.xCube[i][j] += (x - 50);
                this.yCube[i][j] += (y - 50);
            }
        }

        for(int i = 0; i < xCube.length; i++) {
            int tempX[] = this.xCube[i];
            int tempY[] = this.yCube[i];

            fillPolygonScanLine(tempX, tempY, colors[i]);
        }
    }

    public void movingCubeWithMatrix(int[][] xCords, int[][] yCords, Color[] fillColors, double incrementX, double incrementY, int steps, boolean cls, boolean isCube) {
        // Copia el estado actual del cubo
        int[][] xCubeCopy = new int[xCords.length][xCords[0].length];
        int[][] yCubeCopy = new int[yCords.length][yCords[0].length];
        
        for (int i = 0; i < xCords.length; i++) {
            System.arraycopy(xCords[i], 0, xCubeCopy[i], 0, xCords[i].length);
            System.arraycopy(yCords[i], 0, yCubeCopy[i], 0, yCords[i].length);
        }
        
        for (int step = 0; step < steps; step++) {
            
            int[] xp = {xCube[0][0], xCube[0][1], xCube[0][2], xCube[0][3]};
            int[] yp = {yCube[0][0], yCube[0][1], yCube[0][2], yCube[0][3]};

            if(cls) {
                clearScreen(xp[0], yp[0], xp[2], yp[2]);
            }

            for (int i = 0; i < xCords.length; i++) {
                double[] auxX = new double[xCords[i].length];
                double[] auxY = new double[yCords[i].length];
        
                for (int j = 0; j < xCords[i].length; j++) {
                    // Aplicar la matriz de traslación para este paso
                    auxX[j] = xCubeCopy[i][j] + (incrementX * (step + 1));
                    auxY[j] = yCubeCopy[i][j] + (incrementY * (step + 1));
                }
        
                // Ahora, convierte los valores de double a int
                int[] intAuxX = new int[auxX.length];
                int[] intAuxY = new int[auxY.length];
        
                for (int j = 0; j < auxX.length; j++) {
                    intAuxX[j] = (int) Math.round(auxX[j]);
                    intAuxY[j] = (int) Math.round(auxY[j]);
                }
        
		        if(i == 0) {
                    // Actualiza las variables xCenter e yCenter
                    this.xCenter = intAuxX[0] + 50;
                    this.yCenter = intAuxY[0] + 50;
                }
    
                // Llama a fillPolygonScanLine con los arreglos de tipo int[]
                fillPolygonScanLine(intAuxX, intAuxY, fillColors[i]);

                // Actualiza las variables xCube y yCube
                xCube[i] = intAuxX;
                yCube[i] = intAuxY;
            }
        
            try {
                Thread.sleep(32);
            } catch (Exception exception) {
                // Manejo de excepciones
            }
            
            repaint();  // Llama a repaint() al final de cada fotograma
        }
    }      

    public void rotatingPoligonWithMatrix(int[][] xCords, int[][] yCords, Color[] fillColors, int[] e, double angle, double giros) {
    
        for (double a = 1; a <= (angle * giros); a+=5) {
            
            clearScreen(xCords[0][0] - 20, yCords[0][0] - 20, xCords[0][2] + 20, yCords[0][2] + 20);

            for (int i = 0; i < xCords.length; i++) {

                double[] auxX = new double[xCords[i].length];
                double[] auxY = new double[xCords[i].length];

                for (int j = 0; j < xCords[i].length; j++) {
                    double tempX = xCords[i][j] - e[0];
                    double tempY = yCords[i][j] - e[1];
                    
                    // Aplicar transformación de rotación
                    double cosA = Math.cos(Math.toRadians(a));
                    double sinA = Math.sin(Math.toRadians(a));
                    
                    auxX[j] = tempX * cosA - tempY * sinA + e[0];
                    auxY[j] = tempX * sinA + tempY * cosA + e[1];
                }

                // Ahora, convierte los valores de double a int
                int[] intAuxX = new int[auxX.length];
                int[] intAuxY = new int[auxY.length];

                for (int j = 0; j < auxX.length; j++) {
                    intAuxX[j] = (int) Math.round(auxX[j]);
                    intAuxY[j] = (int) Math.round(auxY[j]);
                }

                int[] xp = {xCords[0][0] - 20, xCords[0][2] + 20};
                int[] yp = {yCords[0][0] - 20, yCords[0][2] + 20};

                // Llama a fillPolygonScanLine con los arreglos de tipo int[]
                fillPolygonScanLine(intAuxX, intAuxY, fillColors[i]);
            }
    
            try {
                Thread.sleep(32);
            } catch (Exception exception) {
                // Manejo de excepciones
            }

            repaint();
        }
    }

    public void scalingWithMatrix(int[][] xCords, int[][] yCords, Color[] c, double scale, int numSteps) {
        double stepSize = (scale - 1.0) / numSteps;
    
        for (int step = 0; step <= numSteps; step++) {
            int[][] scaledX = new int[xCords.length][];
            int[][] scaledY = new int[yCords.length][];
    
            for (int i = 0; i < xCords.length; i++) {
                int[] xPoints = xCords[i];
                int[] yPoints = yCords[i];
    
                double centerX = 0.0;
                double centerY = 0.0;
    
                for (int j = 0; j < xPoints.length; j++) {
                    centerX += xPoints[j];
                    centerY += yPoints[j];
                }
    
                centerX /= xPoints.length;
                centerY /= yPoints.length;
    
                scaledX[i] = new int[xPoints.length];
                scaledY[i] = new int[yPoints.length];
    
                for (int j = 0; j < xPoints.length; j++) {
                    double deltaX = xPoints[j] - centerX;
                    double deltaY = yPoints[j] - centerY;
    
                    scaledX[i][j] = (int) (centerX + deltaX * (1 + stepSize * step));
                    scaledY[i][j] = (int) (centerY + deltaY * (1 + stepSize * step));
                }
            }
    
            // Guarda las coordenadas de la última iteración en las variables globales
            if (step == numSteps) {
                xCube = scaledX;
                yCube = scaledY;
            }
    
            clearScreen(xCords[0][0] - 5, yCords[0][0] - 5, xCords[0][2] + 5, yCords[0][2] + 5);

            for (int i = 0; i < scaledX.length; i++) {
                fillPolygonScanLine(scaledX[i], scaledY[i], c[i]);
            }

            repaint();
    
            try {
                Thread.sleep(32); // Pausa para ver la animación
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }    
}
