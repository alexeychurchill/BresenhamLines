package alexeychurchill.github.io.bresenhamlines.graphics.primitives;

import alexeychurchill.github.io.bresenhamlines.graphics.Raster;

/**
 * Primitive class
 * Has color field
 */

public abstract class Primitive implements Raster {
    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
