import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConvertTxtToCords {
    
    public int[] convertTxtToXPoints(String filePath) {
        List<Integer> xPointsList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int x = Integer.parseInt(parts[0].trim());
                    xPointsList.add(x);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] xPoints = new int[xPointsList.size()];
        for (int i = 0; i < xPointsList.size(); i++) {
            xPoints[i] = xPointsList.get(i);
        }

        return xPoints;
    }

    public int[] convertTxtToYPoints(String filePath) {
        List<Integer> yPointsList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int y = Integer.parseInt(parts[1].trim());
                    yPointsList.add(y);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] yPoints = new int[yPointsList.size()];
        for (int i = 0; i < yPointsList.size(); i++) {
            yPoints[i] = yPointsList.get(i);
        }

        return yPoints;
    }

    public int countVertices(String filePath) {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count;
    }

    public void printCoordinates(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
