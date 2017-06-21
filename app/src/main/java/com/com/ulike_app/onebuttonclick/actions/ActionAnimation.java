package com.com.ulike_app.onebuttonclick.actions;

import android.content.Context;
import android.view.animation.RotateAnimation;
import android.widget.Button;

import com.com.ulike_app.onebuttonclick.MainActivity;
import com.com.ulike_app.onebuttonclick.R;

/**
 * Created by RDL on 21/06/2017.
 */

public class ActionAnimation implements Action {
    @Override
    public void actionRun(Context context) {
        Button button = (Button) ((MainActivity) context).findViewById(R.id.btn_action);
        final RotateAnimation rotateAnimation =
                new RotateAnimation(0, 360, button.getWidth() / 2, button.getHeight() / 2);
        rotateAnimation.setDuration(1000);
        button.startAnimation(rotateAnimation);
    }
}
