package pl.edu.agh.parallel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba Fortunka on 14.11.2016.
 */
public class Matrix {

    private BigDecimal[][] matrix;
    private int rows;
    private int columns;

    private int index;

    public Matrix(List<List<BigDecimal>> matrix) {
        this.matrix = new BigDecimal[matrix.size()][];
        for (int i=0;i<matrix.size();i++) {
            this.matrix[i] = new BigDecimal[matrix.get(i).size()];
            for (int j=0;j<matrix.get(i).size();j++) {
                this.matrix[i][j] = matrix.get(i).get(j);
            }
        }
        this.rows = this.matrix.length;
        this.columns = this.matrix[0].length;
    }

    public Matrix multiply(Matrix m) throws Exception {
        if (this.columns() != m.rows()) {
            String msg = "Bad dimensions! :( \n";
            msg += "A.columns = " + this.columns() + "\n";
            msg += "B.rows = " + m.rows();
//            System.out.println(msg);
//            return null;
            throw new Exception(msg);
        } else {
            List<List<BigDecimal>> tmp = new ArrayList<>();
            for (int i=0;i<this.rows();i++) {
                tmp.add(new ArrayList<>());
                for (int j=0;j<m.columns();j++) {
                    BigDecimal sum = new BigDecimal(0);
                    for (int k=0;k<this.columns();k++) {
                        sum = sum.add(this.getValue(k,i).multiply(m.getValue(j,k)));
                    }
                    tmp.get(i).add(sum);
                }
            }
            Matrix ans = new Matrix(tmp);
            ans.setIndex(this.getIndex());
            return ans;
        }
    }

    public int columns() {
        return this.columns;
    }

    public int rows() {
        return this.rows;
    }

    public BigDecimal getValue(int col, int row) {
        return matrix[row][col];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<matrix.length;i++) {
            for (int j=0;j<matrix[i].length;j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    protected BigDecimal[][] getMatrix() {
        return matrix;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Matrix) {
            Matrix m = (Matrix)obj;
            BigDecimal[][] otherMatrix = m.getMatrix();
            for (int i=0;i<matrix.length;i++) {
                for (int j=0;j<matrix[i].length;j++) {
                    if (!otherMatrix[i][j].equals(matrix[i][j])) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
