package alexeychurchill.github.io.bresenhamlines.graphics.primitives;

import java.util.ArrayList;
import java.util.List;

/**
 * Polyline class
 */

public class Polyline extends Line {
    private List<Point> points = new ArrayList<>();

    public List<Point> getPoints() {
        return points;
    }

    @Override
    protected List<Point> getLinePoints() {
        return new ArrayList<>(points);
    }
}
