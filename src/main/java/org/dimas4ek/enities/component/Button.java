package org.dimas4ek.enities.component;

import org.dimas4ek.enities.types.ButtonStyle;

public class Button {
    private static ButtonStyle style;
    private static String label;
    private static String url;
    private static String id;
    private static boolean disabled;
    
    public Button() {
    }
    
    public static Button of(ButtonStyle style, String label, String id) {
        Button.style = style;
        Button.label = label;
        Button.id = id;
        return new Button();
    }
    
    public static Button of(ButtonStyle style, String label) {
        Button.style = style;
        Button.label = label;
        return new Button();
    }
    
    public Button setDisabled(boolean disabled) {
        Button.disabled = disabled;
        return this;
    }
    
    public Button setURL(String url) {
        Button.url = url;
        return this;
    }
    
    public ButtonStyle getStyle() {
        return style;
    }
    
    public String getLabel() {
        return label;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getId() {
        return id;
    }
    
    public boolean isDisabled() {
        return disabled;
    }
}
