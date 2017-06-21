package com.com.ulike_app.onebuttonclick.actions;

/**
 * Created by RDL on 21/06/2017.
 */

public class ActionFactory {
    public static final String ANIMATION = "animation";
    public static final String TOAST = "toast";
    public static final String CALL = "call";
    public static final String NOTIFICATION = "notification";

    public Action getAction(String type) {
        Action action = null;
        switch (type) {
            case ANIMATION:
                action = new ActionAnimation();
                break;
            case TOAST:
                action = new ActionToast();
                break;
            case CALL:
                action = new ActionCall();
                break;
            case NOTIFICATION:
                action = new ActionNotification();
                break;
        }
        return action;
    }
}
