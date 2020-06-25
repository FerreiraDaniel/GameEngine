package com.dferreira.gameEngine.views

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.SurfaceHolder
import com.dferreira.gameController.GameEngineTouchListener

/**
 * View with game rendered
 */
class GameEngineView(context: Context?, attrs: AttributeSet?) : GLSurfaceView(context, attrs) {
    private val gameEngineRenderer: GameEngineRenderer
    private val touchListener: GameEngineTouchListener?
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        super.surfaceChanged(holder, format, w, h)
        if (touchListener != null) {
            touchListener.setHeight(this.height)
            touchListener.setWidth(this.width)
        }
    }

    /**
     *
     */
    @Throws(Throwable::class)
    override fun finalize() {
        super.finalize()
        gameEngineRenderer.cleanUp()
    }

    /**
     * Standard View constructor. In order to render something, you
     * must call [.setRenderer] to register a renderer.
     *
     * @param context context where this view will be called
     * @param attrs   attributes that are passed to the view
     */
    init {

        // Tell the surface view we want to create an OpenGL ES 2.0-compatible
        // context, and set an OpenGL ES 2.0-compatible renderer.
        setEGLContextClientVersion(2)
        // Request an 565 Color buffer with 16-bit depth and 0-bit stencil
        this.setEGLConfigChooser(5, 6, 5, 0, 16, 0)
        setEGLContextClientVersion(2)
        //Set the render of the view
        gameEngineRenderer = GameEngineRenderer(context!!)
        setRenderer(gameEngineRenderer)

        //Set the listener that are going to handle the on view touch
        touchListener = GameEngineTouchListener()
        setOnTouchListener(touchListener)
        touchListener.setHeight(this.height)
        touchListener.setWidth(this.width)
    }
}