package alexeychurchill.github.io.bresenhamlines.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import alexeychurchill.github.io.bresenhamlines.graphics.primitives.Drawing;

/**
 * Render view
 * Renders drawings
 */

public class RenderView extends SurfaceView implements SurfaceHolder.Callback {
    private RenderThread renderThread;
    private int bgColor = Color.WHITE;
    private boolean renderThreadPaused = false;
    private List<Drawing> drawings = new ArrayList<>();

    public RenderView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public RenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public RenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    public List<Drawing> getDrawings() {
        return renderThread == null ? drawings : renderThread.getDrawings();
    }

    public void setRenderThreadPaused(boolean paused) {
        if (renderThread != null) {
            renderThread.setPaused(paused);
        }
        renderThreadPaused = paused;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        renderThread = new RenderThread(holder);
        renderThread.setRunning(true);
        renderThread.setDrawings(drawings);
        renderThread.setBgColor(bgColor);
        renderThread.setPaused(renderThreadPaused);
        renderThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (renderThread != null) {
            boolean retryJoin = true;
            renderThread.setRunning(false);
            while (retryJoin) {
                try {
                    renderThread.join();
                    retryJoin = false;
                } catch (InterruptedException e) {
                }
            }
            drawings = renderThread.getDrawings();
            renderThread = null;
        }
    }
}
