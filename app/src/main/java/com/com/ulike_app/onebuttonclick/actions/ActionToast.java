package com.com.ulike_app.onebuttonclick.actions;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by RDL on 21/06/2017.
 */

public class ActionToast implements Action {
    public static final String TOAST_MSG = "Current Action is Toast";

    @Override
    public void actionRun(Context context) {
        Toast.makeText(context, TOAST_MSG, Toast.LENGTH_SHORT).show();
    }
}
