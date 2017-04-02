package com.example.jack.hal.pattern;

/**
 * Created by Jack on 2017-04-02.
 */

public enum PatternState {
    ACTIVE, INACTIVE;


    public static PatternState int2State(int i) {
        switch (i) {
            case 0:
                return INACTIVE;
            case 1:
                return ACTIVE;
        }

        return null;
    }
}
