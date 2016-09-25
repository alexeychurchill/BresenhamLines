package alexeychurchill.github.io.bresenhamlines.graphics.primitives;

import java.util.List;

/**
 * Closed polyline class
 */

public class ClosedPolyline extends Polyline {
    @Override
    protected List<Point> getLinePoints() {
        List<Point> points = super.getLinePoints();
        if (points != null) {
            if (points.size() > 0) {
                points.add(points.get(0));
            }
        }
        return points;
    }
}
