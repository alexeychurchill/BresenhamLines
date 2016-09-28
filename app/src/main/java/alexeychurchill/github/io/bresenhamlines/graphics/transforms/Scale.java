package alexeychurchill.github.io.bresenhamlines.graphics.transforms;

public class Scale extends Transform {
    public Scale() {
        getTransformMatrix().set(0, 0, 1.0); //INITIAL SCALING BY X
        getTransformMatrix().set(1, 1, 1.0); //INITIAL SCALING BY Y
        getTransformMatrix().set(2, 2, 1.0);
    }

    public void setScaleByX(double scaleX) {
        getTransformMatrix().set(0, 0, scaleX);
    }

    public double getScaleByX() {
        return getTransformMatrix().get(0, 0);
    }

    public void setScaleByY(double scaleByY) {
        getTransformMatrix().set(1, 1, scaleByY);
    }

    public double getScaleByY() {
        return getTransformMatrix().get(1, 1);
    }
}
