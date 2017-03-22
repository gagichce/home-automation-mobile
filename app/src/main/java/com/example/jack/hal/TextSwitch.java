package com.example.jack.hal;

import android.widget.Switch;
import android.widget.TextView;

import com.example.jack.hal.descriptors.Status;

/**
 * Created by Jack on 2017-02-05.
 */

public class TextSwitch {

    private Status status;

    public Status getStatus() {
        return status;
    }

    private String label;
    private int aSwitchId;
    private Boolean isChecked;

    public TextSwitch() {
        super();
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TextSwitch(String label, int aSwitchId, Boolean isChecked, Status status) {
        super();
        this.label = label;
        this.aSwitchId = aSwitchId;
        this.isChecked = isChecked;
        this.status = status;


    }


    public String getLabel() {
        return label;
    }

    public int getaSwitchId() {

        return aSwitchId;
    }

    public Boolean getisChecked() {
        return isChecked;
    }
}
