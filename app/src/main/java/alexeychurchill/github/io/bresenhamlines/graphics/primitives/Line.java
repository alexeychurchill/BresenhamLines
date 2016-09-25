package alexeychurchill.github.io.bresenhamlines.graphics.primitives;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Line class
 * Has Implemented Bresenham's algorithm
 * Method getLinePoints() must be implemented
 */

public abstract class Line extends Primitive {
    /**
     * getLinePoints() must be implemented
     * @return List of points of the line (polyline or 2-end line)
     */
    protected abstract List<Point> getLinePoints();

    @Override
    public List<Point> getRasterPoints() {
        List<Point> rasterPoints = new ArrayList<>();
        List<Point> linePoints = getLinePoints();
        if (linePoints != null) {
            for (int i = 0; i < linePoints.size() - 1; i++) {
                List<Point> bresenhamResult = getBresenham(linePoints.get(i), linePoints.get(i + 1));
                if (bresenhamResult != null) {
                    rasterPoints.addAll(bresenhamResult);
                }
            }
        }
        return rasterPoints;
    }

    private List<Point> getBresenham(Point begin, Point end) {
        List<Point> rasterPoints = new ArrayList<>();
        int xErr = 0, yErr = 0;
        int dx = end.getX() - begin.getX();
        int dy = end.getY() - begin.getY();
        int incX = sign(dx), incY = sign(dy);
        dx = Math.abs(dx);
        dy = Math.abs(dy);
        int d = Math.max(dx, dy);
        int x = begin.getX(), y = begin.getY();
        int color = getColor();
        Point point = new Point(x, y, color);
        rasterPoints.add(point);
        for (int ptNum = 0; ptNum < d; ptNum++) {
            xErr += dx;
            yErr += dy;
            if (xErr > d) {
                xErr -= d;
                x += incX;
            }
            if (yErr > d) {
                yErr -= d;
                y += incY;
            }
            rasterPoints.add(new Point(x, y, color));
        }
        return rasterPoints;
    }

    public static int sign(int n) {
        return (n == 0) ? 0 : ((n > 0) ? 1 : -1);
    }
}
