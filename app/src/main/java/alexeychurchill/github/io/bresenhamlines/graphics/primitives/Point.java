package alexeychurchill.github.io.bresenhamlines.graphics.primitives;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Raster point class
 * Has two integer parameters - x and y coordinates
 */

public class Point extends Primitive {
    private int x;
    private int y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int color) {
        this.x = x;
        this.y = y;
        setColor(color);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public List<Point> getRasterPoints() {
        return new ArrayList<>(Collections.singletonList(this));
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
