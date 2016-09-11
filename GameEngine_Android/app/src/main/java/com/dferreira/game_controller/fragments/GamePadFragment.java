package com.dferreira.game_controller.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dferreira.game_controller.GamePad;
import com.dferreira.game_engine.R;

/**
 * Represents one game pad that can be used by the by the user to control
 * the scene
 */
public class GamePadFragment extends Fragment implements View.OnTouchListener {

    /**
     * Bind the views to the click listener method
     *
     * @param view the view where is to bind the ui elements
     */
    private void bindEvents(View view) {
        //Find the elements in the view
        ImageButton btnUp = (ImageButton) view.findViewById(R.id.btnUp);
        ImageButton btnDown = (ImageButton) view.findViewById(R.id.btnDown);
        ImageButton btnLeft = (ImageButton) view.findViewById(R.id.btnLeft);
        ImageButton btnRight = (ImageButton) view.findViewById(R.id.btnRight);
        ImageButton btnTriangle = (ImageButton) view.findViewById(R.id.btnTriangle);
        ImageButton btnX = (ImageButton) view.findViewById(R.id.btnX);
        ImageButton btnSquare = (ImageButton) view.findViewById(R.id.btnSquare);
        ImageButton btnCircle = (ImageButton) view.findViewById(R.id.btnCircle);

        //Make the bind itself
        btnUp.setOnTouchListener(this);
        btnDown.setOnTouchListener(this);
        btnLeft.setOnTouchListener(this);
        btnRight.setOnTouchListener(this);
        btnTriangle.setOnTouchListener(this);
        btnX.setOnTouchListener(this);
        btnSquare.setOnTouchListener(this);
        btnCircle.setOnTouchListener(this);
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.game_controller,
                container, false);

        bindEvents(view);

        return view;
    }


    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v           The view the touch event has been dispatched to.
     * @param motionEvent The MotionEvent object containing full information about
     *                    the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        boolean btnClicked = false;
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                btnClicked = true;
                break;
            case MotionEvent.ACTION_UP:
                btnClicked = false;
                break;
        }

        switch (v.getId()) {
            case R.id.btnUp:
                GamePad.setKey(GamePad.KEY_UP, btnClicked);
                break;
            case R.id.btnDown:
                GamePad.setKey(GamePad.KEY_DOWN, btnClicked);
                break;
            case R.id.btnLeft:
                GamePad.setKey(GamePad.KEY_LEFT, btnClicked);
                break;
            case R.id.btnRight:
                GamePad.setKey(GamePad.KEY_RIGHT, btnClicked);
                break;
            case R.id.btnTriangle:
                GamePad.setKey(GamePad.KEY_TRIANGLE, btnClicked);
                break;
            case R.id.btnSquare:
                GamePad.setKey(GamePad.KEY_SQUARE, btnClicked);
                break;
            case R.id.btnCircle:
                GamePad.setKey(GamePad.KEY_CIRCLE, btnClicked);
                break;
            case R.id.btnX:
                GamePad.setKey(GamePad.KEY_X, btnClicked);
                break;
        }

        return true;
    }
}
