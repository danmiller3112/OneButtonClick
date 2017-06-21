package com.com.ulike_app.onebuttonclick.entity;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by RDL on 20/06/2017.
 */

public class ActionEntity implements Comparable<ActionEntity> {

    private String type;
    private boolean enabled;
    private int priority;
    private int[] valid_days;
    private long cool_down;
    private long startTime;

    public ActionEntity() {
    }

    public ActionEntity(String type, boolean enabled, int priority, int[] valid_days, long cool_down) {
        this.type = type;
        this.enabled = enabled;
        this.priority = priority;
        this.valid_days = valid_days;
        this.cool_down = cool_down;
        this.startTime = 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int[] getValid_days() {
        return valid_days;
    }

    public void setValid_days(int[] valid_days) {
        this.valid_days = valid_days;
    }

    public long getCool_down() {
        return cool_down;
    }

    public void setCool_down(long cool_down) {
        this.cool_down = cool_down;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "ActionEntity{" +
                "type='" + type + '\'' +
                ", enabled=" + enabled +
                ", priority=" + priority +
                ", valid_days=" + Arrays.toString(valid_days) +
                ", cool_down=" + cool_down +
                ", startTime=" + startTime +
                '}';
    }

    @Override
    public int compareTo(@NonNull ActionEntity o) {
        return this.getPriority() < o.getPriority() ? 1 : (this.getPriority() == o.getPriority()) ? 0 : -1;
    }

    public boolean isValidDay() {
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        for (Integer day : valid_days) {
            if (day == currentDay)
                return true;
        }
        return false;
    }

    public boolean isCoolDown() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis() < (startTime + cool_down);
    }
}
