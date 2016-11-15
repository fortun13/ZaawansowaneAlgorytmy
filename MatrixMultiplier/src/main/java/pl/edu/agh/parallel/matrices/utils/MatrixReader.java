package pl.edu.agh.parallel.matrices.utils;

import pl.edu.agh.parallel.matrices.model.Matrix;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba Fortunka on 14.11.2016.
 */
public class MatrixReader {

    public static List<Matrix> readMatrices(File f, int numberOfMatrices) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        List<Matrix> ans = new ArrayList<>();
        List<List<BigDecimal>> matrixValues = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (line.equals("")) {
                ans.add(new Matrix(matrixValues));
                if (ans.size() == numberOfMatrices) {
                    break;
                }
                matrixValues.clear();
            } else {
                matrixValues.add(parseLine(line));
            }
        }
        br.close();
        return ans;
    }

    private static List<BigDecimal> parseLine(String line) {
        String[] tmp = line.split(";");
        List<BigDecimal> ans = new ArrayList<>();
        for (String number : tmp) {
            ans.add(BigDecimal.valueOf(Double.valueOf(number.trim())));
        }
        return ans;
    }
}
