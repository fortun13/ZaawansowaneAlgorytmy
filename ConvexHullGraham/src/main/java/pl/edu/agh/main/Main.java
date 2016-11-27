package pl.edu.agh.main;

import pl.edu.agh.algorithm.GrahamAlgorithm;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Jakub Fortunka on 27.11.2016.
 */
public class Main {

    private static final File POINTS_FILE = new File("punktyPrzykladowe.csv");

    public static void main(String[] args) {
        try {
            List<Point2D> points = readPointsFile(POINTS_FILE);
            GrahamAlgorithm ga = new GrahamAlgorithm();
            Stack<Point2D> hull = ga.getConvexHull(points);
            StringBuilder sb = new StringBuilder();
            sb.append("Convex hull: ").append("\n");
            for (Point2D p : hull) {
                sb.append(p.toString()).append("\n");
            }
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //print
    }

    private static List<Point2D> readPointsFile(File pointsFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(POINTS_FILE));
        String line;
        List<Point2D> ans = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty()) {
                String[] tmp = line.split(",");
                ans.add(new Point2D.Double(Double.valueOf(tmp[0].trim()), Double.valueOf(tmp[1].trim())));
            }
        }
        return ans;
    }

}
