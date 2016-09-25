package alexeychurchill.github.io.bresenhamlines.graphics.primitives;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 2-end line class
 */

public class SingleLine extends Line {
    private Point begin;
    private Point end;

    public SingleLine() {
        this(new Point(), new Point());
    }

    public SingleLine(Point begin, Point end) {
        this.begin = begin;
        this.end = end;
    }

    public Point getBegin() {
        return begin;
    }

    public void setBegin(Point begin) {
        this.begin = begin;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    @Override
    protected List<Point> getLinePoints() {
        return new ArrayList<>(Arrays.asList(begin, end));
    }
}
