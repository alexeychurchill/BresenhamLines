package alexeychurchill.github.io.bresenhamlines.graphics.primitives;

import java.util.ArrayList;
import java.util.List;

import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Point;
import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Primitive;

/**
 * Container for primitives
 */

public class Drawing extends Primitive {
    private List<Primitive> primitives = new ArrayList<>();

    public List<Primitive> getPrimitives() {
        return primitives;
    }

    @Override
    public List<Point> getRasterPoints() {
        if (primitives == null) {
            return null;
        }
        List<Point> rasterPoints = new ArrayList<>();
        for (Primitive primitive : primitives) {
            primitive.getTransforms().clear();
            primitive.getTransforms().addAll(getTransforms());
            primitive.setColor(getColor());
            rasterPoints.addAll(primitive.getRasterPoints());
        }
        return rasterPoints;
    }
}
