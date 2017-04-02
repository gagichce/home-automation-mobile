package com.example.jack.hal.pattern;

import android.widget.Switch;
import android.widget.TextView;

import com.example.jack.hal.descriptors.Status;

/**
 * Created by Jack on 2017-02-05.
 */

public class TextSwitch {

    private PatternState status;


    private String label;
    private int aSwitchId;

    public TextSwitch(String label, int aSwitchId, PatternState status) {
        this.aSwitchId = aSwitchId;
        this.label = label;
        this.status = status;
    }

    public int getaSwitchId() {
        return aSwitchId;
    }

    public void setaSwitchId(int aSwitchId) {
        this.aSwitchId = aSwitchId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PatternState getStatus() {
        return status;
    }

    public void setStatus(PatternState status) {
        this.status = status;
    }
}
