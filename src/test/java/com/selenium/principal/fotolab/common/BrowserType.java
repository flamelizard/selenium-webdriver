package com.selenium.principal.fotolab.common;

public enum BrowserType {
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge");

    private String type;

    BrowserType(String type) {
        this.type = type;
    }

    @Override
    public java.lang.String toString() {
        return "[" + type + "]";
    }
}
