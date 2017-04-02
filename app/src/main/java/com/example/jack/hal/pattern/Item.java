package com.example.jack.hal.pattern;

/**
 * Created by Jack on 2017-04-02.
 */

public class Item {
    private TextSwitch header;
    private String description;
    private int imageButtonId;

    public TextSwitch getHeader() {
        return header;
    }

    public void setHeader(TextSwitch header) {
        this.header = header;
    }

    public Item(TextSwitch header, String description,  int imageButtonId) {
        this.description = description;
        this.header = header;

        this.imageButtonId = imageButtonId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageButtonId() {
        return imageButtonId;
    }

    public void setImageButtonId(int imageButtonId) {
        this.imageButtonId = imageButtonId;
    }
}
