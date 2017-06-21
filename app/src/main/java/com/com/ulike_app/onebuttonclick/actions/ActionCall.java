package com.com.ulike_app.onebuttonclick.actions;

import android.content.Context;

import com.com.ulike_app.onebuttonclick.MainActivity;

/**
 * Created by RDL on 21/06/2017.
 */

public class ActionCall implements Action {
    @Override
    public void actionRun(Context context) {
        ((MainActivity)context).runCall();
    }
}
