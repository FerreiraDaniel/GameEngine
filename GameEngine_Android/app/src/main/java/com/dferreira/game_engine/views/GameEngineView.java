package com.dferreira.game_engine.views;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

import com.dferreira.game_controller.GameEngineTouchListener;

/**
 * View with game rendered
 */
public class GameEngineView extends GLSurfaceView {

    private final GameEngineRenderer gameEngineRenderer;
    private final GameEngineTouchListener touchListener;

    /**
     * Standard View constructor. In order to render something, you
     * must call {@link #setRenderer} to register a renderer.
     *
     * @param context context where this view will be called
     * @param attrs   attributes that are passed to the view
     */
    public GameEngineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Tell the surface view we want to create an OpenGL ES 2.0-compatible
        // context, and set an OpenGL ES 2.0-compatible renderer.
        this.setEGLContextClientVersion(2);
        // Request an 565 Color buffer with 16-bit depth and 0-bit stencil
        this.setEGLConfigChooser(5, 6, 5, 0, 16, 0);

        this.setEGLContextClientVersion(2);
        //Set the render of the view
        gameEngineRenderer = new GameEngineRenderer(context);
        this.setRenderer(gameEngineRenderer);

        //Set the listener that are going to handle the on view touch
        touchListener = new GameEngineTouchListener();
        this.setOnTouchListener(touchListener);

        touchListener.setHeight(this.getHeight());
        touchListener.setWidth(this.getWidth());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        super.surfaceChanged(holder, format, w, h);
        if (touchListener != null) {
            touchListener.setHeight(this.getHeight());
            touchListener.setWidth(this.getWidth());
        }
    }

    /**
     *
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.gameEngineRenderer.cleanUp();
    }
}
