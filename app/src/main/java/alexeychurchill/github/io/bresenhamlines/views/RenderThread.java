package alexeychurchill.github.io.bresenhamlines.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.List;

import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Drawing;
import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Point;

/**
 * Renderer thread
 * Renders drawings
 */

public class RenderThread extends Thread {
    private boolean running = true;
    private boolean paused = false;
    private SurfaceHolder surfaceHolder;
    private int bgColor = Color.WHITE;
    private Paint mPaint = new Paint();
    private List<Drawing> drawings;

    public RenderThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        mPaint.setStrokeWidth(1.0F);
    }

    @Override
    public void run() {
        Canvas canvas = null;
        while (running) {
            //Rendering
            synchronized (this) {
                if (paused) {
                    continue;
                }
                if (surfaceHolder == null) {
                    continue;
                }
                try {
                    canvas = surfaceHolder.lockCanvas();
                    if (canvas != null) {
                        canvas.drawColor(bgColor);
                        if (drawings != null) {
                            for (Drawing drawing : drawings) {
                                drawDrawing(canvas, drawing);
                            }
                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
            //Rendering
        }
    }

    private void drawDrawing(Canvas canvas, Drawing drawing) {
        if (canvas == null || drawing == null) {
            return;
        }
        List<Point> points = drawing.getRasterPoints();
        for (Point point : points) {
            if (point.getX() < 0 || point.getX() >= canvas.getWidth()) {
                continue;
            }
            if (point.getY() < 0 || point.getY() >= canvas.getHeight()) {
                continue;
            }
            mPaint.setColor(point.getColor());
            canvas.drawPoint(point.getX(), point.getY(), mPaint);
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public synchronized void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public List<Drawing> getDrawings() {
        return drawings;
    }

    public synchronized void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setDrawings(List<Drawing> drawings) {
        this.drawings = drawings;
    }
}
