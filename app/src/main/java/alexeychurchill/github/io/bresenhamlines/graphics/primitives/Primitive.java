package alexeychurchill.github.io.bresenhamlines.graphics.primitives;

import java.security.PublicKey;
import java.util.LinkedList;
import java.util.List;

import alexeychurchill.github.io.bresenhamlines.graphics.Raster;
import alexeychurchill.github.io.bresenhamlines.graphics.transforms.Transform;

/**
 * Primitive class
 * Has color field
 */

public abstract class Primitive implements Raster {
    private List<Transform> transforms = new LinkedList<>();

    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<Transform> getTransforms() {
        return transforms;
    }

    public Point transformPoint(Point point) {
        Point newPoint = point;
        for (Transform transform : transforms) {
            newPoint = transform.transform(point);
        }
        return newPoint;
    }
}
