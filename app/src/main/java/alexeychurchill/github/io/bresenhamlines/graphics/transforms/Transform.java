package alexeychurchill.github.io.bresenhamlines.graphics.transforms;

import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Point;

public class Transform {
    private Matrix transformMatrix = new Matrix(3, 3);

    public Point transform(Point point) {
        Matrix sourceXyMatrix = new Matrix(3, 1);
        sourceXyMatrix.set(0, 0, point.getX());
        sourceXyMatrix.set(1, 0, point.getY());
        sourceXyMatrix.set(2, 0, 1.0);
        Matrix resultXyMatrix = transformMatrix.multiply(sourceXyMatrix);
        if (resultXyMatrix == null) {
            return null;
        }
        return new Point(
                ((int) Math.round(resultXyMatrix.get(0, 0))),
                ((int) Math.round(resultXyMatrix.get(1, 0)))
        );
    }

    protected Matrix getTransformMatrix() {
        return transformMatrix;
    }
}
