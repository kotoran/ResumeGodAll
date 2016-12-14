package com.ajemian.resumegod;

/**
 * Created by Kudo on 10/8/16.
 */

public class Entry {
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private String label;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;


    public Entry(String label){
        this.label = label;
    }
    public Entry(String label, String text){
        this.label = label;
        this.text = text;
    }


}
