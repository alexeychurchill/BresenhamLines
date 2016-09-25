package alexeychurchill.github.io.bresenhamlines.graphics;

import java.util.List;

import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Point;

/**
 * Raster interface
 * Provides interface for rasterization of the primitives
 */

public interface Raster {
    List<Point> getRasterPoints();
}
