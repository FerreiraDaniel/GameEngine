package com.dferreira.gameEngine.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dferreira.gameEngine.R;

/**
 * Show An activity with the game
 */
public class GameEngineActivity extends FragmentActivity {
    /**
     * Returns if openGL 2.0 exists in device or not
     *
     * @return false -> There are no GLes v2.0 available into device
     * true  -> Exist GLes v2.0 into device
     */
    private boolean detectOpenGLES20() {
        ActivityManager am =
                (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x20000);
    }

    /**
     * Tell the surface view we want to create an OpenGL ES 2.0-compatible
     * context, and set an OpenGL ES 2.0-compatible renderer.
     *
     * @param savedInstanceState bundle with data that was saved in a previous cycle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (detectOpenGLES20()) {
            this.setContentView(R.layout.game_engine_activity);
        }
    }
}
