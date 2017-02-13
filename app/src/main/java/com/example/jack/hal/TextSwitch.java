package com.example.jack.hal;

import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Jack on 2017-02-05.
 */

public class TextSwitch {


    private String label;
    private int aSwitchId;
    private Boolean isChecked;

    public TextSwitch() {
        super();
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public TextSwitch(String label, int aSwitchId, Boolean isChecked) {
        super();
        this.label = label;
        this.aSwitchId = aSwitchId;
        this.isChecked = isChecked;

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
