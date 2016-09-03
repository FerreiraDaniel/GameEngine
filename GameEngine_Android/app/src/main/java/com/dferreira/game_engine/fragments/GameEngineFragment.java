package com.dferreira.game_engine.fragments;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dferreira.game_engine.R;

/**
 * Fragment with game view inside
 */
public class GameEngineFragment extends Fragment {

    private GLSurfaceView mGLSurfaceView;

    /**
     *  Create of inflate  the Fragment's UI, and return it.
     *
     * @param inflater Responsible for inflating the view
     * @param container container where the fragment is going to be included
     *
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     *
     * @return view of the Europe continent inflated
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.game_engine, container, false);
    }

    /**
     *
     * @param savedInstanceState bundle with data that was saved in on save instance if any
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mGLSurfaceView = (GLSurfaceView) getActivity().findViewById(R.id.gl_surface);
    }

    /**
     * Ideally a game should implement onResume() and onPause()
     * to take appropriate action when the activity looses focus
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onResume();
        }
    }

    /**
     * Ideally a game should implement onResume() and onPause()
     * to take appropriate action when the activity looses focus
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mGLSurfaceView != null) {
            mGLSurfaceView.onPause();
        }
    }
}
