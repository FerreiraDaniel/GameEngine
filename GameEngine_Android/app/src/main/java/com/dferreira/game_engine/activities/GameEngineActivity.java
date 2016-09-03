package com.dferreira.game_engine.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dferreira.commons.utils.LoadUtils;
import com.dferreira.game_engine.R;

/**
 * Show An activity with the game
 */
public class GameEngineActivity extends FragmentActivity {
    /**
     * Tell the surface view we want to create an OpenGL ES 2.0-compatible
     * context, and set an OpenGL ES 2.0-compatible renderer.
     *
     * @param savedInstanceState bundle with data that was saved in a previous cycle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LoadUtils.detectOpenGLES20(this)) {
            this.setContentView(R.layout.game_engine_activity);
        }
    }
}
