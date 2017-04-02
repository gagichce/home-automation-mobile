package com.example.jack.hal.descriptors;

import com.example.jack.hal.pattern.PatternState;

/**
 * Created by Jack on 2017-04-02.
 */

public class PatternDescriptor {
    private int id;
    private int deviceId;
    private String description;
    private String patternRule;
    private PatternState status;

    public PatternDescriptor(int id, int deviceId, String description, String patternRule, PatternState status) {
        this.id = id;
        this.deviceId = deviceId;
        this.description = description;
        this.patternRule = patternRule;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatternRule() {
        return patternRule;
    }

    public void setPatternRule(String patternRule) {
        this.patternRule = patternRule;
    }

    public PatternState getStatus() {
        return status;
    }

    public void setStatus(PatternState status) {
        this.status = status;
    }
}
