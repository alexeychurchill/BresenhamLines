package alexeychurchill.github.io.bresenhamlines.graphics.matrix;

import java.util.Arrays;

public class Matrix {
    public double defaultValue = 0.0;
    public double[][] matrix;

    public Matrix(int rowsCount, int colsCount) {
        matrix = new double[rowsCount][colsCount];
    }

    public boolean set(int i, int j, double value) {
        if (matrix == null) {
            return false;
        }
        if (i >= matrix.length) {
            return false;
        }
        if (j >= matrix[i].length) {
            return false;
        }
        matrix[i][j] = value;
        return true;
    }

    public double get(int i, int j, double defaultValue) {
        if (matrix == null) {
            return defaultValue;
        }
        if (i >= matrix.length) {
            return defaultValue;
        }
        if (j >= matrix[i].length) {
            return defaultValue;
        }
        return matrix[i][j];
    }

    public double get(int i, int j) {
        return get(i, j, defaultValue);
    }

    public int getRowsCount() {
        if (matrix == null) {
            return 0;
        }
        return matrix.length;
    }

    public int getColsCount() {
        if (matrix == null) {
            return 0;
        }
        if (getRowsCount() == 0) {
            return 0;
        }
        return matrix[0].length;
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Matrix multiply(Matrix matrix) {
        if (matrix == null) {
            return null;
        }
        if (getColsCount() != matrix.getRowsCount()) {
            return null;
        }
        Matrix resultMatrix = new Matrix(getRowsCount(), matrix.getColsCount());
        for (int i = 0; i < resultMatrix.getRowsCount(); i++) {
            for (int j = 0; j < resultMatrix.getColsCount(); j++) {
                double cellValue = 0.0;
                for (int k = 0; k < getColsCount(); k++) {
                    cellValue += get(i, k) * matrix.get(k, j);
                }
                resultMatrix.set(i, j, cellValue);
            }
        }
        return resultMatrix;
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "matrix=" + Arrays.deepToString(matrix) +
                '}';
    }
}
