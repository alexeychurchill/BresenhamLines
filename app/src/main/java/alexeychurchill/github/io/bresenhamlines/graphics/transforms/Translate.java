package alexeychurchill.github.io.bresenhamlines.graphics.transforms;

public class Translate extends Transform {
    public Translate() {
        initMatrix();
    }

    public void setTranslationX(int x) {
        getTransformMatrix().set(0, 2, x);
    }

    public int getTranslationX() {
        return ((int) Math.round(getTransformMatrix().get(0, 2)));
    }

    public void setTranslationY(int y) {
        getTransformMatrix().set(1, 2, y);
    }

    public int getTranslationY() {
        return ((int) Math.round(getTransformMatrix().get(1, 2)));
    }

    private void initMatrix() {
        Matrix matrix = getTransformMatrix();
        matrix.set(0, 0, 1.0);
        matrix.set(1, 1, 1.0);
        matrix.set(2, 2, 1.0);
    }
}
